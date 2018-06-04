package com.molto.android.topquiz.Model;

import android.widget.TextView;

/**
 * Created by Romain on 03/06/2018.
 */

public class User {

    private String mFirstName;

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    @Override
    public String toString() {
        return "User{" +
                "mFirstName='" + mFirstName + '\'' +
                '}';
    }
}
