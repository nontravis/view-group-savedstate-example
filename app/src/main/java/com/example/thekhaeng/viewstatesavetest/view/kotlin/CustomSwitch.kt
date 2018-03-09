package com.example.thekhaeng.viewstatesavetest.view.kotlin

import android.content.Context
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View
import android.widget.Switch

/**
 * Created by TheKhaeng
 */

class CustomSwitch
    : Switch {

    private var customState: Int = 0

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {}

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


    internal class SavedState : View.BaseSavedState {
        var state: Int = 0

        constructor(superState: Parcelable) : super(superState)

        constructor(`in`: Parcel, classLoader: ClassLoader) : super(`in`) {
            state = `in`.readInt()
        }

        override
        fun writeToParcel(out: Parcel, flags: Int) = with(out) {
            super.writeToParcel(out, flags)
            out.writeInt(state)
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
