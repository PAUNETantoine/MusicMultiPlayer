package main.java.dev.gui.menu;


import main.java.dev.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class FenetrePrincipale extends JFrame
{

	public main.java.dev.gui.menu.PrincipalPanel prcplPnl;

	public FenetrePrincipale(String username)
	{
		this.setSize(1000, 800);
		this.setResizable(false);
		this.setTitle("Music Multi Player - " + username + " - Lecteur");
		this.setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon(getClass().getResource("/files/images/icon.png")).getImage());


		this.prcplPnl = new PrincipalPanel();


		this.add(this.prcplPnl);
		this.setVisible(true);


		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e) {

				// Fonction appelée lors du clic sur la croix
				Controleur.sendMsgToServ("stop");

				System.exit(0);
			}
		});
	}

	public PrincipalPanel getPrcplPnl()
	{
		return this.prcplPnl;
	}
}
