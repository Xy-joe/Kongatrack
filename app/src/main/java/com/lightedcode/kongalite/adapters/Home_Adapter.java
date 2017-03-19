package com.lightedcode.kongalite.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lightedcode.kongalite.R;

/**
 * Created by joebuntu on 3/12/17.
 */

public class Home_Adapter extends PagerAdapter{
int[] img_exibit={R.raw.suit5,R.raw.odema,R.raw.wed5,R.raw.mob,R.raw.underwear,R.raw.anka7a,R.raw.lap1};
    Context ctx;
    private LayoutInflater inflater;

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myview = inflater.inflate(R.layout.home_intro_content,container,false);
        ImageView iv = (ImageView)myview.findViewById(R.id.dp);
        TextView header = (TextView)myview.findViewById(R.id.designer);
        TextView desi = (TextView)myview.findViewById(R.id.designer2);
        TextView pri = (TextView)myview.findViewById(R.id.price);
        iv.setImageResource(img_exibit[position]);
      String[] tt=  header.getResources().getStringArray(R.array.collections);
        desi.getResources().getStringArray(R.array.collections);
        header.setText(tt[position]);
        desi.setText(tt[position]);
        pri.getResources().getStringArray(R.array.price);
        container.addView(myview);
        return myview;
    }

    public Home_Adapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return img_exibit.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout)object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

}
