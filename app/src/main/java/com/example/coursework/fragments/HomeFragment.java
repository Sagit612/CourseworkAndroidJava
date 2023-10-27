package com.example.coursework.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.coursework.R;
import com.example.coursework.activities.MainActivity;
import com.example.coursework.adapters.HikeAdapter;
import com.example.coursework.database.AppDatabase;
import com.example.coursework.fragments.hike.EditFragment;
import com.example.coursework.fragments.observation.ObservationsFragment;
import com.example.coursework.models.HikeWithObservations;
import com.example.coursework.models.Observation;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment  implements HikeAdapter.OnDeleteClickListener, HikeAdapter.OnMoreClickListener, HikeAdapter.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private AppDatabase appDatabase;
    private HikeAdapter adapter;
    MaterialButton deleteAllButton;
    RecyclerView recyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        appDatabase = Room.databaseBuilder(getContext(), AppDatabase.class, "courework")
                .allowMainThreadQueries()
                .build();
        deleteAllButton = rootView.findViewById(R.id.deleteAllButton);
        deleteAllButton.setOnClickListener(view ->  {
//                appDatabase.hikeDao().deleteAllObservations();
                appDatabase.hikeDao().deleteAllHikes();
                appDatabase.hikeDao().deleteAllObservations();
                ((MainActivity) getActivity()).displayFragment(null);
        });
        recyclerView = rootView.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<HikeWithObservations> hikesWithObservations = appDatabase.hikeDao().getHikeWithObservations();
        adapter = new HikeAdapter(hikesWithObservations, this::onDeleteClick, this::onMoreCLick, this::onItemClick);
        recyclerView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return rootView;
    }
    @Override
    public void onDeleteClick(HikeWithObservations hike) {
        long hikeId = hike.hike.getHikeId();
        appDatabase.hikeDao().deleteHike(hike.hike);
        appDatabase.hikeDao().deleteObservationByHikeId(hikeId);
        Toast.makeText(getActivity(), "Hike has been deleted with id: " + hikeId,
                Toast.LENGTH_LONG
        ).show();
        ((MainActivity) getActivity()).displayFragment(null);
    }

    @Override
    public void onMoreCLick(HikeWithObservations hike) {
        Bundle bundle = new Bundle();
        bundle.putLong("hikeId", hike.hike.getHikeId());
        bundle.putString("date", hike.hike.getDate());
        Fragment observationFragment = new ObservationsFragment();
        observationFragment.setArguments(bundle);
        ((MainActivity) getActivity()).displayFragment(observationFragment);
    }

    @Override
    public void onItemClick(HikeWithObservations hike) {
        Bundle bundle = new Bundle();
        bundle.putLong("hikeId", hike.hike.getHikeId());
        bundle.putString("name", hike.hike.getName());
        bundle.putString("location", hike.hike.getLocation());
        bundle.putString("date", hike.hike.getDate());
        bundle.putString("parkingAvailable", hike.hike.getParkingAvailable());
        bundle.putString("length", hike.hike.getLengthOfTheHike());
        bundle.putString("level", hike.hike.getLevel());
        bundle.putString("description", hike.hike.getDescription());
        Fragment editFragment = new EditFragment();
        editFragment.setArguments(bundle);
        ((MainActivity) getActivity()).displayFragment(editFragment);
    }
}