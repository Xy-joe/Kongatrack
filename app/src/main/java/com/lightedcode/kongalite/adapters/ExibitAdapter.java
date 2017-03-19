package com.lightedcode.kongalite.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lightedcode.kongalite.R;
import com.lightedcode.kongalite.models.Promo;
import com.lightedcode.kongalite.views.ProductDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by joebuntu on 3/13/17.
 */

public class ExibitAdapter extends RecyclerView.Adapter<ExibitAdapter.ViewHolderClass>{
Context context;
    LayoutInflater inflater;
    ArrayList<Promo> data = new ArrayList<>();
    private ClickListner clickListner;
    public ExibitAdapter(Context context, ArrayList<Promo> data) {
        this.context = context;
        this.data = data;

    }
    public interface ClickListner {

        public void Itemclicked(View view, int position);

    }


    @Override
    public ViewHolderClass onCreateViewHolder(ViewGroup parent, int viewType) {

                View myview = inflater.from(parent.getContext()).inflate(R.layout.list_content_images,parent,false);
        ViewHolderClass vhc = new ViewHolderClass(myview);
        return vhc;
    }

    @Override
    public void onBindViewHolder(ViewHolderClass holder, int position) {
        Promo list = data.get(position);
        holder.name.setText(list.getName());
        holder.details.setText(list.getDescription());
        holder.price.setText("$"+list.getPrice());
        holder.discount.setText(list.getDiscount()+" % discount");
        Picasso.with(context).load(list.getImage()).into(holder.dp);

    }
    public void setClickListner(ClickListner clickListner){
        this.clickListner = clickListner;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name, details,price, discount;
        ImageView dp;
        Context ctx;
        ArrayList<Promo> dat = new ArrayList<>();
        public ViewHolderClass(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = (TextView)itemView.findViewById(R.id.p_name);
            details = (TextView)itemView.findViewById(R.id.details);
            price = (TextView)itemView.findViewById(R.id.price);
            discount = (TextView)itemView.findViewById(R.id.discount);
            dp = (ImageView)itemView.findViewById(R.id.dp);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Promo list = data.get(position);
            Intent intent = new Intent(context, ProductDetails.class);
            intent.putExtra("name_id",list.getName());
            intent.putExtra("details_id",list.getDescription());
            intent.putExtra("price_id",list.getPrice());
            intent.putExtra("discount_id",list.getDiscount());
            intent.putExtra("image_id",list.getImage());
             context.startActivity(intent);
        }
    }
}
