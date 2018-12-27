package cliente.controlador;

import java.util.ArrayList;

import cliente.interfazApp.PanelDatos;
import cliente.interfazApp.PanelTexto;
import cliente.mundo.Cliente;

public class Controlador {

	private static PanelDatos pnlDatos;
	private static PanelTexto pnlTexto;
	private Cliente cliente;
	
	public Controlador()
	{
		cliente = new Cliente();
	}

	public void conectar(PanelDatos pnlDatos, PanelTexto pnlTexto) 
	{
		this.pnlDatos=pnlDatos;
		this.pnlTexto=pnlTexto;
	}
	
	public void socketCtrl(String mensaje)
	{
		cliente.setSocket(pnlDatos.getIp(), pnlDatos.getNick(), mensaje);
	}
	public void setActulizarChat(String mensaje)
	{
		pnlTexto.setTextoLocal(mensaje);
	}
	
	public void onLine()
	{
		cliente.envioOnline(pnlDatos.getNick());
	}
	
	public void offLine()
	{
		cliente.envioOffline(pnlDatos.getNick());
	}
	public static void setModificaCombox(ArrayList<String> IpsMenu)
	{
		pnlDatos.setModificaCombox(IpsMenu);
	}
	
	public static void setRecibe(String nick, String mensaje)
	{
		pnlTexto.setTextoEnvio(nick, mensaje);
	}
	
	
}
