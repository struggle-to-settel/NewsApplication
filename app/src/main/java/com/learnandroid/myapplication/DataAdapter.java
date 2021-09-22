package com.learnandroid.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    private Context context;
    private ArrayList<Data> dataArrayList;
    private OnItemClickListener mListener;
    int total, current;

    interface  OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    //constructor for adapter
    public DataAdapter(Context context, ArrayList<Data> dataArrayList) {
        this.context = context;
        this.dataArrayList = dataArrayList;


    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.example, parent, false);
        return new DataViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {

        Data currentData = dataArrayList.get(position);
        holder.title.setText(currentData.getTitle());
        holder.description.setText(currentData.getDescription());
        holder.source.setText(currentData.getSource());

        String url = currentData.getImageUrl();
        if (url.length() < 5) {
            holder.imageView.setImageResource(R.drawable.na);
        } else {
            Picasso.with(context).load(url).fit().centerCrop().into(holder.imageView);
        }


    }


    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }


    public class DataViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView title;
        public TextView description;
        public TextView source;

        public DataViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image_view);
            title = view.findViewById(R.id.text);
            description = view.findViewById(R.id.text2);
            source = view.findViewById(R.id.text3);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }


    }
}
