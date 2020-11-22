package com.boat.boatmonitoring;

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
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsFragment extends Fragment {

    public compass_view mcompass_view;
    public Float oldcap=0f;
    private  Handler handler = new Handler();

    public ImageView mneedle;
    public TextView value1, value2, value3,value4,value5,value6,value7, depth,temperature,distancetowp, distancetodest,positionN, positionE, cap;
    public DetailsFragment() {
        // Required empty public constructor
    }
    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment ();

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
        View root=inflater.inflate ( R.layout.fragment_details, container, false );
        setHasOptionsMenu(true);
        mcompass_view  = root.findViewById ( R.id.compasscust);
        value1 = root.findViewById ( R.id.value1 );
        value2 = root.findViewById ( R.id.value2 );
        value3 = root.findViewById ( R.id.value3 );
        value4 = root.findViewById ( R.id.value4 );
        value5 = root.findViewById ( R.id.value5 );
        value6 = root.findViewById ( R.id.value6 );
        value7 = root.findViewById ( R.id.value7 );
        depth = root.findViewById ( R.id.depth );
        cap = root.findViewById ( R.id.cap );
        temperature = root.findViewById ( R.id.temperature );
        distancetodest = root.findViewById ( R.id.distancetodest );
        distancetowp = root.findViewById ( R.id.distancetowp );
        positionE = root.findViewById ( R.id.positionE );
        positionN = root.findViewById ( R.id.positionN );
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
                fragment = new HomeFragment ();
                fragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment)
                        .commit();
            }
        });
        mneedle = root.findViewById ( R.id.needle );
        RotateAnimation rotate2 = new RotateAnimation(oldcap, -dataNavigation.dataNavigation[0][23] ,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rotate2.setDuration(4000);
        mcompass_view.setAnimation(rotate2);
        return  root;
    }


    final Runnable my_runnable = new Runnable () {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run() {
            value1.setText ( String.format ( "%s L", dataNavigation.dataNavigation[0][7] )) ;
            value2.setText ( String.format ( "%s L", dataNavigation.dataNavigation[0][9] )) ;
            value3.setText ( String.format ( "%s L/h", dataNavigation.dataNavigation[0][3] )) ;
            try {
                value4.setText ( String.format ( "%s L/h", dataNavigation.dataNavigation[0][1] / dataNavigation.dataNavigation[0][2] ) );

            } finally {
                value4.setText ("0 L/h");
            }
            try {
                value5.setText ( String.format ( "%s h ", dataNavigation.dataNavigation[0][1]/dataNavigation.dataNavigation[0][3] )) ;
            } finally {
                value5.setText ("0 h");
            }

            value6.setText ( String.format ( "%s nm" , dataNavigation.dataNavigation[0][5] )) ;
            value7.setText ( String.format ( "%s L", dataNavigation.dataNavigation[0][6] )) ;


            positionN.setText ( String.format ( "Lat. :  %s °", dataNavigation.dataNavigation[0][21] ) );
            positionE.setText ( String.format ( "Long. : %s °", dataNavigation.dataNavigation[0][22] ) );
            temperature.setText ( String.format ( "%s °C", dataNavigation.dataNavigation[0][25] ) );
            depth.setText ( String.format ( "%s m", dataNavigation.dataNavigation[0][24] ) );
            distancetodest.setText ( String.format ( "to dest. %s nm", dataNavigation.dataNavigation[0][27] ) );
            distancetowp.setText ( String.format ( "to WP %s nm", dataNavigation.dataNavigation[0][26] ) );
            cap.setText ( String.format ( "%s °", dataNavigation.dataNavigation[0][23] ) );
            RotateAnimation rotate = new RotateAnimation(-oldcap, -dataNavigation.dataNavigation[0][23] ,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                    0.5f);
            oldcap = dataNavigation.dataNavigation[0][23] ;
            rotate.setDuration(4000);
            mcompass_view.setAnimation(rotate);
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