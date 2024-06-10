package negocio.Factories;

import modelo.Cliente;
import modelo.Interaccion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;


import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
public class XMLClienteFactory extends ClienteFactory {
	private String pre ="[XMLFactory]";
	
	
	 
	@Override
    public void agregaInteraccion(Interaccion interaccion) {
        try {
            // Crear un documento XML nuevo o cargar uno existente
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc;

            File archivo = new File("log.xml");

            if (!archivo.exists()) {
                // Crear un nuevo documento XML
                doc = docBuilder.newDocument();
                // Crear el elemento raíz
                Element rootElement = doc.createElement("interacciones");
                doc.appendChild(rootElement);
            } else {
                // Cargar el documento XML existente
                doc = docBuilder.parse(archivo);
            }

            // Crear un elemento para la nueva interacción
            Element interaccionElement = doc.createElement("interaccion");

            // Agregar elementos para los atributos de la interacción
            Element dniElement = doc.createElement("DNI");
            dniElement.appendChild(doc.createTextNode(interaccion.getDNI()));
            interaccionElement.appendChild(dniElement);

            Element tipoElement = doc.createElement("tipo");
            tipoElement.appendChild(doc.createTextNode(interaccion.getInteraccion().toString()));
            interaccionElement.appendChild(tipoElement);

            // Agregar la nueva interacción al documento
            doc.getDocumentElement().appendChild(interaccionElement);

            // Escribir el documento XML de vuelta al archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(archivo);
            transformer.transform(source, result);

            System.out.println("Interacción agregada al archivo interacciones.xml.");
        } catch (ParserConfigurationException | SAXException | IOException | javax.xml.transform.TransformerException e) {
            e.printStackTrace();
        }
    }


     

	@Override
	    public Cliente crearCliente(String dni) {
		 
		 try {
			    System.out.println(pre+"Entrando al XMLFactory");
	            File archivoXML = new File("db.xml");
	            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	            Document doc = dBuilder.parse(archivoXML);
	            doc.getDocumentElement().normalize();
	            NodeList listaClientes = doc.getElementsByTagName("cliente");
	            for (int temp = 0; temp < listaClientes.getLength(); temp++) {
	                Node nodoCliente = listaClientes.item(temp);
	                if (nodoCliente.getNodeType() == Node.ELEMENT_NODE) {
	                    Element elementoCliente = (Element) nodoCliente;
	                    String dniCliente = elementoCliente.getAttribute("dni");
	                    if (dniCliente.equals(dni)) {
	                        byte edad = Byte.parseByte(elementoCliente.getElementsByTagName("edad").item(0).getTextContent());
	                        String afinidad = elementoCliente.getElementsByTagName("afinidad").item(0).getTextContent();
	                        Cliente cliente = new Cliente(dni, afinidad, edad);
	                        return cliente;
	                    }
	                }
	            }
	            return new Cliente(dni);
	        } catch (Exception e) {
	        	 return null;
	        }
	       
		
	    
	 	}
	 
}
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		/*// Crear una instancia de XStream
		    System.out.println(pre+"Entrando al XMLFactory");
	        XStream xstream = new XStream();

	        // Alias para la clase para que se vea más limpio en el XML
	        xstream.alias("Cliente", Cliente.class);

	        // Leer el archivo XML
	        File file = new File("db.xml");
	        List<Cliente> clientes = (List<Cliente>) xstream.fromXML(file);
	        
	        
	        System.out.println(pre+"Lista de clientes totales convertida del XML: "+ clientes.toString());
	        
	        int i=0;
	        
	        while (i<clientes.size() && !clientes.get(i).getDNI().equals(dni))
	        	i++;
	        
		 return i<clientes.size()?clientes.get(i):new Cliente(dni);
	    }
} */

