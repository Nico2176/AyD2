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

import javax.swing.JOptionPane;

import modelo.Cliente;

public class SistemaClientes implements Runnable {
	private static SistemaClientes instancia;
	private Socket socket;
	private ObjectOutputStream flujoSalida;
    private ObjectInputStream flujoEntrada;
	private ArrayList<Cliente> pendientes = new ArrayList<Cliente>();
	private Thread hilo;
	private String pre ="[CLIENTE]";
	
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
            this.hilo = new Thread(this);
    		hilo.start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public void enviarDatos(String DNI) throws Exception{
			System.out.println("Enviando datos al servidor");
			this.flujoSalida.writeObject(new Cliente(DNI));
			System.out.println("Datos enviados al servidor");	
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
				if (object instanceof Integer) { 
					int x = (int) object;
					if (x==404) {
						JOptionPane.showMessageDialog(null, "El DNI ya está registrado en el sistema");
					} else if (x==505) {
						JOptionPane.showMessageDialog(null, "Registro exitoso!");
					}
				} 
				
				
				
			} catch (ClassNotFoundException | IOException e) {
				System.out.println(pre+"Excepcion recibiendo la queue "+ e.toString());
			}
    		
		}
		
	}
}
