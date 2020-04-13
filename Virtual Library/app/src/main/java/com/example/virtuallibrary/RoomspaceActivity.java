package com.example.virtuallibrary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class RoomspaceActivity extends AppCompatActivity {

    private String userName;
    private String roomId;
    private String roomTitle;
    private String users;
    public String subject;
    public String task;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomspace);
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        roomId = intent.getStringExtra("roomId");
        roomTitle = intent.getStringExtra("title");
        subject = intent.getStringExtra("subject");
        task = intent.getStringExtra("tasks");
        users = intent.getStringExtra("users");


        final String url = "https://techtailors.sytes.net:8400/rooms/leava/" + roomId + "/" + userName;

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Room: "+roomTitle);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "You will leave the room...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        Intent intent = new Intent(RoomspaceActivity.this, RoomListActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        NavController navController = Navigation.findNavController(findViewById(R.id.nav_host_fragment));
        // pass data
        Bundle bundle = new Bundle();
        bundle.putString("subject", subject);
        bundle.putString("task", task);
        bundle.putString("users", users);
        bundle.putString("currentUser", userName);
        navController.setGraph(R.navigation.nav_graph, bundle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.resources) {
            Intent intent = new Intent(RoomspaceActivity.this, ResourceListActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
