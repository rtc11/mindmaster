package no.group3.mindmaster.Network;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by tordly on 18.03.14.
 */
public class IncomingCommunication implements Runnable {

    private final String TAG = "MindMaster.IncomingCommunication";
    private Socket socket;
    private BufferedReader input;
    private Handler updateConversationHandler;
    private Context ctxt;
    private Connection con;

    public IncomingCommunication(Socket socket, Handler handler, Context ctxt){
        this.socket = socket;
        this.updateConversationHandler = handler;
        this.ctxt = ctxt;
        this.con = Connection.getInstance(ctxt);

        try{
            input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

            Log.d(TAG, "input-channel running");

        }catch(IOException e){
            Log.d(TAG, e.getMessage());
        }
    }

    @Override
    public void run() {
        while(socket.isConnected()){
            try{
                String read;
                if((read = input.readLine()) != null){
                    Log.d(TAG, "Message received: " + read);
                    updateConversationHandler.post(new UpdateUIThread(read, ctxt, con));
                }
            }catch(IOException e){
                Log.d(TAG, e.getMessage());
            }
        }
        Log.d(TAG, "Socket disconnected");
    }
}
