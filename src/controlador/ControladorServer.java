package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vista.Ivista;
import vista.VentanaServer;

public class ControladorServer implements ActionListener {

	private Ivista vista;

	public ControladorServer() {
		this.vista = new VentanaServer();
		this.vista.setActionListener(this);
		this.vista.mostrar();
		//crear servidor!! servidor.getinstance.abrir() o algo asi xd
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		System.out.println(comando);
	}
	

}
