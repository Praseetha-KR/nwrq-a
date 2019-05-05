package in.imagineer.nwrq;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.util.EntityUtils;


public class ApacheHttpClientActivity extends AppCompatActivity {
    TextView textRequest;
    TextView textResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apache_http_client);

        Intent intent = getIntent();
        String url = intent.getStringExtra(MainActivity.API_URL);

        textRequest = findViewById(R.id.textRequest);
        textRequest.setText(R.string.state_requesting);

        textResponse = findViewById(R.id.textResponse);
        textResponse.setText(R.string.state_waiting_response);

        new ApiCallTask().execute(url);
    }

    private class ApiCallTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
            reqWithApacheHttpClient(urls[0]);
            return null;
        }
    }

    void reqWithApacheHttpClient(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        textRequest.setText(httpGet.getRequestLine().toString());

        CloseableHttpResponse response;
        try {
            response = httpclient.execute(httpGet);
        } catch (IOException e) {
            textResponse.setText(R.string.state_error);
            return;
        }
        try {
            HttpEntity entity = response.getEntity();
            String res = EntityUtils.toString(entity);
            final String resJson = Utils.prettifyJson(res);

            ApacheHttpClientActivity.this.runOnUiThread(() -> {
                textResponse.setText(resJson);
            });
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
            textResponse.setText(R.string.state_error);
        }
    }
}
