package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ConnectException;
import java.util.Random;

import negocio.SistemaEmpleados;
import vista.Ivista;
import vista.VentanaPersonal;
import vista.VentanaServer;

public class ControladorPersonal implements ActionListener{
	private Ivista vista;

	public ControladorPersonal() {
		this.vista = new VentanaPersonal();
		this.vista.setActionListener(this);
		this.vista.mostrar();
		this.conectarServer();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		System.out.println(comando);
		if (comando.equalsIgnoreCase("Siguiente")) { //bien

		} else if (comando.equalsIgnoreCase(("FinalizarTurno"))){ //bien

		}
		
	}
	
	private void conectarServer() {
		SistemaEmpleados.getInstancia().conectar("localhost", 1); //puerto del server hardcodeado en 1
		SistemaEmpleados.getInstancia().crearHilo();
		
}
}
