package com.lightedcode.kongalite.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lightedcode.kongalite.ClickListner;
import com.lightedcode.kongalite.R;
import com.lightedcode.kongalite.models.ListCategory;
import com.lightedcode.kongalite.views.AutoIndus;
import com.lightedcode.kongalite.views.BabyandKids;
import com.lightedcode.kongalite.views.Categories;
import com.lightedcode.kongalite.views.Computer;
import com.lightedcode.kongalite.views.FashionCategories;
import com.lightedcode.kongalite.views.HomeandKitchen;
import com.lightedcode.kongalite.views.Others;
import com.lightedcode.kongalite.views.PhoneCategories;
import com.lightedcode.kongalite.views.TechCategories;

import java.util.ArrayList;

/**
 * Created by joebuntu on 3/13/17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolderClass>{
Context context;
    LayoutInflater inflater;
    ArrayList<ListCategory> data = new ArrayList<>();
    ClickListner clickListner;

    public CategoryAdapter(Context context, ArrayList<ListCategory> data) {
        this.context = context;
        this.data = data;
    }

    public void setClickListner(ClickListner clickListner) {
        this.clickListner = clickListner;
    }

    @Override
    public ViewHolderClass onCreateViewHolder(ViewGroup parent, int viewType) {

                View myview = inflater.from(parent.getContext()).inflate(R.layout.list_content,parent,false);
        ViewHolderClass vhc = new ViewHolderClass(myview);
        return vhc;
    }

    @Override
    public void onBindViewHolder(ViewHolderClass holder, int position) {
        ListCategory list = data.get(position);
        holder.name.setText(list.getNames());
        holder.number.setText(position+")");
       holder.number.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name, number;
        public ViewHolderClass(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = (TextView)itemView.findViewById(R.id.category);
            number = (TextView)itemView.findViewById(R.id.number);

        }

        @Override
        public void onClick(View view) {
            Intent intent;
            switch (getAdapterPosition()){
                case 0:
                    intent = new Intent(context, PhoneCategories.class);
                    break;
                case 1:
                    intent = new Intent(context, Computer.class);
                    break;
                case 2:
                    intent = new Intent(context, AutoIndus.class);
                    break;
                case 3:
                    intent = new Intent(context, TechCategories.class);
                    break;
                case 4:
                    intent = new Intent(context, FashionCategories.class);
                    break;
                case 5:
                    intent = new Intent(context, HomeandKitchen.class);
                    break;
                case 6:
                    intent = new Intent(context, BabyandKids.class);
                    break;
                case 7:
                    intent = new Intent(context, Others.class);
                    break;
                default:
                    intent = new Intent(context, Others.class);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);

        }
    }
}
