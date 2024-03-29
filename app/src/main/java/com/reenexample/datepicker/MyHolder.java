package com.reenexample.datepicker;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by reen on 7/11/16.
 */
public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView img;
    TextView nametxt,posTxt;
    ItemClickListener itemClickListener;
    public MyHolder(View itemView) {
        super(itemView);
        nametxt= (TextView) itemView.findViewById(R.id.nameTxt);
        posTxt= (TextView) itemView.findViewById(R.id.posTxt);
        img= (ImageView) itemView.findViewById(R.id.playerImage);
        itemView.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v,getLayoutPosition());
    }
    public void setItemClickListener(ItemClickListener ic)
    {
        this.itemClickListener=ic;
    }
}
