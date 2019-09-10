package in.imagineer.nwrq;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.io.Serializable;


public class SpringRestTemplateActivity extends AppCompatActivity {
    TextView textRequest;
    TextView textResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spring_rest_template);

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
            String result = reqWithSpringRestTpl(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String result){
            textResponse.setText(result);
            super.onPostExecute(result);
        }
    }

//    String reqWithSpringRestTpl(String url) {
//        String response;
//        textRequest.setText(url);
//
//        RestTemplate restTemplate = new RestTemplate();
//        String res = restTemplate.getForObject(url, String.class);
//        final String resJson = Utils.prettifyJson(res);
//        response = resJson;
//
//        return response;
//    }

    public class User implements Serializable {
        private int id;
        private String name;
        private String job;

        User(int id, String name, String job) {
            this.id = id;
            this.name = name;
            this.job = job;
        }
    }

    String reqWithSpringRestTpl(String url) {
        url = "https://reqres.in/api/users?hello=1&hi=2";

        String response;
        textRequest.setText(url);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Test-1", "test1");
        headers.add("X-Test-2", "test2");

        User user = new User(1, "Neo", "Programmer");
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        String res = restTemplate.postForObject(url, entity, String.class);
        final String resJson = Utils.prettifyJson(res);
        response = resJson;

        return response;
    }
}
