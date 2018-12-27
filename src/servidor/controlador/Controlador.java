package servidor.controlador;

import servidor.interfazApp.PanelTextoSer;
import servidor.mundo.Servidor;

public class Controlador {

	private static PanelTextoSer pnlTextoSer;
	private Servidor servidor;
	
	public Controlador()
	{
		servidor = new Servidor();
	}

	public void conectar(PanelTextoSer pnlTextoSer) 
	{
		this.pnlTextoSer=pnlTextoSer;
	}
	
	public static void  setEnviaTexto(String texto)
	{
		pnlTextoSer.setTexto(texto);
	}
	
	
}
