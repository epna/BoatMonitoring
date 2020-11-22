package com.boat.boatmonitoring;

//import android.app.Fragment;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
public class HomeFragment extends Fragment {
    private Handler handler = new Handler();
    public view_dashboard mMainDashBoard;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate ( R.layout.dashboard_view, container, false );

        setHasOptionsMenu(true);
        mMainDashBoard = root.findViewById ( R.id.mdashboard );
        root.setOnTouchListener(new OnSwipeTouchListener(getContext ()) {
            public void onSwipeRight() {
                Fragment fragment;
                FragmentManager fragmentManager = getParentFragmentManager(); // For AppCompat use getSupportFragmentManager
                fragment = new DetailsFragment ();
                fragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment)
                        .commit();
            }
            public void onSwipeLeft() {
                Fragment fragment;
                FragmentManager fragmentManager = getParentFragmentManager(); // For AppCompat use getSupportFragmentManager
                fragment = new ParametersFragment ();
                fragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment)
                        .commit();
            }
        });
        start ();
        return root;
    }
    Runnable my_runnable = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run() {
            mMainDashBoard.tourmMinuteB = dataNavigation.dataNavigation[0][0] ;
            mMainDashBoard.tourmMinuteT = dataNavigation.dataNavigation[1][0] ;
            mMainDashBoard.temperatureB = dataNavigation.dataNavigation[0][2] ;
            mMainDashBoard.temeratureT = dataNavigation.dataNavigation[1][2] ;
            mMainDashBoard.consoInstB = dataNavigation.dataNavigation[0][3] ;
            mMainDashBoard.consoInstT = dataNavigation.dataNavigation[1][3] ;
            mMainDashBoard.heureMoteursB = dataNavigation.dataNavigation[0][4] ;
            mMainDashBoard.heureMoteursT = dataNavigation.dataNavigation[1][4] ;
            mMainDashBoard.batterieB = dataNavigation.dataNavigation[0][6] ;
            mMainDashBoard.batterieT = dataNavigation.dataNavigation[1][6] ;
            mMainDashBoard.speed = dataNavigation.dataNavigation[0][20] ;
            mMainDashBoard.gasLevel=  dataNavigation.dataNavigation[0][7] ;
            mMainDashBoard.invalidate ();
            handler.postDelayed(my_runnable, dataNavigation.delay_interval);

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

