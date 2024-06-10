package negocio.Factories;

import modelo.Cliente;
import modelo.Interaccion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JSONClienteFactory extends ClienteFactory {

	private String pre ="[JSONFactory]";
	
	
	  @Override
	    public void agregaInteraccion(Interaccion interaccion) {
	        Gson gson = new GsonBuilder().setPrettyPrinting().create();

	        try {
	            File archivo = new File("log.json");

	            // Crear el archivo si no existe
	            if (!archivo.exists()) {
	                archivo.createNewFile();
	            }

	            // Convertir la interacción a JSON
	            String jsonInteraccion = gson.toJson(interaccion);

	            // Escribir la interacción en el archivo JSON
	            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
	                writer.write(jsonInteraccion);
	                writer.newLine(); // Agregar nueva línea para cada interacción
	            }

	            System.out.println("Interacción agregada al archivo interacciones.json.");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
	
	
	@Override
    public Cliente crearCliente(String dni) {
		System.out.println(pre+"Entrando al factory JSON");
		 try {
	            Gson gson = new Gson();
	            // Crear un lector de JSON
	            JsonReader reader = new JsonReader(new FileReader("db.json"));

	            // Convertir el JSON a una lista de clientes
	            List<Cliente> clientes = gson.fromJson(reader, new TypeToken<List<Cliente>>(){}.getType());
	            
	            System.out.println(pre+"Lista de clientes totales convertida del JSON: "+ clientes.toString());
	            
	            int i=0;
	            while (i<clientes.size() && !clientes.get(i).getDNI().equals(dni))
		        	i++;

	            return i<clientes.size()?clientes.get(i):new Cliente(dni);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 return null;
    }
}
