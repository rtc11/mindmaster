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

import android.os.Handler;

/**
 * Created by tordly on 10.03.14.
 */
public class Server implements Runnable {

    public static final String TAG = "Server";

    /** The server socket */
    private ServerSocket serverSocket;
    private Connection con;
    private Context ctxt;
    private Handler updateConversationHandler;

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
                //Client found; accept the connection
                socket = serverSocket.accept();
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
                this.input = new BufferedReader(
                        new InputStreamReader(this.clientSocket.getInputStream()));
            }catch(IOException e){
                Log.d(TAG, e.getMessage());
            }
        }

        /** Receive message from the client */
        public void run(){
            while(!Thread.currentThread().isInterrupted()){
                try{
                    String read = input.readLine();
                    updateConversationHandler.post(new updateUIThread(read));
                }catch(IOException e){
                    Log.d(TAG, e.getMessage());
                }
            }
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
            Toast.makeText(ctxt, msg, Toast.LENGTH_SHORT).show();
            Log.d(TAG, msg);

            //If the incoming message contains clientip, we need to start the client thread
            if(msg.contains("clientip")){
                String ip = msg.replaceAll("clientip", "");
                Log.d(TAG, "msg: " + msg + ", ip: " + ip);
                con.clientThread(ip);
            }
            else if(msg.contains("lol")){
                String test = msg.replaceAll("lol", "");
                Toast.makeText(ctxt, test, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
