package com.example.tender.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.tender.fragments.AccountNavHostFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.tender.R;
//import com.example.tender.Utils;
import com.example.tender.adapters.ViewPagerAdapter;
//import com.example.tender.models.TinderCard;
import com.example.tender.fragments.AccountFragment;
import com.example.tender.fragments.ChatFragment;
import com.example.tender.fragments.FeedFragment;
import com.example.tender.fragments.SwipeViewFragment;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        BottomNavigationView bnv = findViewById(R.id.bottom_navigation);

        ArrayList<Fragment> fragList = new ArrayList<>();
        fragList.add(new SwipeViewFragment());
        fragList.add(new FeedFragment());
        fragList.add(new ChatFragment());
        fragList.add(new AccountNavHostFragment());
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(fragList, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    if (menuItem.getItemId() == R.id.fire) {
                        viewPager.setCurrentItem(0);
                    }  else if (menuItem.getItemId() == R.id.feed) {
                        viewPager.setCurrentItem(1);
                    }
                     else if (menuItem.getItemId() == R.id.chat) {
                        viewPager.setCurrentItem(2);
                    } else if (menuItem.getItemId() == R.id.account) {
                        viewPager.setCurrentItem(3);
                    }
                    return true;
            }
        });

//        Test Firebase DB
        FirebaseApp.initializeApp(this);

        fetchAndLogUserData();
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Bạn có muốn thoát không?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Đóng tất cả các Activity và thoát ứng dụng
                        finishAffinity();
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Đóng hộp thoại và không làm gì cả
                        dialog.dismiss();
                    }
                })
                .show();
    }



    private void fetchAndLogUserData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("user");

        usersRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String userId = document.getId();
                    Log.d("Firebase", "User ID: " + userId);
                    logUserCollections(userId);
                }
            } else {
                Log.w("Firebase", "Error getting documents.", task.getException());
            }
        });
    }

    private void logUserCollections(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Fetch user_auth collection
        db.collection("user").document(userId).collection("user_auth")
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("Firebase", "user_auth: " + document.getData());
                        }
                    } else {
                        Log.w("Firebase", "Error getting user_auth documents.", task.getException());
                    }
                });

        // Fetch user_collection collection
        db.collection("user").document(userId).collection("user_collection")
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("Firebase", "user_collection: " + document.getData());
                        }
                    } else {
                        Log.w("Firebase", "Error getting user_collection documents.", task.getException());
                    }
                });

        // Fetch user_detail collection
        db.collection("user").document(userId).collection("user_detail")
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("Firebase", "user_detail: " + document.getData());
                        }
                    } else {
                        Log.w("Firebase", "Error getting user_detail documents.", task.getException());
                    }
                });
    }
}