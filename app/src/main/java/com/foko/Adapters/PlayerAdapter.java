package com.foko.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foko.nhl.R;
import com.foko.pojo.roster.Roster;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    Context mContext;
    List<Roster> mData;
    ItemClickListener listener;

    public PlayerAdapter(Context context){
        this.mContext =  context;
        mData = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_player, parent, false);
        return new PlayerAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name.setText(mData.get(position).getPerson().getFullName());
        holder.position.setText(mData.get(position).getPosition().getType());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.OnItemClick(mData.get(position).getPerson().getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addDataSet(List<Roster> list){
        this.mData = list;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.p_name);
            position = itemView.findViewById(R.id.p_posision);
        }
    }

    public void setListener(ItemClickListener listener){
        this.listener = listener;
    }

    public interface ItemClickListener{
        void OnItemClick(int id);
    }
}
