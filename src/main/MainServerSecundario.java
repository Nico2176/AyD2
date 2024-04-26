package main;

import controlador.ControladorServer;

public class MainServerSecundario {
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		ControladorServer controlador = new ControladorServer(2);
	}

}
