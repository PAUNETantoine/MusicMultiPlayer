package main.java.dev.gui.menu;


import main.java.dev.Controleur;
import main.java.dev.metier.Connexion;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuPanel extends JPanel implements ActionListener
{
	private BufferedImage logo;

	private JTextField txtNomUtilisateur;
	private JPasswordField txtMotDePasse;

	private JLabel lblErreur;
	private JLabel lblTxtNomUser;
	private JLabel lblTxtMdp;
	private JLabel lblErreurI;

	private JButton btnConnexion;
	private JButton btnInscription;

	private JFrame inscriptionFrame;

	public MenuPanel(int code)
	{
		this.setBackground(Color.GRAY);

		this.setLayout(null);

		try{
			this.logo = ImageIO.read(getClass().getResourceAsStream("/files/images/Music Multi Player Logo1.png"));
		}catch (Exception e)
		{
			System.out.println("Erreur de lecture de l'image logo");
		}


		this.lblErreur 			= new JLabel();
		this.txtNomUtilisateur 	= new JTextField();
		this.txtMotDePasse 		= new JPasswordField(30);
		this.btnConnexion		= new JButton("Se connecter");
		this.btnInscription		= new JButton("S'inscrire");



		if (code != 0)// Si erreur on affiche que la connexion au serveur n'a pas aboutie
		{
			this.setLblErreur("Erreur de connexion au serveur, veuillez ressayer...");
			return;
		}

		this.lblTxtNomUser		= new JLabel("Nom d'utilisateur :");
		this.lblTxtMdp			= new JLabel("Mot de passe :");


		this.txtNomUtilisateur.setBounds(Menu.width/2 -  (268/2), 280, 250, 30);
		this.lblTxtNomUser.setBounds(this.txtNomUtilisateur.getX(), this.txtNomUtilisateur.getY() - 30, 250, 30);
		this.txtMotDePasse.setBounds(Menu.width/2 -  (268/2), 350, 250, 30);
		this.lblTxtMdp.setBounds(this.txtMotDePasse.getX(), this.txtMotDePasse.getY() - 30, 250, 30);
		this.btnConnexion.setBounds(this.lblTxtMdp.getX() - 8, this.lblTxtMdp.getY() + 75, 130, 40);
		this.btnInscription.setBounds(this.lblTxtMdp.getX() + 127, this.lblTxtMdp.getY() + 75, 130, 40);

		this.setFocusable(true);
		this.requestFocusInWindow();

		this.add(this.txtNomUtilisateur);
		this.add(this.txtMotDePasse);
		this.add(this.lblTxtNomUser);
		this.add(this.lblTxtMdp);
		this.add(this.btnConnexion);
		this.add(this.btnInscription);


		this.btnConnexion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				lblErreur.setVisible(false);
				sendConnexion();
			}
		});


		this.btnInscription.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				lblErreur.setVisible(false);
				inscription();
			}
		});
	}

	public void setLblErreur(String txt)
	{
		this.lblErreur.setText(txt);
		this.lblErreur.setForeground(Color.ORANGE);
		this.lblErreur.setBounds(360, 450, 350, 30);
		this.add(this.lblErreur);
		this.lblErreur.setVisible(true);
	}


	public void inscription()
	{
		this.inscriptionFrame = new JFrame();
		this.inscriptionFrame.setVisible(true);
		this.inscriptionFrame.setSize(400, 600);
		this.inscriptionFrame.setLocationRelativeTo(null);
		this.inscriptionFrame.setTitle("Music Multi Player - Inscription");
		this.inscriptionFrame.setResizable(false);

		JPanel pnlInscription = new JPanel();
		pnlInscription.setLayout(null);

		pnlInscription.setBackground(Color.GRAY);

		ImageIcon imgSrc				= new ImageIcon(getClass().getResource("/files/images/Music Multi Player Logo1.png"));
		JLabel imgLogo					= new JLabel(imgSrc);

		this.lblErreurI 				= new JLabel();
		JLabel lblTxtNomUserI			= new JLabel("Nom d'utilisateur : ");
		JTextField txtNomUtilisateurI 	= new JTextField();
		JLabel lblTxtMdpI				= new JLabel("Mot de passe :");
		JTextField txtMotDePasseI 		= new JPasswordField(30);
		JLabel lblTxtMdpConfI			= new JLabel("Confirmez votre mot de passe :");
		JTextField txtMotDePasseConfI 	= new JPasswordField(30);
		JButton btnInscriptionI = new JButton("Inscription");

		imgLogo.setBounds((this.inscriptionFrame.getWidth()/2 - imgSrc.getIconWidth() / 2) - 5 , 30, imgSrc.getIconWidth(), imgSrc.getIconHeight());
		lblErreurI.setBounds(this.inscriptionFrame.getWidth()/2 -  (268/2), 310, 250, 30);
		txtNomUtilisateurI.setBounds(this.inscriptionFrame.getWidth()/2 -  (268/2), 280, 250, 30);
		lblTxtNomUserI.setBounds(txtNomUtilisateurI.getX(), txtNomUtilisateurI.getY() - 30, 250, 30);
		txtMotDePasseI.setBounds(this.inscriptionFrame.getWidth()/2 -  (268/2), 370, 250, 30);
		lblTxtMdpI.setBounds(this.inscriptionFrame.getWidth()/2  -  (268/2), txtMotDePasseI.getY() - 30, 250, 30);
		lblTxtMdpConfI.setBounds(txtMotDePasseI.getX(), txtMotDePasseI.getY() + 50, 250, 30);
		txtMotDePasseConfI.setBounds(lblTxtMdpConfI.getX(), lblTxtMdpConfI.getY() + 30, 250, 30);
		btnInscriptionI.setBounds(this.inscriptionFrame.getWidth()/2 - 70, lblTxtMdpConfI.getY() + 75, 130, 40);


		this.inscriptionFrame.add(pnlInscription);
		pnlInscription.add(imgLogo);
		pnlInscription.add(lblErreurI);
		pnlInscription.add(lblTxtNomUserI);
		pnlInscription.add(txtNomUtilisateurI);
		pnlInscription.add(lblTxtMdpI);
		pnlInscription.add(txtMotDePasseI);
		pnlInscription.add(lblTxtMdpConfI);
		pnlInscription.add(txtMotDePasseConfI);
		pnlInscription.add(btnInscriptionI);


		this.setFocusable(true);
		this.requestFocusInWindow();


		btnInscriptionI.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(txtMotDePasseConfI.getText().equals(txtMotDePasseI.getText()))
				{
					Controleur.sendMsgToServ("inscription " + txtNomUtilisateurI.getText() + " " + txtMotDePasseConfI.getText());
				}else{
					lblErreurI.setText("Vos mots de passe ne correspondent pas");
					lblErreurI.setVisible(true);
					lblErreurI.setForeground(Color.ORANGE);
				}
			}
		});
	}

	public void setLblErreurI(String erreur)
	{
		this.lblErreurI.setText(erreur);
		this.lblErreurI.setForeground(Color.ORANGE);
		this.lblErreurI.setVisible(true);
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		g.drawImage(this.logo,  super.getWidth()/2 - this.logo.getWidth()/2, 50, this);
	}


	public void sendConnexion()
	{
		if(this.txtNomUtilisateur.getText().contains(" "))
		{
			this.lblErreur.setText("Le nom d'utilisateur ne peut contenir d'espaces.");
			return;
		}

		Connexion connexion = new Connexion(this.txtNomUtilisateur.getText(), this.txtMotDePasse.getPassword());


		if (connexion.getEstConnecter()){return;}

		this.lblErreur.setText("Nom d'utilisateur ou mot de passe incorrect.");
	}

	public JFrame getInscriptionFrame()
	{
		return this.inscriptionFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{

	}
}
