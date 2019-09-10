package in.imagineer.nwrq;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
            String result = reqWithHttpURLConnection(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String result){
            textResponse.setText(result);
            super.onPostExecute(result);
        }
    }

//    String reqWithHttpURLConnection(String url) {
//        String response = "";
//        url = "https://reqres.in/api/users?hello=1&hi=2";
//        try {
//            URL apiUrl = new URL(url);
//            HttpURLConnection urlConnection = (HttpURLConnection) apiUrl.openConnection();
//            textRequest.setText(url);
//            urlConnection.setRequestMethod("POST");
//
//            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//            String res = readStream(in);
//            final String resJson = Utils.prettifyJson(res);
//            response = resJson;
//
//            urlConnection.disconnect();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return response;
//    }

    String reqWithHttpURLConnection(String url) {
        String response = "";
        url = "https://reqres.in/api/users?hello=1&hi=2";
        try {
            URL apiUrl = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) apiUrl.openConnection();
            textRequest.setText(url);

            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setDoOutput(true);

            JsonObject json = new JsonObject();
            json.addProperty("name", "Neo");
            json.addProperty("job", "programmer");

            OutputStream os = urlConnection.getOutputStream();
            os.write(json.toString().getBytes("UTF-8"));
            os.close();

            urlConnection.connect();


            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String res = readStream(in);
            final String resJson = Utils.prettifyJson(res);
            response = resJson;

            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
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
