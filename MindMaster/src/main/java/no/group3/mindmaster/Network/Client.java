package no.group3.mindmaster.Network;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by tordly on 10.03.14.
 */
public class Client implements Runnable {

    private static final String TAG = "Client";

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

    @Override
    public void run() {
        try {
            InetAddress serverAddr = InetAddress.getByName(serverIP);
            clientSocket = new Socket(serverAddr, Connection.PORT);
            printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
//            printWriter.println("DETTE KOMMER FRA: " + utils.getNetworkInfo().get(Connection.IP_ADDRESSS));
            clientSocket.close();
            printWriter.close();

        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }
}
