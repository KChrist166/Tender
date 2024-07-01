package com.example.tender.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tender.R;
import com.example.tender.activities.FilterActivity;
import com.example.tender.adapters.CardStackAdapter;
import com.example.tender.entities.CardItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;

public class SwipeViewFragment extends Fragment {

    private CardStackView cardStackView;
    private ImageButton filterButton;
    FloatingActionButton fabBack;
    View rootLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootLayout = inflater.inflate(R.layout.fragment_swipe_view, container, false);
        fabBack = rootLayout.findViewById(R.id.fabBack);
        filterButton = rootLayout.findViewById(R.id.filterBtn);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start EditActivity when the edit button is clicked
                Intent intent = new Intent(getActivity(), FilterActivity.class);
                startActivity(intent);
            }
        });
        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CardItem> newItems = createCardItems();
                CardStackAdapter adapter = new CardStackAdapter(newItems);
                cardStackView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        return rootLayout;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardStackView = view.findViewById(R.id.card_stack_view);
        setupCardStackView();
    }

    private void setupCardStackView() {
        CardStackLayoutManager layoutManager = new CardStackLayoutManager(requireContext());
        layoutManager.setStackFrom(StackFrom.Bottom);
        layoutManager.setVisibleCount(3);
        layoutManager.setSwipeableMethod(SwipeableMethod.Manual);
        cardStackView.setLayoutManager(layoutManager);
        List<CardItem> items = createCardItems();
        CardStackAdapter adapter = new CardStackAdapter(items);
        cardStackView.setAdapter(adapter);
    }

    private List<CardItem> createCardItems() {
        List<CardItem> items = new ArrayList<>();
        // Add your card items here
        items.add(new CardItem("Title 1", "Description 1", R.drawable.cat2));
        items.add(new CardItem("Title 2", "Description 2", R.drawable.cat1));
        items.add(new CardItem("Title 3", "Description 3", R.drawable.cat2));
        items.add(new CardItem("Title 4", "Description 4", R.drawable.cat3));
        items.add(new CardItem("Title 5", "Description 5", R.drawable.cat4));
        items.add(new CardItem("Title 6", "Description 6", R.drawable.cat4));
        items.add(new CardItem("Title 7", "Description 7", R.drawable.cat1));
        return items;
    }
}
