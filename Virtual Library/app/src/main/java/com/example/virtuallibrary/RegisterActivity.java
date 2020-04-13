package com.example.virtuallibrary;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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

public class RegisterActivity  extends AppCompatActivity {
    private Button registerBin;
    private TextInputEditText usernameInput;
    private TextInputEditText passwordInput;
    private TextInputEditText nameInput;
    private TextInputEditText accountInput;
    private OkHttpClient client;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        registerBin = findViewById(R.id.register_btn);
        registerBin.setEnabled(false);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        nameInput = findViewById(R.id.nameInput);
        accountInput = findViewById(R.id.accountTypeInput);
        client = new OkHttpClient();

        /*

            Log in Button click listener

         */

        registerBin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();
                String name = nameInput.getText().toString();
                String accountType = accountInput.getText().toString();
                Log.d("register", "onClick: " + password);
                RequestBody requestBody = new FormBody.Builder()
                        .add("username", username)
                        .add("password", password)
                        .add("name", name)
                        .add("accountType", accountType)
                        .build();

                String registerUrl = "https://techtailors.sytes.net:8400/users/register";
                Request request = new Request.Builder()
                        .url(registerUrl)
                        .post(requestBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(RegisterActivity.this, "Log in failed, try it later", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String responseData =response.body().string();
                        Log.d("response", "register response: "+ responseData);
                        try {
                            final JSONObject jsonData = new JSONObject(responseData);
                            String userID = jsonData.getString("_id");
                            String token = jsonData.getString("password");

                            Intent intent = new Intent(RegisterActivity.this, RoomListActivity.class);
                            intent.putExtra("userName", jsonData.getString("username"));
                            startActivity(intent);
                            finish();
//
//                            if (result){

//                            }
//                            else{
//                                Toast.makeText(RegisterActivity.this, "Failed, please verify your code", Toast.LENGTH_SHORT).show();
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//
//                                        Toast.makeText(RegisterActivity.this, "Failed, please verify your code", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//
//                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });



        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                registerBin.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameInput.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordInput.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameInput.getText().toString(),
                        passwordInput.getText().toString());
            }
        };

        usernameInput.addTextChangedListener(afterTextChangedListener);
        passwordInput.addTextChangedListener(afterTextChangedListener);



    }
}
