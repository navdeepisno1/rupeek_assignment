package com.suvidha.rupeekhiring;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerViewAdapterPOI extends RecyclerView.Adapter<RecyclerViewAdapterPOI.ViewH> {
    private List<POI> poiList;
    private Context context;

    public RecyclerViewAdapterPOI(List<POI> poiList, Context context) {
        this.poiList = poiList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_poi_card, parent,false);
        return new ViewH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterPOI.ViewH holder, int position) {

        POI poi = poiList.get(position);

        holder.textView_name.setText(poi.getName());
        holder.textView_address.setText(poi.getAddress());

        SharedPreferences sharedPreferences = context.getSharedPreferences("upvote", Context.MODE_PRIVATE);
        if(sharedPreferences.getInt(poi.getName(),-1) == poi.getId())
        {
            holder.imageView_fav.setImageResource(R.drawable.icon_fav_selected);
        }
        else
        {
            holder.imageView_fav.setImageResource(R.drawable.icon_fav_not_selected);
        }

        holder.imageView_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("count","" + sharedPreferences.getInt("count",-1));

                if(sharedPreferences.getInt(poi.getName(),-1) == poi.getId())
                {
                    //Change icon
                    holder.imageView_fav.setImageResource(R.drawable.icon_fav_not_selected);
                    //Decrease Count from sharedpref
                    decreaseCount(poi.getName());
                }
                else
                {
                    //Change icon
                    int count = sharedPreferences.getInt("count",0);
                    if(count==3)
                    {
                        Toast.makeText(context,"You can vote only 3 destinations",Toast.LENGTH_LONG).show();
                        return;
                    }
                    holder.imageView_fav.setImageResource(R.drawable.icon_fav_selected);
                    //Increase Count from sharedpref
                    increaseCount(poi.getName(),poi.getId());
                }
            }
        });

        Glide
                .with(context)
                .load(poi.getImagePath())
                .centerCrop()
                .placeholder(R.drawable.icon_imageloading)
                .into(holder.imageView);

        holder.button_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Destination.class);
                intent.putExtra("name",poi.getName());
                intent.putExtra("address",poi.getAddress());
                intent.putExtra("latitude",poi.getLatitude());
                intent.putExtra("longitude",poi.getLongitude());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return poiList.size();
    }

    public class ViewH extends RecyclerView.ViewHolder
    {
        ImageView imageView,imageView_fav;
        TextView textView_name,textView_address;
        Button button_show;
        public ViewH(@NonNull View itemView) {
            super(itemView);
            imageView_fav = itemView.findViewById(R.id.rv_item_poi_iv_fav);
            imageView = itemView.findViewById(R.id.rv_item_poi_iv);
            textView_name = itemView.findViewById(R.id.rv_item_poi_tv_name);
            textView_address = itemView.findViewById(R.id.rv_item_poi_tv_address);
            button_show = itemView.findViewById(R.id.rv_item_poi_btn_show_direction);
        }
    }

    private void decreaseCount(String name)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("upvote", Context.MODE_PRIVATE);
        int count = sharedPreferences.getInt("count",0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("count",count-1);
            editor.putInt(name,-1);
            editor.apply();
    }

    private void increaseCount(String name ,int id)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("upvote", Context.MODE_PRIVATE);
        int count = sharedPreferences.getInt("count",0);

        if(count==3)
        {
            Toast.makeText(context,"You can vote only 3 destinations",Toast.LENGTH_LONG).show();
            return;
        }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("count",count+1);
            editor.putInt(name,id);
            editor.apply();
    }
}

