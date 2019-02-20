package com.testtask.androiddev.activity.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.testtask.androiddev.R;
import com.testtask.androiddev.activity.MainActivity;
import com.testtask.androiddev.holders.CameraHolder;

public class PhotoFragment extends Fragment implements View.OnClickListener
{
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        final FloatingActionButton cameraTapOk = view.findViewById(R.id.camera_tap_ok);
        cameraTapOk.setOnClickListener(this);

        final FloatingActionButton cameraTapCancel = view.findViewById(R.id.camera_tap_cancel);
        cameraTapCancel.setOnClickListener(this);

        final ImageView _imageView = view.findViewById(R.id.photo_view);
        if (CameraHolder.LAST_PHOTO != null)
        {
            _imageView.setImageBitmap(CameraHolder.LAST_PHOTO);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.camera_fragment_photo, container, false);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.camera_tap_ok:
            {
                ((MainActivity) getActivity()).notifySavePhoto();
                break;
            }
            case R.id.camera_tap_cancel:
            {
                ((MainActivity) getActivity()).notifyChangeFragment(MainActivity.CAMERA_FRAGMENT);
                break;
            }
        }
    }
}