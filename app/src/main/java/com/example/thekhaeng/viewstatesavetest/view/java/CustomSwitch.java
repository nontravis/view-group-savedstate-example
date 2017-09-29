package com.example.thekhaeng.viewstatesavetest.view.java;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.Switch;

/**
 * Created by TheKhaeng
 */

public class CustomSwitch extends Switch {

    private int customState;

    public CustomSwitch( Context context ){
        super( context );
    }

    public CustomSwitch( Context context, AttributeSet attrs ){
        super( context, attrs );
    }

    public CustomSwitch( Context context, AttributeSet attrs, int defStyleAttr ){
        super( context, attrs, defStyleAttr );
    }

    public void setCustomState( int customState ){
        this.customState = customState;
    }

    @Override
    public Parcelable onSaveInstanceState(){
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState( superState );
        ss.state = customState;
        return ss;
    }

    @Override
    public void onRestoreInstanceState( Parcelable state ){
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setCustomState( ss.state );
    }

    private static class SavedState extends BaseSavedState{
        int state;

        SavedState( Parcelable superState ){
            super( superState );
        }

        private SavedState( Parcel in ){
            super( in );
            state = in.readInt();
        }

        @Override
        public void writeToParcel( Parcel out, int flags ){
            super.writeToParcel( out, flags );
            out.writeInt( state );
        }

        public static final Creator<SavedState> CREATOR
                = new Creator<SavedState>(){
            public SavedState createFromParcel( Parcel in ){
                return new SavedState( in );
            }

            public SavedState[] newArray( int size ){
                return new SavedState[size];
            }
        };
    }
}
