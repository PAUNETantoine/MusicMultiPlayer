package main.java.dev.gui.menu;



import main.java.dev.Controleur;
import main.java.dev.metier.*;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class PrincipalPanel extends JPanel implements MouseListener, MouseWheelListener
{
	private BufferedImage bg;
	private BufferedImage logo;
	private BufferedImage playPause;
	private BufferedImage bgMenuMulti;
	private BufferedImage stopBtn;
	private BufferedImage creerPlaylistBtn;
	private BufferedImage ajouterMusique;
	private BufferedImage supprimerPlaylist;
	private BufferedImage supprimerMusic;
	private BufferedImage creerGroupBtn;
	private BufferedImage rejoindreGroupBtn;
	private BufferedImage quitterGroupBtn;
	private BufferedImage choixFalse;
	private BufferedImage choixTrue;

	public static Playlist selectedPlaylist;
	public static Music selectedMusic;
	public static Group selectedGroup;

	private ArrayList<Playlist> playlists;
	private ArrayList<Group> groups;


	public String tmpNomGrpSelected;
	public String tmpSelectedPlaylist;
	public String tmpSelectedMusc;

	private int[] posPlayPause;
	private int[] posStop;
	private int[] posCreerPlaylist;
	private int scroll = 0;

	private FenetreRejoindreGroupe fenetreRejGrp;


	public PrincipalPanel()
	{
		this.setLayout(null);
		this.setSize(super.getWidth(), super.getHeight());
		this.addMouseListener(this);
		this.addMouseWheelListener(this);
		this.groups    = new ArrayList<>(10);
		this.groups.add(new Group("Aucun Groupe"));
		selectedGroup = this.groups.getFirst();
		this.playlists = selectedGroup.playlistsGroup;
		selectedPlaylist = selectedGroup.selectedPlaylist;
		selectedMusic = selectedGroup.selectedMusic;
	}


	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setStroke(new BasicStroke(10));

		try{
			bg                  = ImageIO.read(getClass().getResourceAsStream("/files/images/BG.jpg"));
			logo                = ImageIO.read(getClass().getResourceAsStream("/files/images/LogoApp1.png"));

			// Chargement conditionnel de l'icône play/pause en fonction de l'état du contrôleur
			if (Controleur.stop || !Controleur.play) {
				playPause       = ImageIO.read(getClass().getResourceAsStream("/files/images/play.png"));
			} else {
				playPause       = ImageIO.read(getClass().getResourceAsStream("/files/images/pause.png"));
			}

			bgMenuMulti         = ImageIO.read(getClass().getResourceAsStream("/files/images/multiMenuBG.png"));
			stopBtn             = ImageIO.read(getClass().getResourceAsStream("/files/images/stop.png"));
			creerPlaylistBtn    = ImageIO.read(getClass().getResourceAsStream("/files/images/bouton_creer_playlist.png"));
			ajouterMusique      = ImageIO.read(getClass().getResourceAsStream("/files/images/btnAddMusic.png"));
			supprimerPlaylist   = ImageIO.read(getClass().getResourceAsStream("/files/images/deletePlaylist.png"));
			supprimerMusic      = ImageIO.read(getClass().getResourceAsStream("/files/images/deleteMusic.png"));
			creerGroupBtn       = ImageIO.read(getClass().getResourceAsStream("/files/images/bouton_creer_groupe.png"));
			rejoindreGroupBtn   = ImageIO.read(getClass().getResourceAsStream("/files/images/bouton_rejoindre_groupe.png"));
			quitterGroupBtn     = ImageIO.read(getClass().getResourceAsStream("/files/images/leaveGrp.png"));
			choixTrue           = ImageIO.read(getClass().getResourceAsStream("/files/images/btnTrue.png"));
			choixFalse          = ImageIO.read(getClass().getResourceAsStream("/files/images/btnFalse.png"));



		}catch (Exception e)
		{
			System.out.println("Erreur de lecture d'une image");
		}

		this.posPlayPause 	= new int[] {super.getWidth()/2-170, 400};
		this.posStop		= new int[] {super.getWidth()/2 - 100, 400};
		this.posCreerPlaylist = new int[] {super.getWidth() - 220, super.getHeight() - 90};

		//Positionnement des images

		g.drawImage(bg, -1, -1, this);
		g.drawImage(logo,  super.getWidth()/2 - logo.getWidth()/2, super.getHeight() - 150, this);
		g2d.setColor(Color.BLACK);
		g.setFont(new Font("PRIMETIME", Font.BOLD, 20));

		if(selectedMusic != null && Controleur.getSoundReader().getMediaPlayer() != null && Controleur.getSoundReader().getDureeMusique() != null) //Changement du temps afficher
		{
			int tmpDuration = (int) Controleur.getSoundReader().getTempsActuel().toSeconds();
			int tmpMin = 0;
			int dureeTotal = (int) Controleur.getSoundReader().getDureeMusique().toSeconds();

			if(tmpDuration > 59)
			{
				tmpMin = tmpDuration / 60;
				tmpDuration = tmpDuration % 60;

			}

			String tmp;
			String tmp2;

			if(tmpMin < 10)
			{
				if(tmpDuration >= 10)
				{
					tmp = "0" + tmpMin + ":" + tmpDuration;
				}else{
					tmp = "0" + tmpMin + ":0" + tmpDuration;
				}
			}else{
				if(tmpDuration >= 10)
				{
					tmp = tmpMin + ":" + tmpDuration;
				}else{
					tmp = tmpMin + ":0" + tmpDuration;
				}
			}

			if(dureeTotal / 60 < 10)
			{
				if(dureeTotal % 60 < 10)
					tmp2 = "0" + dureeTotal / 60 + ":0" + dureeTotal % 60;
				else
					tmp2 = "0" + dureeTotal / 60 + ":" + dureeTotal % 60;
			}else{
				if(dureeTotal % 60 < 10)
					tmp2 = dureeTotal / 60 + ":0" + dureeTotal % 60;
				else
					tmp2 = dureeTotal / 60 + ":" + dureeTotal % 60;
			}

			g.drawString( tmp + " / " + tmp2, this.posStop[0] + 70, this.posStop[1] + 30);
		}else{
			g.drawString("00:00 / 00:00", this.posStop[0] + 70, this.posStop[1] + 30);
		}

		g.drawLine(bgMenuMulti.getWidth(), 370, super.getWidth() - bgMenuMulti.getWidth(), 370);
		g2d.setColor(Color.BLUE);
		g.drawImage(playPause, this.posPlayPause[0], this.posPlayPause[1], this);
		g.drawImage(stopBtn, this.posStop[0], this.posStop[1], this);
		g.drawImage(bgMenuMulti, -1, -1, this);
		g.drawImage(bgMenuMulti, super.getWidth() - bgMenuMulti.getWidth(), -1, this);
		g2d.setColor(Color.BLUE);
		g2d.drawLine(super.getWidth()/2 - 160, 470, (Controleur.getSoundReader().getMusicProgression()*3 + super.getWidth()/2 - 160), 470);
		g2d.setColor(Color.CYAN);
		g2d.drawLine(super.getWidth()/2 - 150 + Controleur.getSoundReader().getMusicProgression()*3, 470, 300 + super.getWidth()/2 - 160, 470);
		g2d.setColor(Color.WHITE);
		g2d.drawOval((Controleur.getSoundReader().getMusicProgression()*3 + super.getWidth()/2 - 160), 465, 10,10);
		g.setColor(Color.BLACK);

		if(this.selectedMusic != null)
		{
			String nomMusicTmp = this.selectedMusic.getNom().substring(0, this.selectedMusic.getNom().length() - 4);
			nomMusicTmp = nomMusicTmp.replace("_", " ");

			if(nomMusicTmp.length() > 29)
			{
				nomMusicTmp = nomMusicTmp.substring(0, 26);
				nomMusicTmp = nomMusicTmp + "...";
			}

			g.drawString("Lecture : " + nomMusicTmp,  super.getWidth()/2 - 160, 520);
		}
		else
			g.drawString("Aucune musique sélectionnée", super.getWidth()/2 - 160, 520);
		g.setColor(Color.ORANGE);
		g.drawString("Playlist : ", super.getWidth() - 180, 50);
		g.drawString("Musiques : ", super.getWidth()/2 - 60, 50);
		g.drawString("Groupes : ", 90, 50);
		g.setColor(Color.BLUE);
		g.setFont(new Font("PRIMETIME", Font.BOLD, 25));
		g.drawImage(creerPlaylistBtn, this.posCreerPlaylist[0], this.posCreerPlaylist[1], this);
		g.drawImage(ajouterMusique, super.getWidth()/2 + 150, 5, this);

		if(!this.playlists.isEmpty())
		{
			for(int i = 0; i < this.playlists.size() ; i++)
			{
				if(this.selectedPlaylist != null && selectedPlaylist.equals(this.playlists.get(i)))
				{
					g.setColor(Color.WHITE);
				}

				g.fillRect(super.getWidth() - 280, (i + 1) * 90 - 20, 300, 50);
				g.setColor(Color.BLACK);
				g.drawString("- " + this.playlists.get(i).getNom(), super.getWidth() - 270, (i+1) * 90 + 10);
				g.setColor(Color.BLUE);

				if(this.selectedPlaylist != null && selectedPlaylist.equals(this.playlists.get(i)))
				{
					g.drawImage(supprimerPlaylist, super.getWidth() - 40, (i + 1) * 90 - 20, this);
				}
			}
		}else{
			g.fillRect(super.getWidth() - 280, 90 - 20, 300, 50);
			g.setColor(Color.BLACK);
			g.drawString("- Aucune playlist", super.getWidth() - 270, 100);
			g.setColor(Color.BLUE);
		}

		g.setFont(new Font("PRIMETIME", Font.BOLD, 20));


		if(selectedPlaylist == null)
		{
			g.fillRect(super.getWidth()/2 - 200, 90 - 20, 400, 40);
			g.setColor(Color.BLACK);
			g.drawString("- Aucune playlist selectionnée", super.getWidth()/2 - 200, 100);
			g.setColor(Color.BLUE);
		} else if (selectedPlaylist.getMusics().isEmpty())
		{
			g.fillRect(super.getWidth()/2 - 200, 90 - 20, 400, 40);
			g.setColor(Color.BLACK);
			g.drawString("- Playlist vide", super.getWidth()/2 - 200, 100);
			g.setColor(Color.BLUE);
		} else{
			for(int i = 0 ; i < this.selectedPlaylist.getMusics().size() && i < 5 ; i++)
			{
				if(this.selectedMusic != null && selectedMusic.equals(selectedPlaylist.getMusics().get(this.scroll + i)))
				{
					g.setColor(Color.WHITE);
				}


				g.fillRect(super.getWidth()/2 - 200, (i+1) * 60 + 20, 400, 40);
				g.setColor(Color.BLACK);

				if(scroll+i >= selectedPlaylist.getMusics().size())
					scroll = 0;

				String nomMusicTmp = selectedPlaylist.getMusics().get(this.scroll + i).getNom().substring(0, selectedPlaylist.getMusics().get(this.scroll + i).getNom().length() - 4);
				nomMusicTmp = nomMusicTmp.replace("_", " ");

				if(nomMusicTmp.length() > 40)
				{
					g.drawString("- " + nomMusicTmp.substring(0, 37) + "...", super.getWidth()/2 - 200, (i+1) * 60 + 45);
				}else{
					g.drawString("- " + nomMusicTmp, super.getWidth()/2 - 200, (i+1) * 60 + 45);
				}
				g.setColor(Color.BLUE);

				if(selectedMusic != null && selectedMusic.equals(this.selectedPlaylist.getMusics().get(this.scroll + i)))
				{
					g.drawImage(supprimerMusic, super.getWidth()/2 + 170, (i+1) * 60 + 22, this);
				}
			}
		}

		g.setFont(new Font("PRIMETIME", Font.BOLD, 25));

		if(this.groups.size() > 1)
		{
			for(int i = 0 ; i < this.groups.size() ; i++)
			{
				if(selectedGroup.equals(this.groups.get(i)) && !selectedGroup.getNom().equals("Aucun Groupe"))
				{
					g.setColor(Color.WHITE);
				}

				else
					g.setColor(Color.BLUE);

				g.fillRect(0, (i+1) * 90 - 20, 280, 50);
				g.setColor(Color.BLACK);
				g.drawString("- " + this.groups.get(i).getNom() , 5, (i+1) * 90 + 20);

				if(selectedGroup.equals(this.groups.get(i)) && !this.groups.get(i).getNom().equals("Aucun Groupe"))
					g.drawImage(this.quitterGroupBtn, 240, (i+1)*90-15, this);
			}
		}else{

			for(int i = 0 ; i < this.groups.size() ; i++)
			{
				g.setColor(Color.WHITE);
				g.fillRect(0, 70, 280, 50);
				g.setColor(Color.BLACK);
				g.drawString("- " + this.groups.getFirst().getNom() , 5, 110);
			}
		}



		//Affichage des boutons relatifs au groupe


		g.drawImage(this.creerGroupBtn, 10, super.getHeight() - 80, this);
		g.drawImage(this.rejoindreGroupBtn, 142, super.getHeight() - 80, this);


		if(selectedPlaylist != null && selectedPlaylist.getAutoSkip())
			g.drawImage(choixTrue, 250, 510, this);
		else
			g.drawImage(choixFalse,250,510,this);

		g.setColor(Color.ORANGE);

		g.drawString("Passage auto", 385, 570);

		repaint();
	}





	@Override
	public void mouseClicked(MouseEvent e)
	{
		try{
			if(e.getX() < posPlayPause[0] + 50 && e.getX() > posPlayPause[0] && e.getY() < posPlayPause[1] + 50 && e.getY() > posPlayPause[1])
			{
				if(Controleur.stop && !Controleur.play && selectedMusic != null)
				{
					Controleur.sendMsgToServ("Play " + selectedGroup.getNom() + " " + selectedPlaylist.getNom() + " " + selectedMusic.getNom());
					return;
				}

				if(Controleur.play && selectedMusic != null)
				{
					Controleur.sendMsgToServ("Pause " + selectedGroup.getNom() + " " + selectedPlaylist.getNom() + " " + selectedMusic.getNom());
					return;
				}else if(selectedMusic != null)
				{
					Controleur.sendMsgToServ("Play " + selectedGroup.getNom() + " " + selectedPlaylist.getNom() + " " + selectedMusic.getNom());
					return;
				}
			}
			if(e.getX() < posStop[0] + 50 && e.getX() > posStop[0] && e.getY() < posStop[1] + 50 && e.getY() > posStop[1])
			{
				if(!Controleur.stop && selectedMusic != null)
				{
					Controleur.sendMsgToServ("StopM " + selectedGroup.getNom() + " " + selectedPlaylist.getNom() + " " + selectedMusic.getNom());
					return;
				}
			}
			if(e.getX() < posCreerPlaylist[0] + this.creerPlaylistBtn.getWidth() && e.getX() > posCreerPlaylist[0] && e.getY() < posCreerPlaylist[1] + this.creerPlaylistBtn.getHeight() && e.getY() > posCreerPlaylist[1])
			{
				new FenetreCreationPlayList();
				return;
			}

			if(e.getX() < super.getWidth()/2 + 213 && e.getX() > super.getWidth()/2 + 150 && e.getY() < 68 && e.getY() > 5)
			{
				new FenetreAjouterMusique();
				return;
			}

			if(e.getX() < 132 && e.getX() > 10 && e.getY() < super.getHeight() - 30 && e.getY() > super.getHeight() - 80)
			{
				new FenetreCreerGroupe();
				return;
			}


			if(e.getX() < 264 && e.getX() > 142 && e.getY() < super.getHeight() - 30 && e.getY() > super.getHeight() - 80)
			{
				this.fenetreRejGrp = new FenetreRejoindreGroupe();
				return;
			}

			if(e.getX() < 375 && e.getX() > 325 && e.getY() < 580 && e.getY() > 545)
			{
				Controleur.ServerOut.writeUTF("autoskip");
			}



			for(int i = 0 ; i < this.groups.size() ; i++)
			{
				if(e.getX() < 280 && e.getX() > 0 && e.getY() < (i+1) * 90 + 30 && e.getY() > (i+1) * 90 - 20)
				{
					if(e.getX() > 240 && e.getY() < 280 && this.selectedPlaylist != null && this.groups.get(i) == selectedGroup)
					{
						// Afficher la boîte de dialogue de confirmation
						int response = JOptionPane.showConfirmDialog(
								null,
								"Êtes-vous sûr de vouloir quitter le groupe '" + this.groups.get(i).getNom() + "' ?",
								"Confirmation",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE
						);

						if (response == JOptionPane.YES_OPTION) {
							selectedGroup = groups.getFirst();
							Controleur.sendMsgToServ("group leave " + this.groups.get(i).getNom());
							this.groups.remove(i);
							Controleur.sndReader.arreterMusique();
							this.selectedMusic = null;
							this.selectedPlaylist = null;
						}

						return;
					}

					selectedGroup = this.groups.get(i);
					Controleur.sendMsgToServ("Selection " + this.groups.get(i).getNom() + " " + null + " " + null);

					if(this.groups.get(i).getNom().equals("Aucun Groupe"))
					{
						selectedPlaylist = null;
						selectedMusic = null;
						this.scroll = 0;
					}else{
						this.playlists = selectedGroup.playlistsGroup;
						this.selectedPlaylist = null;
						this.selectedMusic = null;
						this.scroll = 0;
					}
				}
			}



			if(selectedPlaylist != null)
			{
				for(int i = 0 ; i < playlists.size() ; i++)
				{
					if(e.getX() < super.getWidth() && e.getX() > super.getWidth() -  45 && e.getY() < ((this.playlists.indexOf(selectedPlaylist) + 1) * 90) + 30 && e.getY() > ((this.playlists.indexOf(selectedPlaylist) + 1) * 90) - 20)
					{
						// Afficher la boîte de dialogue de confirmation
						int response = JOptionPane.showConfirmDialog(
								null,
								"Êtes-vous sûr de vouloir supprimer la playlist '" + this.selectedPlaylist.getNom() + "' ?",
								"Confirmation",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE
						);


						if (response == JOptionPane.YES_OPTION) {
							this.scroll = 0;
							this.deletePlaylist(this.selectedPlaylist);
							this.selectedPlaylist = null;
						}

						return;
					}
				}

				for(int i = 0 ; i < selectedPlaylist.getMusics().size() ; i++)
				{
					if(e.getX() < super.getWidth()/2 + 200 && e.getX() > super.getWidth()/2 + 170 && e.getY() < ((this.selectedPlaylist.getMusics().indexOf(selectedMusic) + 1 - scroll) * 60) + 62 && e.getY() > ((this.selectedPlaylist.getMusics().indexOf(selectedMusic) + 1 - scroll) * 60) + 20 )
					{
						// Afficher la boîte de dialogue de confirmation
						int response = JOptionPane.showConfirmDialog(
								null,
								"Êtes-vous sûr de vouloir supprimer cette musique '" + this.selectedMusic.getNom() + "' ?",
								"Confirmation",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE
						);


						if (response == JOptionPane.YES_OPTION) {
							this.deleteMusic(selectedMusic);
							selectedMusic = null;
						}

						return;
					}
				}



				for(int i = 0 ; i < selectedPlaylist.getMusics().size() ; i++)
				{
					if(e.getX() < super.getWidth()/2 + 200 && e.getX() > super.getWidth()/2 - 200 && e.getY() < ((i+1) * 60) + 62 && e.getY() > ((i+1) * 60) + 17)
					{
						selectedMusic = selectedPlaylist.getMusics().get(this.scroll + i);
						Controleur.stop = true;
						Controleur.play = false;
						Controleur.sndReader.arreterMusique();
						Controleur.sendMsgToServ("Selection " + selectedGroup.getNom() + " " + selectedPlaylist.getNom() + " " + selectedMusic.getNom());
						return;
					}
				}
			}


			for(int i = 0 ; i < playlists.size() ; i++)
			{
				if(e.getX() < super.getWidth() && e.getX() > super.getWidth() - 280 && e.getY() < ((i+1) * 90) + 30 && e.getY() > ((i+1) * 90) - 20)
				{
					selectedPlaylist = playlists.get(i);
					Controleur.sendMsgToServ("Selection " + selectedGroup.getNom() + " " + selectedPlaylist.getNom() + " " + null);
					this.scroll = 0;
					return;
				}
			}
		}catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public void deletePlaylist(Playlist playlist)
	{
		this.playlists.remove(this.playlists.indexOf(playlist));
		try {
			Controleur.sendMsgToServ("playlist delete " + playlist.getNom() + " " + selectedGroup.getNom());
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void deleteMusic(Music music)
	{
		this.selectedPlaylist.getMusics().remove(this.selectedPlaylist.getMusics().indexOf(music));

		try{
			Controleur.sendMsgToServ("music delete " + music.getNom().replace(" ", "_") + " " + this.selectedPlaylist.getNom() + " " + selectedGroup.getNom());
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	public void setSelectedMusic(Music music)
	{
		this.selectedMusic = music;
	}


	public void addPlaylist(String nom)
	{
		this.playlists.add(new Playlist(nom));
	}

	public ArrayList<Playlist> getPlaylists()
	{
		return this.playlists;
	}



	@Override
	public void mousePressed(MouseEvent e)
	{

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	public void addMusicToPlaylist(String nomPlaylist, String nomMusic, String urlMusic, String nomGroup)
	{
		for(Group g : groups)
		{
			if(g.getNom().equals(nomGroup))
			{
				for(Playlist p : g.playlistsGroup)
				{
					if(p.getNom().equals(nomPlaylist))
					{
						p.getMusics().add(new Music(nomMusic, p, urlMusic));
					}
				}
			}
		}
	}




	public Playlist getSelectedPlaylist()
	{
		return this.selectedPlaylist;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		if(e.getX() > super.getWidth()/2 - 200 && e.getX() < super.getWidth()/2 + 200 && e.getY() > 65 && e.getY() < 370 && selectedPlaylist != null)
		{
			if(e.getWheelRotation() > 0)
			{
				if(this.selectedPlaylist.getMusics().size() > 5)
				{
					this.scroll++;

					if(scroll > this.selectedPlaylist.getMusics().size() - 5)
					{
						this.scroll = this.selectedPlaylist.getMusics().size() - 5;
					}
				}
			}else if(e.getWheelRotation() < 0)
			{
				this.scroll--;
				if(scroll < 0)
				{
					this.scroll = 0;
				}
			}
		}
	}

	public void addGroup(String groupName)
	{
		this.groups.add(new Group(groupName));
	}

	public ArrayList<Group> getGroups()
	{
		return this.groups;
	}

	public JFrame getFenetreRejGrp()
	{
		return this.fenetreRejGrp;
	}

	public Group getGroupsByName(String arg)
	{
		for (Group g : groups)
		{
			if(g.getNom().equals(arg))
			{
				return g;
			}
		}
		return null;
	}

	public Music getMusicbyName(String nomMusic, String nomPlaylist, String nomGroup)
	{
		for(int i = 0 ; i < this.groups.size() ; i++)
		{
			if(this.groups.get(i).getNom().equals(nomGroup))
			{
				for(int j = 0 ; j < this.groups.get(i).playlistsGroup.size() ; j++)
				{
					if(this.groups.get(i).playlistsGroup.get(j).getNom().equals(nomPlaylist))
					{
						for(Music m : this.groups.get(i).playlistsGroup.get(j).getMusics())
						{
							if(m.getNom().equals(nomMusic))
							{
								return m;
							}
						}
					}
				}
				return null;
			}
		}
		return null;
	}

	public Playlist getPlaylistByName(String nomPlaylist)
	{
		for(Group g : groups)
		{
			for(Playlist p : g.playlistsGroup)
			{
				if(p.getNom().equals(nomPlaylist))
				{
					return p;
				}
			}
		}
		return null;
	}
}
