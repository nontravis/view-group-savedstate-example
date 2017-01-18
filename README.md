# ViewGroup savedstate EXAMPLE

[Read my medium blog [th]](https://blog.nextzy.me/savedstate-viewgroup-%E0%B9%83%E0%B8%AB%E0%B9%89%E0%B8%96%E0%B8%B9%E0%B8%81%E0%B8%A7%E0%B8%B4%E0%B8%98%E0%B8%B5-ebd9615715e1?source=linkShare-51da4bed1004-1484761618)

If you want to test savedstate

Enable **Developer mode**

```
Go to Developer Options -> and check ** Don't Keep Activities **
```

![View savedstate][view_savedstate]

![ViewGroup savedstate][viewgroup_savedstate]

---

[**BaseViewGroup.java**](./app/src/main/java/com/example/thekhaeng/viewstatesavetest/view/base/BaseViewGroup.java)
```java
abstract public class BaseViewGroup extends FrameLayout{

    ...

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof ChildSavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        ChildSavedState ss = (ChildSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        onRestoreChildInstanceState(ss);
    }

    protected Parcelable onSaveInstanceChildState( ChildSavedState ss ){
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

    private void onRestoreInstanceChildState(ChildSavedState ss) {
        for (int i = 0; i < getChildCount(); i++) {
            int id = getChildAt(i).getId();
            if (id != 0) {
                if (ss.childrenStates.get(id) != null) {
                    SparseArray childrenState = (SparseArray) ss.childrenStates.get(id);
                    getChildAt(i).restoreHierarchyState(childrenState);
                }
            }
        }
    }

    ...

    @Override
    protected void dispatchSaveInstanceState( SparseArray<Parcelable> container ){
        dispatchFreezeSelfOnly( container );
    }

    @Override
    protected void dispatchRestoreInstanceState( SparseArray<Parcelable> container ){
        dispatchThawSelfOnly( container );
    }


    public static abstract class ChildSavedState extends BaseSavedState{
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

}

```


[**SwitchViewGroup.java**](./app/src/main/java/com/example/thekhaeng/viewstatesavetest/view/SwitchViewGroup.java)
```java
public class SwitchViewGroup extends BaseViewGroup{

    ...

    @Override
    public Parcelable onSaveInstanceState(){
        Parcelable superState = super.onSaveInstanceState();
        // Must call
        SavedState ss = (SavedState) onSaveInstanceChildState( new SavedState( superState ) );
        //save data here

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
        //restore data here

    }

    ...

    private static class SavedState extends ChildSavedState{

        SavedState( Parcelable superState ){
            super( superState );
        }

        private SavedState( Parcel in, ClassLoader classLoader ){
            super( in, classLoader );
            //save data here
        }

        @Override
        public void writeToParcel( Parcel out, int flags ){
            super.writeToParcel( out, flags );
            //restore data here
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

```

[view_savedstate]: ./media/view_savedstate.gif "Logo Title Text 2"
[viewgroup_savedstate]: ./media/viewgroup_savedstate.gif "Logo Title Text 2"
