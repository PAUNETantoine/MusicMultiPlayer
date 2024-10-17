package main.java.dev.gui.menu;



import main.java.dev.Controleur;
import main.java.dev.metier.Playlist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FenetreAjouterMusique extends JFrame {

	private JComboBox<String> comboBoxPlaylists;
	private JButton btnSelectFile, btnAddMusic;
	private JLabel labelSelectedFile;
	private File selectedFile;

	public FenetreAjouterMusique() {
		// Paramètres de la fenêtre
		this.setTitle("Ajouter Musique");
		this.setSize(400, 200);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.setBackground(Color.BLUE);
		this.setIconImage(new ImageIcon(getClass().getResource("/files/images/icon.png")).getImage());

		// Panneau principal
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		String[] tmp = new String[Controleur.getPrcplPnl().getPlaylists().size()];

		for(int i = 0 ; i < Controleur.getPrcplPnl().getPlaylists().size() ; i++)
		{
			tmp[i] = Controleur.getPrcplPnl().getPlaylists().get(i).getNom();
		}

		// Menu déroulant pour les playlists
		comboBoxPlaylists = new JComboBox<>(tmp);
		comboBoxPlaylists.setPreferredSize(new Dimension(300, 30));

		if(Controleur.getPrcplPnl().getSelectedPlaylist() != null)
		{
			for(int i = 0 ; i < comboBoxPlaylists.getItemCount() ; i++)
			{
				if(comboBoxPlaylists.getItemAt(i).equals(Controleur.getPrcplPnl().getSelectedPlaylist().getNom()))
				{
					comboBoxPlaylists.setSelectedIndex(i);
				}
			}
		}



		// Bouton pour sélectionner un fichier
		btnSelectFile = new JButton("Sélectionner un fichier");
		labelSelectedFile = new JLabel("Aucun fichier sélectionné.");

		// Action du bouton pour ouvrir le sélecteur de fichiers
		btnSelectFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					selectedFile = fileChooser.getSelectedFile();
					labelSelectedFile.setText("Fichier sélectionné : " + selectedFile.getName());
				}
			}
		});

		// Bouton pour ajouter la musique à la playlist
		btnAddMusic = new JButton("Ajouter Musique");
		btnAddMusic.setPreferredSize(new Dimension(300, 30));
		btnAddMusic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (selectedFile != null && comboBoxPlaylists.getSelectedItem() != null && Controleur.getPrcplPnl().getSelectedPlaylist() != null)
				{
					String playlistName = comboBoxPlaylists.getSelectedItem().toString();


					for(int i = 0 ; i < Controleur.getPrcplPnl().getPlaylists().size() ; i++)
					{
						Playlist tmp = Controleur.getPrcplPnl().getPlaylists().get(i);

						if(tmp.getNom().equals(playlistName))
						{
							for(int j = 0 ; j < tmp.getMusics().size() ; j++)
							{
								if(tmp.getMusics().get(j).getNom().equals(selectedFile.getName()))
								{
									JOptionPane.showMessageDialog(null, "Cette musique existe déjà dans cette playlist.", "Erreur", JOptionPane.ERROR_MESSAGE);
									dispose();
									return;
								}
							}

							if(selectedFile.getName().contains(" "))
							{
								//Controleur.getPrcplPnl().getPlaylists().get(i).addMusic(new Music(selectedFile.getName(), tmp, "http://127.0.0.1/musicMultiPlayer/" + selectedFile.getName().replace(" ", "_")));
								Controleur.sendFileToServer(selectedFile.getName().replace(" ", "_"), selectedFile.getPath(), Controleur.getPrcplPnl().getPlaylists().get(i).getNom());
							}else{
								//Controleur.getPrcplPnl().getPlaylists().get(i).addMusic(new Music(selectedFile.getName(), tmp, "http://127.0.0.1/musicMultiPlayer/" + selectedFile.getName()));
								Controleur.sendFileToServer(selectedFile.getName(), selectedFile.getPath(), Controleur.getPrcplPnl().getPlaylists().get(i).getNom());
							}

							if(!Controleur.transfert)
							{

								JOptionPane.showMessageDialog(null, "La musique " + selectedFile.getName() + " a été ajoutée à " + playlistName);

								dispose();
							}
						}
					}


				} else {
					JOptionPane.showMessageDialog(null, "Veuillez sélectionner un fichier et une playlist.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// Ajout des composants au panneau
		panel.add(new JLabel("Sélectionnez une playlist :"));
		panel.add(comboBoxPlaylists);
		panel.add(Box.createVerticalStrut(10));
		panel.add(btnSelectFile);
		panel.add(labelSelectedFile);
		panel.add(Box.createVerticalStrut(10));
		panel.add(btnAddMusic);

		// Ajout du panneau à la fenêtre
		this.add(panel, BorderLayout.CENTER);
		this.setVisible(true);
	}
}