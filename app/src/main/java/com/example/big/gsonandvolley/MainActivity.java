package com.example.big.gsonandvolley;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RequestQueue queue;
    public static String URL = "";
    public static String URLTest = URL;
    TextView textView;
    TextView idTextView;
    TextView dimosTextView;
    Button resetImageButton;
    NetworkImageView networkImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView = (TextView) findViewById(R.id.textview);
        idTextView = (TextView) findViewById(R.id.points_id);
        dimosTextView = (TextView) findViewById(R.id.points_dimos);

        if (URL.isEmpty()){
            Toast.makeText(this,"Please go to MainActivity class and fill in your URL!",Toast.LENGTH_LONG).show();
        }

        resetImageButton = (Button)findViewById(R.id.resetimagebutton);
        resetImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkImageView.setImageResource(0);
                networkImageView.setImageResource(android.R.color.transparent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                makeTheRequest();
            }
        });
    }

    private void makeTheRequest() {
        // Get a RequestQueue
        queue = MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();
        @SuppressWarnings("unchecked")


        GsonRequest gsonRequest = new GsonRequest(URLTest, Point[].class, null, new Response.Listener<Point[]>() {
            @Override
            public void onResponse(Point[] points) {
                try {
                    List<Point> pointList = Arrays.asList(points);
                    for (Point point : pointList){
                        Toast.makeText(getApplicationContext(),point.getDimos(),Toast.LENGTH_LONG).show();
                        Log.e("A POINT ", point.getId() + "");
                        Log.e("A POINT DIMOS ", point.getDimos());
                        Log.e("A POINT image ", point.getHighResImageUrl1());
                        idTextView.setText(point.getId());
                        dimosTextView.setText(point.getDimos());
                    }

                    ImageLoader imageLoader = MySingleton.getInstance(getApplicationContext()).getImageLoader();
                    networkImageView = (NetworkImageView)findViewById(R.id.networkimageview);
                    String imgurl = pointList.get(1).getHighResImageUrl1();
                    System.out.println("IMG URL");
                    System.out.println(imgurl);
                    networkImageView.setImageUrl(imgurl,imageLoader);
                    //get image at position 1

                }
                catch (Exception e) {
                    Log.e("error", String.valueOf(e));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(volleyError != null) Log.e("MainActivity", volleyError.getMessage());
            }
        });

        queue.add(gsonRequest);
        //loadJsonFromURL();
    }



//    private void loadJsonFromURL() {
//
//        JsonArrayRequest request = new JsonArrayRequest
//                (Request.Method.GET, URL, new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        try {
//
//                            textView.setText(String.valueOf(response));
//                            Toast toast = Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG);
//
//                            toast.show();
//
//
//                        } catch (Exception e) {
//                            Log.i("error", String.valueOf(e));
//                        }
//                    }
//                },new Response.ErrorListener(){
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.i("error", String.valueOf(error));
//                    }
//                });
//
//        MySingleton.getInstance(this).addToRequestQueue(request);
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
