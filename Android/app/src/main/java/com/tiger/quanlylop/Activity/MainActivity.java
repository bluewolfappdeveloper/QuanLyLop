package com.tiger.quanlylop.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.tiger.quanlylop.Fragment.AccountFragment;
import com.tiger.quanlylop.Fragment.ClassFragment;
import com.tiger.quanlylop.Fragment.FeeFragment;
import com.tiger.quanlylop.Fragment.HomeFragment;
import com.tiger.quanlylop.Fragment.SettingFragment;
import com.tiger.quanlylop.Fragment.StatisticFragment;
import com.tiger.quanlylop.Fragment.StudentFragment;
import com.tiger.quanlylop.R;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private AppCompatTextView textFullName;
    private View view;

    long id=-1;
    String fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();

        setSupportActionBar(toolbar);

        //Get DisplayName
        Intent intent = getIntent();
        fullname = intent.getStringExtra("displayName");
        id = intent.getLongExtra("id", -1);
        textFullName.setText(fullname);

        //Config toggle Button in toolbar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Change the icon
        //toolbar.setNavigationIcon(R.drawable.ic_class);

        if (savedInstanceState == null){
            Fragment homeFragment = new HomeFragment();
            loadFragment(homeFragment);
            navView.setCheckedItem(R.id.home);
            navView.setPadding(5,0,5,0);
        }

        toolbar.setTitle(R.string.home);
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.activity_main_drawer);

        //Get TextView in LayoutHeader
        navView = findViewById(R.id.navView);
        navView.setNavigationItemSelectedListener(itemSelected);

        view = navView.inflateHeaderView(R.layout.layout_header);
        textFullName  =  view.findViewById(R.id.textFullName);

    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
            finish();
        }

    }

    NavigationView.OnNavigationItemSelectedListener itemSelected = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;

            navView.setCheckedItem(item.getItemId());
            switch (item.getItemId()){
                case R.id.home:
                    toolbar.setTitle(R.string.home);
                    Fragment homeFragment = new HomeFragment();
                    loadFragment(homeFragment);

                    break;
                case R.id._class:
                    toolbar.setTitle(R.string.lophoc);
                    Fragment classFragment = new ClassFragment();
                    loadFragment(classFragment);

                    break;
                case R.id.student:
                    toolbar.setTitle(R.string.hocsinh);
                    Fragment studentFragment = new StudentFragment();
                    loadFragment(studentFragment);

                    break;
                case R.id.fee:
                    toolbar.setTitle(R.string.khoanphi);
                    Fragment feeFragment = new FeeFragment();
                    loadFragment(feeFragment);

                    break;
                case R.id.statistics:
                    toolbar.setTitle(R.string.thongke);
                    Fragment statisticFragment = new StatisticFragment();
                    loadFragment(statisticFragment);
                    break;
                case R.id.setting:
                    toolbar.setTitle(R.string.caidat);
                    Fragment settingFragment = new SettingFragment();
                    loadFragment(settingFragment);
                    break;
                case R.id.userInfo:
                    toolbar.setTitle(R.string.taikhoan);
                    Fragment accountFragment = new AccountFragment(id, fullname);
                    loadFragment(accountFragment);
                    break;
                case R.id.exit:
                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                    ;
                    break;

            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }
    };

}