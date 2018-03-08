package com.example.thekhaeng.viewstatesavetest.view.kotlin

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.CompoundButton
import android.widget.EditText
import com.example.thekhaeng.viewstatesavetest.R
import com.example.thekhaeng.viewstatesavetest.testSavedStateAdapter.adapter.UserAdapter
import com.example.thekhaeng.viewstatesavetest.view.kotlin.base.BaseViewGroup

/**
 * Created by TheKhaeng
 */

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
    fun setupStyleables(attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) {}

    override
    fun bindView() {
        editText = findViewById(R.id.editText)
        toggle = findViewById(R.id.toggle)
    }

    override
    fun setupView() {}

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
