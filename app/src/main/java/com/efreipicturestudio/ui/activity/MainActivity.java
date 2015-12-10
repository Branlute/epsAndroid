package com.efreipicturestudio.ui.activity;

import android.animation.ValueAnimator;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;

import com.efreipicturestudio.R;
import com.efreipicturestudio.ui.fragment.common.EPSFragment;
import com.efreipicturestudio.ui.fragment.common.photo.ListAlbumsFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    //region Attributes


    //region Stack

    private EPSFragment rootFragment;


    //endregion Stack


    //region DrawLayer

    private ActionBarDrawerToggle toggle;
    private boolean backButtonVisible = false;
    @Bind(R.id.toolbar)
    protected Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    protected DrawerLayout drawer;

    //endregion DrawLayer


    //endregion Attributes


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationOnClickListener(this);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    //region DrawLayer

    /**
     * Fonction appelé au clic sur le bouton du menu ou du back
     * @param view La vue qui déclenche l'action
     */
    @Override
    public void onClick(View view) {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if (backButtonVisible) {//Si on a la bouton back d'afficher, on pop un fragment de la stack
                popFragment(true);
            }
            else {
                drawer.openDrawer(GravityCompat.START);
            }
        }
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        EPSFragment fragment = null;
        if (id == R.id.nav_camera) {
            fragment = ListAlbumsFragment.newInstance();
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            fragment = new EPSFragment();
        } else if (id == R.id.nav_slideshow) {
            fragment = new EPSFragment();
        } else if (id == R.id.nav_manage) {
            fragment = new EPSFragment();
        } else if (id == R.id.nav_share) {
            fragment = new EPSFragment();
        } else if (id == R.id.nav_send) {
            fragment = new EPSFragment();
        }


        if (fragment != null) {
            pushFragment(fragment);
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void showBackButton(boolean value) {
        if (backButtonVisible == value) {//Si l'état est déjà bon
            return;
        }
        ValueAnimator anim = ValueAnimator.ofFloat(value ? 0 : 1, value ? 1 : 0);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float slideOffset = (Float) valueAnimator.getAnimatedValue();
                toggle.onDrawerSlide(null, slideOffset);
            }
        });
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(500);
        anim.start();
        backButtonVisible = value;
    }

    //endregion DrawLayer

    //region Navigation

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //Si on peut faire un back sur la satck des fragments
            if (getCountOfFragmentInStack() > 1) {
                popFragment(true);
            }
            else {// Sinon on fait le back par défault
                super.onBackPressed();

            }
        }
    }


    public void pushFragment(EPSFragment newFragment, boolean shouldSave) {
        if (getFragmentManager().getBackStackEntryCount() >= 1) {
            showBackButton(true);
        }
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, newFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (shouldSave) {
            transaction.addToBackStack(newFragment.getUniqueId());
        }
        transaction.commit();
    }


    public void pushFragment(EPSFragment newFragment) {
        pushFragment(newFragment, true);

    }


    public void pushFragmentWithNoSave(EPSFragment newFragment) {
        pushFragment(newFragment, false);

    }


    public boolean popFragment(boolean immediately) {
        if (getFragmentManager().getBackStackEntryCount() > 1) {

            if (getFragmentManager().getBackStackEntryCount() == 2) {
                showBackButton(false);
            }
            if (immediately) {
                return getFragmentManager().popBackStackImmediate();
            } else {
                getFragmentManager().popBackStack();
                return true;
            }
        }
        return false;
    }

    public boolean popToFragment(EPSFragment fragment, boolean immediately) {

        if (getFragmentManager().getBackStackEntryCount() > 1) {

            if (immediately) {
                return getFragmentManager().popBackStackImmediate(fragment.getUniqueId(), 0);
            } else {
                getFragmentManager().popBackStack(fragment.getUniqueId(), 0);
                return true;
            }
        }
        return false;
    }


    public boolean popToRootFragment(boolean immediately) {
        return popToFragment(rootFragment, immediately);
    }


    public Fragment popToClassOfFragment(Class classFragment) {
        for (int i = getFragmentManager().getBackStackEntryCount() - 1; i > 0; i--) {
            String nameTransaction = getFragmentManager().getBackStackEntryAt(i).getName();
            if (nameTransaction.contains(classFragment.getName())) {
                getFragmentManager().popBackStackImmediate(nameTransaction, 0);
                return getCurrentFragment();
            }
        }
        return null;
    }

    public Fragment getCurrentFragment() {
        return getFragmentManager().findFragmentById(R.id.content_frame);
    }

    public int getCountOfFragmentInStack() {
        return getFragmentManager().getBackStackEntryCount();
    }

    //endregion Navigation

}
