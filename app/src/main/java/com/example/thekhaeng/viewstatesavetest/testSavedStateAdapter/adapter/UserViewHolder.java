package com.example.thekhaeng.viewstatesavetest.testSavedStateAdapter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.thekhaeng.viewstatesavetest.R;
import com.example.thekhaeng.viewstatesavetest.view.kotlin.SwitchViewGroup;

/**
 * Created by TheKhaeng
 */

public class UserViewHolder extends RecyclerView.ViewHolder{
    public TextView name;
    public TextView age;
    public SwitchViewGroup sw;

    public UserViewHolder( View itemView ){
        super( itemView );
        name = (TextView) itemView.findViewById( R.id.name );
        age = (TextView) itemView.findViewById( R.id.age );
        sw = (SwitchViewGroup) itemView.findViewById( R.id.switch1 );
    }
}
