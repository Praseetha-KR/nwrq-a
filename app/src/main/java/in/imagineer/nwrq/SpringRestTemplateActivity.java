package in.imagineer.nwrq;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.springframework.web.client.RestTemplate;


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
            reqWithSpringRestTpl(urls[0]);
            return null;
        }
    }

    void reqWithSpringRestTpl(String url) {
        RestTemplate restTemplate = new RestTemplate();
        textRequest.setText(restTemplate.getClass().toString());

        String res = restTemplate.getForObject(url, String.class);
        final String resJson = Utils.prettifyJson(res);

        SpringRestTemplateActivity.this.runOnUiThread(() -> {
            textResponse.setText(resJson);
        });
    }
}
