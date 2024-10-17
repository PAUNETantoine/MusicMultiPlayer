package main.java.dev.metier;



import main.java.dev.Controleur;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSendFile
{

	public ClientSendFile(String name, String nomFichier, String playlist, int res) throws IOException
	{
		Socket fileSocket = new Socket("localhost", 5000);
		FileInputStream fis = new FileInputStream(name);
		OutputStream os = fileSocket.getOutputStream();
		PrintWriter serverOut = new PrintWriter(fileSocket.getOutputStream(), true);

		//serverOut.println("upload " + nomFichier + " " + playlist + " " + res);
		Controleur.ServerOut.writeUTF("upload " + nomFichier + " " + playlist + " " + res);

		byte[] buffer = new byte[32768];
		int bytesRead;

		while ((bytesRead = fis.read(buffer)) != -1) {
			os.write(buffer, 0, bytesRead);
		}

		os.flush();
		fis.close();
		fileSocket.close();  // Fermer seulement la socket du fichier
	}
}