package servidor.mundo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Team 
{
	private ArrayList<Usuario> team;
    
    /* Constructor */
    public Team()
    { /*
      user = new User[3];
    
      user[0] = new User("Billy Joel", "192.168.0.1");
      user[1] = new User("Thom Yorke", "192.168.0.2");
      user[2] = new User("Gordon Matthew - Sting", "192.168.0.3");
      */
    	
      
      team = new ArrayList<Usuario>();
    //1.Crear la conexion 
		Connection conexion;
		try {
			
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat","root","");
			//2. Preparar la consulta
			PreparedStatement mi_sentencia=conexion.prepareStatement("SELECT nick, estado, ip FROM usuarios_conectados");
			//3. Ejecutar y recorer consulta
			ResultSet rs = mi_sentencia.executeQuery();
			while(rs.next())
			{
				System.out.println("llenando array");
				team.add(new Usuario(rs.getString("ip"), rs.getString("nick"), null, rs.getBoolean("estado")));
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
       	
    }
    public ArrayList<Usuario> getArray()
    {
    	return team;
    }
}
