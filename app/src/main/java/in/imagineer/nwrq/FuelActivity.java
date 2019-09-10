package in.imagineer.nwrq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;


public class FuelActivity extends AppCompatActivity {
    TextView textRequest;
    TextView textResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel);

        Intent intent = getIntent();
        String url = intent.getStringExtra(MainActivity.API_URL);

        textRequest = findViewById(R.id.textRequest);
        textRequest.setText(R.string.state_requesting);

        textResponse = findViewById(R.id.textResponse);
        textResponse.setText(R.string.state_waiting_response);

        reqWithFuel(url);
    }


    void reqWithFuel(String url) {
        textRequest.setText(url);

        Fuel.get(url).responseString(new Handler<String>() {
            @Override
            public void success(Request request, Response response, String s) {
                final String resJson = Utils.prettifyJson(s);

                FuelActivity.this.runOnUiThread(() -> {
                    textResponse.setText(resJson);
                });
            }

            @Override
            public void failure(Request request, Response response, FuelError fuelError) {
                textResponse.setText(R.string.state_error);
            }
        });
    }
}
