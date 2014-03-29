package no.group3.mindmaster.Network;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import no.group3.mindmaster.Controller.Controller;
import no.group3.mindmaster.MainActivity;

/**
 * Created by tordly on 06.03.14.
 */
public class Connection {

    private static final String TAG = "MindMaster.Connection";
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
    public static final int PORT = 13443;

    private boolean isOutputCommunicationConnected = false;


    private Connection(Context c) {
        this.ctxt = c;
        utils = Utils.getInstance(ctxt);
    }

    public void setOutputConnectionStatus(boolean status){
        this.isOutputCommunicationConnected = status;
    }

    public boolean isOutputCommunicationConnected(){
        return this.isOutputCommunicationConnected;
    }

    public static Connection getInstance(Context ctxt){
        if(instance == null){
            synchronized (Connection.class){
                instance = new Connection(ctxt);
            }
        }
        return Connection.instance;
    }

    /**
     * Starts the server instance
     */
    public void serverThread() {
        server = new Server(ctxt);
        Thread serverThread = new Thread(server);
        serverThread.start();
    }

    /**
     * Starts the client instance
     */
    public void clientThread(String serverIP) {
        client = new Client(serverIP, ctxt);
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
        if(Controller.getInstance(ctxt).isGameCreator()){
            Log.d(TAG, "isGameCreator = true. Server trying to send message");
            server.sendMessage(message);
        }
        else{
            Log.d(TAG, "isGameCreator = false. JoinGameFragment trying to send message");
            client.sendMessage(message);
        }
    }

    /**
     * Get the IP to this device
     */
    public String getIP(){
        return utils.getNetworkInfo().get(Connection.IP_ADDRESSS);
    }
}
