package no.group3.mindmaster.Network;

import android.content.Context;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by tordly on 18.03.14.
 */
public class OutgoingCommunication implements Runnable {

    private final String TAG = "MindMaster.OutgoingCommunication";
    private Socket socket;
    private PrintWriter output;
    private Context ctxt;
    private Connection con;

    public OutgoingCommunication(Socket socket, Context ctxt){
        this.socket = socket;
        this.ctxt = ctxt;
        con = Connection.getInstance(ctxt);

        try{
            output = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())), true);

            Log.d(TAG, "Output-channel running");
            con.setOutputConnectionStatus(true);

        }catch(IOException e){
            Log.d(TAG, e.getMessage());
        }
    }

    /**
     * Send message to the server
     */
    public void sendMessage(String message) {
        Log.d(TAG, "Sending message...: " + message);
        if(socket != null && output != null){
            if (socket.isBound()) {
                output.println(message);
                Log.d(TAG, "Message sent!");
            }
            else{
                Log.d(TAG, "Socket disconnected.");
            }
        }
    }

    @Override
    public void run() {
        //Nothing happends before sendMessage is called
    }
}
