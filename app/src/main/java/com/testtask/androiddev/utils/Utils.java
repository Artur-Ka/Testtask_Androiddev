package com.testtask.androiddev.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class Utils
{
    public static byte[] BitmapToArray(Bitmap bitmap)
    {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        bitmap.recycle();

        return stream.toByteArray();
    }

    public static Bitmap ArrayToBitmap(byte[] data)
    {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
