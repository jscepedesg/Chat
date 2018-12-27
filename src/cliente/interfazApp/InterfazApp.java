package cliente.interfazApp;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import cliente.controlador.Controlador;
import util.Util;


public class InterfazApp extends JFrame{
	
		//Atributos
		private PanelDatos pnlDatos;
		private PanelTexto pnlTexto;
		private PanelEnvia pnlEnvia;
		
		// Atributo controlador
		Controlador controlador;

	public InterfazApp(Controlador controlador)
	{
		setTitle("Chat");
		getContentPane( ).setLayout( null );
		
		//Icono JFrame
		/*Toolkit mipantalla=  Toolkit.getDefaultToolkit();
		Image MiIcono=mipantalla.getImage("imagenes/IconAge.png");//Icono
		setIconImage(MiIcono);*/
		
		
		// Integra el Controlador. 
	   	  this.controlador=controlador;
	   	  
	   // Instancia los paneles 
	   	pnlDatos = new PanelDatos(controlador);
	   	pnlDatos.setBounds(10, 10, 375, 55);
	   	pnlTexto = new PanelTexto();
	   	pnlTexto.setBounds(10, 80, 375, 300);
	   	pnlEnvia = new PanelEnvia(controlador);
	   	pnlEnvia.setBounds(10, 390, 375, 55);
	   	
	   	
	 // Organizar el panel principal. 
	      getContentPane( ).add( pnlDatos);
	      getContentPane( ).add( pnlTexto);
	      getContentPane( ).add( pnlEnvia);
	      
	   // Conecta objetos al controlador.
	      controlador.conectar(pnlDatos,pnlTexto);
	   // Propiedades de la interfaz.
	      
	      setSize(400,490);
	      setResizable(false);
	      setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	      this.getContentPane().setBackground(Color.GRAY.darker().darker().darker());
	      
	  //  Centrar ventana.
	      	
	      Util.centrarVentana(this);
	      
	      addWindowListener(new WindowAdapter(){
	    	  public void windowOpened(WindowEvent e) //se abre ventana
	    		{
	    			controlador.onLine();
	    		}
	    	  
	    	  public void windowClosing(WindowEvent arg0) //cierra programa 
	    		{
	    			controlador.offLine();
	    		}
	    	  
	      });
	      
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InterfazApp ventana = new InterfazApp(new Controlador());
		ventana.setVisible(true);
	}

}


