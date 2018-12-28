# ViewGroup savedstate EXAMPLE

[![Build Status](https://travis-ci.org/TheKhaeng/view-group-savedstate-example.svg?branch=master)](https://travis-ci.org/TheKhaeng/view-group-savedstate-example)

[Read my medium blog [en]](https://blog.nextzy.me/the-right-way-to-save-state-viewgroup-c6cb6a5c5b91)
[Read my medium blog [th]](https://blog.nextzy.me/savedstate-viewgroup-%E0%B9%83%E0%B8%AB%E0%B9%89%E0%B8%96%E0%B8%B9%E0%B8%81%E0%B8%A7%E0%B8%B4%E0%B8%98%E0%B8%B5-ebd9615715e1?source=linkShare-51da4bed1004-1484761618)


#### [UPDATE]

- fix bug
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
abstract class BaseViewGroup : FrameLayout {

    @JvmOverloads
    constructor(
            context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = 0)
            : super(context, attrs, defStyleAttr) {
        setup(attrs, defStyleAttr, 0)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        setup(attrs, defStyleAttr, defStyleRes)
    }


    private fun setup(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs != null) setupStyleables(attrs, defStyleAttr, defStyleRes)
        inflateLayout()
        bindView()
        setupInstance()
        setupView()
    }

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

    private fun inflateLayout() {
        View.inflate(context, getLayoutRes(), this)
    }

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


    protected abstract fun getLayoutRes(): Int

    protected abstract fun setupView()

    protected open fun setupStyleables(attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) {}

    protected open fun bindView() {}

    protected open fun setupInstance() {}



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

---

[**SwitchViewGroup.java**](./app/src/main/java/com/example/thekhaeng/viewstatesavetest/view/java/SwitchViewGroup.java)

[**SwitchViewGroup.kt**](./app/src/main/java/com/example/thekhaeng/viewstatesavetest/view/kotlin/SwitchViewGroup.kt)
```kotlin
class SwitchViewGroup
@JvmOverloads constructor(context: Context,
                          attrs: AttributeSet? = null,
                          defStyleAttr: Int = 0,
                          defStyleRes: Int = 0)
    : BaseViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    private var editText: EditText? = null
    private var toggle: CustomSwitch? = null
    private val checkChangeListener: UserAdapter.OnCheckedChangeListener? = null
    private val textChangedListener: UserAdapter.OnTextChangeListener? = null


    override
    fun getLayoutRes(): Int = R.layout.switch_custom_view_group

    override
    fun setupStyleables(attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) {
    }

    override
    fun bindView() {
        editText = findViewById(R.id.editText)
        toggle = findViewById(R.id.toggle)
    }

    override
    fun setupView() {
    }

    fun setTextToEditText(message: String?) {
        editText?.setText(message)
    }

    fun setTextChangedListener(listener: TextWatcher) {
        editText?.addTextChangedListener(listener)
    }

    fun setOnCheckChangeListener(listener: CompoundButton.OnCheckedChangeListener) {
        toggle?.setOnCheckedChangeListener(listener)
    }

    fun setCheck(check: Boolean) {
        toggle?.isChecked = check
    }

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


    internal class SavedState : BaseViewGroup.ChildSavedState {

        constructor(superState: Parcelable) : super(superState) {}

        constructor(`in`: Parcel, classLoader: ClassLoader) : super(`in`, classLoader) {
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

---

[**CustomSwitch.java**](./app/src/main/java/com/example/thekhaeng/viewstatesavetest/view/java/CustomSwitch.java)

[**CustomSwitch.kt**](./app/src/main/java/com/example/thekhaeng/viewstatesavetest/view/kotlin/CustomSwitch.kt)
```kotlin
class CustomSwitch
    : Switch {

    private var customState: Int = 0

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
    }

    fun setCustomState(customState: Int) {
        this.customState = customState
    }

    override
    fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        val ss = SavedState(superState)
        ss.state = customState
        return ss
    }


    override
    fun onRestoreInstanceState(state: Parcelable) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        super.onRestoreInstanceState(state.superState)
        setCustomState(state.state)
    }


    internal class SavedState : ViewSavedState {
        var state: Int = 0

        constructor(superState: Parcelable) : super(superState)

        constructor(`in`: Parcel) : super(`in`) {
            state = `in`.readInt()
        }

        override
        fun writeToParcel(out: Parcel, flags: Int) = with(out) {
            super.writeToParcel(out, flags)
            out.writeInt(state)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {

                override
                fun createFromParcel(`in`: Parcel): SavedState? = SavedState(`in`)

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
