package in.imagineer.nwrq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public String url = "https://pokeapi.co/api/v2/characteristic/1/";

    TextView editUrl;
    TextView textRequest;
    TextView textResponse;

    String jsonSerialize(String text) {
        String jsonRes;
        try {
            JSONObject jsonObject = new JSONObject(text);
            jsonRes = jsonObject.toString(4);
        } catch (Exception e) {
            jsonRes = "Json error";
        }
        return jsonRes;
    }

    void reqOkHttp(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        textRequest.setText(request.toString());

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                textResponse.setText("Errored");
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().string();
                textResponse.setText("Parsing response...");
                final String jsonRes = jsonSerialize(res);

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textResponse.setText(jsonRes);
                    }
                });

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUrl = findViewById(R.id.editUrl);
        textRequest = findViewById(R.id.textRequest);
        textResponse = findViewById(R.id.textResponse);

        editUrl.setText(url);
        textRequest.setText("Requesting...");
        textResponse.setText("Awaiting Response...");

        try {
            reqOkHttp(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
