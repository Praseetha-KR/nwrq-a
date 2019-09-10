package in.imagineer.nwrq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class VolleyActivity extends AppCompatActivity {
    TextView textRequest;
    TextView textResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);

        Intent intent = getIntent();
        String url = intent.getStringExtra(MainActivity.API_URL);

        textRequest = findViewById(R.id.textRequest);
        textRequest.setText(R.string.state_requesting);

        textResponse = findViewById(R.id.textResponse);
        textResponse.setText(R.string.state_waiting_response);

        reqWithVolley(url);
    }

//    void reqWithVolley(String url) {
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        StringRequest getRequest = new StringRequest(
//            Request.Method.GET,
//            url,
//            (response) -> {
//                String resJson = Utils.prettifyJson(response);
//                textResponse.setText(resJson);
//            },
//            (error) -> {
//                textResponse.setText(R.string.state_error);
//            }
//        );
//        textRequest.setText(getRequest.toString());
//
//        queue.add(getRequest);
//    }


    void reqWithVolley(String url) {
        url = "https://reqres.in/api/users?hello=1&hi=2";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest postRequest = new StringRequest(
                Request.Method.POST,
                url,
                (response) -> {
                    String resJson = Utils.prettifyJson(response);
                    textResponse.setText(resJson);
                },
                (error) -> {
                    textResponse.setText(R.string.state_error);
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("name", "Neo");
                params.put("job", "programmer");
                return params;
            }
            @Override
            public Map getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json");
                headers.put("X-Test-1", "test1");
                headers.put("X-Test-2", "test2");
                return headers;
            }
        };
        textRequest.setText(postRequest.toString());

        queue.add(postRequest);
    }
}
