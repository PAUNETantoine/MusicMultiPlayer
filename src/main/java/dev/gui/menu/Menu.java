package main.java.dev.gui.menu;


import main.java.dev.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class Menu extends JFrame
{
	private MenuPanel pnlCentral;
	public static int width = 1000;

	public Menu(int code)
	{
		this.setSize(1000,800);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Music Multi Player - Connexion");
		this.setIconImage(new ImageIcon(getClass().getResource("/files/images/icon.JPG")).getImage());


		//Définition du panel
		this.pnlCentral = new MenuPanel(code);

		//Ajout des éléments au panel / Frame
		this.add(this.pnlCentral);


		this.setVisible(true);





		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				if (code == 0)
				{
					Controleur.sendMsgToServ("stop");
				}

				// Pour fermer explicitement l'application après l'action
				System.exit(0);
			}
		});
	}


	public MenuPanel getMenuPanel()
	{
		return this.pnlCentral;
	}




}
