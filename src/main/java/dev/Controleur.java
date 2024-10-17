package main.java.dev;



import main.java.dev.gui.menu.FenetrePrincipale;
import main.java.dev.gui.menu.Menu;
import main.java.dev.gui.menu.PrincipalPanel;
import main.java.dev.metier.Playlist;
import main.java.dev.metier.SoundReaderFX;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import static main.java.dev.gui.menu.PrincipalPanel.*;


public class Controleur {
	private static Menu menu;
	public static FenetrePrincipale fntrPrincipal;
	public static Socket socket;
	public static DataOutputStream ServerOut;
	public static DataInputStream ServerIn;
	public static InputStream serverFileIn;
	public static OutputStream outputStream;
	private int code;
	private String[] args;
	public static SoundReaderFX sndReader;
	public static boolean play = false;
	public static boolean stop = true;
	public static boolean transfert = false;

	public static String userName;

	public Controleur() {
		try {
			new Thread(() -> sndReader = new SoundReaderFX()).start();

			Thread thd = new Thread(this::connexionServeur);
			thd.start();

			SwingUtilities.invokeLater(() -> menu = new Menu(code));

		} catch (Exception e) {
			this.menu = new Menu(1);
		}
	}

	public static PrincipalPanel getPrcplPnl() {
		return Controleur.fntrPrincipal.getPrcplPnl();
	}

	public static SoundReaderFX getSoundReader() {
		return sndReader;
	}

	public static String getUserName()
	{
		return userName;
	}

	// Méthode pour envoyer un fichier au serveur
	public static boolean sendFileToServer(String nomFichier, String cheminFichier, String playlist)
	{
		try {
			// Envoyer la commande d'upload avec le nom du fichier et la playlist
			new Thread(new Runnable() {
				@Override
				public void run()
				{
					Controleur.sndReader.sendFileToServ(cheminFichier, nomFichier, playlist);
				}
			}).start();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}


	public static void sendMsgToServ(String msg)
	{
		try{
			if(!transfert)
			{
				ServerOut.writeUTF(msg);
			}
			else{
				System.out.println(msg);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	public Runnable connexionServeur()
	{
		try {
			socket = new Socket("90.108.151.200", 8080); //ip : 90.108.151.200
			this.code = 0;

			ServerIn = new DataInputStream(socket.getInputStream());
			ServerOut = new DataOutputStream(socket.getOutputStream());

			String reponse;
			while (true)
			{
				reponse = ServerIn.readUTF();

				//System.out.println("Réponse du serveur : " + reponse);

				args = reponse.split(" ");


				switch (args[0]) {
					case "inscription": {
						if (args[1].contains("true")) {
							System.out.println("Inscription réussie");
							this.menu.getMenuPanel().getInscriptionFrame().dispose();
						} else {
							menu.getMenuPanel().setLblErreurI("Nom d'utilisateur déjà pris");
						}
						break;
					}
					case "connexion": {
						if (args[1].contains("true")) {
							System.out.println("Connexion réussie");
							this.menu.dispose();
							this.fntrPrincipal = new FenetrePrincipale(args[2]);
							this.userName = args[2];
							ServerOut.writeUTF("Playlists " + this.userName);
						} else {
							System.out.println("Nom d'utilisateur ou mot de passe incorrect");
							menu.getMenuPanel().setLblErreur("Nom d'utilisateur ou mot de passe incorrect.");
						}
						break;
					}
					case "Playlist":
					{
						if (!args[1].equals("'vide'"))
						{
							for(int i = 0 ; i < fntrPrincipal.getPrcplPnl().getGroups().size() ; i++)
							{
								if(fntrPrincipal.getPrcplPnl().getGroups().get(i).getNom().equals(args[2]) && fntrPrincipal.getPrcplPnl().getGroups().get(i).getPlaylistByName(args[1]) == null)
								{
									this.fntrPrincipal.getPrcplPnl().getGroups().get(i).addPlaylist(new Playlist(args[1]));
								}
							}
						}
						break;
					}
					case "Music":
					{
						if(fntrPrincipal.getPrcplPnl().getMusicbyName(args[2], args[1], args[4]) == null && selectedGroup.getNom().equals(args[4]))
							fntrPrincipal.getPrcplPnl().addMusicToPlaylist(args[1], args[2], args[3], args[4]);
						break;
					}
					case "Group":
					{
						if(fntrPrincipal.getPrcplPnl().getGroupsByName(args[1]) == null) //Si le groupe n'est pas ajouter
							fntrPrincipal.getPrcplPnl().addGroup(args[1]);
						break;
					}
					case "Selection": //Selection group playlist music
					{
						if(fntrPrincipal.getPrcplPnl().getPlaylistByName(args[2]) != null && fntrPrincipal.getPrcplPnl().getGroupsByName(args[1]) != null && !selectedGroup.getNom().equals("Playlist Local"))
						{
							if(!args[2].equals("null"))
								selectedPlaylist = fntrPrincipal.getPrcplPnl().getPlaylistByName(args[2]);
							else
								selectedPlaylist = null;

							if(!args[3].equals("null"))
								selectedMusic = fntrPrincipal.getPrcplPnl().getMusicbyName(args[3], args[2], args[1]);
							else
								selectedMusic = null;
						}
						break;
					}
					case "Play":
					{
						String nomMusic = args[1];

						if(stop && !play && args.length < 4)
						{
							Controleur.stop = false;
							Controleur.play = true;
							if(args.length > 2 && Integer.parseInt(args[2]) > 0)
							{
								new Thread(Controleur.sndReader.lireMusique(nomMusic, Integer.parseInt(args[2]))).start();
							}
							else
							{
								new Thread(new Runnable()
								{
									@Override
									public void run()
									{
										Controleur.sndReader.lireMusique(nomMusic);
									}
								}).start();
							}
						}
						else if(!play && args.length < 4)
						{
							Controleur.sndReader.reprendreLecture();
							Controleur.play = true;
						}

						if(args.length == 4 && selectedMusic != null)
						{
							Controleur.sendMsgToServ("Play " + selectedGroup.getNom() + " " + selectedPlaylist.getNom() + " " + selectedMusic.getNom());
						}

						break;
					}
					case "Pause":
					{
						Controleur.sndReader.mettreEnPause();
						Controleur.play = false;
						break;
					}
					case "Stop":
					{
						Controleur.sndReader.arreterMusique();
						Controleur.stop = true;
						Controleur.play = false;
						break;
					}
					case "autoskip":
					{
						if(selectedPlaylist != null)
						{
							if(args[1].equals("true"))
								selectedPlaylist.setAutoSkip(true);
							else
								selectedPlaylist.setAutoSkip(false);
						}
						break;
					}
					case "transfertEnd" :
					{
						System.out.println("Fin de transfert");
						transfert = false;
						break;
					}
					default: break;
				}
			}

		} catch (Exception e) {
			System.out.println("Déconnexion");
			this.code = 1;
		}

		return null;
	}
}
