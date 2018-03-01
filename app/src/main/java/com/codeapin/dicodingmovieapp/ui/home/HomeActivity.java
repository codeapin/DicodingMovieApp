package com.codeapin.dicodingmovieapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.codeapin.dicodingmovieapp.R;
import com.codeapin.dicodingmovieapp.ui.movielist.ListMovieFragment;
import com.codeapin.dicodingmovieapp.ui.searchmovie.SearchMovieActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.result_tabs)
    TabLayout resultTabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setupView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                break;
            case R.id.action_search:
                startActivity(new Intent(this, SearchMovieActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_now_playing:
                resultTabs.getTabAt(0).select();
                break;
            case R.id.nav_upcoming:
                resultTabs.getTabAt(1).select();
                break;
            case R.id.nav_favorite:
                resultTabs.getTabAt(2).select();
                break;
            case R.id.nav_about:
                new AlertDialog.Builder(this)
                        .setMessage(R.string.home_hello_world)
                        .setPositiveButton("OK", null)
                        .show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupView() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);
        navView.getMenu().getItem(0).setChecked(true);

        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        fragmentAdapter.addFragment(ListMovieFragment.newInstance(ListMovieFragment.SORT_ORDER_NOW_PLAYING), getString(R.string.nav_now_playing));
        fragmentAdapter.addFragment(ListMovieFragment.newInstance(ListMovieFragment.SORT_ORDER_UPCOMING), getString(R.string.nav_upcoming));
        fragmentAdapter.addFragment(ListMovieFragment.newInstance(ListMovieFragment.SORT_ORDER_FAVORIT), getString(R.string.nav_favorite));
        viewpager.setAdapter(fragmentAdapter);
        resultTabs.setupWithViewPager(viewpager);
        resultTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                navView.getMenu().getItem(tab.getPosition()).setChecked(true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
