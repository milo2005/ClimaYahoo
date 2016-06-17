package unicauca.movil.clima;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import unicauca.movil.clima.net.HttpAsyncTask;

public class MainActivity extends AppCompatActivity implements HttpAsyncTask.OnHttpResponse {

    TextView des, temp, hum, pres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        des = (TextView) findViewById(R.id.descripcion);
        temp = (TextView) findViewById(R.id.temp);
        hum = (TextView) findViewById(R.id.humedad);
        pres = (TextView) findViewById(R.id.presion);

        HttpAsyncTask task = new HttpAsyncTask(HttpAsyncTask.GET, this);
        task.execute("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22popayan%2C%20co%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");
    }

    @Override
    public void onResponse(String response) {

        try {
            JSONObject json = new JSONObject(response);
            JSONObject query = json.getJSONObject("query");
            JSONObject results = query.getJSONObject("results");
            JSONObject channel = results.getJSONObject("channel");
            JSONObject atmosphere = channel.getJSONObject("atmosphere");

            String humedad = atmosphere.getString("humidity");
            String presion = atmosphere.getString("pressure");

            JSONObject item = channel.getJSONObject("item");
            JSONObject condition = item.getJSONObject("condition");

            String descripcion = condition.getString("text");
            String temperatura = condition.getString("temp");

            temp.setText(temperatura);
            hum.setText(humedad);
            des.setText(descripcion);
            pres.setText(presion);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
