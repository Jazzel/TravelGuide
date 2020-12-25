package com.example.myapplication.ui.distance;

import androidx.lifecycle.ViewModelProvider;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class DistanceFragment extends Fragment {

    private DistanceViewModel mViewModel;

    public static DistanceFragment newInstance() {
        return new DistanceFragment();
    }

    public JSONObject obj;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View distance = inflater.inflate(R.layout.distance_fragment, container, false);
        TextInputEditText city_1 = (TextInputEditText) distance.findViewById(R.id.city_1);
        TextInputEditText city_2 = (TextInputEditText) distance.findViewById(R.id.city_2);
        TextView description = (TextView) distance.findViewById(R.id.description);
        TextView cities = (TextView) distance.findViewById(R.id.cities);

        Button get_distance = (Button) distance.findViewById(R.id.get_distance);

        get_distance.setOnClickListener(view -> {
            String URL = "https://jazz-travel-guide.herokuapp.com/api/v1/distance?";

            if (city_1.getText().toString().isEmpty() || city_2.getText().toString().isEmpty()) {
                Toast.makeText(distance.getContext(), "Please enter both cites and try again !", Toast.LENGTH_SHORT).show();
            } else {
                URL = URL + "city_1=" + city_1.getText().toString() + "&city_2=" + city_2.getText().toString();
                cities.setText("From " + city_1.getText().toString() + " to " + city_2.getText().toString());
                description.setText("Loading....");
                StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            obj = new JSONObject(response);
                            try {
                                description.setText(obj.getString("distance"));
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
                city_1.setText("");
                city_2.setText("");
            }

        });

        return distance;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DistanceViewModel.class);
        // TODO: Use the ViewModel
    }


}