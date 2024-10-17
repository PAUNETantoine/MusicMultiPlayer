package main.java.dev.metier;

import java.util.ArrayList;

public class Playlist
{
    private String nom;
    private Group group;
    private ArrayList<Music> musics;
    private boolean autoSkip;

    public Playlist(String nom)
    {
        this.nom = nom;
        this.group = null;
        this.musics = new ArrayList<>(20);
        this.autoSkip = true;
    }

    public String getNom()
    {
        return nom;
    }

    public ArrayList<Music> getMusics()
    {
        return this.musics;
    }

    public Group getGroup()
    {
        return this.group;
    }

    public void addMusic(Music m)
    {
        this.musics.add(m);
    }

    public void setAutoSkip(boolean b)
    {
        this.autoSkip = b;
    }

	public boolean getAutoSkip()
    {
        return this.autoSkip;
	}
}
