package in.imagineer.nwrq;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class HttpURLConnectionActivity extends AppCompatActivity {
    TextView textRequest;
    TextView textResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_urlconnection);

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
            reqWithHttpURLConnection(urls[0]);
            return null;
        }
    }

    void reqWithHttpURLConnection(String url) {
        try {
            URL apiUrl = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) apiUrl.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String res = readStream(in);
            final String resJson = Utils.prettifyJson(res);
            urlConnection.disconnect();

            HttpURLConnectionActivity.this.runOnUiThread(() -> {
                textResponse.setText(resJson);
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readStream(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(in),1024);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        in.close();
        return sb.toString();
    }
}
