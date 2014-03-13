package no.group3.mindmaster.Network;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by tordly on 10.03.14.
 */
public class Client implements Runnable {

    private static final String TAG = "MindMaster.Client";

    /**
     * The client socket
     */
    private Socket clientSocket;
    /**
     * Sends message over socket
     */
    private PrintWriter printWriter;
    private Context ctxt;
    private String serverIP;

    public Client(Context ctxt, String serverIP) {
        this.ctxt = ctxt;
        this.serverIP = serverIP;
    }

    /**
     * Send message to the server
     */
    public void sendMessage(String message) {

        Log.d(TAG, "Trying to send msg: " + message);

        if(clientSocket != null){
            if (clientSocket.isBound()) {

                try {
                    printWriter = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(clientSocket.getOutputStream())), true);

                    printWriter.println(message);
                    Log.d(TAG, "Sent message; " + message);
                } catch (IOException e) {
                    Log.d(TAG, e.getMessage());
                }
            }
            else{
                Log.d(TAG, "Socket disconnected.");
            }
        }
    }

    @Override
    public void run() {
        try {
            InetAddress serverAddr = InetAddress.getByName(serverIP);

            //Try to connect to the server socket
            clientSocket = new Socket(serverAddr, Connection.PORT);
            Log.d(TAG, "Connected (output-channel)");
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }
}
