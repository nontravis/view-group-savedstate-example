package com.example.thekhaeng.viewstatesavetest.view.kotlin.base

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.view.View
import android.widget.FrameLayout

/**
 * Created by TheKhaeng on 9/22/2016.
 */

abstract class BaseViewGroup
@JvmOverloads constructor(context: Context,
                          attrs: AttributeSet? = null,
                          defStyleAttr: Int = 0,
                          defStyleRes: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    init {
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

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state !is ChildSavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        val ss = state
        super.onRestoreInstanceState(ss.superState)
        onRestoreChildInstanceState(ss)
    }

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


    abstract class ChildSavedState : View.BaseSavedState {
        internal var childrenStates: SparseArray<Any>? = null

        constructor(superState: Parcelable) : super(superState) {}

        constructor(`in`: Parcel, classLoader: ClassLoader) : super(`in`) {
            childrenStates = `in`.readSparseArray(classLoader)
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeSparseArray(childrenStates)
        }
    }


    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        dispatchThawSelfOnly(container)
    }

    private fun inflateLayout() {
        View.inflate(context, getLayoutRes(), this)
    }

    protected abstract fun getLayoutRes(): Int

    protected abstract fun setupView()

    protected open fun setupStyleables(attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) {}

    protected open fun bindView() {}

    protected fun setupInstance() {}

}
