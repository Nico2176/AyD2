package negocio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modelo.Cliente;

public class SistemaClientes implements Runnable{
	private static SistemaClientes instancia;
	private Socket socket;
	private Socket socketSecundario;
	private boolean principalActivo=true;
	private ObjectOutputStream flujoSalida;
    private ObjectInputStream flujoEntrada;
    private ObjectOutputStream flujoSalidaSecundario;
    private ObjectInputStream flujoEntradaSecundario;
    private String pre="[CLIENTE]";
	private ArrayList<Cliente> pendientes = new ArrayList<Cliente>();
	private Thread hilo;
	
	public static SistemaClientes getInstancia() {
		if (instancia == null)
			instancia = new SistemaClientes();
		return instancia;
	}
	
	
	public void addCliente(Cliente cliente) {
		this.pendientes.add(cliente);
	}
	
	public boolean validarCadenaNumerica(String cadena) {
        // Patrón para verificar si la cadena es una secuencia de 8 números
        Pattern patron = Pattern.compile("^\\d{8}$");
        Matcher matcher = patron.matcher(cadena);
        return matcher.matches(); //true si cadena son 8 números
    }
	
	
	
	public void conectar(String host, int puerto) throws Exception{ 
		this.socket = new Socket(host, puerto); 
		this.flujoSalida = new ObjectOutputStream(socket.getOutputStream());
        this.flujoEntrada = new ObjectInputStream(socket.getInputStream());
        System.out.println(pre+"Cliente conectado con el servidor, puerto del socket: "+ this.socket.getLocalPort());
        try {   
            this.socketSecundario = new Socket("localhost",2);
            this.flujoSalidaSecundario = new ObjectOutputStream(socketSecundario.getOutputStream());
            this.flujoEntradaSecundario = new ObjectInputStream(socketSecundario.getInputStream());
            this.hilo = new Thread (this);
            hilo.start();
        } catch (IOException e) {
            System.out.println("No hay servidor secundario");
        }
        
        
    }
	
	public void enviarDatos(String DNI) throws Exception{
		try {
			System.out.println(pre+"Enviando datos al servidor principal");
			this.flujoSalida.writeObject(new Cliente(DNI));
			System.out.println(pre+"Datos enviados al servidor principal");	
		} catch (Exception e) {
			System.out.println(pre+"Error enviando datos el server principal. Está caído.");
		}
		
		try {
			System.out.println(pre+"Enviando datos al servidor secundario");
			this.flujoSalidaSecundario.writeObject(new Cliente(DNI));
			System.out.println(pre+"Datos enviados al servidor secundario");	
		} catch (Exception e) {
			System.out.println(pre+"Error enviando datos al server secundario. Está caído.");
		}
		
	/*	if (this.principalActivo) {
			System.out.println(pre+"Enviando datos al servidor principal");
			this.flujoSalida.writeObject(new Cliente(DNI));
			System.out.println(pre+"Datos enviados al servidor principal");
		} else {
			System.out.println(pre+"Enviando datos al servidor secundario");
			this.flujoSalidaSecundario.writeObject(new Cliente(DNI));
			System.out.println(pre+"Datos enviados al servidor secundario");	
		}	*/
	}


	@Override
	public void run() {
		while (true) {
			try {
					System.out.println(pre+"Escuchando a ver si cambia el servidor!!!");
					ObjectInputStream flujo = new ObjectInputStream(this.socketSecundario.getInputStream());  ////?????????????????? no tocar, por algun motivo no funciona con el input original y tuve que crear este xD
					Object object =   flujo.readObject();
					System.out.println(pre+"Recibió el objeto "+ object.toString());
					if (object instanceof Integer) {
						int x = (int) object;
						if (x==17) {
							System.out.println("Cambiando a servidor secundario!!");
							this.socket.close();
							this.principalActivo=false;
						} else if (x==777) {
							Thread.sleep(100);  //un pequeño delay porque sino no le da tiempo a abrirse al servidor primario
							System.out.println("Volvió el primario, cambiando nuevamente");
							this.socket = new Socket("localhost", 1); 
				            System.out.println(pre+"Cliente reconectado con el servidor principal "+ this.socket.getLocalPort());
				            this.flujoSalida = new ObjectOutputStream(socket.getOutputStream());
				            this.flujoEntrada = new ObjectInputStream(socket.getInputStream());
							this.principalActivo=true;
							
						}
						//return;
					}
									
				}	catch (Exception e) {
					System.out.println(e.getMessage());
				}
}
}
}
