package com.lightedcode.kongalite.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lightedcode.kongalite.R;
import com.lightedcode.kongalite.models.ListCategory;

import java.util.ArrayList;

/**
 * Created by joebuntu on 3/13/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolderClass>{
Context context;
    LayoutInflater inflater;
    ArrayList<ListCategory> data = new ArrayList<>();

    public NewsAdapter(Context context, ArrayList<ListCategory> data) {
        this.context = context;
        this.data = data;
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

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name, number;
        public ViewHolderClass(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.category);
            number = (TextView)itemView.findViewById(R.id.number);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
