package servidor.mundo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;

import cliente.mundo.Usuario;
import servidor.controlador.Controlador;

public class Servidor implements Runnable {
	
	private String mensaje_texto;
	private DataInputStream flujo_entrada;
	private Gson gson;
	private ArrayList<Usuario> envia_comb;
	//private Controlador controlador;
	public Servidor()
	{
		Thread mihilo = new Thread(this);
		mihilo.start();
		//this.controlador=controlador;
		
		envia_comb = new ArrayList<Usuario>();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			ServerSocket servidor = new ServerSocket(9999);
			gson = new Gson();
			
			while(true)
			{
				Socket misocket = servidor.accept();
				
				flujo_entrada = new DataInputStream(misocket.getInputStream());
				mensaje_texto = flujo_entrada.readUTF();
				Usuario jsonToJava;
		        jsonToJava = gson.fromJson(mensaje_texto, Usuario.class);
		        System.out.println("entré·····toString = " + jsonToJava.toString());
		        
		        if(jsonToJava.getMensaje().equals(" online"))
		        {
		        	//online vamos a crear los usuarios y enviar notificacion de que estoy conectado
		        	System.out.println("Servidor: estoy en online");
		        	
//		        	···········DETECTA ONLINE = detecta es ip··························
					InetAddress localizacion = misocket.getInetAddress();
//						nombre red
					String IpRemota = localizacion.getHostAddress();
					System.out.println("Servidor: estoy en online: "+IpRemota);
					//System.out.println(IpRemota);
		        	setOnlineBD(jsonToJava.getNick(), IpRemota);
//		        	jsonToJava.setIp(IpRemota);
		        	misocket.close();
		        	setCombox();
		        	getTengoMensaje(jsonToJava.getNick(),IpRemota);
		        	
		        }else if(jsonToJava.getMensaje().equals(" offline"))
		        {
		        	InetAddress localizacion = misocket.getInetAddress();
//					nombre red
		        	String IpRemota = localizacion.getHostAddress();
		        	setUpdateOffline(jsonToJava.getNick(), IpRemota);
		        	System.out.println("Servidor: estoy en offline");
		        	misocket.close();
		        	setCombox();
		        }else
		        {
		        	//enviar archivo json nick, mensaje a cliente
		        	Controlador.setEnviaTexto("Ip: "+jsonToJava.getIp()+" Nick: "+jsonToJava.getNick()+" Mensaje: "+jsonToJava.getMensaje());
		        	
		        	if(getComprueba(jsonToJava.getIp()))
		        	{
		        		Socket enviaDestinatario = new Socket(getIpdes(jsonToJava.getIp()), 9099);
			        	
			        	Usuario usuario = new Usuario(getIpdes(jsonToJava.getIp()),jsonToJava.getNick(),jsonToJava.getMensaje(),true);
						
						Gson gson = new Gson();
				        String json = gson.toJson(usuario);        
				        System.out.println("Envio a destino ······ json = " + json);
				        DataOutputStream paquete_datos = new DataOutputStream(enviaDestinatario.getOutputStream());
				        paquete_datos.writeUTF(json);
				        enviaDestinatario.close();
				        paquete_datos.close();
		        	}
		        	else
		        	{
		        		
		        		setMensajeDesconectados(jsonToJava.getNick(), jsonToJava.getIp(), jsonToJava.getMensaje());
		        		System.out.println("Está desconectado");
		        		
		        	}
		        	
			        
		        }
		        
		        	
		        
		        
//			con este se cierre la conexión
//				misocket.close();	
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void setOnlineBD(String nick,String ip)
	{
		try {
			//1.Crear la conexion 
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat","root","");
			//2. Preparar la consulta
			PreparedStatement mi_sentencia=conexion.prepareStatement("SELECT nick, estado FROM usuarios_conectados WHERE nick=?");
			//3. Establecer parametros de consulta
			mi_sentencia.setString(1,nick);
			//4. Ejecutar y recorer consulta
			ResultSet miresultset = mi_sentencia.executeQuery();
			
			boolean existe = false;
			
			while(miresultset.next())
			{existe=true;}
			
			if(existe == false)
			{
				System.out.println("el nick NO existe");
				
				setCrearNick(nick, ip);
			}
			else
			{
				System.out.println("el nick SI existe");
				setUpdateOnline(nick,ip);
				
			}
			
			conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error de conexion");
		}
	}
	
	public void setCrearNick(String nick, String ip)
	{
		//1. Crear conexion 
		Connection conexion;
		try 
		{
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat","root","");
			//2.Crear objecto Statement
			Statement mistatement = conexion.createStatement();
			
			
			//Insertar valores
			String instruccion_sql = "insert into usuarios_conectados values (?,true,?)";
			
			PreparedStatement psIU = conexion.prepareStatement(instruccion_sql);
			psIU.setString(1, nick);
			psIU.setString(2, ip);
	
			int realizado = psIU.executeUpdate();
			
			if(realizado == 1)
			{
				System.out.println("se puso");
			}else
			{
				System.out.println("no se puso");
			}
			psIU.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setUpdateOnline(String nick, String ip)
	{
		try
		{
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat","root","");
			
			
			String instruccionSql = "UPDATE usuarios_conectados SET estado = true, ip = ? WHERE nick = ?";
			
			PreparedStatement psIU = conexion.prepareStatement(instruccionSql);
					
			psIU.setString(1, ip);
			psIU.setString(2, nick);
			
			int realizado = psIU.executeUpdate();
			
			if(realizado == 1)
			{
				System.out.println("SI lo hizo");
			}else
				System.out.println("NO lo hizo");
			
			conexion.close();
			psIU.close();
		}
		catch(Exception e)
		{
			System.out.println("Error");
		}
		

	}
	public void setUpdateOffline(String nick, String ip)
	{
		try
		{
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat","root","");
			
			
			String instruccionSql = "UPDATE usuarios_conectados SET estado = false, ip = ? WHERE nick = ?";
			
			PreparedStatement psIU = conexion.prepareStatement(instruccionSql);
					
			psIU.setString(1, ip);
			psIU.setString(2, nick);
			
			psIU.executeUpdate();
			
			conexion.close();
			psIU.close();
		}
		catch(Exception e)
		{
			System.out.println("Error");
		}
		

	}
	
	public void setCombox()
	{	
		try
		{
			//1.Crear la conexion 
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat","root","");
			//2. Preparar la consulta
			PreparedStatement mi_sentencia=conexion.prepareStatement("SELECT nick, estado, ip FROM usuarios_conectados WHERE estado = true");
			//3. Ejecutar y recorer consulta
			ResultSet rs = mi_sentencia.executeQuery();
			while(rs.next())
			{
				/*System.out.println("llenando array");
				envia_comb.add(new Usuario(rs.getString("ip"), rs.getString("nick"), null, rs.getBoolean("estado")));*/
				Socket enviaDestinatario = new Socket(rs.getString("ip"), 9090);
				//Usuario usuario = new Usuario(rs.getString("ip"), rs.getString("nick"), null, rs.getBoolean("estado"));
				Team team = new Team();
				Gson gson = new Gson();
				String json = gson.toJson(team);
				
				System.out.println("json = " + json);
		        //String json = gson.toJson(envia_comb);
		        DataOutputStream paqueteReenvio = new DataOutputStream(enviaDestinatario.getOutputStream());
				paqueteReenvio.writeUTF(json);
				
				paqueteReenvio.close();
				enviaDestinatario.close();
			}
			
			
			
		
	        
			
		}
		catch(Exception e)
		{
			System.out.println("aaaak"+e.getMessage());
		}
	}
	
	public String getIpdes(String nick)
	{
		String ip="";
		
		try
		{
			//1.Crear la conexion 
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat","root","");
			//2. Preparar la consulta
			PreparedStatement mi_sentencia=conexion.prepareStatement("SELECT nick, estado, ip FROM usuarios_conectados WHERE nick = ?");
			
			mi_sentencia.setString(1, nick);
			//3. Ejecutar y recorer consulta
			ResultSet rs = mi_sentencia.executeQuery();
			while(rs.next())
			{
				ip = rs.getString("ip");
				System.out.println("llego 3 " + ip);
			}
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		
		return ip;
	}
	
	public boolean getComprueba(String nick)
	{
		boolean estado=false;
		try
		{
			//1.Crear la conexion 
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat","root","");
			//2. Preparar la consulta
			PreparedStatement mi_sentencia=conexion.prepareStatement("SELECT nick, estado, ip FROM usuarios_conectados WHERE nick = ?");
			
			mi_sentencia.setString(1, nick);
			//3. Ejecutar y recorer consulta
			ResultSet rs = mi_sentencia.executeQuery();
			while(rs.next())
			{
				estado = rs.getBoolean("estado");
			}
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		
		return estado;
	}
	
	public void setMensajeDesconectados(String nick, String ip, String mensaje) 
	{
		//1. Crear conexion 
				Connection conexion;
				try 
				{
					conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat","root","");
					//2.Crear objecto Statement
					Statement mistatement = conexion.createStatement();
					
					String ip2 = ip.substring(0, ip.length()-1);
					
					
					//Insertar valores
					String instruccion_sql = "insert into mensajes values (?,?,?)";
					
					PreparedStatement psIU = conexion.prepareStatement(instruccion_sql);
					psIU.setString(1, nick);
					psIU.setString(2, ip2);
					psIU.setString(3, mensaje);
			
					int realizado = psIU.executeUpdate();
					
					if(realizado == 1)
					{
						System.out.println("se puso");
					}else
					{
						System.out.println("no se puso");
					}
					psIU.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
	public void getTengoMensaje(String nick,String ip) 
	{
		try
		{
			//1.Crear la conexion 
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat","root","");
			//2. Preparar la consulta
			PreparedStatement mi_sentencia=conexion.prepareStatement("SELECT nick_incio, nick_destino, mensaje FROM mensajes WHERE nick_destino = ?");
			
			mi_sentencia.setString(1, nick);
			//3. Ejecutar y recorer consulta
			ResultSet rs = mi_sentencia.executeQuery();
			while(rs.next())
			{	
				Socket enviaDestinatario = new Socket(ip, 9099);
	        	
	        	Usuario usuario = new Usuario("",rs.getString("nick_incio"),rs.getString("mensaje"),true);
				
				Gson gson = new Gson();
		        String json = gson.toJson(usuario);        
		        System.out.println("Envio a destino desconectado ······ json = " + json);
		        DataOutputStream paquete_datos = new DataOutputStream(enviaDestinatario.getOutputStream());
		        paquete_datos.writeUTF(json);
		        enviaDestinatario.close();
		        paquete_datos.close();
				
			}
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		
		try {
			
			Connection miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat?autoReconnect=true&useSSL=false", "root", "");
			
			boolean existe = false;
			String instruccionSql = "";
			
			instruccionSql = "DELETE FROM MENSAJES WHERE nick_destino=?";
			
			PreparedStatement psEliminar = miConexion.prepareStatement(instruccionSql);
			
			psEliminar.setString(1, nick);
			
			psEliminar.executeUpdate();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public static String getIPAddressIPv4(String id) {
        try {

            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (intf.getName().contains(id)) {
                    List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                    for (InetAddress addr : addrs) {

                        if (!addr.isLoopbackAddress()) {
                            String sAddr = addr.getHostAddress();
                            if (addr instanceof Inet4Address) {
                                return sAddr;
                            }
                        }

                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
