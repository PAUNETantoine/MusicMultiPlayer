package main.java.dev.metier;

import java.util.ArrayList;

public class Group
{
    public ArrayList<Playlist> playlistsGroup = new ArrayList<>(10);
    private String nom;
    private ArrayList<String> users;
    private ArrayList<String> connectedUsers;
    public Playlist selectedPlaylist;
    public Music selectedMusic;

    public Group(String nom)
    {
        this.nom = nom;
    }

    public String getNom()
    {
        return this.nom;
    }

    public void addPlaylist(Playlist playlist)
    {
        this.playlistsGroup.add(playlist);
    }

    public Object getPlaylistByName(String arg)
    {
        for(Playlist p : playlistsGroup)
        {
            if(p.getNom().equals(arg))
            {
                return p;
            }
        }
        return null;
    }
}
