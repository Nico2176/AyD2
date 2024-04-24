package negocio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import modelo.Cliente;

public class MonitorDisponibilidad implements Runnable {

	private Socket socketPrimario;
	private ObjectOutputStream flujoSalida;
	private ObjectInputStream flujoEntrada;
	private String pre="[MONITOR]";
	
	
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
					} else {
						System.out.println(pre+"Recibi basura");
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
                //desde aquí se debería enviar la orden para cambiar de servidor o lo que fuere.

        }
    }	

}
