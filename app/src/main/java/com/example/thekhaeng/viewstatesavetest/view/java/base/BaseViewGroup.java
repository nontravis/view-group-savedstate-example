package com.example.thekhaeng.viewstatesavetest.view.java.base;

import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.FrameLayout;

/**
 * Created by TheKhaeng on 9/22/2016.
 */

@SuppressWarnings( "unchecked" )
public abstract class BaseViewGroup extends FrameLayout{


    public BaseViewGroup( Context context ){
        super( context );
        setup( null, 0, 0 );
    }

    public BaseViewGroup( Context context, AttributeSet attrs ){
        super( context, attrs );
        setup( attrs, 0, 0 );
    }

    public BaseViewGroup( Context context, AttributeSet attrs, int defStyleAttr ){
        super( context, attrs, defStyleAttr );
        setup( attrs, defStyleAttr, 0 );
    }

    @RequiresApi( api = Build.VERSION_CODES.LOLLIPOP )
    public BaseViewGroup( Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes ){
        super( context, attrs, defStyleAttr, defStyleRes );
        setup( attrs, defStyleAttr, defStyleRes );
    }

    private void setup( AttributeSet attrs, int defStyleAttr, int defStyleRes ){
        if( attrs != null ) setupStyleables( attrs, defStyleAttr, defStyleRes );
        inflateLayout();
        bindView();
        setupInstance();
        setupView();
    }

    /**
     * Custom handle SavedState child to "fix restore state in same resource id"
     * when use ViewGroup more than one.
     * <p>
     * the method must be call in subclass.
     **/
    protected Parcelable onSaveChildInstanceState( ChildSavedState ss ){
        ss.childrenStates = new SparseArray();
        for( int i = 0; i < getChildCount(); i++ ){
            int id = getChildAt( i ).getId();
            if( id != 0 ){
                SparseArray childrenState = new SparseArray();
                getChildAt( i ).saveHierarchyState( childrenState );
                ss.childrenStates.put( id, childrenState );
            }
        }
        return ss;
    }

    @Override
    protected void onRestoreInstanceState( Parcelable state ){
        if( !( state instanceof ChildSavedState ) ){
            super.onRestoreInstanceState( state );
            return;
        }
        ChildSavedState ss = (ChildSavedState) state;
        super.onRestoreInstanceState( ss );
        onRestoreChildInstanceState( ss );
    }

    private void onRestoreChildInstanceState( ChildSavedState ss ){
        for( int i = 0; i < getChildCount(); i++ ){
            int id = getChildAt( i ).getId();
            if( id != 0 ){
                if( ss.childrenStates.get( id ) != null ){
                    SparseArray childrenState = (SparseArray) ss.childrenStates.get( id );
                    getChildAt( i ).restoreHierarchyState( childrenState );
                }
            }
        }
    }


    public abstract static class ChildSavedState extends BaseSavedState{
        SparseArray childrenStates;

        public ChildSavedState( Parcelable superState ){
            super( superState );
        }

        public ChildSavedState( Parcel in, ClassLoader classLoader ){
            super( in );
            childrenStates = in.readSparseArray( classLoader );
        }

        @Override
        public void writeToParcel( Parcel out, int flags ){
            super.writeToParcel( out, flags );
            out.writeSparseArray( childrenStates );
        }
    }


    @Override
    protected void dispatchSaveInstanceState( SparseArray<Parcelable> container ){
        dispatchFreezeSelfOnly( container );
    }

    @Override
    protected void dispatchRestoreInstanceState( SparseArray<Parcelable> container ){
        dispatchThawSelfOnly( container );
    }

    private void inflateLayout(){
        inflate( getContext(), getLayoutRes(), this );
    }

    protected abstract int getLayoutRes();

    protected void setupStyleables( AttributeSet attrs, int defStyleAttr, int defStyleRes ){
    }

    protected void bindView(){
    }

    protected void setupInstance(){
    }

    protected abstract void setupView();

}
