# ViewGroup savedstate EXAMPLE

[![Build Status](https://travis-ci.org/TheKhaeng/view-group-savedstate-example.svg?branch=master)](https://travis-ci.org/TheKhaeng/view-group-savedstate-example)

[Read my medium blog [th]](https://blog.nextzy.me/savedstate-viewgroup-%E0%B9%83%E0%B8%AB%E0%B9%89%E0%B8%96%E0%B8%B9%E0%B8%81%E0%B8%A7%E0%B8%B4%E0%B8%98%E0%B8%B5-ebd9615715e1?source=linkShare-51da4bed1004-1484761618)


#### [UPDATE]

- kotlin CustomView example :)

If you want to test savedstate Enable **Developer mode**

```
Go to Developer Options -> and check ** Don't Keep Activities **
```

![View savedstate][view_savedstate]     ![ViewGroup savedstate][viewgroup_savedstate]

---

[**BaseViewGroup.java**](./app/src/main/java/com/example/thekhaeng/viewstatesavetest/view/java/base/BaseViewGroup.java)

[**BaseViewGroup.kt**](./app/src/main/java/com/example/thekhaeng/viewstatesavetest/view/kotlin/base/BaseViewGroup.kt)
```kotlin
abstract class BaseViewGroup
@JvmOverloads constructor(context: Context,
                          attrs: AttributeSet? = null,
                          defStyleAttr: Int = 0,
                          defStyleRes: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    init {
        setup(attrs, defStyleAttr, defStyleRes)
    }

    ...

    /**
     * Custom handle SavedState child to "fix restore state in same resource id"
     * when use ViewGroup more than one.
     *
     *
     * the method must be call in subclass.
     */
    protected fun onSaveChildInstanceState(ss: ChildSavedState): Parcelable {
        ss.childrenStates = SparseArray()
        for (i in 0 until childCount) {
            val id = getChildAt(i).id
            if (id != 0) {
                val childrenState = SparseArray<Parcelable>()
                getChildAt(i).saveHierarchyState(childrenState)
                ss.childrenStates?.put(id, childrenState)
            }

        }
        return ss
    }

    override
    fun onRestoreInstanceState(state: Parcelable) {
        if (state !is ChildSavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        super.onRestoreInstanceState(state.superState)
        onRestoreChildInstanceState(state)
    }



    override
    fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        dispatchFreezeSelfOnly(container)
    }

    override
    fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        dispatchThawSelfOnly(container)
    }

    ...

    @Suppress("UNCHECKED_CAST")
    private fun onRestoreChildInstanceState(ss: ChildSavedState) {
        for (i in 0 until childCount) {
            val id = getChildAt(i).id
            if (id != 0) {
                if (ss.childrenStates?.get(id) != null) {
                    val childrenState = ss.childrenStates?.get(id) as SparseArray<Parcelable?>
                    getChildAt(i).restoreHierarchyState(childrenState)
                }
            }
        }
    }

    ...

    abstract class ChildSavedState : View.BaseSavedState {
        internal var childrenStates: SparseArray<Any>? = null

        constructor(superState: Parcelable) : super(superState)

        constructor(`in`: Parcel, classLoader: ClassLoader) : super(`in`) {
            childrenStates = `in`.readSparseArray(classLoader)
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeSparseArray(childrenStates)
        }
    }
}

```


[**SwitchViewGroup.java**](./app/src/main/java/com/example/thekhaeng/viewstatesavetest/view/java/SwitchViewGroup.java)

[**SwitchViewGroup.kt**](./app/src/main/java/com/example/thekhaeng/viewstatesavetest/view/kotlin/SwitchViewGroup.kt)
```java
class SwitchViewGroup
@JvmOverloads constructor(context: Context,
                          attrs: AttributeSet? = null,
                          defStyleAttr: Int = 0,
                          defStyleRes: Int = 0)
    : BaseViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    ...

    public override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        // Must call
        val ss = onSaveChildInstanceState(SavedState(superState)) as SavedState
        // save data here
        return ss
    }


    public override fun onRestoreInstanceState(state: Parcelable) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        super.onRestoreInstanceState(state)
        // restore data here
    }


    private class SavedState : BaseViewGroup.ChildSavedState {

        internal constructor(superState: Parcelable) : super(superState) {}

        private constructor(`in`: Parcel, classLoader: ClassLoader) : super(`in`, classLoader) {
            // save data here
        }

        override
        fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            // restore data here
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.ClassLoaderCreator<SavedState> = object : Parcelable.ClassLoaderCreator<SavedState> {
                override
                fun createFromParcel(source: Parcel, loader: ClassLoader): SavedState = SavedState(source, loader)

                override
                fun createFromParcel(`in`: Parcel): SavedState? = null

                override
                fun newArray(size: Int): Array<SavedState?> = arrayOfNulls(size)
            }
        }
    }

}
```

### Developed By Thai android developer.


[<img src="./media/profile2_circle.png" width="170">](https://www.facebook.com/nonthawit) [![TheKhaeng](./media/thekhaeng_logo.png)](https://www.facebook.com/thekhaeng.io/)


Follow [facebook.com/thekhaeng.io](https://www.facebook.com/thekhaeng.io) on Facebook page.
or [@nonthawit](https://medium.com/@nonthawit) at my Medium blog. :)

For contact, shoot me an email at nonthawit.thekhaeng@gmail.com



[view_savedstate]: ./media/view_savedstate.gif "Logo Title Text 2"
[viewgroup_savedstate]: ./media/viewgroup_savedstate.gif "Logo Title Text 2"
