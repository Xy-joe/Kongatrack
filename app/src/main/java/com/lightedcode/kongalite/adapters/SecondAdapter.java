package com.lightedcode.kongalite.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lightedcode.kongalite.R;
import com.lightedcode.kongalite.fragments.Homeviews;
import com.lightedcode.kongalite.models.Content;
import com.lightedcode.kongalite.views.Categories;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by joebuntu on 3/18/17.
 */

public class SecondAdapter extends RecyclerView.Adapter<SecondAdapter.MyviewHolder> {
    Context ctx;
    Content data;
    ArrayList<Content> list = new ArrayList<>();
    LayoutInflater inflater;
   ClickListner clickListner;
    public SecondAdapter(Context ctx, ArrayList<Content> list) {
        this.ctx = ctx;
        this.list = list;

    }
    public interface ClickListner {

        public void Itemclicked(View view, int position);

    }


    public class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView title;
        CardView card;

        public MyviewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            image = (ImageView) itemView.findViewById(R.id.dp);
            title = (TextView) itemView.findViewById(R.id.designer2);
            card = (CardView) itemView.findViewById(R.id.card);
        }

        @Override
        public void onClick(View view) {
            int number = getAdapterPosition();
            Content list1 = list.get(number);
            Intent intent = new Intent(ctx, Categories.class);
            ctx.startActivity(intent);


        }
    }


    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.home_intro_content2,parent,false);
        MyviewHolder mvh = new MyviewHolder(view);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {
        data = list.get(position);
        holder.title.setText(data.getDescription());
        holder.image.setImageResource(data.getImg());
        Picasso.with(ctx).load(data.getImg()).into(holder.image);


    }
    public void setClickListner(ClickListner clickListner){
        this.clickListner = clickListner;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
