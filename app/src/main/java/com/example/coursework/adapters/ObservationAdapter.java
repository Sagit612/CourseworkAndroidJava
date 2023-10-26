package com.example.coursework.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework.R;
import com.example.coursework.models.Observation;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.ObservationHolder> {

    private List<Observation> observations;
    private OnDeleteClickListener onDeleteClickListener;
    private OnEditClickListener onEditClickListener;

    public interface OnEditClickListener {
        void onEditCLick(Observation observation);
    }
    public interface OnDeleteClickListener {
        void onDeleteClick(Observation observation);
    }

    public ObservationAdapter(List<Observation> observations, OnDeleteClickListener onDeleteClickListener, OnEditClickListener onMoreClickListener) {
        this.observations = observations;
        this.onDeleteClickListener = onDeleteClickListener;
        this.onEditClickListener = onMoreClickListener;
    }

    @NonNull
    @Override
    public ObservationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_observation_card, parent, false);
        return new ObservationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ObservationHolder holder, int position) {
        Observation observation = observations.get(position);
        holder.name.setText(observation.getObservation());
        holder.deleteButton.setOnClickListener(view -> {
            if(onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(observation);
            }
        });
        holder.editButton.setOnClickListener(view -> {
            if (onEditClickListener != null) {
                onEditClickListener.onEditCLick(observation);
            }
        });
    }

    @Override
    public int getItemCount() {
        return observations.size();
    }

    public static class ObservationHolder extends RecyclerView.ViewHolder {

        TextView name;
        MaterialButton editButton, deleteButton;
        public ObservationHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.hikeName);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
