package negocio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import modelo.Cliente;

public class MonitorDisponibilidad implements Runnable {

	private Socket socketPrimario;
	private Socket socketSecundario;
	private ObjectOutputStream flujoSalida;
	private ObjectInputStream flujoEntrada;
	private ObjectOutputStream flujoSalidaSecundario;
	private ObjectInputStream flujoEntradaSecundario;
	private String pre="[MONITOR]";
	private Queue<Cliente> clientes = new LinkedList<>();
	
	
	public ObjectOutputStream getFlujoSalidaSecundario() {
		return flujoSalidaSecundario;
	}

	public MonitorDisponibilidad() {
		try {
			this.conectar("localhost", 1);
			Thread hilo = new Thread(this);
			hilo.run();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error! Servidor no disponible" + e.getMessage());
		}
		
	}

	private void conectar(String host, int puerto) throws Exception{ 
        try {
            this.socketPrimario = new Socket(host, puerto); 
            this.flujoSalida = new ObjectOutputStream(socketPrimario.getOutputStream());
            this.flujoEntrada = new ObjectInputStream(socketPrimario.getInputStream());
            this.flujoSalida.writeObject(123);
            this.socketSecundario = new Socket(host, 2); 
            this.flujoSalidaSecundario = new ObjectOutputStream(socketSecundario.getOutputStream());
            this.flujoEntradaSecundario = new ObjectInputStream(socketSecundario.getInputStream());
            this.flujoSalidaSecundario.writeObject(123);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

	@Override
	public void run() {
		final int tiempoEspera = 5000;
		System.out.println(pre+"Ejecutando hilo de escucha de disponibildiad");
		ObjectInputStream flujo;
		
		try {
			
			Timer timer = new Timer();
			HeartBeat tarea = new HeartBeat();
			timer.schedule(tarea, tiempoEspera);   //tarea que dice qwue si pasan tiempoEspera segundos, no se recibio nada, tonces esta caido
			while (true) {
				try {
					System.out.println(pre+"Escuchando........");
					flujo = new ObjectInputStream(this.socketPrimario.getInputStream());
					Object object =   flujo.readObject();
					if (object instanceof String) {
						String cadena = (String) object;
						if (cadena.equals("PUM PUM")) {     //si lee pum pum entonces recibio heartbeat con exito y reinicio el timer
							System.out.println(pre+"Recibió el objeto "+ cadena + " \t Reiniciando temporizador");
							tarea.cancel();
							tarea = new HeartBeat();
							timer.schedule(tarea, tiempoEspera);       //tarea que dice qwue si pasan tiempoEspera segundos, no se recibio nada, tonces esta caido
						} else 
							System.out.println("recibí basura");
					} else if (object instanceof List){
						this.clientes= (Queue<Cliente>) object;
						System.out.println(pre+"Actualicé la lista de clientes en el monitor de disponibilidad "+ this.clientes.toString());
					}
					
					
					
					
					
				} catch (ClassNotFoundException | IOException e) {
					//System.out.println(pre+"Excepcion recibiendo datos "+ e.toString());
					return;
				}
	    		
		 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  ////?????????????????? no tocar, por algun motivo no funciona con el input original y tuve que crear este xD
		
			
			
		
		
	}
	
	class HeartBeat extends TimerTask {
        public HeartBeat() {

        }

        @Override
        public void run() {
                System.err.println(pre + "Tiempo de espera excedido. No se recibió el objeto a tiempo.");
                try {
                	System.out.println("Enviando señal de activación al servidor secundario");
					flujoSalidaSecundario.writeObject(17); //codigo de falla de server
				} catch (IOException e) {
					e.printStackTrace();
				}

        }
    }	

}
