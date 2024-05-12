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
import modelo.Datos;
import modelo.DatosEstadisticos;




public class SistemaEmpleados extends Observable implements Runnable {
	private static SistemaEmpleados instancia;
	private Socket socket;
	private Socket socketSecundario;
	private ObjectOutputStream flujoSalida;
	private ObjectInputStream flujoEntrada;
	private ObjectOutputStream flujoSalidaSecundario;
	private ObjectInputStream flujoEntradaSecundario;
	private boolean principalActivo = true;
    private Thread hilo;
    private Thread hiloEscuchaSecundario;
    private Queue<Cliente> clientes;
    private String clienteActual="";
    private int Box;
    private String pre="[EMPLEADO]";
    private int segundosDesocupado=0;
    private Date comienzo;
    private Date comienzoDesocupado = new java.util.Date();

    
	
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
		hilo = new Thread(this);
		hilo.start();
		hiloEscuchaSecundario = new Thread(new EscuchaCambioServer());
		hiloEscuchaSecundario.start();
		
		
	}

	
	public void siguiente() throws Exception{
		if (this.clienteActual!=null && !this.clienteActual.equalsIgnoreCase("")) 
			throw new Exception (pre+"Primero debes finalizar tu turno actual");
		
			
		try {
			clienteActual = this.clientes.poll().getDNI();	
		} catch (Exception e) {
			throw new Exception(pre+"No hay clientes en la Queue");
			
		}

			

		try {
			System.out.println(pre+"DNI de cliente actual que estamos atendiendo: "+ clienteActual);
			if (this.principalActivo) {
				System.out.println("Notificando servidor principal para siguiente.");
				this.flujoSalida.writeObject(new Datos(this.clientes,this.Box,true,clienteActual)); //queue, box que pidió al siguiente, y true para identificar que se está pidiendo a alguien y no es un nuevo ingreso en la Queue
				this.flujoSalida.flush();
				System.out.println("Notificado");
				this.flujoSalidaSecundario.writeObject(new Datos(this.clientes,this.Box,true,clienteActual)); 
				this.flujoSalidaSecundario.flush();
			} else {
				this.flujoSalidaSecundario.writeObject(new Datos(this.clientes,this.Box,true,clienteActual));
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
			DatosEstadisticos datos = new DatosEstadisticos();
			datos.setSegundosDesocupado(this.segundosDesocupado);
			datos.setSegundosAtendiendo(((int)((fin.getTime() - this.comienzo.getTime()) / 1000)));
			if (this.principalActivo) {
				this.flujoSalida.writeObject(datos);
			} else {
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
            this.socketSecundario = new Socket(host,2);
            System.out.println(pre+"Conectado con el servidor, puerto del socket: "+ this.socket.getLocalPort());
            this.flujoSalida = new ObjectOutputStream(socket.getOutputStream());
            this.flujoEntrada = new ObjectInputStream(socket.getInputStream());
            this.flujoSalida.writeObject(21);
            this.flujoSalidaSecundario = new ObjectOutputStream(socketSecundario.getOutputStream());
            this.flujoEntradaSecundario = new ObjectInputStream(socketSecundario.getInputStream());
            this.flujoSalidaSecundario.writeObject(21);
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
	public void run() {
		System.out.println(pre+"Ejecutando hilo de empleado");
	//	while (SistemaEmpleados.this.flag) {
		while (true) {
			try {
				if (this.principalActivo) {
					System.out.println(pre+"Escuchando al servidor principal........");
					//Thread.sleep(300);
					ObjectInputStream flujo = new ObjectInputStream(this.socket.getInputStream());  ////?????????????????? no tocar, por algun motivo no funciona con el input original y tuve que crear este xD
					Object object =   flujo.readObject();
					
					System.out.println(pre+"Recibió el objeto "+ object.toString());
					if (object instanceof List) { //recibió la queue de clientes actualizada
						Queue<Cliente> clientes = (Queue<Cliente>) object;
						this.clientes= clientes;
						this.setChanged();
						notifyObservers(clientes);
						//ControladorPersonal.getInstancia().printeaLista(clientes);
						//actualizar ventana del empleado con la queue
					} else if (object instanceof Integer) {
						this.setChanged();
						int box = (int) object;
						notifyObservers(box);
					}
				} else { //si el principal no ta activo, recibe datos del secundario xd
					System.out.println(pre+"Escuchando al servidor secundario........");
					//ObjectInputStream flujoS = flujoEntradaOrdenTres;
					ObjectInputStream flujoS = new ObjectInputStream(this.socketSecundario.getInputStream());  ////?????????????????? no tocar, por algun motivo no funciona con el input original y tuve que crear este xD
					Object object =   flujoS.readObject();
					System.out.println(pre+"Recibió el objeto del sv secundario"+ object.toString());
					//flujoS=new ObjectInputStream(this.socketSecundario.getInputStream());  //esta linea la voy a dejar comentada porque estuve horas buscando un error y era esta linea aca nonsense, la odio ahre
					if (object instanceof List) { //recibió la queue de clientes actualizada
						Queue<Cliente> clientes = (Queue<Cliente>) object;
						this.clientes= clientes;
						this.setChanged();
						notifyObservers(clientes);
						//ControladorPersonal.getInstancia().printeaLista(clientes);
						//actualizar ventana del empleado con la queue
					} else if (object instanceof Integer) {
						int x = (int) object;
						if (x!=777) {
							this.setChanged();
							int box = (int) object;
							notifyObservers(box);
						} else if (x==777) {
							System.out.println(pre+"Volvió el primario, cambiando nuevamente");
							SistemaEmpleados.this.socket.close();
							//SistemaEmpleados.this.socketSecundario.close();
							Thread.sleep(100);  //un pequeño delay porque sino no le da tiempo a abrirse al servidor primario
							SistemaEmpleados.this.reconecto("localhost", 1);
				            this.principalActivo=true;
				           // System.out.println("Interrumpiendo hilo de escucha");
				            Thread.currentThread().interrupt();
							return;
						}
						
					}
				
					
					
				}	
			} catch (Exception e) {
				//e.printStackTrace();
				System.out.println(pre+"Catcheamos excepcion recibiendo datos "+ e.toString());
			}
    		//return;
		} 
		
	}
	

	
	public class EscuchaCambioServer implements Runnable { //un hilo simplemente para q escuche cuando se cambia el servidor

		@Override
		public void run() {
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

}
