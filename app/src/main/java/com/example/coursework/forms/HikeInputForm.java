package com.example.coursework.forms;

import android.view.View;

import androidx.appcompat.app.AlertDialog;

public class HikeInputForm implements IForm{
    // This class is used for receiving input data from a specific View
    // makes sure all important data is entered
    // can validates the data

    // initialize important data that can received from View
    public String name;
    public String location;
    public String date;
    public boolean parkingAvai;
    public String length;
    public String level;
    public String description;

    public HikeInputForm(String name, String location, String date, boolean parkingAvai, String length, String level, String description) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.parkingAvai = parkingAvai;
        this.length = length;
        this.level = level;
        this.description = description;
    }

    @Override
    public boolean validateFields(View rootView) {
        int desiredLength = 2;
        if(name.length() < desiredLength) {
            AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
            builder.setMessage("All required fields must be filled")
                    .setTitle("Confirmation")
                    .setPositiveButton("OK", (dialog, id) -> {
                        dialog.cancel();
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        } else if (location.length() < desiredLength) {
            AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
            builder.setMessage("All required fields must be filled")
                    .setTitle("Confirmation")
                    .setPositiveButton("OK", (dialog, id) -> {
                        dialog.cancel();
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        } else if (date.length()< desiredLength) {
            AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
            builder.setMessage("All required fields must be filled")
                    .setTitle("Confirmation")
                    .setPositiveButton("OK", (dialog, id) -> {
                        dialog.cancel();
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        } else if (parkingAvai) {
            AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
            builder.setMessage("All required fields must be filled")
                    .setTitle("Confirmation")
                    .setPositiveButton("OK", (dialog, id) -> {
                        dialog.cancel();
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        } else if (length.length() < desiredLength) {
            AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
            builder.setMessage("All required fields must be filled")
                    .setTitle("Confirmation")
                    .setPositiveButton("OK", (dialog, id) -> {
                        dialog.cancel();
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        } else if (level.length() < desiredLength) {
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