package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import negocio.Estadisticas;
import negocio.Servidor;
import vista.Ivista;
import vista.VentanaEstadisticas;
import vista.VentanaServer;

public class ControladorServer implements ActionListener, Observer {

	private Ivista vista;
	private VentanaEstadisticas ventanaEstadisticas;

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
			ventanaServer.getBtnNewButton_1().setEnabled(true);
			ventanaServer.serverON();
		} else if (comando.equalsIgnoreCase("Estadisticas")) {
			if (this.ventanaEstadisticas==null) {
				this.ventanaEstadisticas= new VentanaEstadisticas();
				this.ventanaEstadisticas.setVisible(true);
				this.ventanaEstadisticas.actualizarEstadisticas(Servidor.getInstancia().getEstadisticas().getClientesAtendidos(), Servidor.getInstancia().getEstadisticas().getSegundosAtendiendo(),Servidor.getInstancia().getEstadisticas().getSegundosDesocupado(), Servidor.getInstancia().getEstadisticas().getSegundosTotales(), Servidor.getInstancia().getEstadisticas().getPromedioTiempoAtencion(), Servidor.getInstancia().getEstadisticas().getPromedioxHora()); //XD
			} else {
				this.ventanaEstadisticas.setVisible(true);
				this.ventanaEstadisticas.requestFocus();
				this.ventanaEstadisticas.actualizarEstadisticas(Servidor.getInstancia().getEstadisticas().getClientesAtendidos(), Servidor.getInstancia().getEstadisticas().getSegundosAtendiendo(),Servidor.getInstancia().getEstadisticas().getSegundosDesocupado(), Servidor.getInstancia().getEstadisticas().getSegundosTotales(), Servidor.getInstancia().getEstadisticas().getPromedioTiempoAtencion(), Servidor.getInstancia().getEstadisticas().getPromedioxHora()); //XD

			}
			//JOptionPane.showMessageDialog(null, Servidor.getInstancia().getEstadisticas().toString());
		}
	}


	@Override
	public void update(Observable o, Object arg) {
		VentanaServer ventana = (VentanaServer) this.vista;
		if (arg instanceof Estadisticas) {
			Estadisticas estadisticas = (Estadisticas) arg;
			this.ventanaEstadisticas.actualizarEstadisticas(estadisticas.getClientesAtendidos(), estadisticas.getSegundosAtendiendo(), estadisticas.getSegundosDesocupado(), estadisticas.getSegundosTotales(), estadisticas.getPromedioTiempoAtencion(), estadisticas.getPromedioxHora());
		} else {
			String cad = (String) arg;
			System.out.println("Al observer servidor le llegó "+ cad);
			if (cad.equalsIgnoreCase("Alta")) 
				ventana.alta();
		    else if (cad.equalsIgnoreCase("Baja")) 
				ventana.baja();
		}
	}
	
	
	

}
