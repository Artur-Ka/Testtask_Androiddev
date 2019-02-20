package com.testtask.androiddev.holders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.testtask.androiddev.R;
import com.testtask.androiddev.activity.MainActivity;

import java.io.IOException;

public class CameraHolder implements Callback
{
    private static final int CAMERA_ID = 0;
    private static final boolean FULL_SCREEN = true;
    private static final int PHOTO_WIDTH = 128;
    private static final int PHOTO_HEIGHT = 128;

    public static Bitmap LAST_PHOTO;

    private final SurfaceView _surfaceView;

    private final MainActivity _activity;
    private Camera _camera;

    public CameraHolder(MainActivity activity)
    {
        _activity = activity;
        _surfaceView = _activity.findViewById(R.id.camera_view);

        final SurfaceHolder holder = _surfaceView.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(this);
    }

    public void openCamera()
    {
        _camera = Camera.open(CAMERA_ID);
        setPreviedSize(FULL_SCREEN);
    }

    public void releaseCamera()
    {
        if (_camera == null)
        {
            return;
        }

        _camera.release();
        _camera = null;
    }

    public void takePicture()
    {
        _camera.takePicture(null, null, new PictureCallback()
        {
            @Override
            public void onPictureTaken(byte[] data, Camera camera)
            {
                Bitmap bitmap = Bitmap.createScaledBitmap(
                        BitmapFactory.decodeByteArray(data, 0, data.length), PHOTO_WIDTH, PHOTO_HEIGHT, false);

                final Matrix matrix = new Matrix();
                matrix.setRotate(90, (float) bitmap.getWidth(), (float) bitmap.getHeight());

                try
                {
                    Bitmap bitmap2 = Bitmap.createBitmap(
                            bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                    if (bitmap != bitmap2)
                    {
                        bitmap.recycle();
                        bitmap = bitmap2;
                    }
                }
                catch (OutOfMemoryError ex)
                {
                    throw ex;
                }

                LAST_PHOTO = bitmap;
                _activity.notifyTakePhoto();
            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        _camera.stopPreview();
        setCameraDisplayOrientation(CAMERA_ID);

        try
        {
            _camera.setPreviewDisplay(holder);
            _camera.startPreview();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {

    }

    private void setPreviedSize(boolean fullScreen)
    {
        final Display display = _activity.getWindowManager().getDefaultDisplay();
        final boolean widthIsMax = display.getWidth() > display.getHeight();
        final Size size = _camera.getParameters().getPreviewSize();

        final RectF rectDisplay = new RectF();
        final RectF rectPreview = new RectF();

        rectDisplay.set(0, 0, display.getWidth(), display.getHeight());

        if (widthIsMax)
        {
            rectPreview.set(0, 0, size.width, size.height);
        }
        else
        {
            rectPreview.set(0, 0, size.height, size.width);
        }

        final Matrix matrix = new Matrix();
        if (fullScreen)
        {
            matrix.setRectToRect(rectDisplay, rectPreview, Matrix.ScaleToFit.START);
            matrix.invert(matrix);
        }
        else
        {
            matrix.setRectToRect(rectPreview, rectDisplay, Matrix.ScaleToFit.START);
        }

        matrix.mapRect(rectPreview);

        _surfaceView.getLayoutParams().height = (int) rectPreview.bottom;
        _surfaceView.getLayoutParams().width = (int) rectPreview.right;
    }

    private void setCameraDisplayOrientation(int cameraId)
    {
        final CameraInfo info = new CameraInfo();
        Camera.getCameraInfo(cameraId, info);

        final int rotation = _activity.getWindowManager().getDefaultDisplay().getRotation();

        int degrees = 0, result = 0;

        switch (rotation)
        {
            case Surface.ROTATION_0: degrees = 0;
                break;
            case Surface.ROTATION_90: degrees = 90;
                break;
            case Surface.ROTATION_180: degrees = 180;
                break;
            case Surface.ROTATION_270: degrees = 270;
                break;
        }

        if (info.facing == CameraInfo.CAMERA_FACING_BACK)
        {
            result = ((360 - degrees) + info.orientation);
        }
        else if (info.facing == CameraInfo.CAMERA_FACING_FRONT)
        {
            result = ((360 - degrees) - info.orientation);
            result += 360;
        }

        _camera.setDisplayOrientation(result % 360);
    }
}
