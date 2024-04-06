package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import modelo.Cliente;
import modelo.Datos;
import negocio.SistemaClientes;
import vista.Ivista;
import vista.VentanaMonitor;
import vista.VentanaRegistro;
import negocio.SistemaMonitor;

public class ControladorMonitor implements ActionListener, Observer {
	
	private Ivista vista;

	public ControladorMonitor() {
		this.vista = new VentanaMonitor();
		this.vista.setActionListener(this);
		this.vista.mostrar();
		SistemaMonitor.getInstancia().addObserver(this);
		SistemaMonitor.getInstancia().conectar("localhost", 1);
		SistemaMonitor.getInstancia().iniciarHilo();
		try {
			
		} catch (Exception e) {
			System.out.println("Excepcion conectando el monitor al servidor "+ e.toString());
		}  
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Observable o, Object arg) {
		VentanaMonitor ventana = (VentanaMonitor) this.vista;
		if (arg instanceof List)
			ventana.printeaLista(arg);
		else if (arg instanceof Datos) {
			Datos datos = (Datos) arg;
			ventana.printLblBox((int) datos.getBox() );
			ventana.printlblSiguiente((String) datos.getDNISig());
		} 
		
	}
	
	

}
