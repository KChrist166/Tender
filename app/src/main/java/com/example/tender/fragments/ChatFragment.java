package com.example.tender.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tender.R;
import com.example.tender.adapters.LikeAdapter;
import com.example.tender.adapters.MessageListAdapter;
import com.example.tender.entities.Like;
import com.example.tender.entities.MessageItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Hien thi danh sach cac cuoc tro chuyen
public class ChatFragment extends Fragment {
    View rootLayout;
    private static final String TAG = MainActivity.class.getSimpleName();
    private List<MessageItem> messageList;
    private List<Like> likeList;
    private MessageListAdapter mAdapter;
    private String[] messages = {"How are you?", "What's up?", "Hey!", "Want a date?", "Hello World!"};
    private int[] counts = {0, 3, 0, 0, 1};
    private int[] messagePictures = {R.drawable.woman_0, R.drawable.woman_0, R.drawable.woman_0, R.drawable.woman_0 , R.drawable.woman_0};
    private int[] likePictures = {R.drawable.woman_0, R.drawable.woman_0};
    private String[] messageNames = {"Jessica1", "Jessica2", "Jessica3", "Jessica4", "Jessica5"};
    private String[] likeNames = {"Jessica1", "Jessica2"};

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootLayout = inflater.inflate(R.layout.fragment_chat, container, false);

        RecyclerView recyclerView = rootLayout.findViewById(R.id.recycler_view_messages);
        messageList = new ArrayList<>();
        mAdapter = new MessageListAdapter(this.getContext(), messageList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        prepareMessageList();

        prepareContactList();

        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnClickListener(new MessageListAdapter.OnClickListener() {
            @Override
            public void onClick(int position, MessageItem model) {
                Log.d("Click", "Click");
                onChatClick();
            }
        });

        LikeAdapter contactAdapter = new LikeAdapter(getContext(), likeList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewContact =  rootLayout.findViewById(R.id.recycler_view_likes);
        recyclerViewContact.setLayoutManager(layoutManager);
        recyclerViewContact.setAdapter(contactAdapter);

        return rootLayout;
    }


    private void prepareMessageList(){

        Random rand = new Random();
        int id = rand.nextInt(100);
        int i;
        for(i=0; i<5; i++) {
            MessageItem message = new MessageItem(id, messageNames[i], messages[i], counts[i], messagePictures[i]);
            messageList.add(message);
        }
    }

    private void prepareContactList(){
        likeList = new ArrayList<>();
        Random rand = new Random();
        int id = rand.nextInt(100);
        int i;
        for(i=0; i<2; i++) {
            Like like = new Like(id, likeNames[i], likePictures[i]);
            likeList.add(like);
        }
    }

    public void onChatClick() {
        Log.d("TExt","Called");
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_chatListFragment_to_chatBoxFragment);
    }
}