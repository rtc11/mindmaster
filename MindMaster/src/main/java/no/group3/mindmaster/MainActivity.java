package no.group3.mindmaster;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import no.group3.mindmaster.Network.Connection;
import no.group3.mindmaster.Network.Utils;

public class MainActivity extends Activity {

    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MainMenu())
                    .commit();
        }

        utils = new Utils(getBaseContext());
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

    public class NewGame extends Fragment{

        public NewGame(){
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState){
            View rootView = inflater.inflate(R.layout.new_game, container, false);

            return rootView;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public class MainMenu extends Fragment {

        public MainMenu() {
        }

       @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.menu, container, false);
           Button howToButton = (Button) rootView.findViewById(R.id.buttonHowTo);
           Button newGameButton = (Button) rootView.findViewById(R.id.buttonNewGame);
           howToButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   getFragmentManager().beginTransaction()
                           .replace(R.id.container, new HowTo())
                           .commit();
               }
           });
           newGameButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   getFragmentManager().beginTransaction()
                           .replace(R.id.container, new NewGame())
                           .commit();
               }
           });


           TextView ipaddress = (TextView) rootView.findViewById(R.id.ipaddress);
           ipaddress.setText(utils.getNetworkInfo().get(Connection.IP_ADDRESSS));

            return rootView;
        }
    }

  public class HowTo extends Fragment {

        public HowTo() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.how_to, container, false);
            Button howToButton = (Button) rootView.findViewById(R.id.buttonmain);
            howToButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getFragmentManager().beginTransaction()
                            .replace(R.id.container, new MainMenu())
                            .commit();
                }
            });
            return rootView;
        }
    }

}
