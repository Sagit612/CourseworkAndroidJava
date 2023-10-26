package com.example.coursework.forms;

import android.view.View;

import androidx.appcompat.app.AlertDialog;

public class ObservationInputForm implements IForm {
    public long  hikeId;
    public String observation;
    public String time;
    public String comment;

    public ObservationInputForm(long hikeId, String observation, String time, String comment) {
        this.hikeId = hikeId;
        this.observation = observation;
        this.time = time;
        this.comment = comment;
    }

    @Override
    public boolean validateFields(View rootView) {
        int desiredLength = 2;
        if(observation.length() < desiredLength) {
            AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
            builder.setMessage("All required fields must be filled")
                    .setTitle("Confirmation")
                    .setPositiveButton("OK", (dialog, id) -> {
                        dialog.cancel();
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        } else if (time.length() < desiredLength) {
            AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
            builder.setMessage("All required fields must be filled")
                    .setTitle("Confirmation")
                    .setPositiveButton("OK", (dialog, id) -> {
                        dialog.cancel();
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        } else if (comment.length() < desiredLength) {
            AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
            builder.setMessage("All required fields must be filled")
                    .setTitle("Confirmation")
                    .setPositiveButton("OK", (dialog, id) -> {
                        dialog.cancel();
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        } else {
            return true;
        }
    }
}