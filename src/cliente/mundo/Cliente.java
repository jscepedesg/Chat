package cliente.mundo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.google.gson.Gson;

import cliente.controlador.Controlador;

public class Cliente  {
	
	private String encriptao;
	private String desencriptao;
	private String ipServ = "172.16.131.250";
	
	private ArrayList<servidor.mundo.Usuario> ips = new ArrayList<servidor.mundo.Usuario>();
	private Usuario usuario;

	public Cliente()
	{
		actualizaCombo mihilo = new actualizaCombo();
		mihilo.start();
		
		escuchaMensaje escucha = new escuchaMensaje();
		escucha.start();
	}
	
	public void setSocket(String ip,String nick,String mensaje)
	{
		if(!mensaje.equals(""))
		{
			try {
				Socket misocket = new Socket(ipServ, 9999);
				System.out.println(ip+" "+nick+" "+mensaje);
				encrip(mensaje);
				
				Usuario usuario = new Usuario(ip,nick,getEncriptao(),true);
				System.out.println(ip+" "+nick+" "+getEncriptao());
				
				Gson gson = new Gson();
		        String json = gson.toJson(usuario);        
		        System.out.println("json = " + json);
		        
		        
		        DataOutputStream paquete_datos = new DataOutputStream(misocket.getOutputStream());
		        paquete_datos.writeUTF(json);
		        paquete_datos.close();
		        misocket.close();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*desencrip(getEncriptao());
			System.out.println(ip+" "+nick+" "+getDesencriptao());*/
		}
		
	}
	
//····························ENVIO DE SEÑAL ONLINE·······
	public void envioOnline(String nick)
	{
		try {
			Socket misocket = new Socket(ipServ, 9999);
			
			Usuario usuario = new Usuario(null,nick," online",true);
			
			Gson gson = new Gson();
	        String json = gson.toJson(usuario);        
	        System.out.println("Envio online ······ json = " + json);
	        
	        DataOutputStream paquete_datos = new DataOutputStream(misocket.getOutputStream());
	        paquete_datos.writeUTF(json);
	        paquete_datos.close();
	        misocket.close();
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//·····················································
	
		
	//····························ENVIO DE SEÑAL OFFLINE·······
		public void envioOffline(String nick)
		{
			try {
				Socket misocket = new Socket(ipServ, 9999);
				
				Usuario usuario = new Usuario(null,nick," offline",true);
				
				Gson gson = new Gson();
		        String json = gson.toJson(usuario);        
		        System.out.println("Envio offline ······ json = " + json);
		        
		        DataOutputStream paquete_datos = new DataOutputStream(misocket.getOutputStream());
		        paquete_datos.writeUTF(json);
		        paquete_datos.close();
		        misocket.close();
				
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	//·····················································
	
	// Metodo para Encriptar el mensaje
	public void encrip(String str)
	{
		char array[] = str.toCharArray();
	
		for(int i = 0; i < array.length; i++)
		{array[i] = (char)(array[i] + (char)3);}
		encriptao = String.valueOf(array);
	}  
	
	public void desencrip(String str)
	{
		char array[] = str.toCharArray();
		
		for(int i = 0; i < array.length; i++)
		{array[i] = (char)(array[i]-(char)3);}
		desencriptao = String.valueOf(array);
	}	
	
		public String getEncriptao() {return encriptao;}	
		public String getDesencriptao() {return desencriptao;}

		
		private class actualizaCombo extends Thread
		{
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
					try
					{
						ServerSocket servidor_cliente = new ServerSocket(9090);
						Socket cliente;
						Gson gson = new Gson();	
						Team jsonToJava;
						ArrayList<String> IpsMenu;
						while(true)
						{
							cliente = servidor_cliente.accept();
							DataInputStream flujo_entrada = new DataInputStream(cliente.getInputStream());
							String mensaje_texto = flujo_entrada.readUTF();
//							acepta todas las conexiones que vengan del exterior
							
								
//								tiene toda la informacion que le ha llegado al cliente por parte del servidor
								
								jsonToJava = gson.fromJson(mensaje_texto, Team.class);
								//System.out.println(" entré cliente·····toString = " + jsonToJava.getIp()+" "+jsonToJava.getNick()+" "+jsonToJava.getMensaje()+" "+jsonToJava.isEstado());
								ips= jsonToJava.getArray();
								System.out.println(mensaje_texto + " 3");
								IpsMenu = new ArrayList<String>();
								for (servidor.mundo.Usuario string : ips) 
								{
									System.out.println("entre a foreach");
//									IpsMenu.add(string.getNick()+"*");
									if(string.isEstado()==false)
									{
										System.out.println("getNick array desconectado");
										IpsMenu.add(string.getNick()+"*");
									}
									else
									{
										System.out.println("getNick array conectado");
										IpsMenu.add(string.getNick());
									}
								}
								Controlador.setModificaCombox(IpsMenu);	
							}
												
							/*if(!jsonToJava.getMensaje().equals("online"))
							{
//								campochat.append("\n" + paqueteRecibido.getNick() + ": " + paqueteRecibido.getMensaje());
							}
							else
							{	
								//campochat.append("\n" + paqueteRecibido.getIps());
								
								System.out.println("Modifica combox");
								
							}*/
								
						
					} //en el catch pone exception para hacerlo generico
					catch(Exception e)
					{
						System.out.println(e.getMessage());
					}
				
			}
			
		}
		
		private class escuchaMensaje extends Thread
		{
			public void run() 
			{
				
					try {
						
						ServerSocket servidor_cliente = new ServerSocket(9099);
						Socket cliente;
						Gson gson = new Gson();
						Usuario jsonToJava;
						while(true)
						{
							cliente = servidor_cliente.accept();
							DataInputStream flujo_entrada = new DataInputStream(cliente.getInputStream());
							String mensaje_texto = flujo_entrada.readUTF();
							jsonToJava = gson.fromJson(mensaje_texto, Usuario.class);
							desencrip(jsonToJava.getMensaje());
							Controlador.setRecibe(jsonToJava.getNick(), getDesencriptao());
							
						}
					} 
					catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
			}
		}

	
}

