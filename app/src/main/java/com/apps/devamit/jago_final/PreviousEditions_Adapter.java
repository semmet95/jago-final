package com.apps.devamit.jago_final;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PreviousEditions_Adapter extends RecyclerView.Adapter<PreviousEditions_Adapter.ViewHolder> {
    private boolean useOld;

    PreviousEditions_Adapter(boolean uO) {
        this.useOld=uO;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_previous, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String date;
        Bitmap bitmap;
        if(!useOld) {
            date = String.valueOf(MetadataHolder.latestDates[position + 1]);
            bitmap=MainActivity.databaseHelper.retrieveImage(MainActivity.databaseHelper,
                    String.valueOf(MetadataHolder.latestDates[position + 1]) + "01");
            Log.e("mayday :", "using key = "+String.valueOf(MetadataHolder.latestDates[position + 1]) + "01");

        } else {
            date = String.valueOf(MetadataHolder.oldDates[position + 1]);
            bitmap=MainActivity.databaseHelper.retrieveImage(MainActivity.databaseHelper,
                    String.valueOf(MetadataHolder.oldDates[position + 1]) + "01");
            Log.e("mayday :", "using key = "+String.valueOf(MetadataHolder.oldDates[position + 1]) + "01");
        }
        date = date.substring(6) + "-" + date.substring(4, 6) + "-" + date.substring(0, 4);

        holder.thumbnail.setImageBitmap(bitmap);
        holder.editionName.setText(date);
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullscreenAdapter.key=holder.getAdapterPosition()+1;
                Intent intent=new Intent(v.getContext(), FullscreenActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView editionName;
        ViewHolder(final View itemView) {
            super(itemView);
            thumbnail= itemView.findViewById(R.id.previous_thumbnail);
            editionName= itemView.findViewById(R.id.previous_imagelabel);
        }
    }
}