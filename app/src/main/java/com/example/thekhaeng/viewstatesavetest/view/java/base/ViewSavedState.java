package com.example.thekhaeng.viewstatesavetest.view.java.base;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * Created by「 The Khaeng 」on 01 Aug 2018 :)
 */
public abstract class ViewSavedState extends View.BaseSavedState {

    public Parcelable superState;

    public ViewSavedState(Parcelable superState) {
        super(EMPTY_STATE);
        this.superState = superState;
    }


    public ViewSavedState(Parcel in) {
        super(in);
        this.superState = in.readParcelable(getClass().getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeParcelable(this.superState, flags);
    }

}
