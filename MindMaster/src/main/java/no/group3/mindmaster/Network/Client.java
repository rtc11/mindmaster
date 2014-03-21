package no.group3.mindmaster.Network;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import java.net.InetAddress;
import java.net.Socket;

import no.group3.mindmaster.Controller.Controller;

/**
 * Created by tordly on 10.03.14.
 */
public class Client implements Runnable {

    private static final String TAG = "MindMaster.Client";
    private Socket socket;
    private String serverIP;
    private Context ctxt;
    private Handler handler;
    private Connection con;
    private OutgoingCommunication out;
    private IncomingCommunication in;

    public Client(String serverIP, Context ctxt) {
        this.serverIP = serverIP;
        this.ctxt = ctxt;
        this.handler = new Handler();
        this.con = Connection.getInstance(ctxt);
    }

    @Override
    public void run() {
        try {
            InetAddress serverAddr = InetAddress.getByName(serverIP);
            socket = new Socket(serverAddr, Connection.PORT);
            Log.d(TAG, "Connected to server");

            //Start outgoing communication
            out = new OutgoingCommunication(socket, ctxt);
            new Thread(out).start();

            //Start incoming communication
            in = new IncomingCommunication(socket, handler, ctxt);
            new Thread(in).start();

            //Get the controller to call the new game method
            Controller controller = Controller.getInstance(ctxt);
            controller.newGame(false); //We are not the game creator

        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public void sendMessage(String message){
        out.sendMessage(message);
    }
}
