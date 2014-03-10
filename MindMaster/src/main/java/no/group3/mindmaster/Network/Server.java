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
    /** The client socket */

    //INPUT
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;
    private String message;

    //OUTPUT
    private PrintWriter send;

    private Context ctxt;
    private Handler updateConversationHandler;

    public Server(Context ctxt) {
        this.ctxt = ctxt;
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

//                inputStreamReader = new InputStreamReader(socket.getInputStream());
//                bufferedReader = new BufferedReader(inputStreamReader); //get the client message
//                message = bufferedReader.readLine();
//                updateConversationHandler.post(new updateUIThread(message));
//                inputStreamReader.close();
//                socket.close();

            } catch (IOException ex) {
                Log.d(TAG, ex.getMessage());
            }
        }
    }

    class updateUIThread implements Runnable {
        private String msg;

        public updateUIThread(String str) {
            this.msg = str;
        }

        @Override
        public void run() {
            Toast.makeText(ctxt, msg, Toast.LENGTH_SHORT).show();
            Log.d(TAG, msg);
        }
    }

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
}
