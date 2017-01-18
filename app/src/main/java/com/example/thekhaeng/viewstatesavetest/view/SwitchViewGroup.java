package com.example.thekhaeng.viewstatesavetest.view;

import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.thekhaeng.viewstatesavetest.R;
import com.example.thekhaeng.viewstatesavetest.testSavedStateAdapter.adapter.UserAdapter;
import com.example.thekhaeng.viewstatesavetest.view.base.BaseViewGroup;

/**
 * Created by TheKhaeng
 */

public class SwitchViewGroup extends BaseViewGroup{

    private EditText editText;
    private CustomSwitch toggle;
    private UserAdapter.OnCheckedChangeListener checkChangeListener;
    private UserAdapter.OnTextChangeListener textChangedListener;

    public SwitchViewGroup( Context context, AttributeSet attrs ){
        super( context, attrs );
    }

    public SwitchViewGroup( Context context, AttributeSet attrs, int defStyleAttr ){
        super( context, attrs, defStyleAttr );
    }

    @RequiresApi( api = Build.VERSION_CODES.LOLLIPOP )
    public SwitchViewGroup( Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes ){
        super( context, attrs, defStyleAttr, defStyleRes );
    }

    @Override
    protected int getLayoutRes(){
        return R.layout.switch_custom_view_group;
    }

    @Override
    protected void setupStyleables( AttributeSet attrs, int defStyleAttr, int defStyleRes ){
    }

    @Override
    protected void bindView(){
        editText = (EditText) findViewById( R.id.text );
        toggle = (CustomSwitch) findViewById( R.id.toggle );
    }

    @Override
    protected void setupView(){
    }

    public void setText( String message ){
        editText.setText( message );
    }

    public void setTextChangedListener( TextWatcher listener ){
        editText.addTextChangedListener( listener );
    }

    public void setOnCheckChangeListener( CompoundButton.OnCheckedChangeListener listener ){
        toggle.setOnCheckedChangeListener( listener );
    }

    public void setCheck( boolean check ){
        toggle.setChecked( check );
    }


    @Override
    public Parcelable onSaveInstanceState(){
        Parcelable superState = super.onSaveInstanceState();
//        SavedState ss = new SavedState( superState );
        // Must call
        SavedState ss = (SavedState) onSaveInstanceChildState( new SavedState( superState ) );
        //no data to save
        return ss;
    }


    @Override
    public void onRestoreInstanceState( Parcelable state ){
        if( !( state instanceof SavedState ) ){
            super.onRestoreInstanceState( state );
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState( ss );
        //no data to restore
    }


    private static class SavedState extends ChildSavedState{

        SavedState( Parcelable superState ){
            super( superState );
        }

        private SavedState( Parcel in, ClassLoader classLoader ){
//            super( in );
            super( in, classLoader );
            //no data to save
        }

        @Override
        public void writeToParcel( Parcel out, int flags ){
            super.writeToParcel( out, flags );
            //no data to restore
        }

        public static final ClassLoaderCreator<SavedState> CREATOR = new ClassLoaderCreator<SwitchViewGroup.SavedState>(){
            @Override
            public SwitchViewGroup.SavedState createFromParcel( Parcel source, ClassLoader loader ){
                return new SwitchViewGroup.SavedState( source, loader );
            }

            public SwitchViewGroup.SavedState createFromParcel( Parcel in ){
                return null;
            }


            public SwitchViewGroup.SavedState[] newArray( int size ){
                return new SwitchViewGroup.SavedState[size];
            }
        };
    }

}
