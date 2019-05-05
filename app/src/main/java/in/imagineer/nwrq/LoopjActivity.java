package in.imagineer.nwrq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.loopj.android.http.*;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class LoopjActivity extends AppCompatActivity {
    TextView textRequest;
    TextView textResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loopj);

        Intent intent = getIntent();
        String url = intent.getStringExtra(MainActivity.API_URL);

        textRequest = findViewById(R.id.textRequest);
        textRequest.setText(R.string.state_requesting);

        textResponse = findViewById(R.id.textResponse);
        textResponse.setText(R.string.state_waiting_response);

        reqWithLoopj(url);
    }

    void reqWithLoopj(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        textRequest.setText(client.toString());

        client.get(url, null, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String res = response.toString();
                final String resJson = Utils.prettifyJson(res);

                LoopjActivity.this.runOnUiThread(() -> {
                    textResponse.setText(resJson);
                });
            }

            public void onFailure(int statusCode, Header[] headers, JSONObject errorResponse, Throwable e) {
                e.printStackTrace();
                textResponse.setText(R.string.state_error);
            }
        });
    }

}
