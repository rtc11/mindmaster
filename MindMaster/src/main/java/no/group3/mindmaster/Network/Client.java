package no.group3.mindmaster.Network;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import no.group3.mindmaster.Views.Connect;
import no.group3.mindmaster.Views.MainMenu;

/**
 * Created by tordly on 10.03.14.
 */
public class Client implements Runnable {

    private static final String TAG = "MindMaster.Client";

    private Socket clientSocket;
    private PrintWriter printWriter;
    private String serverIP;
    private static boolean isConnected = false;
    private int PORT;

    public Client(String serverIP, int PORT) {
        this.serverIP = serverIP;
        this.PORT = PORT;
    }

    /**
     * Send message to the server
     */
    public void sendMessage(String message) {

        Log.d(TAG, "Sending message...: " + message);

        if(clientSocket != null){
            if (clientSocket.isBound()) {

                try {
                    printWriter = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(clientSocket.getOutputStream())), true);

                    printWriter.println(message);
                    Log.d(TAG, "Message sent!");
                } catch (IOException e) {
                    Log.d(TAG, e.getMessage());
                }
            }
            else{
                Log.d(TAG, "Socket disconnected.");
            }
        }
    }

    public static boolean isConnected(){
        return Client.isConnected;
    }

    @Override
    public void run() {
        try {
            InetAddress serverAddr = InetAddress.getByName(serverIP);
            clientSocket = new Socket(serverAddr, PORT); //Trying to connect
            Client.isConnected = true;
            Log.d(TAG, "(Output) Connected!");
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }
}
