package com.example.coursework.fragments.observation;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coursework.R;
import com.example.coursework.activities.MainActivity;
import com.example.coursework.database.AppDatabase;
import com.example.coursework.forms.ObservationInputForm;
import com.example.coursework.fragments.IAffectingDBFragment;
import com.example.coursework.models.Observation;
import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditObservationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditObservationFragment extends Fragment implements IAffectingDBFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AppDatabase appDatabase;
    private ObservationInputForm observationForm;
    private Observation observation;

    EditText observationText, timeText, commentText;
    MaterialButton editButton, backspaceButton;

    public EditObservationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditObservationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditObservationFragment newInstance(String param1, String param2) {
        EditObservationFragment fragment = new EditObservationFragment();
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
        View rootView =  inflater.inflate(R.layout.fragment_edit_observation, container, false);
        appDatabase = Room.databaseBuilder(requireContext(), AppDatabase.class, "courework")
                .allowMainThreadQueries()
                .build();
        observation = getArguments().getParcelable("observation");
        long hikeId = observation.getHikeId();
        observationText = rootView.findViewById(R.id.observationText);
        timeText = rootView.findViewById(R.id.timeText);
        commentText = rootView.findViewById(R.id.commentText);
        observationText.setText(observation.getObservation());
        timeText.setText(observation.getTimeOfObservation());
        commentText.setText(observation.getComment());
        editButton = rootView.findViewById(R.id.buttonEdit);
        backspaceButton = rootView.findViewById(R.id.buttonBackspace);
        backspaceButton.setOnClickListener(view -> {
            Fragment observationFragment = new ObservationsFragment();
            Bundle bundle = new Bundle();
            bundle.putLong("hikeId", hikeId);
            observationFragment.setArguments(bundle);
            ((MainActivity) getActivity()).displayFragment(observationFragment);
        });
        editButton.setOnClickListener(view -> {
            observationForm = new ObservationInputForm(
                    hikeId,
                    observationText.getText().toString(),
                    timeText.getText().toString(),
                    commentText.getText().toString()
            );
            AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
            builder.setMessage(returnMessage())
                    .setTitle("Confirmation")
                    .setPositiveButton("Confirm", (dialog, id) -> {
                        if (observationForm.validateFields(rootView)) {
                            saveDetails();
                            clearData();
                        } else {
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());
            AlertDialog dialog = builder.create();
            dialog.show();
        });
        return rootView;
    }

    private void clearData() {
        observationText.setText("");
        timeText.setText("");
        commentText.setText("");
    }

    @Override
    public String returnMessage() {
        return "Observation: " + observationText.getText().toString() + "\n" +
                "Time of observation: " + timeText.getText().toString() + "\n" +
                "Comment: " + commentText.getText().toString() + "\n" +
                "\n" +
                "Are you sure?";
    }

    @Override
    public void saveDetails() {
        Observation updatedObservation = new Observation(
                observationForm.hikeId,
                observationForm.observation,
                observationForm.time,
                observationForm.comment
        );
        updatedObservation.setObservationId(observation.getObservationId());
        long observationId = appDatabase.hikeDao().updateObservation(updatedObservation);
        Toast.makeText(getActivity(), "Observation has been updated with id: " + observationId,
                Toast.LENGTH_LONG
        ).show();
        Fragment observationFragment = new ObservationsFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("hikeId", observationForm.hikeId);
        observationFragment.setArguments(bundle);
        ((MainActivity) getActivity()).displayFragment(observationFragment);
    }
}