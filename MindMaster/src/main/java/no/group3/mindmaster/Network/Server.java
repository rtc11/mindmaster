package no.group3.mindmaster.Network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import android.os.Handler;

import no.group3.mindmaster.Controller.Controller;
import no.group3.mindmaster.MainActivity;
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
    private Handler updateConversationHandler;
    private Context ctxt;
    private boolean isGameCreator;
    private int PORT;

    public Server(Connection con, Context ctxt, boolean isGameCreator, int PORT) {
        this.con = con;
        this.ctxt = ctxt;
        this.isGameCreator = isGameCreator;
        this.PORT = PORT;
        this.updateConversationHandler = new Handler();
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        while (!Thread.currentThread().isInterrupted()) {
            try {
                //Client found; accept the incoming connection
                socket = serverSocket.accept();
                Log.d(TAG, "(Input) Connected! GameCreator: " + isGameCreator);

                //If we host the game, we also need to send messages to the client
                if(isGameCreator){
                    Log.d(TAG, "(Output) Connecting...");
                    //Now we need to start the client thread (output-channel)
                    con.clientThread(socket.getInetAddress().getHostAddress(), PORT);
                }

                //Get the controller to call the new game method
                Controller controller = Controller.getInstance(ctxt, con);
                controller.newGame(isGameCreator);

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

            //If the message contains "peg" it means that the solution string has been received.
            if(msg.contains("peg")){
                String solution = msg.replaceAll("peg", "");
                Log.d(TAG, "Solution received: " + solution);

                //Get the solution from the host
                ColorPegSolutionSequence seq = ColorPegSolutionSequence.getInstance(false);
                Controller controller = Controller.getInstance(ctxt, con);
                seq.setSolution(controller.getColorPegSequence(solution));

                //We are the client and received the solution, tell the controller that we are ready
                Controller.isReady = true;
            }
            //The opponent is still waiting for the solution
            else if(msg.contains("waiting")){

                //Send the solution to the opponent
                ColorPegSolutionSequence seq = ColorPegSolutionSequence.getInstance(false);
                Controller controller = Controller.getInstance(ctxt, con);
                String solution = controller.getColorPegSequenceString(seq.getSolution());
                con.sendMessage("peg" + solution);
            }
        }
    }
}
