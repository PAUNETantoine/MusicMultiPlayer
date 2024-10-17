package main.java.dev.gui.menu;


import main.java.dev.Controleur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FenetreCreationPlayList extends JFrame
{
	private String nom;
	private JButton btnSend;
	private JTextField txtNom;
	private BufferedImage imgBG;

	public FenetreCreationPlayList()
	{
		this.setTitle("Music Multi Player - Création de Playlist");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setSize(400, 300);

		// Chargement de l'image de fond
		try {
			imgBG = ImageIO.read(getClass().getResourceAsStream("/files/images/BG.jpg"));
		} catch (Exception e) {
			System.out.println("Erreur de lecture de l'image");
		}

		// Créer les composants
		JPanel pnl = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (imgBG != null) {
					g.drawImage(imgBG, 0, 0, getWidth(), getHeight(), this);
				}
			}
		};
		pnl.setOpaque(false);
		pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));

		// Créer le label et le champ texte
		JLabel txtA = new JLabel("Nom de votre playlist : ");
		this.txtNom = new JTextField(20);
		this.btnSend = new JButton("Créer");

		// Configurer les composants
		txtA.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrer le label
		txtNom.setPreferredSize(new Dimension(200, 30));
		txtNom.setMaximumSize(txtNom.getPreferredSize()); // Fixer la taille maximale pour éviter l'étirement
		btnSend.setPreferredSize(new Dimension(100, 30));

		btnSend.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtNom.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Ajout des composants au panneau
		pnl.add(Box.createVerticalStrut(30)); // Espace avant le label
		pnl.add(txtA); // Ajouter le label
		pnl.add(Box.createVerticalStrut(10)); // Espace entre le label et le champ texte
		pnl.add(txtNom); // Ajouter le champ texte
		pnl.add(Box.createVerticalStrut(20)); // Espace entre le champ texte et le bouton
		pnl.add(btnSend); // Ajouter le bouton

		this.add(pnl);

		// Action Listener pour le bouton
		this.btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!txtNom.getText().isEmpty()) {
					boolean playlistExists = false;
					for (int i = 0; i < Controleur.getPrcplPnl().getPlaylists().size(); i++) {
						if (Controleur.getPrcplPnl().getPlaylists().get(i).getNom().equals(txtNom.getText())) {
							playlistExists = true;
							break;
						}
					}

					if (playlistExists)
					{
						JOptionPane.showMessageDialog(null, "Playlist déjà existante", "Erreur", JOptionPane.ERROR_MESSAGE);
					} else {
						Controleur.getPrcplPnl().addPlaylist(txtNom.getText().replace(" ", "_"));
						Controleur.sendMsgToServ("playlist create " + PrincipalPanel.selectedGroup.getNom() + " " + txtNom.getText().replace(" ", "_"));
						close();
					}
				}
			}
		});

		this.setVisible(true);
	}

	private void close() {
		this.dispose();
	}
}