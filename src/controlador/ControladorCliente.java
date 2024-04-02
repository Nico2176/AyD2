package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import negocio.SistemaCliente;
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
			if (SistemaCliente.getInstancia().validarCadenaNumerica(ventanaR.getTextField().getText())) { //devuelve true si el DNI es una cadena de 8 digitos
				ventanaR.getTextField().setText("");
				JOptionPane.showMessageDialog(null, "Registro exitoso");
				//enviar al servidor aqui
				//this.vista.cerrar();        //creo que no conviene cerrar pues el kiosco lo usarán muchos clientes
			} else { //bien
				JOptionPane.showMessageDialog(null, "DNI inválido, vuelva a ingresar");
			}
		}
	}
}
