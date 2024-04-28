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
	
	
	
	public void conectar(String host, int puerto) throws Exception{ //falta validar cuando alguno está caído desde un principio y esas cosiñas
        try {
            this.socket = new Socket(host, puerto); 
            this.socketSecundario = new Socket("localhost",2);
            System.out.println(pre+"Cliente conectado con el servidor, puerto del socket: "+ this.socket.getLocalPort());
            this.flujoSalida = new ObjectOutputStream(socket.getOutputStream());
            this.flujoEntrada = new ObjectInputStream(socket.getInputStream());
            this.flujoSalidaSecundario = new ObjectOutputStream(socketSecundario.getOutputStream());
            this.flujoEntradaSecundario = new ObjectInputStream(socketSecundario.getInputStream());
            this.hilo = new Thread (this);
            hilo.start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public void enviarDatos(String DNI) throws Exception{
		System.out.println(pre+"Enviando registro a AMBOS servidores");
		boolean flag=false;
		try {         //trata de enviar a los dos servidores
			this.flujoSalida.writeObject(new Cliente(DNI));
			flag=true; //para el caso que el secundario está caido pero el primario no, muy chancha esta flag ahre 
			this.flujoSalidaSecundario.writeObject(new Cliente(DNI));
		} catch (Exception e) {     //si falla, trata de enviar solo al segundo
			try {
				this.flujoSalidaSecundario.writeObject(new Cliente(DNI));
			} catch (Exception e1) {   //y si falla es que ambos están caídos xd 
				System.out.println(pre+"Entrando al catch ese ");
				if (flag) {
					System.out.println(pre+"Se enviaron datos solo al sv principal");
				} else {
					throw new Exception();
				}
				
			}
			
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
							this.principalActivo=false;
							return;
						}
					}
									
				}	catch (Exception e) {
					
				}
}
}
}
