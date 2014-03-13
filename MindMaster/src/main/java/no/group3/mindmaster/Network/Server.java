package no.group3.mindmaster.Network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import android.os.Handler;

import no.group3.mindmaster.Controller.Controller;
import no.group3.mindmaster.Model.ColorPeg;
import no.group3.mindmaster.Model.ColorPegSequence;
import no.group3.mindmaster.Model.ColorPegSolutionSequence;

/**
 * Created by tordly on 10.03.14.
 */
public class Server implements Runnable {

    public static final String TAG = "MindMaster.Server";

    /** The server socket */
    private ServerSocket serverSocket;
    private Connection con;
    private Context ctxt;
    private Handler updateConversationHandler;

    //TODO: Context can be removed when we no longer use Toast.makeText();
    public Server(Context ctxt, Connection con) {
        this.ctxt = ctxt;
        this.con = con;

        //TODO: this might be called from outside the thread?
        updateConversationHandler = new Handler();
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(Connection.PORT);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        while (!Thread.currentThread().isInterrupted()) {
            try {
                //Client found; accept the incoming connection
                socket = serverSocket.accept();
                Log.d(TAG, "Connected (input-channel)");

                //Start the communication thread
                CommunicationThread communicationThread = new CommunicationThread(socket);
                new Thread(communicationThread).start();

            } catch (IOException ex) {
                Log.d(TAG, ex.getMessage());
            }
        }
    }

    /** Communication thread that listens to incoming messages */
    class CommunicationThread implements Runnable{

        private Socket clientSocket;
        private BufferedReader input;

        public CommunicationThread(Socket clientSocket){
            this.clientSocket = clientSocket;

            try{
                input = new BufferedReader(
                        new InputStreamReader(this.clientSocket.getInputStream()));
            }catch(IOException e){
                Log.d(TAG, e.getMessage());
            }
        }

        /** Receive message from the client */
        public void run(){
            while(clientSocket.isBound()){
                try{
                    if(input.ready()){
                        String read = input.readLine();
                        updateConversationHandler.post(new updateUIThread(read));
                        Log.d(TAG, "Message received: " + read);
                    }
                }catch(IOException e){
                    Log.d(TAG, e.getMessage());
                }
            }
            Log.d(TAG, "Socket disconnected");
        }
    }

    /** Handler for the incoming message */
    class updateUIThread implements Runnable {
        private String msg;

        public updateUIThread(String str) {
            this.msg = str;
        }

        @Override
        public void run() {
            //If the incoming message contains clientip, we need to start the client thread
            if(msg.contains("clientip")){
                String ip = msg.replaceAll("clientip", "");
                con.clientThread(ip);

                //TODO: this is only for watching both ways communication
                con.sendMessage("lolhahahahaha");
            }

            //TODO: this is only for receiving the lolhahahaha message from above
            else if(msg.contains("lol")){
                String test = msg.replaceAll("lol", "");
                Toast.makeText(ctxt, test, Toast.LENGTH_SHORT).show();
            }

            //If the message contains "peg" it means that the solution string has been received.
            else if(msg.contains("peg")){
                String solution = msg.replaceAll("peg", "");
                Log.d(TAG, "Solution received: " + solution);

                //Use singleton to create the solution instance
                ColorPegSolutionSequence seq = ColorPegSolutionSequence.getInstance(false);
                seq.setSolution(Controller.getColorPegSequence(solution));

                //We are the client and received the solution, tell the controller that we are ready
                Controller.isReady = true;
            }
        }
    }
}
