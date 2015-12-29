package com.jash.lesson2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Callback {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Call call;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.main_list);
        adapter = new ItemAdapter(this);
        listView.setAdapter(adapter);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://m2.qiushibaike.com/article/list/suggest?page=").get().build();
        call = client.newCall(request);
        //同步请求
        //Response response = call.execute();
        call.enqueue(this);
    }

    /**
     * 失败(在非UI线程中执行)
     * @param request
     * @param e
     */
    @Override
    public void onFailure(Request request, IOException e) {
        e.printStackTrace();
        Log.d(TAG, "onFailure: " + Thread.currentThread().getName());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 成功(在非UI线程中执行)
     * @param response
     * @throws IOException
     */
    @Override
    public void onResponse(Response response) throws IOException {
        String s = response.body().string();
        try {
            JSONObject object = new JSONObject(s);
            JSONArray items = object.getJSONArray("items");
            final List<Item> list = new ArrayList<>();
            for (int i = 0; i < items.length(); i++) {
                list.add(new Item(items.getJSONObject(i)));
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.addAll(list);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        call.cancel();
    }
}
