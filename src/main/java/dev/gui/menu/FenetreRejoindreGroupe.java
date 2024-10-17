package main.java.dev.gui.menu;

import main.java.dev.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FenetreRejoindreGroupe extends JFrame {

	private JTextField groupNameField;
	private JPasswordField passwordField;

	public FenetreRejoindreGroupe() {
		// Configuration de la fenêtre
		setTitle("Rejoindre un Groupe");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null); // Layout nul pour placer les composants manuellement
		this.setVisible(true);
		this.setIconImage(new ImageIcon(getClass().getResource("/files/images/icon.png")).getImage());


		// Ajouter l'image de fond
		JLabel background = new JLabel(new ImageIcon(getClass().getResource("/files/images/BG.jpg")));
		background.setBounds(0, 0, 400, 300);
		add(background);

		// Ajouter le champ pour le nom du groupe
		JLabel nameLabel = new JLabel("Nom du groupe:");
		nameLabel.setForeground(Color.WHITE); // Texte blanc pour être visible sur l'image
		nameLabel.setBounds(50, 70, 120, 25);
		background.add(nameLabel);

		groupNameField = new JTextField();
		groupNameField.setBounds(180, 70, 150, 25);
		background.add(groupNameField);

		// Ajouter le champ pour le mot de passe
		JLabel passwordLabel = new JLabel("Mot de passe:");
		passwordLabel.setForeground(Color.WHITE);
		passwordLabel.setBounds(50, 110, 120, 25);
		background.add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(180, 110, 150, 25);
		background.add(passwordField);

		// Ajouter un bouton pour rejoindre le groupe
		JButton joinButton = new JButton("Rejoindre Groupe");
		joinButton.setBounds(130, 160, 150, 30);
		background.add(joinButton);

		// Action lors du clic sur "Rejoindre Groupe"
		joinButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				joinGroup();
			}
		});
	}

	private void joinGroup() {
		String groupName = groupNameField.getText();
		String password = new String(passwordField.getPassword());

		if (groupName.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
		}

		// Logique pour rejoindre le groupe (par exemple, vérification des identifiants)
		try {
			Controleur.sendMsgToServ("group join " + groupName + " " + password + " " + Controleur.userName);
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		this.dispose();
	}
}
