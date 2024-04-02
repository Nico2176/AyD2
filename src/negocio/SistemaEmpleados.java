package negocio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controlador.ControladorCliente;
import modelo.Cliente;




public class SistemaEmpleados {
	private static SistemaEmpleados instancia;
	private Socket socket;
	private ObjectOutputStream flujoSalida;
    private ObjectInputStream flujoEntrada;

	
	public static SistemaEmpleados getInstancia() {
		if (instancia == null)
			instancia = new SistemaEmpleados();
		return instancia;
	}
	
	
	

	
	
	public void conectar(String host, int puerto) { 
        try {
            this.socket = new Socket(host, puerto); 
            System.out.println("Empleado conectado con el servidor, puerto del socket: "+ this.socket.getLocalPort());
            this.flujoSalida = new ObjectOutputStream(socket.getOutputStream());
            this.flujoEntrada = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
