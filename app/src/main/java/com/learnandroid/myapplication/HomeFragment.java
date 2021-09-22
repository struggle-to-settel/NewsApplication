package com.learnandroid.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements DataAdapter.OnItemClickListener {

    public static final String API_KEY = "pub_13349e367046a126386612e882d93e950055";
    public static final String PARAM = "param";
    public static final String AUTHOR = "author";
    public static final String TITLE = "title";
    public static final String DETAIL = "detail";
    public static final String URL = "url";
    String keyword;
    String url;
    int page = 1;
    NestedScrollView nestedScrollView;
    ProgressBar progressBar;
    RequestQueue requestQueue;
    DataAdapter dataAdapter;
    ArrayList<Data> list;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;


    public static HomeFragment newInstance(String param){
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM,param);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        if(getArguments().getString(PARAM)!=null){
            keyword =  getArguments().getString("param","");
        }else{
            keyword  = "&language=fr";
        }

        url = "https://newsdata.io/api/1/news?apikey="+API_KEY+"&language=en"+keyword;
        progressBar = view.findViewById(R.id.progressBar);
        nestedScrollView = view.findViewById(R.id.nestedView);
        recyclerView = view.findViewById(R.id.recyclerView);
        NavigationView navigationView = view.findViewById(R.id.navigation_view);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<Data>();
        requestQueue = Volley.newRequestQueue(getContext());
        parseJson(url);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                  if(scrollY==v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight()){
                      page++;
                      progressBar.setVisibility(View.VISIBLE);
                      if(page<23){
                          url = url+"&page="+page;
                          parseJson(url);
                      }
                  }
            }
        });
        return view;
    }


    private void parseJson(String url) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject results = jsonArray.getJSONObject(i);
                                String description = results.getString("description");
                                String url = results.getString("image_url");
                                String title = results.getString("title");
                                String content = results.getString("content");
                                String source = results.getString("source_id");
                                if(content.length()>=description.length()){
                                    list.add(new Data(title,content,source,url));
                                }else {
                                    list.add(new Data(title, description, source, url));
                                }
                            }
                            if (progressBar.getVisibility()==View.VISIBLE){
                                progressBar.setVisibility(View.GONE);
                            }
                            dataAdapter = new DataAdapter(getContext(), list);
                            recyclerView.setAdapter(dataAdapter);
                            dataAdapter.setOnItemClickListener(HomeFragment.this::onItemClick);
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

        requestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent =  new Intent(getActivity(),DetailActivity.class);
         Data clickedData = list.get(position);
         detailIntent.putExtra(AUTHOR,clickedData.getSource());
         detailIntent.putExtra(URL,clickedData.getImageUrl());
         detailIntent.putExtra(DETAIL,clickedData.getDescription());
         detailIntent.putExtra(TITLE,clickedData.getTitle());
         startActivity(detailIntent);
         
    }
}
