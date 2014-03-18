package no.group3.mindmaster.Network;

import android.content.Context;
import android.os.Handler;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import no.group3.mindmaster.MainActivity;

/**
 * Created by tordly on 06.03.14.
 */
public class Connection {

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

    /** Singleton object of this class */
    private static Connection instance = null;


    private Connection(Context c) {
        this.ctxt = c;
        utils = Utils.getInstance(ctxt);
    }

    public static Connection getInstance(Context ctxt){
        if(instance == null){
            synchronized (Connection.class){
                instance = new Connection(ctxt);
            }
        }
        return instance;
    }

    /**
     * Starts the server thread
     */
    public void serverThread(boolean isGameCreator, int PORT) {
        server = new Server(this, ctxt, isGameCreator, PORT);
        Thread serverThread = new Thread(server);
        serverThread.start();
    }

    /**
     * Starts the client thread
     */
    public void clientThread(String serverIP, int PORT) {
        client = new Client(serverIP, PORT);
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
    public String getIP(){
        return utils.getNetworkInfo().get(Connection.IP_ADDRESSS);
    }
}
