//package com.example.myapplication;
//
//
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.core.view.GravityCompat;
//import androidx.drawerlayout.widget.DrawerLayout;
//
//public class MenuActivity extends AppCompatActivity {
//    Toolbar toolbar;
//    DrawerLayout drawerLayout;
////    NavigationView navigationView;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_menu);
//        toolbar = findViewById(R.id.toolbar);
//        drawerLayout = findViewById(R.id.drawerLayout);
////        navigationView = findViewById(R.id.navication);
//        setSupportActionBar(toolbar);//toolBar设置支持actionBar
//        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MenuActivity.this, "hah", Toast.LENGTH_SHORT).show();
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });
////        navigationView.setCheckedItem(R.id.add);
////        //对navicationView进行监听
////        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
////            @Override
////            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
////                Toast.makeText(MenuActivity.this, "haSSSSh", Toast.LENGTH_SHORT).show();
////                return false;
////            }
////        });
//    }
//
//
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.create_court_dance_group, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.add) {
//            Toast.makeText(MenuActivity.this, "添加", Toast.LENGTH_SHORT).show();
//        }
//        return true;
//    }
//
//}
//
