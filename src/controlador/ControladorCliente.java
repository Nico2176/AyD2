package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

import negocio.SistemaEmpleados;
import negocio.SistemaClientes;
import vista.Ivista;
import vista.VentanaRegistro;

public class ControladorCliente implements ActionListener {
	
	private Ivista vista;

	public ControladorCliente() {
		this.vista = new VentanaRegistro();
		this.vista.setActionListener(this);
		this.vista.mostrar();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		System.out.println(comando);
		if (comando.equalsIgnoreCase("Registrarse")) { //bien
			VentanaRegistro ventanaR = (VentanaRegistro) this.vista;
			String DNI =ventanaR.getTextField().getText();
			if (SistemaClientes.getInstancia().validarCadenaNumerica(DNI)) { //devuelve true si el DNI es una cadena de 8 numeros
			
				
				
				try {
					SistemaClientes.getInstancia().conectar("localhost", 1);  
					SistemaClientes.getInstancia().enviarDatos(DNI);
					ventanaR.getTextField().setText("");
					JOptionPane.showMessageDialog(null, "Registro exitoso");
					//puerto del servidor hardcodeado en 1 ver si poner esta linea afuera 
					//del if ya que se conecta de nuevo cada vez que alguien se registra
				} catch (Exception e1) {
					 System.out.println(e1.getMessage());
					 JOptionPane.showMessageDialog(null, "Ha ocurrido un error.", "Error", JOptionPane.ERROR_MESSAGE);
				} 
				
				
				//this.vista.cerrar();        //creo que no conviene cerrar la ventana pues el kiosco lo usarán muchos clientes
			} else { //bien
				JOptionPane.showMessageDialog(null, "DNI inválido, vuelva a ingresar");
			}
		}
	}
}
