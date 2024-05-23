package negocio.Factories;

import java.io.BufferedReader;
import java.io.FileReader;

import modelo.Cliente;

public class TXTClienteFactory extends ClienteFactory{
		private String pre="[FACTORY_TXT]";
		
		@Override
		public Cliente crearCliente(String dni) {
			String filePath = "db.txt";
	        String DNIaux = "";
	        String[] partes=null;
	        System.out.println(pre+"Entramos a buscar el cliente en el archivo");
	        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	            String linea;
	            while ((linea = br.readLine()) != null && !DNIaux.equals(dni)) {
	            		partes = linea.split("\\|"); // Dividir la línea en partes utilizando el carácter '|'
	                    DNIaux = partes[0]; // Obtener los datos de cada parte       
	                    System.out.println(pre+"Analizando DNI "+ DNIaux);
	            } 
	        if (DNIaux.equals(dni)) { //si lo encontró entonces devuelve el cliente con todos sus datos
	        	Cliente cliente = new Cliente(dni);
	        	cliente.setAfinidad(partes[1]);
	        	cliente.setEdad(Byte.parseByte(partes[2]));
	        	System.out.println(pre+"Cliente cargado con datos "+ cliente.getAfinidad() + cliente.getEdad());
	        	return cliente;  	
	        } else 
	        	return new Cliente(dni);
	        

	        } catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
			
		}
    }


