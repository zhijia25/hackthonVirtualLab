package com.resource.lib;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ResourceManagerFactory {
    OkHttpClient okHttpClient = new OkHttpClient();
    String url = "https://techtailors.sytes.net:8400/resources/listall";

    public void collectResource(String url) throws IOException {
        Request request = new Request.Builder().url(url).get().build();
        // create sync request to server
//        Call call = okHttpClient.newCall(request);
//        Response execute = call.execute();
//        return execute.toString();


        // create async get request

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpexted code" + response);
                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                System.out.println(response.body().string());
            }
        });
    }

    public static ResourceManager createResourceManagerFromJson(String res) {
        Gson gson = new Gson();
        ResourceManager resourceManager = gson.fromJson(res, ResourceManager.class);
        return resourceManager;
    }

//    public ResourceManager createResourceManager() throws IOException {
//        String json = this.collectResource(this.url);
//        return this.createResourceManagerFromJson(json);
//    }
}
