package com.foko.nhl;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foko.Adapters.TeamsAdapter;
import com.foko.nhl.fragment.PlayerDetailFragment;
import com.foko.nhl.fragment.PlayerListFragment;
import com.foko.nhl.interfaces.MenuItemClickListener;
import com.foko.pojo.ResponseObject;
import com.foko.pojo.player.PlayerObject;
import com.foko.utilities.GetTeamDataService;
import com.foko.utilities.RetrofitClientInstance;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,TeamsAdapter.ItemClickListener,PlayerListFragment.OnFragmentInteractionListener {

    private static final String TAG = "NHL-Test";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private MenuItemClickListener listener;
    ProgressDialog progressDoalog;
    DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        getCountryListApi();

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        recyclerView = findViewById(R.id.teams_nav_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            PlayerListFragment fragment = PlayerListFragment.newInstance(1);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment).commit();
        }

    }

    private void generateDataList(ResponseObject TeamsList) {

        if (TeamsList != null) {

            mAdapter = new TeamsAdapter(getApplicationContext(), TeamsList.getTeams());
            ((TeamsAdapter) mAdapter).setListener(this);
            recyclerView.setAdapter(mAdapter);
        }

    }

    public void setMenuItemClickListener(MenuItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_name) {
            if(listener != null){
                listener.nameSort();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(int position) {
       /* Toast.makeText(this, "This is the Itemclick listener : " + position, Toast.LENGTH_LONG)
                .show();*/
        PlayerListFragment fragment = PlayerListFragment.newInstance(position);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).commit();
        drawer.closeDrawers();
    }


    @Override
    public void onItemClickListener(int id) {
        Log.d(TAG,"Player id : "+ id);
        /*Toast.makeText(this,"OnItemClickListener() fragment with Player id : "+  id,Toast.LENGTH_LONG)
                .show();*/
        getPlayerInfoApi(id);
    }

    // Get country list api call.
    private void getCountryListApi(){
        GetTeamDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetTeamDataService.class);
        Call<ResponseObject> call = service.getAllTeams();
        call.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                progressDoalog.dismiss();
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                progressDoalog.dismiss();
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Get Player info api call.
    private void getPlayerInfoApi(int id){
        GetTeamDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetTeamDataService.class);
        Call<PlayerObject> call = service.getPlayer(id);
        call.enqueue(new Callback<PlayerObject>() {
            @Override
            public void onResponse(Call<PlayerObject> call, Response<PlayerObject> response) {
                progressDoalog.dismiss();
                PlayerObject object = response.body();
                String nationality = null;
                if(object != null){
                   nationality = object.getPeople().get(0).getNationality();
                }
                PlayerDetailFragment fragment =PlayerDetailFragment.newInstance(nationality != null ? nationality:"CA");
                fragment.show(getSupportFragmentManager(),PlayerDetailFragment.TAG);
            }

            @Override
            public void onFailure(Call<PlayerObject> call, Throwable t) {
                progressDoalog.dismiss();
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
