package com.example.coursework.fragments.hike;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coursework.R;
import com.example.coursework.activities.MainActivity;
import com.example.coursework.database.AppDatabase;
import com.example.coursework.forms.HikeInputForm;
import com.example.coursework.fragments.IAffectingDBFragment;
import com.example.coursework.models.Hike;
import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment implements IAffectingDBFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private AppDatabase appDatabase;
    private HikeInputForm hikeInputForm;

    EditText nameText, locationText, lothText, dlText, descriptionText;
    TextView dothText;
    RadioGroup radioGroup;
    String radioButtonData;
    RadioButton yesRadioButton, noRadioButton;
    MaterialButton buttonAdd;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);
        appDatabase = Room.databaseBuilder(requireContext(), AppDatabase.class, "courework")
                .allowMainThreadQueries()
                .build();
        nameText = rootView.findViewById(R.id.nameText);
        locationText = rootView.findViewById(R.id.locationText);
        dothText = rootView.findViewById(R.id.dothText);
        dothText.setOnClickListener(view -> {
            openDatePickerDialog();
        });
        yesRadioButton = rootView.findViewById(R.id.yes);
        noRadioButton = rootView.findViewById(R.id.no);
        radioGroup = rootView.findViewById(R.id.choice);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                if (checkId == R.id.yes) {
                    radioButtonData = yesRadioButton.getText().toString();

                } else if (checkId == R.id.no) {
                    radioButtonData = noRadioButton.getText().toString();
                }
            }
        });
        lothText = rootView.findViewById(R.id.lothText);
        dlText = rootView.findViewById(R.id.dlText);
        descriptionText = rootView.findViewById(R.id.descriptionText);
        buttonAdd = rootView.findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = radioGroup.getCheckedRadioButtonId() == -1;
                hikeInputForm = new HikeInputForm(
                        nameText.getText().toString(),
                        locationText.getText().toString(),
                        dothText.getText().toString(),
                        isChecked,
                        lothText.getText().toString(),
                        dlText.getText().toString(),
                        descriptionText.getText().toString()
                );
                radioButtonData = (isChecked)? "N/A" : radioButtonData;
                AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
                builder.setMessage(returnMessage())
                        .setTitle("Confirmation")
                        .setPositiveButton("Confirm", (dialog, id) -> {
                            if (hikeInputForm.validateFields(rootView)) {
                                saveDetails();
                                clearData();
                            } else {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }
    public void clearData() {
        nameText.setText("");
        locationText.setText("");
        dothText.setText("");
        radioButtonData = null;
        radioGroup.clearCheck();
        lothText.setText("");
        dlText.setText("");
        descriptionText.setText("");
    }

    @Override
    public String returnMessage() {
        return "Name: " + nameText.getText().toString() + "\n" +
                "Date of Hike: " + locationText.getText().toString() + "\n" +
                "Distance: " + dothText.getText().toString() + "\n" +
                "Location: " + lothText.getText().toString() + "\n" +
                "Difficulty: " + dlText.getText().toString() + "\n" +
                "Has Parking: " +  radioButtonData + "\n" +
                "\n" +
                "Are you sure?";
    }
    @Override
    public void saveDetails() {
        Hike newHike = new Hike(
                hikeInputForm.name,
                hikeInputForm.location,
                hikeInputForm.date,
                radioButtonData,
                hikeInputForm.length,
                hikeInputForm.level,
                hikeInputForm.description);
        long hikeId = appDatabase.hikeDao().insertHike(newHike);
        Toast.makeText(getActivity(), "Hike has been created with id: " + hikeId,
                Toast.LENGTH_LONG
        ).show();
        ((MainActivity) getActivity()).displayFragment(null);
    }

    private void openDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                dothText.setText(String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(year));
            }
        }, 2023, 0, 0);
        datePickerDialog.show();
    }
}