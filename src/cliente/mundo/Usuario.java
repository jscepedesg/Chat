package cliente.mundo;

public class Usuario {
	
	private String ip;
	private String nick;
	private String mensaje;
	private boolean estado;
	
	public Usuario(String ip,String nick,String mensaje,boolean estado)
	{
		this.ip=ip;
		this.nick=nick;
		this.mensaje=mensaje;
		this.estado=estado;
		
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	
}
