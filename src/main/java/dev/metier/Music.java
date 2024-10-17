package main.java.dev.metier;

public class Music
{
    private String nom;
    private Playlist playlist;
    private String url;

    public Music(String nom, Playlist playlist, String url)
    {
        this.nom = nom;
        this.playlist = playlist;
        this.url = url;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public String getNom() {
        return nom;
    }

    public String getUrl() {
        return url;
    }
}
