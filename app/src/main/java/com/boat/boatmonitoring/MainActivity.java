package com.boat.boatmonitoring;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.google.android.material.bottomnavigation.BottomNavigationView;
public class MainActivity extends AppCompatActivity implements interfaceUDP {

    private Configuration newConfig;
    public Context  context;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 30; j++) {
                dataNavigation.dataNavigation[i][j] = 0f;
            }
        }
        context = getApplicationContext ();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setSubtitle("Using ToolBar");
        //getSupportActionBar ().setIcon (  );
        //getSupportActionBar().setIcon(R.drawable.ic_baseline_wifi_24)        ;

        startListenningUDP ();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       String xxx= dataNavigation.getHotspotName (getApplicationContext ());
        Toast.makeText(getApplicationContext(), xxx, Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        //getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onUDPreceive(final String result) {
        Log.d("ooo",result);
    }

    @Override
    protected void onResume() {
        super.onResume ();
        startListenningUDP ();
    }

    @Override
    protected void onPause() {
        Intent stopIntent = new Intent ( this, listenningUDP.class );
        stopIntent.putExtra ( "action", "stop" );
        startService ( stopIntent );
        super.onPause ();
    }

    public void startListenningUDP() {
        listenningUDP.setOnStopTrackEventListener ( this );
        Intent startIntent = new Intent ( this, listenningUDP.class );
        startIntent.putExtra ( "action", "start" );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startService (startIntent);
        } else {
            context.startService(startIntent);        }
    }
}
