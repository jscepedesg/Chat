package servidor.interfazApp;

import java.awt.Color;
import javax.swing.JFrame;
import servidor.controlador.Controlador;
import util.Util;

public class InterfazApp extends JFrame{
	
	    //Atributos
	    private PanelTextoSer pnlTextoSer;
			
	    // Atributo controlador
	    Controlador controlador;

		public InterfazApp(Controlador controlador)
		{
			setTitle("Servidor");
			getContentPane( ).setLayout( null );
			
			//Icono JFrame
			/*Toolkit mipantalla=  Toolkit.getDefaultToolkit();
			Image MiIcono=mipantalla.getImage("imagenes/IconAge.png");//Icono
			setIconImage(MiIcono);*/
			
			
			// Integra el Controlador. 
		   	  this.controlador=controlador;
		   	  
		   // Instancia los paneles 
		   	pnlTextoSer = new PanelTextoSer();
		   	pnlTextoSer.setBounds(10, 10, 375, 440);
		   	
		   	
		 // Organizar el panel principal. 
		      getContentPane( ).add( pnlTextoSer);

		      
		   // Conecta objetos al controlador.
		      controlador.conectar(pnlTextoSer);
		   // Propiedades de la interfaz.
		      
		      setSize(400,490);
		      setResizable(false);
		      setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		      this.getContentPane().setBackground(Color.GRAY.darker().darker().darker());
		      
		  //  Centrar ventana.
		      	
		      Util.centrarVentana(this);
		      
		}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InterfazApp ventana = new InterfazApp(new Controlador());
		ventana.setVisible(true);
	}

}
