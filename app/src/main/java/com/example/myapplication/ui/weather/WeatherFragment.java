package com.example.myapplication.ui.weather;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherFragment extends Fragment {

    public String city = "Karachi";
    private WeatherViewModel mViewModel;
    public JSONObject obj;


    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        String weather_URL = "https://jazz-travel-guide.herokuapp.com/api/v1/weather?city=";
        weather_URL = weather_URL + city;
        View weather_view = inflater.inflate(R.layout.weather_fragment2, container, false);
        Toast.makeText(weather_view.getContext(), "Refreshing the feed", Toast.LENGTH_SHORT).show();

        TextView weather_type = (TextView) weather_view.findViewById(R.id.weather_type);
        TextView feels_like = (TextView) weather_view.findViewById(R.id.feels_like);
        TextView min_max = (TextView) weather_view.findViewById(R.id.min_max);
        TextView lat_long = (TextView) weather_view.findViewById(R.id.lat_long);
        TextView wind_speed = (TextView) weather_view.findViewById(R.id.wind_speed);
        TextView psi_hum = (TextView) weather_view.findViewById(R.id.psi_hum);
        TextView city_name = (TextView) weather_view.findViewById(R.id.city);
        ImageView weather_image = (ImageView) weather_view.findViewById(R.id.weather_image);

        StringRequest stringRequest = new StringRequest(weather_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    obj = new JSONObject(response);
                    Log.d("data", obj.toString());
                    try {
                        String weather = obj.getString("weather").toString().replace("]", "").replace("[", "");
                        JSONObject weather_desc_obj = new JSONObject(weather);
                        String weather_desc = weather_desc_obj.getString("description");
                        weather_type.setText(weather_desc.substring(0, 1).toUpperCase() + weather_desc.substring(1));
                        String icon = weather_desc_obj.getString("icon");
                        String icon_url = "https://openweathermap.org/img/wn/" + icon + "@2x.png";
                        Log.d("icon", icon_url);

                        Glide.with(weather_view.getContext()).load(icon_url).into(weather_image);
//                        URL get_icon = new URL(icon_url);
//                        Bitmap w_icon = BitmapFactory.decodeStream(get_icon.openConnection().getInputStream());
//                        weather_image.setImageBitmap(w_icon);


                        String temperature = obj.getString("main");
                        JSONObject temperature_desc_obj = new JSONObject(temperature);
                        feels_like.setText("Real Feel: " + temperature_desc_obj.getString("feels_like") + " C");
                        min_max.setText("Minimum: " + temperature_desc_obj.getString("temp_min") + "\nMaximum: " + temperature_desc_obj.getString("temp_max") + " C");
                        psi_hum.setText("Pressure: " + temperature_desc_obj.getString("pressure") + " Pa  Humidity: " + temperature_desc_obj.getString("humidity") + " %");
                        city_name.setText(obj.getString("name"));

                        String coords = obj.getString("coord");
                        JSONObject coords_obj = new JSONObject(coords);
                        lat_long.setText("Latitude: " + coords_obj.getString("lat") + "°   Longitude: " + coords_obj.getString("lon") + "°");

                        String wind = obj.getString("wind");
                        JSONObject wind_obj = new JSONObject(wind);
                        wind_speed.setText("Wind Speed: " + wind_obj.getString("speed") + " km/h");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("MainActivity.class", "onErrorResponse: " + error.getMessage());

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


        return weather_view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        // TODO: Use the ViewModel
    }

}