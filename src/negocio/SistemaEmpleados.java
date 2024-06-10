package negocio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controlador.ControladorCliente;
import controlador.ControladorPersonal;
import modelo.Cliente;
import modelo.Cola;
import modelo.Pedido;
import negocio.State.StatePrincipalActivo;
import negocio.State.StateSecundarioActivo;
import negocio.State.StateServer;
import modelo.EstadisticaEmpleado;




public class SistemaEmpleados extends Observable implements Runnable {
	private static SistemaEmpleados instancia;
	private Socket socket;
	private Socket socketSecundario;
	private ObjectOutputStream flujoSalida;
	private ObjectInputStream flujoEntrada;
	private ObjectOutputStream flujoSalidaSecundario;
	private ObjectInputStream flujoEntradaSecundario;
	private boolean principalActivo = true;
	private boolean secundarioActivo = false;
	private Cola cola;
    private Thread hilo;
    private Thread hiloEscuchaSecundario;
    private ArrayList<Cliente> clientes;
    private String clienteAnterior=null;
    private String clienteActual="";
    private int Box;
    private String pre="[EMPLEADO]";
    private int segundosDesocupado=0;
    private Date comienzo;
    private Date comienzoDesocupado = new java.util.Date();
    private StateServer estadoServidor;

    
	
	public int getBox() {
		return Box;
	}

	public void setBox(int box) {
		Box = box;
	}

	public void setClienteActual(String clienteActual) {
		this.clienteActual = clienteActual;
	}

	public String getClienteActual() {
		return clienteActual;
	}

	public static SistemaEmpleados getInstancia() {
		if (instancia == null)
			instancia = new SistemaEmpleados();
		return instancia;
	}
	
	public void crearHilo() { 
		//System.out.println("Socket secundario isconnected: "+ socketSecundario);
		hilo = new Thread(this);
		hilo.start();	
		if (this.socketSecundario!=null) {
			System.out.println("El socket secundario SÍ está coenctado. creando hilo");	
			hiloEscuchaSecundario = new Thread(new EscuchaCambioServer());
			hiloEscuchaSecundario.start();
		} else {
			System.out.println("El socket scundario NO está conectado");
		}
		
		
	}

	public void ausente(){
		this.clienteActual="";	
	}
	
	public void rellamar() throws Exception {
		
		if (this.principalActivo) {
			if (this.clienteActual!="") {
				this.flujoSalida.writeObject(new Pedido(this.clientes,this.Box,true,clienteActual)); //queue, box que pidió al siguiente, y true para identificar que se está pidiendo a alguien y no es un nuevo ingreso en la Queue
				this.flujoSalida.flush();	
				if (this.secundarioActivo) {
					this.flujoSalidaSecundario.writeObject(new Pedido(this.clientes,this.Box,true,clienteActual)); //queue, box que pidió al siguiente, y true para identificar que se está pidiendo a alguien y no es un nuevo ingreso en la Queue
					this.flujoSalidaSecundario.flush();
				}
			} else throw new Exception ("No hay cliente actual");
		} else if (this.secundarioActivo) {
			this.flujoSalidaSecundario.writeObject(new Pedido(this.clientes,this.Box,true,clienteActual)); //queue, box que pidió al siguiente, y true para identificar que se está pidiendo a alguien y no es un nuevo ingreso en la Queue
			this.flujoSalidaSecundario.flush();
		}
	}
	
	public void siguiente() throws Exception{
		if (this.clienteActual!=null && !this.clienteActual.equalsIgnoreCase("")) 
			throw new Exception (pre+"Primero debes finalizar tu turno actual");
		
			
		try {
			clienteAnterior=clienteActual;
			clienteActual = this.cola.getLista().remove(0).getDNI();
			//clienteActual = this.clientes.poll().getDNI();	
		} catch (Exception e) {
			throw new Exception(pre+"No hay clientes en la Queue");
			
		}

			

		try {
			System.out.println(pre+"DNI de cliente actual que estamos atendiendo: "+ clienteActual);
			if (this.principalActivo) {
				System.out.println("Notificando servidor principal para siguiente.");
				this.flujoSalida.writeObject(new Pedido(this.clientes,this.Box,true,clienteActual)); //queue, box que pidió al siguiente, y true para identificar que se está pidiendo a alguien y no es un nuevo ingreso en la Queue
				this.flujoSalida.flush();
				System.out.println("Notificado");
				if (this.secundarioActivo) {
					this.flujoSalidaSecundario.writeObject(new Pedido(this.clientes,this.Box,true,clienteActual)); 
					this.flujoSalidaSecundario.flush();
				}
			} else {
				if (this.secundarioActivo) {
					this.flujoSalidaSecundario.writeObject(new Pedido(this.clientes,this.Box,true,clienteActual));
				}
			}
			comienzo = new java.util.Date(); //para comenzar a contar los segundos transcurridos 
			java.util.Date finDesocupado = new java.util.Date();
			this.segundosDesocupado=(int)((finDesocupado.getTime() - this.comienzoDesocupado.getTime()) / 1000);
			//this.flujoSalida.writeObject("Siguiente");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(pre+"Excepcion cuando hace siguiente "+ e.toString());
		}
	}
	

	
	
	public int finalizaTurno() {
		java.util.Date fin = new java.util.Date();
		try {
			EstadisticaEmpleado datos = new EstadisticaEmpleado();
			datos.setSegundosDesocupado(this.segundosDesocupado);
			datos.setSegundosAtendiendo(((int)((fin.getTime() - this.comienzo.getTime()) / 1000)));
			System.out.println("CLIENTE Q FINALICE DE ATENDER: "+ this.clienteActual);
			if (this.principalActivo) {
				this.flujoSalida.writeObject(this.Box+this.clienteActual);
				this.flujoSalida.writeObject(datos);
			} else {
				this.flujoSalidaSecundario.writeObject(this.Box+this.clienteActual);
				this.flujoSalidaSecundario.writeObject(datos);
			}
			this.comienzoDesocupado= new java.util.Date();
		} catch (IOException e) {
			System.out.println(pre+"Error enviando las estadisticas");
		}
		return (int)((fin.getTime() - this.comienzo.getTime()) / 1000);
	}
	
	public void conectar(String host, int puerto) throws Exception { 
            this.socket = new Socket(host, puerto); 
           
            System.out.println(pre+"Conectado con el servidor, puerto del socket: "+ this.socket.getLocalPort());
            this.flujoSalida = new ObjectOutputStream(socket.getOutputStream());
            this.flujoEntrada = new ObjectInputStream(socket.getInputStream());    
            this.estadoServidor = new StatePrincipalActivo();
            this.flujoSalida.writeObject(21);
            
            try {
            	this.socketSecundario = new Socket(host,2);
	            this.flujoSalidaSecundario = new ObjectOutputStream(socketSecundario.getOutputStream());
	            this.flujoEntradaSecundario = new ObjectInputStream(socketSecundario.getInputStream());
	            this.flujoSalidaSecundario.writeObject(21);
	            this.secundarioActivo=true;
            } catch (Exception e) {
            	System.out.println("No hay servidor secundario");
            }
            this.crearHilo();
        } 


	public void reconecto(String host, int puerto) throws Exception{
		this.socket = new Socket(host, puerto); 
		System.out.println(pre+"Conectado con el servidor, puerto del socket: "+ this.socket.getLocalPort());
		this.flujoSalida = new ObjectOutputStream(socket.getOutputStream());
        this.flujoEntrada = new ObjectInputStream(socket.getInputStream());
        this.flujoSalida.writeObject(21);
        this.crearHilo();
	}



	@Override
	public synchronized void run() {
		System.out.println(pre+"Ejecutando hilo de empleado");
			while(true) {
				try {
						this.estadoServidor.manejarEvento(this);
				} catch (Exception e) {
					e.printStackTrace();
					//System.out.println(pre+"Catcheamos excepcion recibiendo datos "+ e.toString());
				}
			}
	}
	
	public void notificarObserver(Object object) {
		this.setChanged();
		if (object instanceof Integer) 
			this.notifyObservers((int) object);	
		else if (object instanceof Cola)
			this.notifyObservers((Cola) object);
		
		
	}
	

	
	public class EscuchaCambioServer implements Runnable { //un hilo simplemente para q escuche cuando se cambia el servidor

		@Override
		public synchronized void run() {
			try {
				
				while(true) {
					System.out.println(pre+"Escuchando a ver si cambió el server");
					ObjectInputStream flujo = new ObjectInputStream(SistemaEmpleados.this.socketSecundario.getInputStream());
					Object object =   flujo.readObject();
					if (object instanceof Integer) {
						int x = (int) object;
						if (x==17) {
							System.out.println(pre+"Se detectó una falla en el servidor principal, cambiando al secundario");
							SistemaEmpleados.this.principalActivo=false;
							SistemaEmpleados.this.estadoServidor = new StateSecundarioActivo();
							break;
							//return;
						} /* else if (x==777) {
							Thread.sleep(1000);  //un pequeño delay porque sino no le da tiempo a abrirse al servidor primario
							System.out.println(pre+"Volvió el primario, cambiando nuevamente");
							SistemaEmpleados.this.socket.close();
							SistemaEmpleados.this.socketSecundario.close();
							SistemaEmpleados.this.conectar("localhost", 1);
							SistemaEmpleados.this.principalActivo=true;

							
						/*	SistemaEmpleados.this.principalActivo=true;
							SistemaEmpleados.this.socket = new Socket("localhost", 1);
							SistemaEmpleados.this.flujoEntrada = new ObjectInputStream(socket.getInputStream());
							SistemaEmpleados.this.flujoSalida = new ObjectOutputStream(socket.getOutputStream());
							SistemaEmpleados.this.hilo.interrupt();
							SistemaEmpleados.this.hilo = new Thread (SistemaEmpleados.this);
							SistemaEmpleados.this.hilo.start();		// /*
						} */
					}
				}
				return; //porque cuando cambia al servidor secundario debe dejar de leer en este hilo ya que entra en conflicto con la información que le llega del propio programa
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
	}

	

	public void setPrincipalActivo(boolean principalActivo) {
		this.principalActivo = principalActivo;
	}

	public void setEstadoServidor(StateServer estadoServidor) {
		this.estadoServidor = estadoServidor;
	}

	public void setCola(Cola cola) {
		this.cola = cola;
	}

	public Socket getSocket() {
		return socket;
	}

	public Socket getSocketSecundario() {
		return socketSecundario;
	}

	public ObjectOutputStream getFlujoSalida() {
		return flujoSalida;
	}

	public ObjectInputStream getFlujoEntrada() {
		return flujoEntrada;
	}

	public ObjectOutputStream getFlujoSalidaSecundario() {
		return flujoSalidaSecundario;
	}

	public ObjectInputStream getFlujoEntradaSecundario() {
		return flujoEntradaSecundario;
	}

	public boolean isPrincipalActivo() {
		return principalActivo;
	}

	public boolean isSecundarioActivo() {
		return secundarioActivo;
	}

	public Cola getCola() {
		return cola;
	}

	public Thread getHilo() {
		return hilo;
	}

	public Thread getHiloEscuchaSecundario() {
		return hiloEscuchaSecundario;
	}

	public ArrayList<Cliente> getClientes() {
		return clientes;
	}

	public String getPre() {
		return pre;
	}

	public int getSegundosDesocupado() {
		return segundosDesocupado;
	}

	public Date getComienzo() {
		return comienzo;
	}

	public Date getComienzoDesocupado() {
		return comienzoDesocupado;
	}

	public StateServer getEstadoServidor() {
		return estadoServidor;
	}
	
	

}
