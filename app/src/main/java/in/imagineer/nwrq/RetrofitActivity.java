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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;


public class RetrofitActivity extends AppCompatActivity {
    TextView textRequest;
    TextView textResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        Intent intent = getIntent();
        String url = intent.getStringExtra(MainActivity.API_URL);

        textRequest = findViewById(R.id.textRequest);
        textRequest.setText(R.string.state_requesting);

        textResponse = findViewById(R.id.textResponse);
        textResponse.setText(R.string.state_waiting_response);

        reqWithRetrofit(url);
    }

    interface PokemonRetrofit {
        @GET("characteristic/{id}")
        Call<PokemonModel.Characteristic> getCharacteristic(@Path("id") int id);
    }

    void reqWithRetrofit(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokemonRetrofit pokemonSvc = retrofit.create(PokemonRetrofit.class);
        textRequest.setText(pokemonSvc.toString());

        Call<PokemonModel.Characteristic> call = pokemonSvc.getCharacteristic(1);
        call.enqueue(new Callback<PokemonModel.Characteristic>() {
            @Override
            public void onResponse(Call<PokemonModel.Characteristic> call, Response<PokemonModel.Characteristic> response) {
                PokemonModel.Characteristic pokeCharacteristic = response.body();
                final String resJson = Utils.prettifyJson(new Gson().toJson(pokeCharacteristic));
                RetrofitActivity.this.runOnUiThread(() -> {
                    textResponse.setText(resJson);
                });
            }
            @Override
            public void onFailure(Call<PokemonModel.Characteristic> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
