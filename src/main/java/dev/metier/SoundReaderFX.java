package main.java.dev.metier;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import main.java.dev.Controleur;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;

public class SoundReaderFX extends JFrame {
    private MediaPlayer mediaPlayer;
    private Timeline timeline;
    private boolean enPause = false;
    private Duration positionPause;
    private int progress = 0;

    private Duration dureeMusique;
    private Duration tempsActuel;

    private String nomMusic;

    public SoundReaderFX()
    {
        JFXPanel fxPanel = new JFXPanel(); // This will initialize JavaFX
    }

    public int lireMusique(String urlFichier)
    {
        Platform.runLater(() ->
        {

            Media media = new Media(urlFichier);

            if(checkIfFileExists(urlFichier))
            {

                mediaPlayer = new MediaPlayer(media);

                mediaPlayer.setOnReady(() -> {
                    // Démarrer la lecture lorsque le fichier est prêt
                    mediaPlayer.play();

                    this.dureeMusique = media.getDuration();

                    // Lancer le Timer pour mettre à jour la barre de progression
                    lancerProgression();
                });
            }else{
                System.out.println("Erreur de lecture du son.");
            }
        });

        if(this.dureeMusique == null)
        {
            return 0;
        }

        return (int)this.dureeMusique.toSeconds();
    }


    public Runnable lireMusique(String urlFichier, int position)
    {
        Platform.runLater(() ->
        {

            Media media = new Media(urlFichier);

            if(checkIfFileExists(urlFichier))
            {

                mediaPlayer = new MediaPlayer(media);

                mediaPlayer.setOnReady(() -> {
                    // Démarrer la lecture lorsque le fichier est prêt
                    mediaPlayer.seek(Duration.seconds(position));
                    mediaPlayer.play();

                    this.dureeMusique = media.getDuration();

                    // Lancer le Timer pour mettre à jour la barre de progression
                    lancerProgression();
                });
            }else{
                System.out.println("Erreur de lecture du son.");
            }
        });
        return null;
    }


    public Duration getDureeMusique()
    {
        return this.dureeMusique;
    }

    public MediaPlayer getMediaPlayer()
    {
        return mediaPlayer;
    }

    private boolean checkIfFileExists(String fileUrl)
    {
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");  // Utilisation de la méthode HEAD pour éviter de télécharger le fichier
            int responseCode = connection.getResponseCode();

            nomMusic = url.getFile();

            nomMusic = nomMusic.substring(18, nomMusic.length() - 4);


            // Si la réponse est 200 (OK), le fichier existe
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            // Si une exception est lancée (par exemple une mauvaise URL), on considère que le fichier n'existe pas
            e.printStackTrace();
            return false;
        }
    }




    public void mettreEnPause() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            positionPause = mediaPlayer.getCurrentTime();
            mediaPlayer.pause();
            enPause = true;
        }
    }

    public void reprendreLecture() {
        if (mediaPlayer != null && enPause) {
            mediaPlayer.seek(positionPause);
            mediaPlayer.play();
            enPause = false;
        }
    }

    public void arreterMusique() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            if(timeline != null)
                timeline.stop();
            this.progress = 0;
        }
    }

    private void lancerProgression() {
        if(timeline != null)
        {
            timeline.stop();
            timeline = null;
        }


        // Utilisation de Timeline pour JavaFX
        timeline = new Timeline(new KeyFrame(Duration.millis(500), e ->
        {
            if (mediaPlayer != null && mediaPlayer.getTotalDuration() != null) {
                Duration currentTime = mediaPlayer.getCurrentTime();
                Duration totalTime = mediaPlayer.getTotalDuration();

                if (totalTime.equals(currentTime)) {
                    Controleur.stop = true;
                    Controleur.play = false;
                    timeline.stop();
                    this.progress = 0;
                    return;
                }

                if (totalTime.toMillis() > 0) {
                    this.progress = (int) ((currentTime.toMillis() / totalTime.toMillis()) * 100);
                }
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public Duration getTempsActuel()
    {
        return this.mediaPlayer.getCurrentTime();
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SoundReaderFX lecteur = new SoundReaderFX();
            lecteur.setVisible(true);
        });
    }

    public int getMusicProgression()
    {
        return this.progress;
    }

    public void sendFileToServ(String cheminFichier, String nomFichier, String playlist)
    {
        File file = new File(cheminFichier);
        String uriString = file.toURI().toString();
        Media media = new Media(uriString);

        mediaPlayer = new MediaPlayer(media);
        AtomicReference<Double> res = new AtomicReference<>((double) 0);

        mediaPlayer.setOnReady(() ->
        {
            res.set(media.getDuration().toSeconds());


            try
            {
                System.out.println("Passage");

                FileInputStream fis = new FileInputStream(cheminFichier);

                Controleur.sendMsgToServ("upload " + nomFichier + " " + playlist + " " + res.get());

                byte[] buffer = new byte[2048];
                int bytesRead;

                Controleur.transfert = true;

                while ((bytesRead = fis.read(buffer)) != -1)
                {
                    Controleur.ServerOut.write(buffer, 0, bytesRead);
                }

                fis.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}