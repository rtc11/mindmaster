package no.group3.mindmaster.Network;

import android.content.Context;
import android.os.Handler;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by tordly on 06.03.14.
 */
public class Connection {

    /** The application runs on this port */
    public static final int PORT = 53442;

    //HAHSMAP KEYS
    public static String DNS1 = "DNS1";
    public static String DNS2 = "DNS2";
    public static String DEFAULT_GATEWAY = "DEFAULT_GATEWAY";
    public static String IP_ADDRESSS = "IP_ADDRESS";
    public static String SUBNET_MASK = "SUBNET_MASK";
    public static String SERVER_IP = "SERVER_IP";
    public static String TO_STRING = "TOSTRING";

    /** The server runnable */
    private Server server;
    /** The client runnable */
    private Client client;
    /** Utils for the network connectivity */
    private Utils utils;
    /** Context of the current activity */
    private Context ctxt;

    //TODO: singleton?
    public Connection(Context c) {
        this.ctxt = c;
        utils = new Utils(ctxt);

        //Server thread will always run (looking for incoming connections)
        serverThread();
    }

    /**
     * Starts the server thread
     */
    private void serverThread() {
        server = new Server(ctxt, this);
        Thread serverThread = new Thread(server);
        serverThread.start();
    }

    /**
     * Starts the client thread
     */
    public void clientThread(final String serverIP) {
        client = new Client(ctxt, serverIP);
        Thread clientThread = new Thread(client);
        clientThread.start();
    }

    /**
     * Instructs the client to send a message
     * 
     * @param message - A String containing the message to be sent. If the message is an IP-address
     *                the message must start with the text "clientip" e.g.: "clientip129.0.0.1".
     *                If the message contains a ColorPegSequence, it must start with "peg", followed
     *                by the first letter of the peg indicating its color.
     */
    public void sendMessage(String message) {
        client.sendMessage(message);
    }
}
