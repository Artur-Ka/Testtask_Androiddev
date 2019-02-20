package com.testtask.androiddev.utils;

import com.testtask.androiddev.model.User;

import java.util.ArrayList;
import java.util.List;

public class IdFactory
{
    public static int getNextId(List<User> users)
    {
        final List<Integer> ids = new ArrayList<>();

        for (User u : users)
        {
            ids.add(u.getId());
        }

        int key = 0;
        int tmp = 0;
        for (int i = 0; i < ids.size(); i++)
        {
            key = ids.get(i);

            if (key - tmp > 1)
            {
                return ids.get(i) -1;
            }

            tmp = key;
        }

        return ids.size() + 1;
    }
}