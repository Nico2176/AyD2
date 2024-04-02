package negocio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;



public class Servidor implements Runnable{
	
	public static Servidor instancia;
	private Socket socket;
	private ServerSocket socketServer;
	
	public static Servidor getInstancia() {
        if (instancia == null) {
            instancia = new Servidor();
        }
        return instancia;
    }
	
	public void abrirServer() {
		Thread hilo = new Thread(this);
        hilo.start();
	}
	
	@Override
    public void run() {
            try {
            	int puerto = 1; //hardcodeado por ahora
				this.socketServer = new ServerSocket(puerto);
				System.out.println("Servidor iniciado. Puerto: " + puerto);
				while (true) {
					socket = socketServer.accept();
				
	        		Thread escucha = new Thread(new Escuchar(socket));   //porque cada socket debe tener un hilo propio escuchando e/s de datos
	        		                                                     //, ademas del hilo original del server que escucha conexiones entrantes
	        		
	        		//solo puede haber un solo flujo de entrada y salida para cada socket así que puse los ObjectInput/OutputStream
					//en el hilo de la clase Escuchar que envía y recibe datos
	        		
	                escucha.start();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
    }
	
	
	private class Escuchar implements Runnable { //seria el hilo de cada socket. puse la clase aca para q esté mas a mano
        private Socket socket;

        public Escuchar(Socket socket) {
        	System.out.println("Creando clase escuchadora");
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
            	System.out.println("Clase escuchadora creada xd");
            	ObjectInputStream flujoEntrada = new ObjectInputStream(socket.getInputStream());
        	    ObjectOutputStream flujoSalida = new ObjectOutputStream(socket.getOutputStream());
            	while (true) {	
            		System.out.println("Escuchando...........");
                    Object object = flujoEntrada.readObject();
                    if (object instanceof String) { 
                    	String cadena = (String) object;
                    	if (cadena.equalsIgnoreCase("Finalizar")) { //Finalizó un turno un empleado
                    		
                    		
                    	} else if (cadena.equalsIgnoreCase("Siguiente")) { //Un empleado llama al siguiente en la queue
                    		
                    		
                    		
                    	} else { //es un DNI (x descarte)
                    		System.out.println("El servidor recibió el DNI "+ cadena);
                    		//ver donde guardar la colección con DNIS para la cola, no sé si es correcto hacerlo en el mismo servidor o habría que hacer una clase aparte
                    		
                    	}
                    	
                    }
            	}
            } catch (Exception e) {
            	System.out.println("Excepcion "+ e.getMessage());
            	
            }
        }
	}
}

