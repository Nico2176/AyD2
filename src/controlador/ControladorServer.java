package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import negocio.EstadisticaServidor;
import negocio.Servidor;
import negocio.Verificador;
import vista.Ivista;
import vista.VentanaEstadisticas;
import vista.VentanaServer;

public class ControladorServer implements ActionListener, Observer {

	private Ivista vista;
	private VentanaEstadisticas ventanaEstadisticas;
	private Servidor servidor;
	private int rol;
	private Verificador verificador = new Verificador();

	public ControladorServer() {
		this.vista = new VentanaServer();
		this.vista.setActionListener(this);
		this.vista.mostrar();
		this.servidor=new Servidor();
		this.servidor.addObserver(this);
		
		this.rol=this.verificador.verificaServidores();; //para indicar si el servidor es primario o secundario
		System.out.println("El verificador devolvió crear servidor "+ this.rol);
		VentanaServer ventanaServer = (VentanaServer) this.vista;
		
		String aux=null;;
		if (this.rol==1)
			aux="Servidor";
		else if  (this.rol==2)
			aux="Servidor secundario";
		else if (this.rol==0)
			System.exit(0);
		ventanaServer.setTitle(aux);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		System.out.println(comando);
		if (comando.equalsIgnoreCase("Iniciar Server")) {
			this.servidor.abrirServer(this.rol);
			VentanaServer ventanaServer = (VentanaServer) this.vista;
			ventanaServer.getBtnNewButton_1().setEnabled(true);
			ventanaServer.serverON();
		} else if (comando.equalsIgnoreCase("Estadisticas")) {
			if (this.ventanaEstadisticas==null) {
				this.ventanaEstadisticas= new VentanaEstadisticas();
				this.ventanaEstadisticas.setVisible(true);
				this.ventanaEstadisticas.actualizarEstadisticas(this.servidor.getEstadisticas().getClientesAtendidos(), this.servidor.getEstadisticas().getSegundosAtendiendo(),this.servidor.getEstadisticas().getSegundosDesocupado(), this.servidor.getEstadisticas().getSegundosTotales(), this.servidor.getEstadisticas().getPromedioTiempoAtencion(), this.servidor.getEstadisticas().getPromedioxHora()); //XD
			} else {
				this.ventanaEstadisticas.setVisible(true);
				this.ventanaEstadisticas.requestFocus();
				this.ventanaEstadisticas.actualizarEstadisticas(this.servidor.getEstadisticas().getClientesAtendidos(), this.servidor.getEstadisticas().getSegundosAtendiendo(),this.servidor.getEstadisticas().getSegundosDesocupado(), this.servidor.getEstadisticas().getSegundosTotales(), this.servidor.getEstadisticas().getPromedioTiempoAtencion(), this.servidor.getEstadisticas().getPromedioxHora()); //XD

			}
			//JOptionPane.showMessageDialog(null, this.servidor.getEstadisticas().toString());
		}
	}


	@Override
	public void update(Observable o, Object arg) {
		VentanaServer ventana = (VentanaServer) this.vista;
		if (arg instanceof EstadisticaServidor) {
			if (this.ventanaEstadisticas!=null) {
				EstadisticaServidor estadisticas = (EstadisticaServidor) arg;
				this.ventanaEstadisticas.actualizarEstadisticas(estadisticas.getClientesAtendidos(), estadisticas.getSegundosAtendiendo(), estadisticas.getSegundosDesocupado(), estadisticas.getSegundosTotales(), estadisticas.getPromedioTiempoAtencion(), estadisticas.getPromedioxHora());
			}
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
