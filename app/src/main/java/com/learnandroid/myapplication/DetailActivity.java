package com.learnandroid.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.learnandroid.myapplication.HomeFragment.AUTHOR;
import static com.learnandroid.myapplication.HomeFragment.DETAIL;
import static com.learnandroid.myapplication.HomeFragment.TITLE;
import static com.learnandroid.myapplication.HomeFragment.URL;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String imgUrl = intent.getStringExtra(URL);
        String detail = intent.getStringExtra(DETAIL);
        String title = intent.getStringExtra(TITLE);
        String author = intent.getStringExtra(AUTHOR);

        ImageView imageView = findViewById(R.id.detail_image);
        TextView detailTitle = findViewById(R.id.detail_title);
        TextView detailAuthor = findViewById(R.id.detail_author);
        TextView detailDescription = findViewById(R.id.detail_details);
        if(imgUrl.length() <5){
            imageView.setImageResource(R.drawable.na);
        }else {
            Picasso.with(this).load(imgUrl).fit().centerCrop().into(imageView);
        }
        detailAuthor.setText(author);
        detailDescription.setText(detail);
        detailTitle.setText(title);

    }
}