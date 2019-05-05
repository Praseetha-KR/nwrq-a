package in.imagineer.nwrq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    public static final String API_URL = "in.imagineer.nwrq.API_URL";
    public String url = "https://pokeapi.co/api/v2/characteristic/1/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView editUrl = findViewById(R.id.editUrl);
        editUrl.setText(url);
    }

    public void reqOkHttp(View view) {
        Intent intent = new Intent(this, OkHttpActivity.class);
        intent.putExtra(API_URL, url);
        startActivity(intent);
    }

    public void reqHttpURLConnection(View view) {
        Intent intent = new Intent(this, HttpURLConnectionActivity.class);
        intent.putExtra(API_URL, url);
        startActivity(intent);
    }

    public void reqVolley(View view) {
        Intent intent = new Intent(this, VolleyActivity.class);
        intent.putExtra(API_URL, url);
        startActivity(intent);
    }

    public void reqSpringRestTemplate(View view) {
        Intent intent = new Intent(this, SpringRestTemplateActivity.class);
        intent.putExtra(API_URL, url);
        startActivity(intent);
    }

    public void reqRetrofit(View view) {
        Intent intent = new Intent(this, RetrofitActivity.class);
        intent.putExtra(API_URL, url);
        startActivity(intent);
    }

    public void reqFeign(View view) {
        Intent intent = new Intent(this, FeignActivity.class);
        intent.putExtra(API_URL, url);
        startActivity(intent);
    }

    public void reqApacheHttpClient(View view) {
        Intent intent = new Intent(this, ApacheHttpClientActivity.class);
        intent.putExtra(API_URL, url);
        startActivity(intent);
    }
}
