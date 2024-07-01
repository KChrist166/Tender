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
import androidx.recyclerview.widget.RecyclerView;

import com.example.tender.R;
import com.example.tender.adapters.ProfileImageAdapter;
import com.example.tender.fragments.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSIONS = 100;

    private ImageButton backBtn;
    private RecyclerView rcvImage;
    private ProfileImageAdapter mImageAdapter;
    private FirebaseAuth firebaseAuth;
    private ImageButton saveProfileBtn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference usersDbRef;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
            return;
        }
        usersDbRef = firebaseDatabase.getReference("Profiles");

        rcvImage = findViewById(R.id.recycler_image);
        mImageAdapter = new ProfileImageAdapter(this);

        EditText editZodiac = findViewById(R.id.editZodiac);
        EditText editStudy = findViewById(R.id.editStudy);
        EditText editCharacter = findViewById(R.id.editCharacter);
        EditText editNickname = findViewById(R.id.editNickname);
        EditText editLiving = findViewById(R.id.editLiving);
        EditText editWork = findViewById(R.id.editWork);
        EditText editSelf = findViewById(R.id.editIntroduce);
        EditText editHobbies = findViewById(R.id.editHobbies);
        saveProfileBtn = findViewById(R.id.buttonSave);
        backBtn = findViewById(R.id.buttonBack);

        saveProfileBtn.setOnClickListener(v -> saveProfile(
                editZodiac.getText().toString(),
                editStudy.getText().toString(),
                editCharacter.getText().toString(),
                editNickname.getText().toString(),
                editLiving.getText().toString(),
                editWork.getText().toString(),
                editHobbies.getText().toString(),
                editSelf.getText().toString()));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rcvImage.setLayoutManager(gridLayoutManager);

        rcvImage.setAdapter(mImageAdapter);

        mImageAdapter.setOnItemClickListener((position, view) -> openGalleryForImage(position));

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
            startActivity(intent);
        });
        loadProfileData();
    }

    private void openGalleryForImage(int position) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            getContentResolver().takePersistableUriPermission(selectedImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            mImageAdapter.setImageUri(requestCode, selectedImageUri);
        }
    }

    private void saveProfile(String zodiac, String study, String character, String nickname, String living, String work, String self, String hobbies) {
        List<Uri> changedImages = mImageAdapter.getChangedImages();

        HashMap<String, Object> profileData = new HashMap<>();
        profileData.put("zodiac", zodiac);
        profileData.put("study", study);
        profileData.put("character", character);
        profileData.put("nickname", nickname);
        profileData.put("living", living);
        profileData.put("work", work);
        profileData.put("self", self);
        profileData.put("hobbies", hobbies);

        ArrayList<String> imageUrls = new ArrayList<>();
        for (Uri imageUri : changedImages) {
            imageUrls.add(imageUri.toString());
        }
        profileData.put("changedImages", imageUrls);

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Profiles").child(uid);
        usersRef.setValue(profileData)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(EditProfileActivity.this, "Profile saved successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditProfileActivity.this, "Failed to save profile", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadProfileData() {
        DatabaseReference profileRef = FirebaseDatabase.getInstance().getReference("Profiles").child(uid);
        profileRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String zodiac = snapshot.child("zodiac").getValue(String.class);
                    String study = snapshot.child("study").getValue(String.class);
                    String character = snapshot.child("character").getValue(String.class);
                    String nickname = snapshot.child("nickname").getValue(String.class);
                    String living = snapshot.child("living").getValue(String.class);
                    String work = snapshot.child("work").getValue(String.class);
                    String self = snapshot.child("self").getValue(String.class);
                    String hobbies = snapshot.child("hobbies").getValue(String.class);

                    setProfileDataToUI(zodiac, study, character, nickname, living, work, self, hobbies);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditProfileActivity.this, "Failed to load profile data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setProfileDataToUI(String zodiac, String study, String character, String nickname, String living, String work, String self, String hobbies) {
        EditText editZodiac = findViewById(R.id.editZodiac);
        EditText editStudy = findViewById(R.id.editStudy);
        EditText editCharacter = findViewById(R.id.editCharacter);
        EditText editNickname = findViewById(R.id.editNickname);
        EditText editLiving = findViewById(R.id.editLiving);
        EditText editWork = findViewById(R.id.editWork);
        EditText editSelf = findViewById(R.id.editIntroduce);
        EditText editHobbies = findViewById(R.id.editHobbies);

        editZodiac.setText(zodiac);
        editStudy.setText(study);
        editCharacter.setText(character);
        editNickname.setText(nickname);
        editLiving.setText(living);
        editWork.setText(work);
        editSelf.setText(self);
        editHobbies.setText(hobbies);
    }

}
