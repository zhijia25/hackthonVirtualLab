package com.example.virtuallibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreateRoomActivity extends AppCompatActivity {

    private Button back_btn;
    private Button submit_btn;
    private TextView user_name;
    private EditText title;
    private EditText subject;
    private  EditText tasks;
    private String input_title;
    private String input_subject;
    private String input_task;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        userName = intent.getStringExtra("username");
        Log.d("username", "onCreate: "+userName);

        setContentView(R.layout.activity_create_room);
        back_btn = findViewById(R.id.back);
        submit_btn = findViewById(R.id.submit_create_room);
        user_name = findViewById(R.id.room_creater);
        title = findViewById(R.id.input_title);
        subject = findViewById(R.id.input_subject);
        tasks = findViewById(R.id.input_task);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                user_name.setText(userName);
            }
        });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_title = title.getText().toString();
                input_subject = subject.getText().toString();
                input_task = tasks.getText().toString();
                Boolean check = invaildInputChecker();
                if (check){
                    submitHttpRequest();
                    Toast.makeText(CreateRoomActivity.this, "all good", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(CreateRoomActivity.this, "check inputs", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private Boolean invaildInputChecker(){
        Boolean check = true;
        if (input_title.length() == 0){
            check = false;

        }
        if (input_subject.length() == 0){
            check = false;

        }
        if (input_task.length() == 0){
            check = false;

        }

        return check;
    }

    private void submitHttpRequest(){
        OkHttpClient client = new OkHttpClient();
        try {
            RequestBody requestBody = new FormBody.Builder()
                    .add("title",input_title)
                    .add("subject", input_subject)
                    .add("tasks", input_task)
                    .add("username", userName)
                    .build();

            Request request = new Request.Builder()
                    .url("https://techtailors.sytes.net:8400/rooms/createnew")
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Toast.makeText(CreateRoomActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String responseData = response.body().string();
                    try {
                        JSONObject jsonData = new JSONObject(responseData);
                        Intent intent = new Intent(CreateRoomActivity.this, RoomListActivity.class);
                        setResult(RESULT_OK, intent);
//                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
