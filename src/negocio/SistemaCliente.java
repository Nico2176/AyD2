package negocio;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class SistemaCliente {
	private static SistemaCliente instancia;
	
	public static SistemaCliente getInstancia() {
		if (instancia == null)
			instancia = new SistemaCliente();
		return instancia;
	}
	
	
	public boolean validarCadenaNumerica(String cadena) {
        // Patrón para verificar si la cadena es una secuencia de 8 números
        Pattern patron = Pattern.compile("^\\d{8}$");
        Matcher matcher = patron.matcher(cadena);
        return matcher.matches(); //true si cadena son 8 números
    }

}
