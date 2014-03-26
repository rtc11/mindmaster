package no.group3.mindmaster;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;

import no.group3.mindmaster.Controller.Controller;
import no.group3.mindmaster.Model.KeyPeg;
import no.group3.mindmaster.Model.Model;
import no.group3.mindmaster.Views.*;

import no.group3.mindmaster.Network.Connection;
import no.group3.mindmaster.Network.Utils;

public class MainActivity extends Activity implements PropertyChangeListener{

    private final String TAG = "MindMaster.MainActivity";
    private Controller controller = null;
    private Connection con = null;
    private static MainActivity instance = null;
    private GameScreen gameFragment = null;
    private MainMenu mainMenu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;

        //Create the connection handler
        con = Connection.getInstance(getBaseContext());

        //Create the controller
        controller = Controller.getInstance(getBaseContext());
        Model model = controller.getModel();
        model.addPropertyChangeListener(this);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MainMenu(getBaseContext()))
                    .commit();
        }
    }

    public static MainActivity getInstance(){
        synchronized (MainActivity.class){
            return instance;
        }
    }

    public void startGameFragment(){
        gameFragment = new GameScreen(getBaseContext(), false);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, gameFragment)
                .addToBackStack(null)
                .commit();
    }

    public void restartGameFragment(){
        setContentView(R.layout.activity_main);
        gameFragment = new GameScreen(getBaseContext(), false);
        controller.getModel().reset();

        getFragmentManager().beginTransaction()
                .replace(R.id.container, gameFragment)
                .addToBackStack(null)
                .commit();
    }

    public void mainMenuFragment(){
        setContentView(R.layout.activity_main);
        mainMenu = new MainMenu(getBaseContext());
        controller.getModel().reset();

        getFragmentManager().beginTransaction()
                .replace(R.id.container, mainMenu)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
public void setTurnText(){
    TextView turnText = (TextView)findViewById(R.id.yourturntext);
    if(controller.isMyTurn()){
        turnText.setText("Your turn");
    }
    else{
        turnText.setText("Not your turn");
    }
}


    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {

        Log.d(TAG, "propertyChange fired in MainActivity");

        if(propertyChangeEvent.getPropertyName().equals("Pegs")){

            ImageView opponentKeyPegTopRight,
                    opponentKeyPegTopLeft,
                    opponentKeyPegBottomRight,
                    opponentKeyPegBottomLeft;

            opponentKeyPegBottomLeft = (ImageView) findViewById(R.id.keyPegBottomLeft);
            opponentKeyPegBottomRight = (ImageView) findViewById(R.id.keyPegBottomRight);
            opponentKeyPegTopLeft = (ImageView) findViewById(R.id.keyPegTopLeft);
            opponentKeyPegTopRight = (ImageView) findViewById(R.id.keyPegTopRight);

            ArrayList<ImageView> keyPegImages = new ArrayList<ImageView>();
            keyPegImages.add(opponentKeyPegBottomRight);
            keyPegImages.add(opponentKeyPegBottomLeft);
            keyPegImages.add(opponentKeyPegTopRight);
            keyPegImages.add(opponentKeyPegTopLeft);

            Log.d(TAG, "Trying to add opponents key pegs");

            ArrayList<KeyPeg> keyPegs = (ArrayList<KeyPeg>)propertyChangeEvent.getNewValue();
            Collections.sort(keyPegs);

            for (int i = keyPegs.size() - 1; i >= 0; i--) {
                if (keyPegs.get(i) == KeyPeg.BLACK) {
                    keyPegImages.get(i).setImageResource(R.drawable.black_peg);
                    Log.d(TAG, "black peg added from opponent");
                }
                else if (keyPegs.get(i) == KeyPeg.WHITE) {
                    keyPegImages.get(i).setImageResource(R.drawable.white_peg);
                    Log.d(TAG, "white peg added from opponent");
                }
                else if (keyPegs.get(i) == KeyPeg.TRANSPARENT) {
                    keyPegImages.get(i).setImageResource(R.drawable.empty_peg);
                    Log.d(TAG, "transparent peg added from opponent");
                }
            }
        }
    }
}
