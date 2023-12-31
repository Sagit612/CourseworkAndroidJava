package com.example.coursework.fragments.hike;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.coursework.R;
import com.example.coursework.activities.MainActivity;
import com.example.coursework.database.AppDatabase;
import com.example.coursework.forms.HikeInputForm;
import com.example.coursework.fragments.IAffectingDBFragment;
import com.example.coursework.models.Hike;
import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends Fragment implements IAffectingDBFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AppDatabase appDatabase;
    private HikeInputForm hikeInputForm;
    EditText nameText, locationText, dothText, lothText, dlText, descriptionText;
    RadioGroup radioGroup;
    String radioButtonData;
    RadioButton yesRadioButton, noRadioButton;
    MaterialButton buttonUpdate, buttonBackspace;

    long hikeId;
    String name;
    String location;
    String date;
    String parkingAvai;
    String length;
    String level;
    String description;

    public EditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditFragment newInstance(String param1, String param2) {
        EditFragment fragment = new EditFragment();
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit, container, false);
        appDatabase = Room.databaseBuilder(requireContext(), AppDatabase.class, "courework")
                .allowMainThreadQueries()
                .build();
        buttonBackspace = rootView.findViewById(R.id.buttonBackspace);
        buttonUpdate = rootView.findViewById(R.id.buttonUpdate);
        nameText = rootView.findViewById(R.id.nameText);
        locationText = rootView.findViewById(R.id.locationText);
        dothText = rootView.findViewById(R.id.dothText);
        yesRadioButton = rootView.findViewById(R.id.yes);
        noRadioButton = rootView.findViewById(R.id.no);
        radioGroup = rootView.findViewById(R.id.choice);
        lothText = rootView.findViewById(R.id.lothText);
        dlText = rootView.findViewById(R.id.dlText);
        descriptionText =rootView.findViewById(R.id.descriptionText);

        // Receiving data from bundle
        Bundle bundle = this.getArguments();
        hikeId = bundle.getLong("hikeId");
        name = bundle.getString("name");
        location = bundle.getString("location");
        date = bundle.getString("date");
        parkingAvai = bundle.getString("parkingAvailable");
        length = bundle.getString("length");
        level = bundle.getString("level");
        description = bundle.getString("description");
        nameText.setText(name);
        locationText.setText(location);
        dothText.setText(date);
        if (parkingAvai.equals("Yes")) {
            ((RadioButton)radioGroup.findViewById(R.id.yes)).setChecked(true);
            radioButtonData = yesRadioButton.getText().toString();
        } else if (parkingAvai.equals("No")) {
            ((RadioButton)radioGroup.findViewById(R.id.no)).setChecked(true);
            radioButtonData = noRadioButton.getText().toString();
        }
        lothText.setText(length);
        dlText.setText(level);
        if (description.equals("")) {
            descriptionText.setHint("Description about the hike");
        } else if (!description.equals("")) {
            descriptionText.setText(bundle.getString("description"));
        }
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
        buttonBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).displayFragment(null);
            }
        });
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
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
                            } else {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return rootView;
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
        String name = nameText.getText().toString();
        String location = locationText.getText().toString();
        String doth = dothText.getText().toString();
        String loth = lothText.getText().toString();
        String dl = dlText.getText().toString();
        String description = descriptionText.getText().toString();
        Hike updatedHike = new Hike(name, location, doth, radioButtonData, loth, dl, description);
        updatedHike.setHikeId(hikeId);
        appDatabase.hikeDao().updateHike(updatedHike);
        ((MainActivity) getActivity()).displayFragment(null);
    }
}