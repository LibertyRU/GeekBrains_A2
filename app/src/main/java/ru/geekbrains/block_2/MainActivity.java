package ru.geekbrains.block_2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PERMISSION_REQUEST_CODE = 10; // Этот код будет возворащаться, когда пользователь согласится
    private String phoneNumber;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        View view = findViewById(R.id.drawer_layout);
        phoneNumber = "tel:89011112542";
        message = "Hello!";
        //noinspection SimplifiableIfStatement
        switch (id){
        case R.id.action_SMS:
        makeSms(phoneNumber, message);
            Snackbar.make(view, "Сообщение отправлено!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        default:

        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        View view = findViewById(R.id.drawer_layout); // получаем драувер навигацию для использования в Снэке
        switch (id)
            {
                case R.id.about_me :
                case R.id.callback_icon:
                case R.id.nav_help:
                default:
                    Snackbar.make(view, "Раздел меню находится в разработке", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }

        DrawerLayout drawer = (DrawerLayout) view;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Запрос пермиссии для вызова
    public void requestForCallPermission() {
        // Можем ли мы запрашивать пермиссии, если нет, то и смысла нет запрашивать
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
            // Запрашиваем пермиссии у пользователя
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);
        }
    }

    // Это результат запроса у пользователя пермиссии
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {   // Это та самая пермиссия, что мы запрашивали?
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Все препоны пройдены и пермиссия дана, можно делать звонок
                makeSms(phoneNumber, message);
            }
        }
    }

    void makeSms(String phoneNumber, String message){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            SmsManager.getDefault().sendTextMessage(phoneNumber, null, message, null, null);
        } else {
// Если пермиссии нет, то запросим у пользователя
            requestForCallPermission();
        }
    }

}
