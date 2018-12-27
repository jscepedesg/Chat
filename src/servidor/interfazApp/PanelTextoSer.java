package servidor.interfazApp;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PanelTextoSer extends JPanel{
	
	private	JTextArea areatexto;
	private JScrollPane laminaBarras;
	
	public PanelTextoSer()
	{
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setLayout(new BorderLayout());
		areatexto=new JTextArea();
		laminaBarras = new JScrollPane(areatexto);
		areatexto.setEditable(false);
		areatexto.setLineWrap(true);
		add(laminaBarras, BorderLayout.CENTER);
		
	}
	
	public void setTexto(String texto)
	{
		areatexto.append("\n" + texto);
	}

}
