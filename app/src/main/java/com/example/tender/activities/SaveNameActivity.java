package com.example.tender.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.tender.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;

public class SaveNameActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSIONS = 100;
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore firestoreDatabase;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_account);
        firestoreDatabase = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            uid = user.getUid();
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
            return;
        }

        EditText editName = findViewById(R.id.name_text);

        saveName(editName.getText().toString());
        loadUserNameData();
    }

    private void saveName(String name) {
        Map<String, Object> usernameData = new HashMap<>();
        usernameData.put("Real name", name);

        DocumentReference userRef = firestoreDatabase.collection("user").document(uid);
        userRef.set(usernameData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(SaveNameActivity.this, "Name saved successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SaveNameActivity.this, "Failed to save your name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadUserNameData() {
        DocumentReference profileRef = firestoreDatabase.collection("user").document(uid);
        profileRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        String name = snapshot.getString("Real name");
                        setUserNameToUI(name);
                    }
                } else {
                    Toast.makeText(SaveNameActivity.this, "Failed to load name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setUserNameToUI(String name) {
        EditText editName = findViewById(R.id.name_text);
        editName.setText(name);
    }

}