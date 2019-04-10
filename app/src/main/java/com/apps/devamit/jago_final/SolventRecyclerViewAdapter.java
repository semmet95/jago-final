package com.apps.devamit.jago_final;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

class SolventRecyclerViewAdapter extends RecyclerView.Adapter<SolventViewHolders> {
    private Context context;
    private String[] imagelabels;
    private int[] imageIds={R.drawable.classifiedspic, R.drawable.adpic, R.drawable.tariffpic, R.drawable.clientspic};

    SolventRecyclerViewAdapter(Context context) {
        this.context=context;
        this.imagelabels=context.getResources().getStringArray(R.array.cardItems);
    }

    @Override
    public SolventViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_card, parent, false);
        return new SolventViewHolders(view);
    }

    @Override
    public void onBindViewHolder(SolventViewHolders holder, int position) {
        holder.imagelabel.setText(imagelabels[position]);
        holder.thumbnail.setImageDrawable(context.getResources().getDrawable(imageIds[position]));
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
