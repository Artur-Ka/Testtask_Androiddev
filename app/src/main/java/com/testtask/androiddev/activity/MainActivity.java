package com.testtask.androiddev.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.testtask.androiddev.R;
import com.testtask.androiddev.activity.fragments.CameraFragment;
import com.testtask.androiddev.activity.fragments.InputFragment;
import com.testtask.androiddev.activity.fragments.OutputFragment;
import com.testtask.androiddev.activity.fragments.PhotoFragment;
import com.testtask.androiddev.datatable.UsersData;

public class MainActivity extends AppCompatActivity
{
    public static final int INPUT_FRAGMENT = 0x01;
    public static final int OUTPUT_FRAGMENT = 0x02;
    public static final int CAMERA_FRAGMENT = 0x03;
    public static final int PHOTO_FRAGMENT = 0x04;

    public final InputFragment _inputFragment = new InputFragment();
    public final OutputFragment _outputFragment = new OutputFragment();
    public final CameraFragment _cameraFragment = new CameraFragment();
    public final PhotoFragment _photoFragment = new PhotoFragment();

    private FragmentManager _fragmentManager;
    private FragmentTransaction _fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _fragmentManager = getSupportFragmentManager();
        _fragmentTransaction = _fragmentManager.beginTransaction();
        _fragmentTransaction.add(R.id.main_fragment_place, _inputFragment);
        _fragmentTransaction.commit();

        final Toolbar _toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(_toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        final int id = item.getItemId();

        switch (id)
        {
            case R.id.action_input:
            {
                notifyChangeFragment(INPUT_FRAGMENT);
                break;
            }
            case R.id.action_output:
            {
                notifyChangeFragment(OUTPUT_FRAGMENT);
                break;
            }
            case R.id.action_reset:
            {
                UsersData.getInstance(getApplicationContext()).delete(null);

                if (_outputFragment != null)
                {
                    _outputFragment.notifyDataChanged();
                }
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void notifyTakePhoto()
    {
        notifyChangeFragment(PHOTO_FRAGMENT);
    }

    public void notifySavePhoto()
    {
        notifyChangeFragment(INPUT_FRAGMENT);
    }

    public void notifyChangeFragment(int fragmentIndex)
    {
        Fragment fragment = null;

        switch (fragmentIndex)
        {
            case INPUT_FRAGMENT:
            {
                fragment = _inputFragment;
                break;
            }
            case OUTPUT_FRAGMENT:
            {
                fragment = _outputFragment;
                break;
            }
            case CAMERA_FRAGMENT:
            {
                fragment = _cameraFragment;
                break;
            }
            case PHOTO_FRAGMENT:
            {
                fragment = _photoFragment;
                break;
            }

        }

        assert fragment != null;
        _fragmentTransaction = _fragmentManager.beginTransaction();
        _fragmentTransaction.setCustomAnimations(R.anim.fragment_open, R.anim.fragment_close);
        _fragmentTransaction.replace(R.id.main_fragment_place, fragment);
        _fragmentTransaction.commit();
    }
}
