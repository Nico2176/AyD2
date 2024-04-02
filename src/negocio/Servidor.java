package negocio;

public class Servidor {
	
	public static Servidor instancia;
	
	public static Servidor getInstancia() {
        if (instancia == null) {
            instancia = new Servidor();
        }
        return instancia;
    }
    

}
