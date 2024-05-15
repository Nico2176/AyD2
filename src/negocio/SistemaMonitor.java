package negocio;

import java.awt.Toolkit;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Observable;
import java.util.Queue;

import modelo.Cliente;
import modelo.Pedido;

public class SistemaMonitor extends Observable implements Runnable {
	private Socket socket;
	private Socket socketSecundario;
	private ObjectOutputStream flujoSalida;
	private ObjectInputStream flujoEntrada;
	private ObjectOutputStream flujoSalidaSecundario;
	private ObjectInputStream flujoEntradaSecundario;
	private Queue<Cliente> clientes;
	private static SistemaMonitor instancia;
	private Thread hilo;
	private Thread hiloCambioServidor;
	private boolean principalActivo = true;
	private String pre = "[PANTALLA]";
	
	public static SistemaMonitor getInstancia() {
		if (instancia == null)
			instancia = new SistemaMonitor();
		return instancia;
	}
	
	public void identificaMonitor() {
		try {
			int x=2176;
			this.flujoSalida.writeObject(x); //codigo identificador del monitor (en el servidor, el socket que reciba este código será el del monitor)
			this.flujoSalida.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void conectar(String host, int puerto) throws Exception { 
            this.socket = new Socket(host, puerto); 
            System.out.println(pre+"Empleado conectado con el servidor, puerto del socket: "+ this.socket.getLocalPort());
            this.flujoSalida = new ObjectOutputStream(socket.getOutputStream());
            this.flujoEntrada = new ObjectInputStream(socket.getInputStream());
            try {
            	this.socketSecundario = new Socket(host,2);
	            this.flujoSalidaSecundario = new ObjectOutputStream(socketSecundario.getOutputStream());
	            this.flujoEntradaSecundario = new ObjectInputStream(socketSecundario.getInputStream());
            } catch (Exception e) {
            	System.out.println("No hay servidor secundario");
            }
            this.identificaMonitor(); //envio el codigo de identificacion de monitor al servidor para que le de el trato que le corresponde 
            this.iniciarHilo(); //inicia los hilos de escucha para recibir informacion y recibir cambio de servidor
    }

	public void iniciarHilo() {
		this.hilo= new Thread(this);
		this.hilo.start();
		if (this.socketSecundario!=null) {
			this.hiloCambioServidor = new Thread(new EscuchaCambioServer());
	        this.hiloCambioServidor.start();
		}
	}

	public void reconecto(String host, int puerto) throws Exception{
		this.socket = new Socket(host, puerto); 
		System.out.println(pre+"Conectado con el servidor, puerto del socket: "+ this.socket.getLocalPort());
		this.flujoSalida = new ObjectOutputStream(socket.getOutputStream());
        this.flujoEntrada = new ObjectInputStream(socket.getInputStream());
        this.flujoSalida.writeObject(2176);
        this.flujoSalida.flush();
        this.flujoSalida.flush();
        this.iniciarHilo();
	}

	@Override
	public synchronized void run() {
		System.out.println(pre+"Ejecutando hilo del monitor");
		
		while (true) {
			if (this.principalActivo){
				try {
					System.out.println(pre+"monitor escuchando del sv primario........");
					ObjectInputStream flujo = new ObjectInputStream(this.socket.getInputStream());  ////?????????????????? no tocar, por algun motivo no funciona con el input original y tuve que crear este xD
					Object object =   flujo.readObject();
					System.out.println(pre+"monitor recibió el objeto "+ object.toString());
					if (object instanceof List) { //recibió la queue de clientes actualizada
						Toolkit.getDefaultToolkit().beep();
						Queue<Cliente> clientes = (Queue<Cliente>) object;
						this.clientes= clientes;
						this.setChanged();
						notifyObservers(clientes);
					} else if (object instanceof Pedido) {
						this.setChanged();
						Pedido datos = (Pedido) object;
						notifyObservers(datos);
					}
					
					
					
				} catch (ClassNotFoundException | IOException e) {
					System.out.println(pre+"Excepcion recibiendo la queue "+ e.toString());
				}
			} else {    //principal no activo
				try {
					System.out.println(pre+"monitor escuchando del sv secundario........");
					ObjectInputStream flujoS = new ObjectInputStream(this.socketSecundario.getInputStream());  ////?????????????????? no tocar, por algun motivo no funciona con el input original y tuve que crear este xD
					Object object =   flujoS.readObject();
					System.out.println(pre+"monitor recibió el objeto "+ object.toString());
					if (object instanceof List) { //recibió la queue de clientes actualizada
						Toolkit.getDefaultToolkit().beep();
						Queue<Cliente> clientes = (Queue<Cliente>) object;
						this.clientes= clientes;
						this.setChanged();
						notifyObservers(clientes);
					} else if (object instanceof Pedido) {
						this.setChanged();
						Pedido datos = (Pedido) object;
						notifyObservers(datos);
					} else if (object instanceof Integer) {
						int x = (int) object;
						if (x==777) {
							System.out.println(pre+"Volvió el primario, cambiando nuevamente");
							SistemaMonitor.this.socket.close();
							//SistemaEmpleados.this.socketSecundario.close();
							Thread.sleep(100);  //un pequeño delay porque sino no le da tiempo a abrirse al servidor primario
							this.flujoEntrada.close();
							this.flujoSalida.close();
							SistemaMonitor.this.reconecto("localhost", 1);
				            this.principalActivo=true;
				            System.out.println(pre+"Interrumpiendo hilo de escucha");
				            Thread.currentThread().interrupt();
							return;
						}
					}
					
					
					
				} catch (Exception e) {
					System.out.println(pre+"Excepcion recibiendo la queue "+ e.toString());
				}
				
				
				
			}
    		
		}
	}
	
	
	public class EscuchaCambioServer implements Runnable { //un hilo simplemente para q escuche cuando se cambia el servidor

		@Override
		public synchronized void run() {
			try {
				while(true) {
					System.out.println(pre+"Escuchando a ver si cambió el server");
					ObjectInputStream flujo = new ObjectInputStream(SistemaMonitor.this.socketSecundario.getInputStream());
					Object object =   flujo.readObject();
					if (object instanceof Integer) {
						int x = (int) object;
						if (x==17) {
							System.out.println(pre+"Se detectó una falla en el servidor principal, cambiando al secundario");
							SistemaMonitor.this.principalActivo=false;
							break;
							//return;
						} 
					}
				}
				return; //porque cuando cambia al servidor secundario debe dejar de leer en este hilo ya que entra en conflicto con la información que le llega del sistema en sí
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
	}

}
