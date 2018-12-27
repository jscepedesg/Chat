package cliente.interfazApp;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PanelTexto extends JPanel{
	
	private JTextArea campochat;
	private JScrollPane laminaBarras;
	
	public PanelTexto()
	{
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setLayout(null);
		campochat = new JTextArea();
		laminaBarras = new JScrollPane(campochat);
		laminaBarras.setBounds(0, 0, 376, 301);
		campochat.setEditable(false);
		campochat.setLineWrap(true);
		add(laminaBarras);
	}
	
	public void setTextoLocal(String mensaje)
	{
		campochat.append("\n "+"Tú: "+mensaje);
	}
	public void setTextoEnvio(String nick,String mensaje)
	{
		campochat.append("\n"+nick+": "+mensaje);
	}

}
