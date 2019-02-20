package com.testtask.androiddev.activity.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.testtask.androiddev.R;
import com.testtask.androiddev.activity.MainActivity;
import com.testtask.androiddev.datatable.UsersData;
import com.testtask.androiddev.model.User;
import com.testtask.androiddev.utils.Utils;

public class OutputFragment extends Fragment
{
    private LayoutInflater _inflater;
    private ListView _listView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        _listView = view.findViewById(R.id.list_view);
        _listView.setAdapter(new CustomListAdapter());
        ((CustomListAdapter) _listView.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        _inflater = inflater;

        return inflater.inflate(R.layout.main_fragment_output, container, false);
    }

    public void notifyDataChanged()
    {
        ((CustomListAdapter) _listView.getAdapter()).notifyDataSetChanged();
    }

    private class CustomListAdapter extends BaseAdapter
    {
        @Override
        public int getCount()
        {
            return UsersData.getInstance(getContext()).getAll().size();
        }

        @Override
        public Object getItem(int position)
        {
            return UsersData.getInstance(getContext()).getAll().get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return UsersData.getInstance(getContext()).getAll().get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            final User user = UsersData.getInstance(getContext()).getAll().get(position);

            convertView = _inflater.inflate(R.layout.list_layout, null);

            ((TextView) convertView.findViewById(R.id.info_name)).setText(user.getName());
            ((TextView) convertView.findViewById(R.id.info_phone)).setText(user.getPhone());
            ((TextView) convertView.findViewById(R.id.info_mail)).setText(user.getMail());
            ((TextView) convertView.findViewById(R.id.info_note)).setText(user.getNote());

            final byte[] photo = user.getPhoto();
            if (photo != null)
            {
                ((ImageView) convertView.findViewById(R.id.info_photo)).setImageBitmap(Utils.ArrayToBitmap(photo));
            }
            else
            {
                ((ImageView) convertView.findViewById(R.id.info_photo)).setImageResource(R.drawable.default_avatar);
            }

            return convertView;
        }
    }
}
