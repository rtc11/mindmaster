package no.group3.mindmaster.Network;

import android.content.Context;
import android.util.Log;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import android.os.Handler;
import no.group3.mindmaster.Controller.Controller;

/**
 * Created by tordly on 10.03.14.
 */
public class Server implements Runnable {

    public static final String TAG = "MindMaster.Server";

    /** The server socket */
    private ServerSocket serverSocket;
    private Socket socket;
    private Connection con;
    private Handler handler;
    private Context ctxt;
    private IncomingCommunication in;
    private OutgoingCommunication out;

    public Server(Context ctxt) {
        this.con = Connection.getInstance(ctxt);
        this.ctxt = ctxt;
        this.handler = new Handler();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(Connection.PORT);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        //Accepts thousands of connections
        while (!Thread.currentThread().isInterrupted()) {
            try {
                socket = serverSocket.accept();
                Log.d(TAG, "Connected to client");

                //Start outgoing communication
                out = new OutgoingCommunication(socket, ctxt);
                new Thread(out).start();

                //Start incoming communication
                in = new IncomingCommunication(socket, handler, ctxt);
                new Thread(out).start();

                //Get the controller to call the new game method
                Controller controller = Controller.getInstance(ctxt, con);
                controller.newGame(true); //We are the game creator

            } catch (IOException ex) {
                Log.d(TAG, ex.getMessage());
            }
        }
    }

    public void sendMessage(String message){
        out.sendMessage(message);
    }
}
