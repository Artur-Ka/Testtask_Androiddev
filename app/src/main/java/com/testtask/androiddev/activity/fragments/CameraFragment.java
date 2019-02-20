package com.testtask.androiddev.activity.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testtask.androiddev.R;
import com.testtask.androiddev.activity.MainActivity;
import com.testtask.androiddev.holders.CameraHolder;

public class CameraFragment extends Fragment implements View.OnClickListener
{
    private CameraHolder _cameraHolder;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        final FloatingActionButton cameraTapPhoto = view.findViewById(R.id.camera_tap_photo);
        cameraTapPhoto.setOnClickListener(this);

        _cameraHolder = new CameraHolder((MainActivity) getActivity());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.camera_fragment_camera, container, false);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        _cameraHolder.openCamera();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        _cameraHolder.releaseCamera();
    }

    @Override
    public void onClick(View v)
    {
        _cameraHolder.takePicture();
    }
}