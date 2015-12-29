package com.jash.lesson2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity implements Callback<com.jash.lesson2.Response> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ItemAdapter adapter;
    private Call<com.jash.lesson2.Response> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.main_list);
        adapter = new ItemAdapter(this);
        listView.setAdapter(adapter);
        Retrofit build = new Retrofit.Builder().baseUrl("http://m2.qiushibaike.com")
//                .addConverterFactory(new Converter.Factory() {
//                    @Override
//                    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
//                        return new Converter<ResponseBody, List<Item>>() {
//                            @Override
//                            public List<Item> convert(ResponseBody value) throws IOException {
//                                String s = value.string();
//                                JSONObject object = null;
//                                List<Item> list = new ArrayList<>();
//                                try {
//                                    object = new JSONObject(s);
//                                    JSONArray items = object.getJSONArray("items");
//                                    for (int i = 0; i < items.length(); i++) {
//                                        list.add(new Item(items.getJSONObject(i)));
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                                return list;
//                            }
//                        };
//                    }
//                })
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        QsbkService service = build.create(QsbkService.class);
        call = service.getList("image", 1);
        call.enqueue(this);

    }

    @Override
    public void onResponse(Response<com.jash.lesson2.Response> response, Retrofit retrofit) {
        adapter.addAll(response.body().getItems());
    }

    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
        Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
    }
}
