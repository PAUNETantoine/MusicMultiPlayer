package main.java.dev.gui.menu;

import main.java.dev.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FenetreCreerGroupe extends JFrame {

	private JTextField groupNameField;
	private JPasswordField passwordField1;
	private JPasswordField passwordField2;

	public FenetreCreerGroupe() {
		// Configurer la fenêtre
		setTitle("Créer un Groupe");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null); // Utilisation d'un layout null pour placer manuellement les composants
		this.setIconImage(new ImageIcon(getClass().getResource("/files/images/icon.png")).getImage());

		// Ajouter l'image de fond
		JLabel background = new JLabel(new ImageIcon(getClass().getResource("/files/images/BG.jpg")));
		background.setBounds(0, 0, 400, 300);
		add(background);

		// Ajouter le champ pour le nom du groupe
		JLabel nameLabel = new JLabel("Nom du groupe:");
		nameLabel.setForeground(Color.WHITE);  // Couleur du texte pour qu'il soit visible sur l'image
		nameLabel.setBounds(50, 50, 120, 25);
		background.add(nameLabel);

		groupNameField = new JTextField();
		groupNameField.setBounds(180, 50, 150, 25);
		background.add(groupNameField);

		// Ajouter les champs pour les mots de passe
		JLabel passwordLabel1 = new JLabel("Mot de passe:");
		passwordLabel1.setForeground(Color.WHITE);
		passwordLabel1.setBounds(50, 90, 120, 25);
		background.add(passwordLabel1);

		passwordField1 = new JPasswordField();
		passwordField1.setBounds(180, 90, 150, 25);
		background.add(passwordField1);

		JLabel passwordLabel2 = new JLabel("Confirmer mdp :");
		passwordLabel2.setForeground(Color.WHITE);
		passwordLabel2.setBounds(50, 130, 150, 25);
		background.add(passwordLabel2);

		passwordField2 = new JPasswordField();
		passwordField2.setBounds(180, 130, 150, 25);
		background.add(passwordField2);

		// Ajouter un bouton pour valider la création du groupe
		JButton createButton = new JButton("Créer Groupe");
		createButton.setBounds(130, 180, 150, 30);
		background.add(createButton);

		// Action lors du clic sur "Créer Groupe"
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createGroup();
			}
		});

		this.setIconImage(new ImageIcon(getClass().getResource("/files/images/icon.png")).getImage());
		this.setVisible(true);
	}

	private void createGroup()
	{
		String groupName = groupNameField.getText().replace(" ", "_");
		String password1 = new String(passwordField1.getPassword());
		String password2 = new String(passwordField2.getPassword());

		if (groupName.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!password1.equals(password2)) {
			JOptionPane.showMessageDialog(this, "Les mots de passe ne correspondent pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
			return;
		}

		for(int i = 0; i < Controleur.getPrcplPnl().getGroups().size() ; i++)
		{
			if(Controleur.getPrcplPnl().getGroups().get(i).getNom().equals(groupName))
			{
				JOptionPane.showMessageDialog(this, "Ce groupe existe déjà", "Erreur", JOptionPane.ERROR_MESSAGE);
				dispose();
				return;
			}
		}


		// Logique pour créer le groupe (par exemple, sauvegarde dans une base de données)
		JOptionPane.showMessageDialog(this, "Groupe '" + groupName + "' créé avec succès !");
		Controleur.getPrcplPnl().addGroup(groupName);

		try{
			Controleur.sendMsgToServ("group create " + groupName + " " + password1);
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		dispose();
	}
}