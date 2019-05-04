package in.imagineer.nwrq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity {
    TextView textRequest;
    TextView textResponse;

    void reqWithOkHttp(String url) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        textRequest.setText(request.toString());

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                textResponse.setText(R.string.state_error);
                call.cancel();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                textResponse.setText(R.string.state_received_response);
                final String res = response.body().string();

                textResponse.setText(R.string.state_parsing_response);
                final String resJson = Utils.prettifyJson(res);

                OkHttpActivity.this.runOnUiThread(() -> {
                    textResponse.setText(resJson);
                });
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);

        Intent intent = getIntent();
        String url = intent.getStringExtra(MainActivity.API_URL);

        textRequest = findViewById(R.id.textRequest);
        textRequest.setText(R.string.state_requesting);

        textResponse = findViewById(R.id.textResponse);
        textResponse.setText(R.string.state_waiting_response);

        reqWithOkHttp(url);
    }
}
