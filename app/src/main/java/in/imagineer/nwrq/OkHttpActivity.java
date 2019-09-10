package in.imagineer.nwrq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity {
    TextView textRequestGet;
    TextView textRequestPostForm;
    TextView textRequestPostJson;
    TextView textResponseGet;
    TextView textResponsePostForm;
    TextView textResponsePostJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);

        Intent intent = getIntent();
        String url = intent.getStringExtra(MainActivity.API_URL);

        textRequestGet = findViewById(R.id.textRequestGet);
        textRequestGet.setText(R.string.state_requesting);

        textRequestPostForm = findViewById(R.id.textRequestPostForm);
        textRequestPostForm.setText(R.string.state_requesting);

        textRequestPostJson = findViewById(R.id.textRequestPostJson);
        textRequestPostJson.setText(R.string.state_requesting);

        textResponseGet = findViewById(R.id.textResponseGet);
        textResponseGet.setText(R.string.state_waiting_response);

        textResponsePostForm = findViewById(R.id.textResponsePostForm);
        textResponsePostForm.setText(R.string.state_waiting_response);

        textResponsePostJson = findViewById(R.id.textResponsePostJson);
        textResponsePostJson.setText(R.string.state_waiting_response);

        reqGetWithOkHttp(url);
        reqPostFormWithOkHttp();
        reqPostJsonWithOkHttp();
    }

    void reqGetWithOkHttp(String url) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        textRequestGet.setText(request.toString());

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                textResponseGet.setText(R.string.state_error);
                call.cancel();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                textResponseGet.setText(R.string.state_received_response);
                final String res = response.body().string();

                textResponseGet.setText(R.string.state_parsing_response);
                final String resJson = Utils.prettifyJson(res);

                OkHttpActivity.this.runOnUiThread(() -> {
                    textResponseGet.setText(resJson);
                });
            }
        });
    }

    void reqPostFormWithOkHttp() {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        RequestBody body = new FormBody.Builder()
                .add("name", "morpheus")
                .add("job", "leader")
                .build();

        Request request = new Request.Builder()
                .url("https://reqres.in/api/users?hello=1&hi=2")
                .post(body)
                .addHeader("X-test-1", "test1")
                .addHeader("X-test-2", "test2")
                .build();

        textRequestPostForm.setText(request.toString());

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                textResponsePostForm.setText(R.string.state_error);
                call.cancel();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                textResponsePostForm.setText(R.string.state_received_response);
                final String res = response.body().string();

                textResponsePostForm.setText(R.string.state_parsing_response);
                final String resJson = Utils.prettifyJson(res);

                OkHttpActivity.this.runOnUiThread(() -> {
                    textResponsePostForm.setText(resJson);
                });
            }
        });
    }
    void reqPostJsonWithOkHttp() {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JsonObject json = new JsonObject();
        json.addProperty("name", "Neo");
        json.addProperty("job", "the one!");
        RequestBody body = RequestBody.create(JSON, json.toString());

        Request request = new Request.Builder()
                .url("https://reqres.in/api/users?hello=1&hi=2")
                .post(body)
                .addHeader("X-test-1", "test1")
                .addHeader("X-test-2", "test2")
                .build();

        textRequestPostJson.setText(request.toString());

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                textResponsePostJson.setText(R.string.state_error);
                call.cancel();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                textResponsePostJson.setText(R.string.state_received_response);
                final String res = response.body().string();

                textResponsePostJson.setText(R.string.state_parsing_response);
                final String resJson = Utils.prettifyJson(res);

                OkHttpActivity.this.runOnUiThread(() -> {
                    textResponsePostJson.setText(resJson);
                });
            }
        });
    }
}
