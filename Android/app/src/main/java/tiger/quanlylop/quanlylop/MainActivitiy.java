package tiger.quanlylop.quanlylop;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import tiger.quanlylop.quanlylop.FragmentApp.ClassFragment;
import tiger.quanlylop.quanlylop.FragmentApp.ClassInfoFeeFragment;
import tiger.quanlylop.quanlylop.FragmentApp.EditClassFragment;
import tiger.quanlylop.quanlylop.FragmentApp.EditFeeFragment;
import tiger.quanlylop.quanlylop.FragmentApp.EditStudentFragment;

public class MainActivitiy extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtFullName;
    Toolbar toolBar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();

        Intent intent = getIntent();
        String fullname = intent.getStringExtra("loginname");
        //Toast.makeText(this, fullname, Toast.LENGTH_LONG).show();
        txtFullName.setText(fullname);

        setSupportActionBar(toolBar);

        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolBar ,R.string.open,R.string.close)
        {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };


        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(drawerToggle);

        drawerToggle.syncState();

        onNavigationItemSelected(navigationView.getMenu().getItem(0));
    }

    private void AnhXa()
    {
        toolBar = (Toolbar)findViewById(R.id.toolBar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView)findViewById(R.id.navigationView);
        View view = navigationView.inflateHeaderView(R.layout.layout_header);
        txtFullName  =  (TextView)view.findViewById(R.id.txtnameuser);
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            //this.finish();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            //super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected( MenuItem item) {
        int id = item.getItemId();

        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();

        switch (id)
        {
            case R.id.home:

                navigationView.setCheckedItem(R.id.home);
               ClassFragment classFragment = new ClassFragment();
               fragmentTransaction.replace(R.id.content,classFragment);
               fragmentTransaction.commit();
               drawerLayout.closeDrawer(GravityCompat.START);
               toolBar.setTitle(R.string.home);

            ;
             break;

            case R.id.student:
                navigationView.setCheckedItem(R.id.student);
                EditStudentFragment editStudentFragment = new EditStudentFragment();
                fragmentTransaction.replace(R.id.content, editStudentFragment);
                fragmentTransaction.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                toolBar.setTitle(R.string.hocsinh);
                ;
                break;

            case R.id.class_:
                navigationView.setCheckedItem(R.id.class_);
                EditClassFragment editClassFragment = new EditClassFragment();
                fragmentTransaction.replace(R.id.content, editClassFragment);
                fragmentTransaction.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                toolBar.setTitle(R.string.lophoc);
                ;
                break;

            case R.id.fee:
                navigationView.setCheckedItem(R.id.fee);
                EditFeeFragment editFeeFragment = new EditFeeFragment();
                fragmentTransaction.replace(R.id.content, editFeeFragment);
                fragmentTransaction.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                toolBar.setTitle(R.string.khoanphi);
                ;
                break;

            case R.id.statistics:
                navigationView.setCheckedItem(R.id.statistics);
                ClassInfoFeeFragment classInfoFeeFragment = new ClassInfoFeeFragment();
                fragmentTransaction.replace(R.id.content, classInfoFeeFragment);
                fragmentTransaction.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                toolBar.setTitle(R.string.thongke);
                ;
                break;
            case R.id.exitmenu:
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                ;
                break;
        }


        return false;
    }


}

