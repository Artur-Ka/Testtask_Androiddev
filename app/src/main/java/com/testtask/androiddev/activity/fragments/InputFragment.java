package com.testtask.androiddev.activity.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.testtask.androiddev.R;
import com.testtask.androiddev.activity.MainActivity;
import com.testtask.androiddev.datatable.UsersData;
import com.testtask.androiddev.holders.CameraHolder;
import com.testtask.androiddev.model.User;
import com.testtask.androiddev.utils.IdFactory;
import com.testtask.androiddev.utils.Utils;

public class InputFragment extends Fragment implements View.OnClickListener
{
    private EditText _name;
    private EditText _phone;
    private EditText _mail;
    private EditText _note;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        _name = view.findViewById(R.id.edit_name);
        _phone = view.findViewById(R.id.edit_phone);
        _mail = view.findViewById(R.id.edit_mail);
        _note = view.findViewById(R.id.edit_note);

        final Button makePhoto = view.findViewById(R.id.button_make_photo);
        makePhoto.setOnClickListener(this);
        final Button add = view.findViewById(R.id.button_add);
        add.setOnClickListener(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.main_fragment_input, container, false);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_make_photo:
            {
                ((MainActivity) getActivity()).notifyChangeFragment(MainActivity.CAMERA_FRAGMENT);
                break;
            }
            case R.id.button_add:
            {
                final String name = _name.getText().toString();

                // Обязательное поле
                if (name.equals(""))
                {
                    return;
                }

                final User user = new User(IdFactory.getNextId(UsersData.getInstance(getContext()).getAll()));
                user.setName(name);
                user.setPhone(_phone.getText().toString().trim());
                user.setMail(_mail.getText().toString().trim());
                user.setNote(_note.getText().toString().trim());

                if (CameraHolder.LAST_PHOTO != null)
                {
                    user.setPhoto(Utils.BitmapToArray(CameraHolder.LAST_PHOTO));
                    CameraHolder.LAST_PHOTO = null;
                }

                _name.setText("");
                _phone.setText("");
                _mail.setText("");
                _note.setText("");

                UsersData.getInstance(getContext()).insert(user);
            }
        }
    }
}