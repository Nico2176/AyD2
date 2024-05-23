package negocio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import modelo.Cola;
import modelo.Pedido;
import negocio.Factories.ClienteFactory;
import negocio.Factories.JSONClienteFactory;
import negocio.Factories.TXTClienteFactory;
import negocio.Factories.XMLClienteFactory;
import negocio.Strategies.EstrategiaAfinidad;
import negocio.Strategies.EstrategiaDefault;
import negocio.Strategies.EstrategiaEdad;
import negocio.Strategies.IEstrategia;
import modelo.EstadisticaEmpleado;



public class Servidor extends Observable implements Runnable{
	
	public static Servidor instancia;
	private Socket socket;
	private Socket socketDisponibilidad;
	private ServerSocket socketServer;
	private Queue<Cliente> clientes = new LinkedList<>();
	private ArrayList<Socket> sockets = new ArrayList<Socket>();
	private ArrayList<Socket> monitores = new ArrayList<Socket>();
	private static int boxes=0;
	private int rol;
	private int criterio=1;  //0 para orden de llegada, 1 para afinidad y 2 para edades. luego hacer que sea un archivo config.txt y que lo obtenga de ahi
	private ClienteFactory clienteFactory;
	private Cola cola = new Cola();
	private String pre="[SERVER]";
	private boolean activo=true;   //si no está activo solo recopila datos, pero no envía, en un principio seria el servidor secundario (redundancia activa) 
	private EstadisticaServidor estadisticas = new EstadisticaServidor();
	private IEstrategia estrategia;
	
	//ICola strategy = new StrategyAfinidad o new StrategyEtario o new StrategyDefecto
	//cada una de esas clases que heredará de la interfaz strategy tendrá su propio metodo de resolución para la queue
	//la interfaz (y por consecuente las strategies) tendrán un método "agregar(ArrayList<Cliente> cola)" donde se le enviará la queue actual que tiene el servidor y se modificará según corresponda
	
	
	
	




	public boolean isActivo() {
		return activo;
	}







	public void setActivo(boolean activo) {
		this.activo = activo;
	}







	public Socket getSocketDisponibilidad() {
		return socketDisponibilidad;
	}




	public void setSocketDisponibilidad(Socket socketDisponibilidad) {
		this.socketDisponibilidad = socketDisponibilidad;
	}




	public EstadisticaServidor getEstadisticas() {
		return estadisticas;
	}




	public ArrayList<Socket> getMonitores() {
		return monitores;
	}




	public ArrayList<Socket> getSockets() {
		return sockets;
	}




	public Queue<Cliente> getClientes() {
		return clientes;
	}

	private void criterios() {
		  String archivo = "config.txt";

	        try {
	            // Crear un lector de archivos de texto
	            BufferedReader lector = new BufferedReader(new FileReader(archivo));

	            // Leer las primeras tres líneas del archivo
	            String linea1 = lector.readLine();
	            String linea2 = lector.readLine();

	            lector.close();

	            // Verificar si las líneas son números 0, 1 o 2 y realizar acciones correspondientes
	            if (linea1 != null && linea2 != null) {
	                int criterioPrioridad = Integer.parseInt(linea1);
	                int criterioArchivos = Integer.parseInt(linea2);

	                switch (criterioPrioridad) {
	                case 0:
	                	this.estrategia= new EstrategiaDefault();
	                	System.out.println(pre+"Creando EstrategiaDefault");
	                    break;
	                case 1:
	                	this.estrategia= new EstrategiaAfinidad();
	                	System.out.println(pre+"Creando EstrategiaAfinidad");
	                    break;
	                case 2:
	             	    this.estrategia = new EstrategiaEdad();
	             	   System.out.println(pre+"Creando EstrategiaEdad");
	                    break;
	                default:
	                	this.estrategia= new EstrategiaDefault();
	                	System.out.println(pre+"Creando EstrategiaDefault");
	                    break;
	            }
	                
	                switch (criterioArchivos) {
	                case 0:
	                    this.clienteFactory = new TXTClienteFactory();
	                    System.out.println(pre+"Creando TXTFactory");
	                    break;
	                case 1:
	                    this.clienteFactory = new JSONClienteFactory();
	                    System.out.println(pre+"Creando JSONFactory");
	                    break;
	                case 2:
	                    this.clienteFactory = new XMLClienteFactory();
	                    System.out.println(pre+"Creando XMLFactory");
	                    break;
	                default:
	                	this.clienteFactory = new TXTClienteFactory();
	                    break;
	            }
	                
	                
	              
	            } else {
	                System.out.println("El archivo no tiene suficientes líneas.");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }


	public void abrirServer(int rol) {
		this.rol=rol;
		this.pre="SERVER["+ rol +"]";
		if (rol==2) {
			this.activo=false;     //si es el servidor secundario, en un principio no estará activo (pero si recibe datos)
		} else {
			try {
				Socket socket = new Socket("localhost",777);  //conecto al monitor
				ObjectOutputStream flujo = new ObjectOutputStream(socket.getOutputStream());
				flujo.writeObject(777); //el servidor primario siempre avisará al monitor cuando fue encendido (para que cuando esté el secundario activo, vuelva el primario a ser el que está en uso)
				flujo.flush();
			} catch (Exception e) {
				System.out.println("No enviamos nada al monitor de disponibilidad pues aun no existe");
			}
			
			/*if (this.socketDisponibilidad!=null) { //el servidor primario siempre avisará al monitor cuando fue encendido (para que cuando esté el secundario activo, vuelva el primario a ser el que está en uso)
				ObjectOutputStream flujo;
				try {
					flujo = new ObjectOutputStream(this.socketDisponibilidad.getOutputStream());
					flujo.writeObject(777);
				} catch (IOException e) {
					e.printStackTrace();
				}	
			} */
		}
		Thread hilo = new Thread(this);
        hilo.start();
        
        
        this.criterios();
      /* if (this.criterio==0)
    	   this.estrategia= new EstrategiaDefault();
       else if (this.criterio==1)
    	   this.estrategia= new EstrategiaAfinidad();
       else if (this.criterio==2)
    	   this.estrategia = new EstrategiaEdad(); */
       
	}
	
	public void enviarQueue() {        //este metodo envia a todos los sockets conectados la queue de clientes
		Iterator<Socket> iterador = this.sockets.iterator();
 		while (iterador.hasNext()) {
 			Socket aux = iterador.next();
			try {
				ObjectOutputStream flujo = new ObjectOutputStream(aux.getOutputStream());
				System.out.println(pre+"Enviando queue al socket de puerto "+ aux.getPort());
				flujo.writeObject(this.cola);
              //  flujo.writeObject(this.clientes);
                flujo.flush();
			} catch (IOException e) {
				System.out.println(pre+"Excepcion enviando queues: "+ e.getMessage());
			}
	}
	}
	
	@Override
    public void run() {
            try {
            	int puerto=0;
            	if (this.rol==1) {
            		puerto = 1;} //hardcodeado por ahora
            	else if (this.rol==2) {
            		puerto=2;}
            	
				this.socketServer = new ServerSocket(puerto);
				System.out.println(pre+"Servidor iniciado. Puerto: " + puerto);
				while (true) {
					socket = socketServer.accept();
					System.out.println(pre+"Ha entrado una conexion al servidor");
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
	
	private void enviarBox(Socket socket) {
		try {
			ObjectOutputStream flujo = new ObjectOutputStream(socket.getOutputStream());
			flujo.writeObject(++this.boxes);
			flujo.flush();
		} catch (IOException e) {
			System.out.println(pre+"Exception enviando el box actual "+ e.toString());
		}
	}
	
	private void notificaCambioServidor(int x) {
		Iterator<Socket> iterador = this.sockets.iterator();
 		while (iterador.hasNext()) {
 			Socket aux = iterador.next();
			try {
				ObjectOutputStream flujo = new ObjectOutputStream(aux.getOutputStream());
				System.out.println(pre+"Notificando del cambio de servidor a "+ aux.getPort());
                flujo.writeObject(x);
                flujo.flush();
			} catch (IOException e) {
				System.out.println(pre+"Excepcion enviando cambiando de servidor: "+ e.getMessage());
			}
 		}
	}
	
	
	public void enviarBoxMonitores(int box, String DNISig) { //envío a todos los monitores el box que hizo el request de siguiente
		Iterator<Socket> iterador = this.monitores.iterator();
 		while (iterador.hasNext()) {
 			Socket aux = iterador.next();
			try {
				ObjectOutputStream flujo = new ObjectOutputStream(aux.getOutputStream());
				System.out.println(pre+"Enviando queue al socket de MONITOR de puerto "+ aux.getPort());
				System.out.println(pre+"DNI que vamos a enviar al monitor: "+ DNISig);
				Pedido datos = new Pedido(box,DNISig);
                flujo.writeObject(datos);
                flujo.flush();
                System.out.println(pre+" Enviamos " + datos.toString() + " a los monitores!!!");
			} catch (IOException e) {
				System.out.println(pre+"Excepcion enviando queues: "+ e.getMessage());
			}
	}
	}
	
	private void enviarQueueMonitorDisponibilidad(Socket socket) {
		try {
			ObjectOutputStream flujo = new ObjectOutputStream(socket.getOutputStream());
			flujo.writeObject(this.cola);
			//flujo.writeObject(this.clientes);
			flujo.flush();
		} catch (IOException e) {
			System.out.println(pre+"Exception enviando el box actual "+ e.toString());
		}
	}
	
	private Cliente buscaCliente(Cliente cliente) {  //este metodo llamaria a los factory segun corresponda y retornaria el cliente
	        String filePath = "db.txt";
	        String DNIaux = "";
	        String[] partes=null;
	        System.out.println(pre+"Entramos a buscar el cliente en el archivo");
	        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	            String linea;
	            while ((linea = br.readLine()) != null && !DNIaux.equals(cliente.getDNI())) {
	            		partes = linea.split("\\|"); // Dividir la línea en partes utilizando el carácter '|'
	                    DNIaux = partes[0]; // Obtener los datos de cada parte       
	                    System.out.println(pre+"Analizando DNI "+ DNIaux);
	            } 
	        if (DNIaux.equals(cliente.getDNI())) { //si lo encontró entonces devuelve el cliente con todos sus datos
	        	cliente.setAfinidad(partes[1]);
	        	cliente.setEdad(Byte.parseByte(partes[2]));
	        	
	        	System.out.println("Cliente cargado con datos "+ cliente.getAfinidad() + cliente.getEdad());
	        } 
	        System.out.println(pre+"Linea: "+ linea);
            return cliente; //retorna cliente, si lo encontró entonces tendrá los datos que le corresponden cargados en el objeto. si no lo encontró, es decir que es uno nuevo, solo tendrá el DNI
  
	        } catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}
	


	
	private class Escuchar implements Runnable { //seria el hilo de cada socket. puse la clase aca para q esté mas a mano y ademas pueda acceder facilmente a los atributos de la clase contenedora
        private Socket socket;

        public Escuchar(Socket socket) {
        	System.out.println(pre+"Creando clase escuchadora");
            this.socket = socket;
        }
        
        

        @Override
        public void run() {
            try {
            	System.out.println(pre+"Clase escuchadora creada xd");
            	ObjectInputStream flujoEntrada = new ObjectInputStream(socket.getInputStream());
        	    ObjectOutputStream flujoSalida = new ObjectOutputStream(socket.getOutputStream());
        	    if (Servidor.this.isActivo()) {
        	    	Servidor.this.enviarQueue(); 
        	    }       //cuando uno nuevo se conecta, envio las queues nuevamente para que si ya habia empleados, se le carguen los datos también.
        	  //  Servidor.this.enviarBox(socket);    //envio el numero de box que le corresponde al empleado que acaba de conectarse
            	while (true) {	
            		System.out.println(pre+"Escuchando...........");
                    Object object = flujoEntrada.readObject();
                    System.out.println(pre+"Recibi el objeto "+ object.toString());
                    if (object instanceof Pedido) {
                    	System.out.println("El servidor recibió un objeto tipo Datos");
                    	Pedido datos = (Pedido) object;
                    	if (datos.isSiguiente()) { // un empleado pidió para siguiente.  implica enviar a los monitores quién fue
                    		System.out.println(pre+"El server recibió DNI "+ datos.getDNISig() +" en una request para siguiente ");
                    		Servidor.this.cola.removeAtendido(Servidor.this.buscaCliente(Servidor.this.buscaCliente(new Cliente(datos.getDNISig())))); //remueve de las listas con las que trabaja
                    		//Servidor.this.getClientes().poll();
                    		if (Servidor.this.rol==2) {
                    			Servidor.this.enviarQueueMonitorDisponibilidad(Servidor.this.socketDisponibilidad);
                    		}
                    		if (Servidor.this.isActivo()) {
	                    		Servidor.this.enviarQueue();  
	                    		Servidor.this.enviarBoxMonitores(datos.getBox(),datos.getDNISig());
                    		}
                    	} else {     //Si entra una clase Pediodo sin siguiente es que la recibió del monitor de disponibilidad para ser actualziada luego de una caida y resubida             
                    		System.out.println("El servidor ha recibido la queue "+ datos.getClientes().toString() + " Del monitor de disponibilidad");
                    		Servidor.this.cola.setLista(datos.getClientes());
                    		//Servidor.this.clientes= datos.getClientes();
                    		Servidor.this.enviarQueue();  
                    	}
                    	
                    } /*else if (object instanceof Cliente) {        //es un registro de cliente
                    	Cliente cliente = (Cliente) object;
                    	System.out.println(pre+"El servidor recibió el DNI "+ cliente.getDNI());
                		Servidor.this.getClientes().add(cliente); //agrego al cliente a una coleccion de clientes
                		Servidor.this.estrategia.agregar(cola, Servidor.this.buscaCliente(cliente));    
                		System.out.println(pre+"Cliente: "+ cliente);
                		if (Servidor.this.isActivo()) {
                			Servidor.this.enviarQueue(); //enviar la queue actualziada a todos los empleados
                		}
                		if (Servidor.this.rol==2) {
                			Servidor.this.enviarQueueMonitorDisponibilidad(Servidor.this.socketDisponibilidad);
                		}
                    } */else if (object instanceof Integer) {     //identificador de monitores
                    	int x = (int) object;
                    	if (x==2176) { //codigo que dan los monitores para identificarse
                    		System.out.println(pre+"Se agrego un nuevo monitor al sistema");
                    		Servidor.this.getMonitores().add(socket);
                    	} else if (x==21) {
                    		System.out.println(pre+"Se agregó un nuevo empleado al sistema");
                    		if (Servidor.this.isActivo()) {
                    			Servidor.this.enviarBox(socket);    //envio el numero de box que le corresponde al empleado que acaba de conectarse
                    		}
                    	} else if (x==123) {
                    		System.out.println("Se agregó un monitor de disponibilidad al servidor");
                    		Servidor.this.setSocketDisponibilidad(socket);
                    		//System.out.println(pre+"Output stream para el socket del monitor disponibilidad: "+ socket.getOutputStream());
                    		if (Servidor.this.isActivo()) {
	                    		Thread hiloHB = new Thread(new EnviaHeartBeat(socket));
	                    		hiloHB.start();
                    		}
                    	} else if (x==17) {  //por convención quien recibirá estos parámetros es siempre el servidor secundario (enviados por el monitor de disponibilidad)
                    		Servidor.this.setActivo(true);
                    		Servidor.this.notificaCambioServidor(17); //se cayo el primero, cambio al secundario
                    	} else if (x==777) { //por convención quien recibirá estos parámetros es siempre el servidor secundario (enviados por el monitor de disponibilidad)
                    		Servidor.this.setActivo(false);
                    		Servidor.this.notificaCambioServidor(777); //volvió el primario
                    	}
                    		
                    	
                    } else if (object instanceof EstadisticaEmpleado) {
                    	EstadisticaEmpleado datos = (EstadisticaEmpleado) object;
                    	Servidor.this.getEstadisticas().clienteAtendido(datos.getSegundosAtendiendo(), datos.getSegundosDesocupado());
                    	System.out.println(datos.toString());
                    	Servidor.this.setChanged();
                    	Servidor.this.notifyObservers(Servidor.this.getEstadisticas());
                    } else if (object instanceof Cola) {
                    	Servidor.this.cola = (Cola) object;
                    	Servidor.this.enviarQueue(); 
                    } else if (object instanceof String) {
                    	String DNI = (String) object;
                    	if (DNI.length()==8) {
                    		//
                        	System.out.println(pre+"El servidor recibió el DNI "+ DNI);
                        	Cliente cliente = Servidor.this.clienteFactory.crearCliente(DNI);
                    		Servidor.this.getClientes().add(cliente); //agrego al cliente a una coleccion de clientes
                    		Servidor.this.estrategia.agregar(cola, Servidor.this.buscaCliente(cliente));    
                    		System.out.println(pre+"Cliente: "+ cliente);
                    		if (Servidor.this.isActivo()) {
                    			Servidor.this.enviarQueue(); //enviar la queue actualziada a todos los empleados
                    		}
                    		if (Servidor.this.rol==2) {
                    			Servidor.this.enviarQueueMonitorDisponibilidad(Servidor.this.socketDisponibilidad);
                    		}
                    		//
                    	}
                    }
                    
                   /* if (object instanceof String) { 
                    	String cadena = (String) object;
                    	if (cadena.equalsIgnoreCase("Finalizar")) { //Finalizó un turno un empleado
                    		
                    		
                    	} else if (cadena.equalsIgnoreCase("Siguiente")) { //Un empleado llama al siguiente en la queue
                    		//Servidor.this.getClientes().poll();
                    		Servidor.this.enviarQueue();                  		
                    	}  else{//es un DNI (x descarte)
                    		System.out.println("El servidor recibió el DNI "+ cadena);
                    		Servidor.this.getClientes().add(new Cliente(cadena)); //agrego al cliente a una coleccion de clientes
                    		Servidor.this.enviarQueue(); //enviar la queue actualziada a todos los empleados
                    		
                    		 
                    	 			
                    	 			
                    	 	} 
                    		
                    		
                    		//ver donde guardar la colección con DNIS para la cola, no sé si es correcto hacerlo en el mismo servidor o habría que hacer una clase aparte
                    		//provisoriamente voy a dejar la coleccion en esta clase
                    		
                    	} else if(object instanceof Integer){
                    		
                    		
                    		
                    		
                    		
                    		
                    	}else if (object instanceof Datos) {
                    		Servidor.this.getClientes().poll();
                    		Servidor.this.enviarQueue();
                    	} */
                    	
                    }
            	
            } catch (Exception e) {
            	e.printStackTrace();
            	System.out.println(pre+"Excepcion recibiendo datos "+ e.getMessage());
            	
            }
        }
	}
	
	
	private class EnviaHeartBeat implements Runnable{
		private Socket socketMonitor;
		
		public EnviaHeartBeat(Socket socketMonitor) {
			this.socketMonitor = socketMonitor;
		}

		@Override
		public void run() {
			ObjectOutputStream flujo;
			try {
				
				while(true) {
					try {
						flujo = new ObjectOutputStream(socketMonitor.getOutputStream());
						//System.out.println(pre+"PUM PUM");
						flujo.writeObject("PUM PUM"); //es el heartbeat, ahre 
						flujo.flush();
						Thread.sleep(2000);
					} catch (IOException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			
		}
		
		
	}
	
}

