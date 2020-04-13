package com.example.virtuallibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textview.MaterialTextView;
import com.resource.lib.GeneralResource;
import com.resource.lib.PdfResource;
import com.resource.lib.ResourceManager;
import com.resource.lib.ResourceManagerFactory;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ResourceListActivity extends Activity implements ResourceListAdapter.Callback, AdapterView.OnItemClickListener {

    ListView listView;
    String url = "https://techtailors.sytes.net:8400/resources/listall";
    ResourceManager resourceManager = new ResourceManager();
    public static final int UPDATE_LIST = 1;
    public static final int UPDATE_FAIL = 2;
    private ResourceListAdapter resourceListAdapter;
    private AVLoadingIndicatorView avi;
    private TextView textView;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case UPDATE_LIST:
                    avi.hide();
                    textView.setVisibility(View.VISIBLE);
                    resourceListAdapter.notifyDataSetChanged();
                    break;
                case UPDATE_FAIL:
                    avi.hide();
                    String notify = "Fail to load resource list. Load Again";
                    Toast.makeText(getApplicationContext(), notify, Toast.LENGTH_SHORT).show();
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resource_list);
        avi = findViewById(R.id.avi);
        avi.show();
        textView = findViewById(R.id.resource_label);
        listView = findViewById(R.id.resource_list);
        // collect resources from server
        // set listview adapter
        resourceListAdapter = new ResourceListAdapter(this, this);
        resourceListAdapter.setResourceList(resourceManager.getPdf());
        listView.setAdapter(resourceListAdapter);
        listView.setOnItemClickListener(this);
        this.collectResource(url);
    }

    public void collectResource(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();

        // create async get request
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Message message = new Message();
                message.what = UPDATE_FAIL;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code" + response);
                ResourceManager addResourceManager = ResourceManagerFactory.createResourceManagerFromJson(response.body().string());
                List<PdfResource> addedResource = addResourceManager.getPdf();
                resourceManager.getPdf().addAll(addedResource);
                // send message to handler to update the UI
                Message message = new Message();
                message.what = UPDATE_LIST;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public void click(View v) {
        int position = (int) v.getTag();
        PdfResource resource = resourceListAdapter.getResourceList().get(position);
        Toast.makeText(this, resource.getUrl(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ResourceListActivity.this, PdfViewActivity.class);
        intent.putExtra("url", resource.getUrl());
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, resourceListAdapter.getResourceList().get(position).getUrl(), Toast.LENGTH_SHORT).show();
    }
}
