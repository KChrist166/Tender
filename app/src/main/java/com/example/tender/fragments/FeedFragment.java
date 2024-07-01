package com.example.tender.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tender.R;
import com.example.tender.adapters.MatchListAdapter;
import com.example.tender.entities.Match;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FeedFragment extends Fragment {

    View rootLayout;
    private static final String TAG = MainActivity.class.getSimpleName();
    private List<Match> matchList;
    private MatchListAdapter mAdapter;
    private String[] matchDates = {"3/4/2020", "3/4/2020", "3/4/2020", "3/4/2020", "3/4/2020"};
    private int[] matchPictures = {R.drawable.cat, R.drawable.cat, R.drawable.cat, R.drawable.cat , R.drawable.cat};
    private String[] matchNames = {"Jessica1", "Jessica2", "Jessica3", "Jessica4", "Jessica5"};
    private String[] matchLocations = {"Da Nang: 3 km", "Da Nang: 18 km", "Hoi An: kilometre", "HCM: 4 km", "HCM: 6 km"};


    public FeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootLayout = inflater.inflate(R.layout.fragment_feed, container, false);


        RecyclerView recyclerView = rootLayout.findViewById(R.id.recycler_view_matchs);
        matchList = new ArrayList<>();
        mAdapter = new MatchListAdapter(getContext(), matchList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        prepareMatchList();
        
        return rootLayout;
    }


    private void prepareMatchList(){

        Random rand = new Random();
        int id = rand.nextInt(100);
        int i;
        for(i=0; i<5; i++) {
            Match match = new Match(id, matchNames[i], matchPictures[i], matchLocations[i], matchDates[i]);
            matchList.add(match);
        }
    }


}
