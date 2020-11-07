package net.smallacademy.authenticatorapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class Activity_Main extends AppCompatActivity {

    BottomNavigationView bottomView;
    HomeFragment homeFragment = new HomeFragment();
    BeritaFragment beritaFragment = new BeritaFragment();
    AcountFragment acountFragment = new AcountFragment();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // <-- di Activity_main.java ini lu reference activity_main.xml yang dimana itu posisi BN berada, makannya intentnya gue rubah kesini.
        bottomView = findViewById(R.id.bn_main); // <-- ini button Nav nya
        //intinya lu BN lu ilang karena salah intent/salah pindah layout
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_container, homeFragment);
        fragmentTransaction.commit();

        bottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();

                switch (item.getItemId()) {
                    case R.id.home_menu:
                        fragmentTransaction2.replace(R.id.fl_container, homeFragment);
                        break;
                    case R.id.berita_menu:
                        fragmentTransaction2.replace(R.id.fl_container, beritaFragment);
                        break;
                    case R.id.account_menu:
                        fragmentTransaction2.replace(R.id.fl_container, acountFragment);
                        break;
//                    case R.id.navigation_maps:
//                        Intent intent = new Intent(MainActivity.this, LocationActivity.class);
//                        startActivity(intent);
//                        break;
                }
                fragmentTransaction2.commit();
                return true;
            }

        });
        }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu, menu);
//        return true;
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId() == R.id.actionKehilangan) {
//            Intent intent = new Intent( this, DataTemuanActivity.class);
//            startActivity(intent);
//        }else if(item.getItemId() == R.id.actionPenemu) {
//            Intent intent = new Intent( this, ListDataTemuan.class);
//            startActivity(intent);
//
//        }else {
//
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.clear();
//            editor.commit();
//
//            Intent intent = new Intent( this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//        }
//        return true;
//    }
//}
//public class Activity_Main extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
////        HomeFragment homeFragment = new HomeFragment();
////        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
////        fragmentTransaction.replace(R.id.fl_container, homeFragment);
////        fragmentTransaction.commit();
//
//
//
//
//        // kita set default nya Home Fragment
//        loadFragment(new HomeFragment());
//        // inisialisasi BottomNavigaionView
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bn_main);
//        // beri listener pada saat item/menu bottomnavigation terpilih
//        bottomNavigationView.setOnNavigationItemSelectedListener(this);
//    }
//
//    private boolean loadFragment(Fragment fragment){
//        if (fragment != null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fl_container, fragment)
//                    .commit();
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        Fragment fragment = null;
//
//        switch (menuItem.getItemId()){
//            case R.id.home_menu:
//
////                fragment = new HomeFragment();
////                HomeFragment homeFragment = new HomeFragment();
////                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
////                fragmentTransaction.replace(R.id.fl_container, homeFragment);
////                fragmentTransaction.commit();
//                break;
//            case R.id.berita_menu:
////                fragment = new BeritaFragment();
////                BeritaFragment   beritaFragment = new BeritaFragment();
////                FragmentTransaction fragmentBeritaTransaction = getSupportFragmentManager().beginTransaction();
////                fragmentBeritaTransaction.replace(R.id.fl_container, beritaFragment );
////                fragmentBeritaTransaction.commit();
//                break;
//
//            case R.id.account_menu:
////                fragment = new AcountFragment();
////                AcountFragment   acountFragment = new AcountFragment();
////                FragmentTransaction fragmentAcountTransaction = getSupportFragmentManager().beginTransaction();
////                fragmentAcountTransaction.replace(R.id.fl_container, acountFragment );
////                fragmentAcountTransaction.commit();
//                break;
//        }
//        return loadFragment(fragment);
//    }
//
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}
//
//
//
//}
