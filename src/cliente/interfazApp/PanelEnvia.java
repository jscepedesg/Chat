package cliente.interfazApp;

import java.awt.Color;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import cliente.controlador.Controlador;

public class PanelEnvia extends JPanel implements ActionListener{
	
	Controlador controlador;
	
	private JTextField campo1;
	private JButton btnEnviar;
	
	public PanelEnvia(Controlador controlador)
	{
		setBackground(new Color(12,211,46));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setLayout(null);
		//Integar controlador
		this.controlador=controlador;
		campo1=new JTextField(20);
		campo1.setBounds(20, 10, 240, 30);
		add(campo1);
		
		//Boton Generar
		btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(this);
		add(btnEnviar);
		btnEnviar.setBounds(270, 10, 80, 30);
		
		//Evento de teclado
		KeyboardFocusManager.getCurrentKeyboardFocusManager().
        addKeyEventDispatcher(new KeyEventDispatcher() 
        {
        	public boolean dispatchKeyEvent(KeyEvent e) 
        	{	
        
        		if (e.getID() == KeyEvent.KEY_RELEASED &&
        				e.getKeyCode() == KeyEvent.VK_ENTER) 
        		{
        			setEnviaMensaje();
        		}
        		return false;
        	}
        });
		
	}

	
	public void actionPerformed(ActionEvent e) 
	{
		setEnviaMensaje();
	}
	
	public void setEnviaMensaje()
	{
		controlador.socketCtrl(campo1.getText());
		controlador.setActulizarChat(campo1.getText());
		campo1.setText("");
	}
	
	
}
