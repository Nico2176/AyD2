package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import negocio.Servidor;
import vista.Ivista;
import vista.VentanaServer;

public class ControladorServer implements ActionListener, Observer {

	private Ivista vista;

	public ControladorServer() {
		this.vista = new VentanaServer();
		this.vista.setActionListener(this);
		this.vista.mostrar();
		Servidor.getInstancia().addObserver(this);
		
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		System.out.println(comando);
		if (comando.equalsIgnoreCase("Iniciar Server")) {
			Servidor.getInstancia().abrirServer();
			VentanaServer ventanaServer = (VentanaServer) this.vista;
			ventanaServer.serverON();
		}
	}


	@Override
	public void update(Observable o, Object arg) {
		VentanaServer ventana = (VentanaServer) this.vista;
		String cad = (String) arg;
		System.out.println("Al observer servidor le llegó "+ cad);
		if (cad.equalsIgnoreCase("Alta")) 
			ventana.alta();
	    else if (cad.equalsIgnoreCase("Baja")) 
			ventana.baja();
		
	}
	
	
	

}
