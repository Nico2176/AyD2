package negocio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

import modelo.Cliente;



public class Servidor extends Observable implements Runnable{
	
	public static Servidor instancia;
	private Socket socket;
	private ServerSocket socketServer;
	private Queue<Cliente> clientes = new LinkedList<>();
	private ArrayList<Socket> sockets = new ArrayList<Socket>();
	
	public static Servidor getInstancia() {
        if (instancia == null) {
            instancia = new Servidor();
        }
        return instancia;
    }
	
	
	
	
	public ArrayList<Socket> getSockets() {
		return sockets;
	}




	public Queue<Cliente> getClientes() {
		return clientes;
	}



	public void abrirServer() {
		Thread hilo = new Thread(this);
        hilo.start();
	}
	
	public void enviarQueue() {        //este metodo envia a todos los sockets conectados la queue de clientes
		Iterator<Socket> iterador = this.sockets.iterator();
 		while (iterador.hasNext()) {
 			Socket aux = iterador.next();
			try {
				ObjectOutputStream flujo = new ObjectOutputStream(aux.getOutputStream());
				System.out.println("Enviando queue al socket de puerto "+ aux.getPort());
                flujo.writeObject(this.clientes);
                flujo.flush();
			} catch (IOException e) {
				System.out.println("Excepcion enviando queues: "+ e.getMessage());
			}
	}
	}
	
	@Override
    public void run() {
            try {
            	int puerto = 1; //hardcodeado por ahora
				this.socketServer = new ServerSocket(puerto);
				System.out.println("Servidor iniciado. Puerto: " + puerto);
				while (true) {
					socket = socketServer.accept();
					System.out.println("Ha entrado una conexion al servidor");
					this.setChanged();
					this.notifyObservers("Alta");
					this.sockets.add(socket);
				
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
                    		Servidor.getInstancia().getClientes().poll();
                    		Servidor.getInstancia().enviarQueue();                  		
                    	} else { //es un DNI (x descarte)
                    		System.out.println("El servidor recibió el DNI "+ cadena);
                    		Servidor.getInstancia().getClientes().add(new Cliente(cadena)); //agrego al cliente a una coleccion de clientes
                    		Servidor.getInstancia().enviarQueue(); //enviar la queue actualziada a todos los empleados
                    		
                    		 
                    	 			
                    	 			
                    	 	} 
                    		
                    		
                    		//ver donde guardar la colección con DNIS para la cola, no sé si es correcto hacerlo en el mismo servidor o habría que hacer una clase aparte
                    		//provisoriamente voy a dejar la coleccion en esta clase
                    		
                    	}
                    	
                    }
            	
            } catch (Exception e) {
            	System.out.println("Excepcion "+ e.getMessage());
            	
            }
        }
	}
}

