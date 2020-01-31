package com.foko.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.foko.nhl.R;
import com.foko.pojo.TeamsDetailsObject;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.CustomViewHolder> {

    private List<TeamsDetailsObject> dataList;
    private Context context;
    private ItemClickListener listener;

    public TeamsAdapter(Context context,List<TeamsDetailsObject> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView txtTitle;
        private ImageView coverImage;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.title);
            coverImage = mView.findViewById(R.id.coverImage);
        }


    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_team_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        holder.txtTitle.setText(dataList.get(position).getName());

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(R.drawable.nhl).networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.coverImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(position);
            }
        });

    }

    public void setListener(ItemClickListener listener){
        this.listener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
