package com.example.tender.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tender.R;

import java.util.ArrayList;
import java.util.List;

public class ProfileImageAdapter extends RecyclerView.Adapter<ProfileImageAdapter.ImageViewHolder> {

    private final Context mContext;
    private final List<Uri> mImageUris;
    private OnItemClickListener mListener;

    public ProfileImageAdapter(Context mContext) {
        this.mContext = mContext;
        this.mImageUris = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            mImageUris.add(null);
        }
    }

    public void setImageUri(int position, Uri imageUri) {
        mImageUris.set(position, imageUri);
        notifyItemChanged(position);
    }

    public Uri getImageUri(int position) {
        return mImageUris.get(position);
    }

    public List<Uri> getChangedImages() {
        List<Uri> changedImages = new ArrayList<>();
        for (Uri uri : mImageUris) {
            if (uri != null) {
                changedImages.add(uri);
            }
        }
        return changedImages;
    }

    public void setImages(List<String> imageUrls) {
        mImageUris.clear();
        for (String imageUrl : imageUrls) {
            mImageUris.add(Uri.parse(imageUrl));
        }
        while (mImageUris.size() < 9) {
            mImageUris.add(null);
        }
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.image_user, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Uri imageUri = mImageUris.get(position);
        if (imageUri != null) {
            holder.imgUser.setImageURI(imageUri);
        } else {
            holder.imgUser.setImageResource(R.drawable.profile1); // Placeholder image
        }

        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(position, v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageUris.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgUser;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.img_user);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }
}
