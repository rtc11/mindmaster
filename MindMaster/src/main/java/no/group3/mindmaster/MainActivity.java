package no.group3.mindmaster;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import no.group3.mindmaster.Controller.Controller;
import no.group3.mindmaster.Views.*;

import no.group3.mindmaster.Network.Connection;
import no.group3.mindmaster.Network.Utils;

public class MainActivity extends Activity {

    private Controller controller = null;
    private Connection con = null;
    private static MainActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;

        //Create the connection handler
        con = Connection.getInstance(getBaseContext());

        //Create the controller
        controller = Controller.getInstance(getBaseContext());

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
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new GameScreen(getBaseContext(), false))
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



}
