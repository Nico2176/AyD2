package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ConnectException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.Random;

import javax.swing.JOptionPane;

import modelo.Cliente;
import negocio.SistemaEmpleados;
import vista.Ivista;
import vista.VentanaPersonal;
import vista.VentanaServer;

public class ControladorPersonal implements ActionListener, Observer{
	private Ivista vista;

	public ControladorPersonal() {
		this.vista = new VentanaPersonal();
		this.vista.setActionListener(this);
		this.vista.mostrar();
		SistemaEmpleados.getInstancia().addObserver(this);
		this.conectarServer();	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		System.out.println(comando);
		if (comando.equalsIgnoreCase("Siguiente")) { //bien
			try {
				SistemaEmpleados.getInstancia().siguiente();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}

			
		} else if (comando.equalsIgnoreCase(("FinalizarTurno"))){ //bien
			VentanaPersonal ventana = (VentanaPersonal) this.vista;
			System.out.println("Cliente actual: "+ SistemaEmpleados.getInstancia().getClienteActual());
			if (SistemaEmpleados.getInstancia().getClienteActual().equalsIgnoreCase("")) 
				JOptionPane.showMessageDialog(null, "Debes estar atendiendo a alguien para finalizar su turno", "Error", JOptionPane.ERROR_MESSAGE);
			else {
				
				ventana.actualizaSiguiente("");
				//SistemaEmpleados.getInstancia().baja(); //para que disminuya en 1 la cantidad de coenctados del servidor 
				SistemaEmpleados.getInstancia().setClienteActual("");  
			}
		}
		
	}
	
	
	private void conectarServer() {
		SistemaEmpleados.getInstancia().conectar("localhost", 1); //puerto del server hardcodeado en 1
		SistemaEmpleados.getInstancia().crearHilo();
    }
	
	@Override
	public void update(Observable o, Object arg) {
		VentanaPersonal ventana = (VentanaPersonal) this.vista;
		if (arg instanceof List) {
			ventana.printeaLista(arg);
			ventana.actualizaSiguiente(SistemaEmpleados.getInstancia().getClienteActual());
		} else if (arg instanceof Integer) {
			ventana.setBox((int) arg);
		}
		/* Datos datos = (Datos) arg;
		ventana.printeaLista(datos.getClientes());
		System.out.println("El controlador recibio el objeto "+ arg +" mediente patrón observer");
		if (datos.isActualizaSig()) {
			ventana.actualizaSiguiente(datos.getClientes().poll().getDNI());
		} */
		
	}
	
}
