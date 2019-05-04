package in.imagineer.nwrq;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import feign.Feign;
import feign.Param;
import feign.RequestLine;
import feign.gson.GsonDecoder;


public class FeignActivity extends AppCompatActivity {
    TextView textRequest;
    TextView textResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feign);

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
            reqWithFeign(urls[0]);
            return null;
        }
    }


    interface Pokemon {
        @RequestLine("GET /characteristic/{id}")
        PokemonModel.Characteristic characteristic(@Param("id") int id);
    }

    void reqWithFeign(String url) {
        Pokemon pokemon = Feign.builder()
                .decoder(new GsonDecoder())
                .target(Pokemon.class, "https://pokeapi.co/api/v2/");
        textRequest.setText(pokemon.toString());

        PokemonModel.Characteristic pokeCharacteristic = pokemon.characteristic(1);

        final String resJson = Utils.prettifyJson(new Gson().toJson(pokeCharacteristic));
        FeignActivity.this.runOnUiThread(() -> {
            textResponse.setText(resJson);
        });
    }
}
