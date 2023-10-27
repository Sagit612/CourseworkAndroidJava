package com.example.coursework.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coursework.R;
import com.example.coursework.activities.MainActivity;
import com.example.coursework.adapters.HikeAdapter;
import com.example.coursework.adapters.ObservationAdapter;
import com.example.coursework.database.AppDatabase;
import com.example.coursework.fragments.hike.EditFragment;
import com.example.coursework.fragments.observation.ObservationsFragment;
import com.example.coursework.models.Hike;
import com.example.coursework.models.HikeWithObservations;
import com.google.android.material.button.MaterialButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements HikeAdapter.OnDeleteClickListener, HikeAdapter.OnItemClickListener, HikeAdapter.OnMoreClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private HikeAdapter hikeAdapter;
    private AppDatabase appDatabase;

    private List<HikeWithObservations> hikesWithObservations;

    EditText searchText;
    MaterialButton searchButton;

    RecyclerView recyclerView;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        appDatabase = Room.databaseBuilder(getContext(), AppDatabase.class, "courework")
                .allowMainThreadQueries()
                .build();
        searchText = rootView.findViewById(R.id.searchText);
        searchButton = rootView.findViewById(R.id.searchButton);
        recyclerView = rootView.findViewById(R.id.recycleView);
        searchButton.setOnClickListener(view -> {
            hikesWithObservations = appDatabase.hikeDao().getHikeWithObservationsByName(searchText.getText().toString());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            hikeAdapter = new HikeAdapter(hikesWithObservations, this::onDeleteClick, this::onMoreCLick, this::onItemClick);
            recyclerView.setAdapter(hikeAdapter);
        });
        return rootView;
    }

    @Override
    public void onDeleteClick(HikeWithObservations hikeWithObservations) {
        long hikeId = hikeWithObservations.hike.getHikeId();
        appDatabase.hikeDao().deleteHike(hikeWithObservations.hike);
        appDatabase.hikeDao().deleteObservationByHikeId(hikeId);
        Toast.makeText(getActivity(), "Hike has been deleted with id: " + hikeId,
                Toast.LENGTH_LONG
        ).show();
        ((MainActivity) getActivity()).displayFragment(null);
    }

    @Override
    public void onMoreCLick(HikeWithObservations hikeWithObservations) {
        Bundle bundle = new Bundle();
        bundle.putLong("hikeId", hikeWithObservations.hike.getHikeId());
        Fragment observationFragment = new ObservationsFragment();
        observationFragment.setArguments(bundle);
        ((MainActivity) getActivity()).displayFragment(observationFragment);
    }

    @Override
    public void onItemClick(HikeWithObservations hikeWithObservations) {
        Bundle bundle = new Bundle();
        bundle.putLong("hikeId", hikeWithObservations.hike.getHikeId());
        bundle.putString("name", hikeWithObservations.hike.getName());
        bundle.putString("location", hikeWithObservations.hike.getLocation());
        bundle.putString("date", hikeWithObservations.hike.getDate());
        bundle.putString("parkingAvailable", hikeWithObservations.hike.getParkingAvailable());
        bundle.putString("length", hikeWithObservations.hike.getLengthOfTheHike());
        bundle.putString("level", hikeWithObservations.hike.getLevel());
        bundle.putString("description", hikeWithObservations.hike.getDescription());
        Fragment editFragment = new EditFragment();
        editFragment.setArguments(bundle);
        ((MainActivity) getActivity()).displayFragment(editFragment);
    }
}