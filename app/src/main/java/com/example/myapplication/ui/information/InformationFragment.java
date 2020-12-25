package com.example.myapplication.ui.information;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.JsonReader;
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

public class InformationFragment extends Fragment {

    private InformationViewModel mViewModel;

    public static final String URL = "https://jazz-travel-guide.herokuapp.com/api/v1/location-info?location=";
    public String str_location = "";

    public String data = "";

    public JSONObject obj;

    public static InformationFragment newInstance() {
        return new InformationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View information = inflater.inflate(R.layout.information_fragment, container, false);
        TextView location = (TextView) information.findViewById(R.id.location);
        TextView description = (TextView) information.findViewById(R.id.description);
        TextView more_description = (TextView) information.findViewById(R.id.more_description);

        Button get_information = (Button) information.findViewById(R.id.get_information);

        TextInputEditText tourist_spot = (TextInputEditText) information.findViewById(R.id.tourist_spot);


        get_information.setOnClickListener(view -> {
            if (tourist_spot.getText().toString().isEmpty()) {
                Toast.makeText(information.getContext(), "Please enter any tourist spot and try again !", Toast.LENGTH_SHORT).show();
            } else {
                str_location = tourist_spot.getText().toString();
                description.setText("Loading....");
                more_description.setText("....");
                StringRequest stringRequest = new StringRequest(URL + str_location, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            obj = new JSONObject(response);
                            try {
                                description.setText(obj.getString("description"));
                                more_description.setText(obj.getString("more_description"));
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

                location.setText(str_location);
                tourist_spot.setText("");
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);

            }

        });


        return information;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InformationViewModel.class);
        // TODO: Use the ViewModel
    }

}