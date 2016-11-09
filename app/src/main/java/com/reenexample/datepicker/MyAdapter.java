package com.reenexample.datepicker;

/**
 * Created by reen on 7/11/16.
 */
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder>{
    Context c;
    ArrayList<NewTodo> todos;
    String mDrawableName = "1";
    private RecyclerView.Adapter<MyHolder> mClass;
    public MyAdapter(Context c, ArrayList<NewTodo> todos) {
        this.c = c;
        this.todos = todos;
    }
    //INITIALIZE VIEWHODER
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //VIEW OBJ
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.model_final,null);
        //HOLDER
        MyHolder holder=new MyHolder(v);
        return holder;
    }
    //BIND VIEW TO DATA
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.img.setImageResource(R.drawable.list);
        String date = todos.get(position).getDate();
        String img = date.split("/")[0];
        holder.nametxt.setText(todos.get(position).getTitle());
        holder.posTxt.setText(todos.get(position).getDate());
        //CLICKED
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                //Snackbar.make(v,todos.get(pos).getTitle(),Snackbar.LENGTH_SHORT).show();
                Intent i1 = new Intent (c, DetailsActivity.class);

                Bundle extras = new Bundle();
                extras.putString("key",todos.get(pos).getDate());
                extras.putString("key1",todos.get(pos).getTime());
                extras.putString("key2",todos.get(pos).getTitle());
                extras.putString("key3",todos.get(pos).getDetails());
                //extras.putString("key4",todos.get(pos).toString());
                i1.putExtras(extras);

                //i1.putExtra("key3",todos.get(pos).getDetails());
                c.startActivity(i1);
            }

        });
    }



    public void removeItem(int position) {
        todos.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, todos.size());
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public int getDrawableId(String name) {
        Class<?> c = R.drawable.class;
        Field f = null;
        int id = 0;

        try {
            f = R.drawable.class.getField(name);
            id = f.getInt(null);
        } catch (NoSuchFieldException e) {
            Log.i("Reflection", "Missing drawable " + name);
        } catch (IllegalAccessException e) {
            Log.i("Reflection", "Illegal access to field " + name);
        }

        return id;
    }


}


