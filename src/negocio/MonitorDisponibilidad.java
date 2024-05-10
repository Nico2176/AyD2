package negocio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import modelo.Cliente;

public class MonitorDisponibilidad implements Runnable {

	private Socket socketPrimario;
	private Socket socketSecundario;
	private ServerSocket socketServidor;
	private ObjectOutputStream flujoSalida;
	private ObjectInputStream flujoEntrada;
	private ObjectOutputStream flujoSalidaSecundario;
	private ObjectInputStream flujoEntradaSecundario;
	private String pre="[MONITOR]";
	private Queue<Cliente> clientes = new LinkedList<>();
	private boolean principalActivo = true;
	
	
	public ObjectOutputStream getFlujoSalidaSecundario() {
		return flujoSalidaSecundario;
	}

	
	
	public MonitorDisponibilidad() {
		try {
			this.conectar("localhost", 1);
			Thread hilo = new Thread(this);
			hilo.run();
			this.escucharConexiones();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error! Servidor no disponible" + e.getMessage());
		}
		
	}

	private void conectar(String host, int puerto) throws Exception{ 
        try {
            this.socketPrimario = new Socket(host, puerto); 
            this.flujoSalida = new ObjectOutputStream(socketPrimario.getOutputStream());
            this.flujoEntrada = new ObjectInputStream(socketPrimario.getInputStream());
            this.flujoSalida.writeObject(123);
            this.socketSecundario = new Socket(host, 2); 
            this.flujoSalidaSecundario = new ObjectOutputStream(socketSecundario.getOutputStream());
            this.flujoEntradaSecundario = new ObjectInputStream(socketSecundario.getInputStream());
            this.flujoSalidaSecundario.writeObject(123);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

	@Override
	public void run() {
		final int tiempoEspera = 5000;
		System.out.println(pre+"Ejecutando hilo de escucha de disponibildiad");
		ObjectInputStream flujo;
		
		try {
			
			Timer timer = new Timer();
			HeartBeat tarea = new HeartBeat();
			timer.schedule(tarea, tiempoEspera);   //tarea que dice qwue si pasan tiempoEspera segundos, no se recibio nada, tonces esta caido
			while (true) {
				try {
					System.out.println(pre+"Escuchando........");
					flujo = new ObjectInputStream(this.socketPrimario.getInputStream());
					Object object =   flujo.readObject();
					if (object instanceof String) {
						String cadena = (String) object;
						if (cadena.equals("PUM PUM")) {     //si lee pum pum entonces recibio heartbeat con exito y reinicio el timer
							System.out.println(pre+"Recibió el objeto "+ cadena + " \t Reiniciando temporizador");
							tarea.cancel();
							tarea = new HeartBeat();
							timer.schedule(tarea, tiempoEspera);       //tarea que dice qwue si pasan tiempoEspera segundos, no se recibio nada, tonces esta caido
						} else 
							System.out.println("recibí basura");
					} else if (object instanceof List){
						this.clientes= (Queue<Cliente>) object;
						System.out.println(pre+"Actualicé la lista de clientes en el monitor de disponibilidad "+ this.clientes.toString());
					}
					
					
					
					
					
				} catch (ClassNotFoundException | IOException e) {
					System.out.println(e.getMessage());
					return;
				}
	    		
		 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  ////?????????????????? no tocar, por algun motivo no funciona con el input original y tuve que crear este xD
		
	}
	
	
	public void escucharConexiones() {
		try {
			this.socketServidor = new ServerSocket(777);
			Thread escucha = new Thread (new EscuchaConexionServer(socketServidor));
			escucha.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void entroConexion() {
		try {
			Thread.sleep(1000);
		 	this.socketPrimario = new Socket("localhost", 1);
			 this.flujoSalida = new ObjectOutputStream(socketPrimario.getOutputStream());
		     this.flujoEntrada = new ObjectInputStream(socketPrimario.getInputStream());
		     this.flujoSalida.writeObject(123);
		     Thread hilo = new Thread(this);
				hilo.start();
		     
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
       
		
	}
	
	class EscuchaConexionServer implements Runnable{
		private ServerSocket socketServidor;
		private Socket socket;

		public EscuchaConexionServer(ServerSocket socketServidor) {
			super();
			this.socketServidor = socketServidor;
		}


		@Override
		public void run() {
			while (true) {
				try {
					socket = socketServidor.accept();
					System.out.println(pre+"Ha entrado una conexion al servidor");  //cada vez que entra una conexión representa que el servidor primario se volvió a abrir, so envío señal de cambiar de server
					flujoSalidaSecundario.writeObject(777);
					MonitorDisponibilidad.this.entroConexion(); //reactivo el heartbeat
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		
		
		
	}
	
	class escuchaDatosServer implements Runnable{

		@Override
		public void run() {
			while (true) {
				try {
						System.out.println(pre+"Escuchando a ver si cambia el servidor!!!");
						ObjectInputStream flujo = new ObjectInputStream(MonitorDisponibilidad.this.socketPrimario.getInputStream());  ////?????????????????? no tocar, por algun motivo no funciona con el input original y tuve que crear este xD
						Object object =   flujo.readObject();
						System.out.println(pre+"Recibió el objeto "+ object.toString());
						if (object instanceof Integer) {
							int x = (int) object;
							if (x==777) {
								if (MonitorDisponibilidad.this.principalActivo) {
									System.out.println("Abriendo server primario por primera vez");
								} else {
									flujoSalidaSecundario.writeObject(777); //codigo de que volvió el primario
									System.out.println("El servidor principal volvió a activarse luego de una caída");
								}
								

							}
						}
										
					}	catch (Exception e) {
						System.out.println(pre+"Error escuchando objetos");
					}
	}
		}
		
	}
	
	class HeartBeat extends TimerTask {
        public HeartBeat() {

        }

        @Override
        public void run() {
                System.err.println(pre + "Tiempo de espera excedido. No se recibió el objeto a tiempo.");
                try {
                	System.out.println("Enviando señal de activación al servidor secundario");
					flujoSalidaSecundario.writeObject(17); //codigo de falla de server
					MonitorDisponibilidad.this.principalActivo=false; //seteo que el server principal no está activo
				} catch (IOException e) {
					e.printStackTrace();
				}

        }
    }	

}
