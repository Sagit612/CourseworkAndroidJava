package com.example.coursework.fragments.observation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.coursework.R;
import com.example.coursework.activities.MainActivity;
import com.example.coursework.adapters.ObservationAdapter;
import com.example.coursework.database.AppDatabase;
import com.example.coursework.fragments.hike.AddFragment;
import com.example.coursework.models.HikeWithObservations;
import com.example.coursework.models.Observation;
import com.google.android.material.button.MaterialButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ObservationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ObservationsFragment extends Fragment implements ObservationAdapter.OnEditClickListener, ObservationAdapter.OnDeleteClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AppDatabase appDatabase;

    private ObservationAdapter observationAdapter;
    RecyclerView recyclerView;

    MaterialButton addObservationButton, backspaceButton;

    public ObservationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ObservationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ObservationsFragment newInstance(String param1, String param2) {
        ObservationsFragment fragment = new ObservationsFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_observations, container, false);
        appDatabase = Room.databaseBuilder(getContext(), AppDatabase.class, "courework")
                .allowMainThreadQueries()
                .build();
        backspaceButton = rootView.findViewById(R.id.buttonBackspace);
        addObservationButton = rootView.findViewById(R.id.addObservationButton);
        recyclerView = rootView.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        long hikeId = this.getArguments().getLong("hikeId");
        List<Observation> observations = appDatabase.hikeDao().getObservationsByHikeId(hikeId);
        Log.d("List",observations.toString());
        observationAdapter = new ObservationAdapter(observations, this::onDeleteClick, this::onEditCLick);
        backspaceButton.setOnClickListener(view -> {
            ((MainActivity) getActivity()).displayFragment(null);
        });
        addObservationButton.setOnClickListener(view ->  {
            Fragment addObservationFragment = new AddObservationFragment();
            Bundle bundle = new Bundle();
            bundle.putString("date", getArguments().getString("date"));
            bundle.putLong("hikeId", hikeId);
            addObservationFragment.setArguments(bundle);
            ((MainActivity) getActivity()).displayFragment(addObservationFragment);
        });
        recyclerView.setAdapter(observationAdapter);
        return rootView;
    }

    @Override
    public void onEditCLick(Observation observation) {
        Fragment editObservationFragment = new EditObservationFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("observation", observation);
        editObservationFragment.setArguments(bundle);
        ((MainActivity) getActivity()).displayFragment(editObservationFragment);
    }

    @Override
    public void onDeleteClick(Observation observation) {
        long observationId = observation.getObservationId();
        appDatabase.hikeDao().deleteObservation(observation);
        Toast.makeText(getActivity(), "Observation has been deleted with id: " + observationId,
                Toast.LENGTH_LONG
        ).show();
        Fragment observationFragment = new ObservationsFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("hikeId", getArguments().getLong("hikeId"));
        observationFragment.setArguments(bundle);
        ((MainActivity) getActivity()).displayFragment(observationFragment);
    }
}