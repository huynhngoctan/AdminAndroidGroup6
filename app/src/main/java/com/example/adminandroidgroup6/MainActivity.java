package com.example.adminandroidgroup6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.adminandroidgroup6.changeInfo.ChangeInfoActivity;
import com.example.adminandroidgroup6.login.LoginActivity;
import com.example.adminandroidgroup6.manageAccount.ManageAccountFragment;
import com.example.adminandroidgroup6.menuFoods.FoodsFragment;
import com.example.adminandroidgroup6.menuOrders.OrdersFragment;
import com.example.adminandroidgroup6.model.User;
import com.example.adminandroidgroup6.model.UserLogin;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    CircleImageView imageView;
    TextView tvEmail;
    View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open, R.string.close);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment, new MainFragment());
        fragmentTransaction.commit();

        User user = UserLogin.getInstance().getUser();
        headerView = navigationView.getHeaderView(0);
        imageView = headerView.findViewById(R.id.imageViewAvartarDrawerHeader);
        tvEmail = headerView.findViewById(R.id.textViewEmailDrawerHeader);
        if (user.getLinkImage() != null)
            Picasso.with(this).load(user.getLinkImage()).fit().centerCrop()
                    .into(imageView);
        tvEmail.setText(user.getEmail());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.menuItemHome:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment, new MainFragment());
                fragmentTransaction.commit();
                break;
            case R.id.menuItemFoods:
                toolbar.setTitle("Thực đơn");
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment, new FoodsFragment());
                fragmentTransaction.commit();
                break;
            case R.id.menuItemOrder:
                toolbar.setTitle("Đơn hàng");
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment, new OrdersFragment());
                fragmentTransaction.commit();
                break;
            case R.id.menuItemExit:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.menuItemUpdateAccount:
                Intent intent1 = new Intent(MainActivity.this, ChangeInfoActivity.class);
                startActivity(intent1);
                break;
            case R.id.menuItemManageAccount:
                toolbar.setTitle("Quản lý tài khoản");
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment, new ManageAccountFragment());
                fragmentTransaction.commit();
                break;
        }

        return true;
    }
}