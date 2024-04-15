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
	private ObjectOutputStream flujoSalida;
	private ObjectInputStream flujoEntrada;
    private Thread hilo;
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
			this.flujoSalida.writeObject(new Datos(this.clientes,this.Box,true,clienteActual)); //queue, box que pidió al siguiente, y true para identificar que se está pidiendo a alguien y no es un nuevo ingreso en la Queue
			comienzo = new java.util.Date(); //para comenzar a contar los segundos transcurridos 
			java.util.Date finDesocupado = new java.util.Date();
			this.segundosDesocupado=(int)((finDesocupado.getTime() - this.comienzoDesocupado.getTime()) / 1000);
			//this.flujoSalida.writeObject("Siguiente");
		} catch (IOException e) {
			System.out.println(pre+"Excepcion cuando hace siguiente "+ e.toString());
		}
	}
	

	
	
	public int finalizaTurno() {
		java.util.Date fin = new java.util.Date();
		try {
			DatosEstadisticos datos = new DatosEstadisticos();
			datos.setSegundosDesocupado(this.segundosDesocupado);
			datos.setSegundosAtendiendo(((int)((fin.getTime() - this.comienzo.getTime()) / 1000)));
			this.flujoSalida.writeObject(datos);
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
            this.flujoSalida.writeObject(21);
            
        } 






	@Override
	public void run() {
		System.out.println(pre+"Ejecutando hilo de empleado");
		while (true) {
			try {
				System.out.println(pre+"Escuchando........");
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
				
				
				
			} catch (ClassNotFoundException | IOException e) {
				System.out.println(pre+"Excepcion recibiendo la queue "+ e.toString());
			}
    		
		}
		
	}

}
