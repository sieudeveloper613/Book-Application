package com.example.mob2041_pnlib_libmana_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mob2041_pnlib_libmana_project.LIBDAO.ThuThuDAO;
import com.example.mob2041_pnlib_libmana_project.LIBFragment.BookManagerFragment;
import com.example.mob2041_pnlib_libmana_project.LIBFragment.BookTypeManagerFragment;
import com.example.mob2041_pnlib_libmana_project.LIBFragment.ChangePasswordFragment;
import com.example.mob2041_pnlib_libmana_project.LIBFragment.CreateUserFragment;
import com.example.mob2041_pnlib_libmana_project.LIBFragment.MemberManagerFragment;
import com.example.mob2041_pnlib_libmana_project.LIBFragment.ProceedsFragment;
import com.example.mob2041_pnlib_libmana_project.LIBFragment.TicketBookManagerFragMent;
import com.example.mob2041_pnlib_libmana_project.LIBFragment.TopBookFragment;
import com.example.mob2041_pnlib_libmana_project.Model.ThuThu;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView tvUser;
    ThuThuDAO thuThuDAO;
    ThuThu thuThu;
    View mHeaderView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Define Toolbar
        setUpActionBar();

        // Define id for view
        initView();

        // Define method
        mainFragment();

        mHeaderView = navigationView.getHeaderView(0);
        tvUser = mHeaderView.findViewById(R.id.tv_user);
        Intent intent = getIntent();
        String username = intent.getStringExtra("user");
//        thuThuDAO = new ThuThuDAO(this);
//        thuThu = thuThuDAO.getID(username);
//        String userName =  thuThu.hoTen;
        tvUser.setText("Welcome " + username.toUpperCase() + " !");

        // admin co quyen add user
        if(username.equalsIgnoreCase("admin")){
          navigationView.getMenu().findItem(R.id.create_account).setVisible(true);
        }

        // check course 4 - video 4.3

        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);


    }

    private void initView(){
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
    }

    private void mainFragment(){
        // use Book Ticket Fragment is Home
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        TicketBookManagerFragMent ticketBookManagerFragMent = new TicketBookManagerFragMent();
        transaction.replace(R.id.fragment_container, ticketBookManagerFragMent);
        transaction.commit();
    }

    private void setUpActionBar(){
        toolbar = findViewById(R.id.tool_bar);
        // set toolbar change from actionbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Phuong Nam Library");
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
            drawerLayout.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (item.getItemId()) {
            case R.id.TBManager:
                setTitle("Book Ticket Manager");
                TicketBookManagerFragMent ticketBookManagerFragMent1 = new TicketBookManagerFragMent();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new TicketBookManagerFragMent()).commit();
                Toast.makeText(this, "you clicked Ticket Book Manager Item", Toast.LENGTH_SHORT).show();
                break;

            case R.id.BTManager:
                setTitle("BookType Manager");
                BookTypeManagerFragment bookTypeManagerFragment = new BookTypeManagerFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new BookTypeManagerFragment()).commit();
                Toast.makeText(this, "you clicked Book Type Manager Item", Toast.LENGTH_SHORT).show();
                break;

            case R.id.BManager:
                setTitle("Book Manager");
                BookManagerFragment bookManagerFragment = new BookManagerFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new BookManagerFragment()).commit();
                Toast.makeText(this, "you clicked Book Manager Item", Toast.LENGTH_SHORT).show();
                break;

            case R.id.TBook:
                setTitle("Top Book");
                TopBookFragment topBookFragment = new TopBookFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new TopBookFragment()).commit();
                Toast.makeText(this, "you clicked Top Book Item", Toast.LENGTH_SHORT).show();
                break;

            case R.id.MManager:
                setTitle("Member Manager");
                MemberManagerFragment memberManagerFragment = new MemberManagerFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new MemberManagerFragment()).commit();
                Toast.makeText(this, "you clicked Member Manager Item", Toast.LENGTH_SHORT).show();
                break;

            case R.id.proceeds:
                setTitle("Proceeds");
                ProceedsFragment proceedsFragment = new ProceedsFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new ProceedsFragment()).commit();
                Toast.makeText(this, "you clicked Proceeds Item", Toast.LENGTH_SHORT).show();
                break;

            case R.id.create_account:
                setTitle("Create User");
                CreateUserFragment createUserFragment = new CreateUserFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new CreateUserFragment()).commit();
                Toast.makeText(this, "you clicked Create User Item", Toast.LENGTH_SHORT).show();
                break;

            case R.id.change_password:
                setTitle("Change Password");
                ChangePasswordFragment changePassWordFragment = new ChangePasswordFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new ChangePasswordFragment()).commit();
                Toast.makeText(this, "you clicked Change Password Item", Toast.LENGTH_SHORT).show();
                break;

            case R.id.log_out:
                startActivity(new Intent(getApplicationContext(), LoginScreen.class));
                finish();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return false;

    }


}