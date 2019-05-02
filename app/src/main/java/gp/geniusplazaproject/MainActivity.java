package gp.geniusplazaproject;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.ProfileAdapter;
import Model.ProfileModel;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ProfileAdapter mProfileAdapter;
    private ArrayList<ProfileModel> mProfileList;
    private RequestQueue mRequestQueue;

    String url = "https://reqres.in/api/users";

    String first, last, image;

    private int count_id;

    private static final String TAG = "MainActivity";

    private Dialog logDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProfileList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);

        parseJSON();

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addUser();

            }
        });

        logDialog = new Dialog(this);
    }

    private void parseJSON() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject data = jsonArray.getJSONObject(i);

                                int id = data.getInt("id");
                                String fName = data.getString("first_name");
                                String lName = data.getString("last_name");
                                String imageUrl = data.getString("avatar");


                                mProfileList.add(new ProfileModel(id, fName, lName, imageUrl));

                            }

                            mProfileAdapter = new ProfileAdapter(MainActivity.this, mProfileList);
                            mRecyclerView.setAdapter(mProfileAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

    private  void postProfile( final int id, final String fName, final String lLast, final String avatar) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(MainActivity.this, "User Added Successfully", Toast.LENGTH_SHORT).show();
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("first_name", fName);
                params.put("last_name", lLast);
                params.put("id", String.valueOf(id+1));
                params.put("avatar", avatar);

                return params;
            }
        };

        mRequestQueue.add(postRequest);
    }

    private void addUser(){

        logDialog.setContentView(R.layout.layout_confirm_dialog);
        ProfileModel user = new ProfileModel(  );

        final EditText addFirstName = logDialog.findViewById( R.id.add_first );
        final EditText addLastName = logDialog.findViewById( R.id.add_last );
        final EditText addImg = logDialog.findViewById( R.id.add_image );

        Button confirm = logDialog.findViewById( R.id.confirm );
        Button cancel = logDialog.findViewById( R.id.cancel );





        confirm.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                first = addFirstName.getText().toString();
                last = addLastName.getText().toString();
                image = addImg.getText().toString();

                postProfile(count_id, first,last,image);
                logDialog.dismiss();

            }
        } );
        cancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logDialog.dismiss();
            }
        } );
        logDialog.getWindow().setBackgroundDrawable(new ColorDrawable( Color.TRANSPARENT));
        logDialog.show();

    }
}
