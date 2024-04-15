package negocio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modelo.Cliente;

public class SistemaClientes {
	private static SistemaClientes instancia;
	private Socket socket;
	private ObjectOutputStream flujoSalida;
    private ObjectInputStream flujoEntrada;
	private ArrayList<Cliente> pendientes = new ArrayList<Cliente>();
	
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
        try {
            this.socket = new Socket(host, puerto); 
            System.out.println("Cliente conectado con el servidor, puerto del socket: "+ this.socket.getLocalPort());
            this.flujoSalida = new ObjectOutputStream(socket.getOutputStream());
            this.flujoEntrada = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public void enviarDatos(String DNI) throws Exception{
			System.out.println("Enviando datos al servidor");
			this.flujoSalida.writeObject(new Cliente(DNI));
			System.out.println("Datos enviados al servidor");

		
		
	}

}
