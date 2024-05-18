package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import javax.swing.JOptionPane;

import modelo.Cliente;
import modelo.Cola;
import modelo.Pedido;
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
		try {
			SistemaMonitor.getInstancia().conectar("localhost", 1);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Servidor no disponible", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
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
		//if (arg instanceof List)
			//ventana.printeaLista(arg);
		if (arg instanceof Cola) {
			ventana.printeaLista((Cola) arg);
		} else if (arg instanceof Pedido) {
			Pedido datos = (Pedido) arg;
			ventana.printLblBox((int) datos.getBox() );
			ventana.printlblSiguiente((String) datos.getDNISig());
		} 
		
		
		
	}
	
	

}
