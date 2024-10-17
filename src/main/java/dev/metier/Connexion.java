package main.java.dev.metier;



import main.java.dev.Controleur;

import java.util.Arrays;

public class Connexion
{

	private String nomUtilisateur;
	private String mdp;
	private boolean estConnecter;

	public Connexion(String nomUtilisateur, char[] mdp)
	{
		this.nomUtilisateur = nomUtilisateur;
		this.mdp = "";

		for (char c : mdp)
		{
			this.mdp = this.mdp + c;
		}

		this.essaieMdp();
	}

	public boolean essaieMdp()
	{
		try{
			if(this.nomUtilisateur.isEmpty() || this.mdp.isEmpty())
				return false;
			Controleur.sendMsgToServ("connexion " + this.nomUtilisateur + " " + this.mdp);
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}


	public boolean getEstConnecter()
	{
		return this.estConnecter;
	}
}
