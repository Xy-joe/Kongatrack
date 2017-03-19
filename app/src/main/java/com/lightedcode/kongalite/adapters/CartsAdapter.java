package com.lightedcode.kongalite.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lightedcode.kongalite.R;
import com.lightedcode.kongalite.models.ListCategory;
import com.lightedcode.kongalite.models.Promo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by joebuntu on 3/13/17.
 */

public class CartsAdapter extends RecyclerView.Adapter<CartsAdapter.ViewHolderClass>{
Context context;
    LayoutInflater inflater;
    ArrayList<Promo> data = new ArrayList<>();

    public CartsAdapter(Context context, ArrayList<Promo> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolderClass onCreateViewHolder(ViewGroup parent, int viewType) {

                View myview = inflater.from(parent.getContext()).inflate(R.layout.cart_contents,parent,false);
        ViewHolderClass vhc = new ViewHolderClass(myview);
        return vhc;
    }

    @Override
    public void onBindViewHolder(ViewHolderClass holder, int position) {
        Promo list = data.get(position);
        holder.name.setText(list.getName());
        holder.price.setText("$"+list.getPrice());
        holder.discount.setText(list.getDiscount()+"discount");
        holder.details.setText(list.getDescription());
        Picasso.with(context).load(list.getImage()).into(holder.dp);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name, details, price,discount;
        ImageView dp;
        public ViewHolderClass(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.p_name);
            details = (TextView)itemView.findViewById(R.id.details);
            price = (TextView)itemView.findViewById(R.id.price);
            discount = (TextView)itemView.findViewById(R.id.discount);
            dp = (ImageView)itemView.findViewById(R.id.dp);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
