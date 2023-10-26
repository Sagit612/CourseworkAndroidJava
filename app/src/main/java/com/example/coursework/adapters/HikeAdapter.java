package com.example.coursework.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework.R;
import com.example.coursework.models.Hike;
import com.example.coursework.models.HikeWithObservations;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.HikeViewHolder> {
    private List<HikeWithObservations> hikeWithObservations;


    public HikeAdapter(List<HikeWithObservations> hikes) {
        this.hikeWithObservations = hikes;
    }
    ;
    private OnItemClickListener onItemClickListener;
    private OnDeleteClickListener onDeleteClickListener;
    private OnMoreClickListener onMoreClickListener;
    public interface OnDeleteClickListener {
        void onDeleteClick(HikeWithObservations hikeWithObservations);
    }
    public interface OnMoreClickListener {
        void onMoreCLick(HikeWithObservations hikeWithObservations);
    }
    public interface OnItemClickListener {
        void onItemClick(HikeWithObservations hikeWithObservations);
    }

    public HikeAdapter(List<HikeWithObservations> hikeWithObservations, OnDeleteClickListener onDeleteClickListener, OnMoreClickListener onMoreClickListener, OnItemClickListener onItemClickListener) {
        this.hikeWithObservations = hikeWithObservations;
        this.onDeleteClickListener = onDeleteClickListener;
        this.onMoreClickListener = onMoreClickListener;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public HikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hike_card, parent, false);
        return new HikeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HikeViewHolder holder, int position) {
        HikeWithObservations hike = hikeWithObservations.get(position);
        holder.name.setText(hike.hike.getName());
        holder.itemView.setOnClickListener(view -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(hikeWithObservations.get(position));
                }
        });
        holder.moreButton.setOnClickListener(view -> {
            if (onMoreClickListener != null) {
                onMoreClickListener.onMoreCLick(hikeWithObservations.get(position));
            }
        });
        holder.deleteButton.setOnClickListener(view -> {
            if(onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(hikeWithObservations.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return hikeWithObservations.size();
    }



//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.listener = listener;
//    }

    public static class HikeViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        MaterialButton moreButton, deleteButton;

        public HikeViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.hikeName);
            moreButton = itemView.findViewById(R.id.moreButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
