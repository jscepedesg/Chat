package cliente.interfazApp;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cliente.controlador.Controlador;

public class PanelDatos extends JPanel{
	
	Controlador controlador;
	private JComboBox ip;
	private String nick_usuario;
	private JLabel texto;
	private JLabel nick;
	private JLabel n_nick;
	
	public PanelDatos(Controlador controlador)
	{
		setBackground(new Color(12,211,46));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.controlador=controlador;
		nick_usuario = JOptionPane.showInputDialog("Nick:");
		n_nick= new JLabel("Nick:");
		Font auxFont=n_nick.getFont();
		n_nick.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 25));
		n_nick.setForeground(Color.BLUE.darker().darker().darker().darker());
		add(n_nick);
		
		nick= new JLabel();
		nick.setText(nick_usuario);
		Font auxFont1=nick.getFont();
		nick.setFont(new Font(auxFont1.getFontName(), auxFont1.getStyle(), 25));
		nick.setForeground(Color.BLACK);
		add(nick);
		
		texto=new JLabel("Online:");
		Font auxFont2=texto.getFont();
		texto.setFont(new Font(auxFont2.getFontName(), auxFont2.getStyle(), 15));
		texto.setForeground(Color.BLUE.darker().darker().darker().darker());
		add(texto);
		
		ip= new JComboBox();
		//ip.addItem("");
		add(ip);
		
	}
	
	public String getNick()
	{
		return nick.getText();
	}
	public String getIp()
	{
		return ip.getSelectedItem().toString();
	}
	
	public void setModificaCombox(ArrayList<String> IpsMenu)
	{
		ip.removeAllItems();
		for (String z : IpsMenu) 
		{	
			ip.addItem(z);
		}
	}

}
