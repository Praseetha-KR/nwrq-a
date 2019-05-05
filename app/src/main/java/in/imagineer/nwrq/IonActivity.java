package in.imagineer.nwrq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;


public class IonActivity extends AppCompatActivity {
    TextView textRequest;
    TextView textResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ion);

        Intent intent = getIntent();
        String url = intent.getStringExtra(MainActivity.API_URL);

        textRequest = findViewById(R.id.textRequest);
        textRequest.setText(R.string.state_requesting);

        textResponse = findViewById(R.id.textResponse);
        textResponse.setText(R.string.state_waiting_response);

        reqWithIon(url);
    }


    void reqWithIon(String url) {
        textRequest.setText(Ion.with(this).load(url).toString());

        Ion.with(this)
            .load(url)
            .asJsonObject()
            .setCallback((Exception e, JsonObject result) -> {
                final String resJson = Utils.prettifyJson(result.toString());
                IonActivity.this.runOnUiThread(() -> {
                    textResponse.setText(resJson);
                });
            });
    }
}
