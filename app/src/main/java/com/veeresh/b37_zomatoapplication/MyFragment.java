package com.techpalle.b37_zomatoapplication;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {
    //13. declare all required variables
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    MyAdapter myAdapter;
    MyTask myTask;
    ArrayList<Restaurant> arrayList;

    //12. prepare recycler view adapter
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getActivity().getLayoutInflater().inflate(
                                            R.layout.row, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            //a. get data from arraylist based on position
            Restaurant r = arrayList.get(position);
            //b. display data on the viewholder (row.xml)
            holder.tv1.setText(r.getRestaurant_name());
            holder.tv2.setText(r.getRestaurant_address());
            holder.tv3.setText(r.getRestaurant_cuisines());
            float rating = Float.parseFloat(r.getRestaurant_rating());
            holder.rb.setRating(rating); //set rating on rating bar
            String imageurl = r.getRestaurant_image();
            //load image onto imageview - using glide library
            Glide.with(getActivity()).load(imageurl).into(holder.iv1);
        }
        @Override
        public int getItemCount() {
            return arrayList.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView tv1, tv2, tv3;
            ImageView iv1, iv2;
            RatingBar rb;

            public ViewHolder(View itemView) {
                super(itemView);
                tv1 = itemView.findViewById(R.id.text);
                tv2 = itemView.findViewById(R.id.textView1);
                tv3 = itemView.findViewById(R.id.textView2);
                iv1 = itemView.findViewById(R.id.image);
                //add onclick listener for image view 1
                iv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //get latitude & longitude where user is clicking
                        int pos = getAdapterPosition();
                        Restaurant r = arrayList.get(pos);
                        double latitude = Double.parseDouble(r.getRestaurant_latitude());
                        double longitude = Double.parseDouble(r.getRestaurant_longitude());
                        Intent in = new Intent(getActivity(), MapsActivity.class);
                        in.putExtra("latitude",latitude);
                        in.putExtra("longitude",longitude);
                        in.putExtra("name",r.getRestaurant_name());
                        getActivity().startActivity(in);
                    }
                });
                iv2 = itemView.findViewById(R.id.imageView3);
                rb = itemView.findViewById(R.id.ratingBar1);
            }
        }
    }
    //11. async task inner class
    public class MyTask extends AsyncTask<String, Void, String>{
        URL myurl;
        HttpURLConnection con;
        InputStream is;
        InputStreamReader reader;
        BufferedReader br;
        String str;
        StringBuilder sb;

        //on pre execute is not required
        @Override
        protected String doInBackground(String... strings) {
            try {
                myurl = new URL(strings[0]);
                con = (HttpURLConnection) myurl.openConnection();
                //we have to apply some get request properties
                con.setRequestProperty("Accept","application/json");
                con.setRequestProperty("user-key","8c35b43b80354924682997cff4a22a0b");

                is = con.getInputStream();
                reader = new InputStreamReader(is);
                br = new BufferedReader(reader);
                sb = new StringBuilder();
                do{
                    str = br.readLine();
                    sb.append(str);
                }while(str != null);

                return sb.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("B37","WRONG URL.."+e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("B37","NETWORK ERROR.."+e.getMessage());
            }
            return null;
        }
        //on progress update is not required
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getActivity(), "ZOMATO RESULT: \n"+s, Toast.LENGTH_SHORT).show();
            //18. JSON REVERSE PARSING LOGIC
            try {
                JSONObject j = new JSONObject(s);
                JSONArray arr = j.getJSONArray("nearby_restaurants");
                for(int i=0; i<arr.length(); i++){
                    JSONObject temp = arr.getJSONObject(i);
                    JSONObject restaurant = temp.getJSONObject("restaurant");
                    String restaurant_name = restaurant.getString("name");
                    JSONObject location = restaurant.getJSONObject("location");
                    String restaurant_address = location.getString("locality");
                    String restaurant_latitude = location.getString("latitude");
                    String restaurant_longitude = location.getString("longitude");
                    String restaurant_cuisines = restaurant.getString("cuisines");
                    String restaurant_image = restaurant.getString("thumb");
                    JSONObject user_rating = restaurant.getJSONObject("user_rating");
                    String restaurant_rating = user_rating.getString("aggregate_rating");
                    //create restaurant object
                    Restaurant r = new Restaurant(restaurant_name,restaurant_cuisines,restaurant_address,restaurant_image,restaurant_latitude,restaurant_longitude,restaurant_rating);
                    //add restaurant to arraylist
                    arrayList.add(r);
                }
                Toast.makeText(getActivity(), "ADDED TO ARRAYLIST", Toast.LENGTH_SHORT).show();
                myAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "JSON ERROR", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //10.go to Fragment java file, write method for interntCheck()
    //with the help of Connectivity manager.
    public boolean internetCheck(){
        ConnectivityManager manager = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(manager != null){
            NetworkInfo info = manager.getActiveNetworkInfo();
            if(info!=null && info.isConnected()){
                return true;
            }
        }
        return  false;
    }

    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my, container, false);
        //14. initialize all variables
        recyclerView = v.findViewById(R.id.recyclerView1);
        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        myTask = new MyTask();
        myAdapter = new MyAdapter();
        arrayList = new ArrayList<Restaurant>();
        //15. establish all links
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(myAdapter);
        //16. check if internet is available and then start asynctask with
        //zomato url
        if (internetCheck() == false){
            Toast.makeText(getActivity(), "NO INTERNET", Toast.LENGTH_SHORT).show();
        }else{
            myTask.execute("https://developers.zomato.com/api/v2.1/geocode?lat=12.8984&lon=77.6179");
        }
        return v;
    }

}
