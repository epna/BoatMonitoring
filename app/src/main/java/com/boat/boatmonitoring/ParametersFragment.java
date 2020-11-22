package com.boat.boatmonitoring;

import android.content.Context;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;

public class ParametersFragment extends Fragment {

    TextView infos, trame, lastupdate;
    private  Handler handler = new Handler();

    public ParametersFragment() {
        // Required empty public constructor
    }

    public static ParametersFragment newInstance(String param1, String param2) {
        ParametersFragment fragment = new ParametersFragment ();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate ( R.layout.fragment_parameters, container, false );
        setHasOptionsMenu(true);
        infos= root.findViewById ( R.id.infos );
        trame= root.findViewById ( R.id.trame );
        lastupdate= root.findViewById ( R.id.lastupdate );


        root.setOnTouchListener(new OnSwipeTouchListener(getContext ()) {
            public void onSwipeRight() {
                Fragment fragment;
                FragmentManager fragmentManager = getParentFragmentManager(); // For AppCompat use getSupportFragmentManager
                fragment = new HomeFragment ();
                fragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment)
                        .commit();

            }
            public void onSwipeLeft() {
                Fragment fragment;
                FragmentManager fragmentManager = getParentFragmentManager(); // For AppCompat use getSupportFragmentManager
                fragment = new DetailsFragment ();
                fragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment)
                        .commit();
            }
        });
        return root;
    }



    Runnable my_runnable = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run() {
           trame.setText ( dataNavigation.trame );
            final Date date = new Date();
            infos.setText ( String.format ( "%s %s", date.toString (), dataNavigation.getHotspotName ( getContext () ) ) );

          if (dataNavigation.lastupdate != null) lastupdate.setText ( dataNavigation.lastupdate.toString () );
            handler.postDelayed ( my_runnable, dataNavigation.delay_interval );
        }
    };

    public Handler mHandler = new Handler(); // use 'new Handler(Looper.getMainLooper());' if you want this handler to control something in the UI
    // to start the handler
    public void start() {
        handler.postDelayed(my_runnable, dataNavigation.delay_demarrage);
    }

    // to stop the handler
    public void stop() {
        handler.removeCallbacks(my_runnable);
    }

    // to reset the handler
    public void restart() {
        handler.removeCallbacks(my_runnable);
        handler.postDelayed(my_runnable, dataNavigation.delay_demarrage);
    }

    @Override
    public void onPause() {

        super.onPause();
        stop ();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        restart ();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar, menu);
        if (dataNavigation.goodWifi ( getContext () )) menu.getItem(0).setIcon( ContextCompat.getDrawable(getContext (), R.drawable.ic_baseline_wifi_24));
        else menu.getItem(0).setIcon( ContextCompat.getDrawable(getContext (), R.drawable.ic_baseline_wifi_off_24));
        super.onCreateOptionsMenu(menu, inflater);
    }
}