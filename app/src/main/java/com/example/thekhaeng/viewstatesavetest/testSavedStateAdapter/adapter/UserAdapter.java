package com.example.thekhaeng.viewstatesavetest.testSavedStateAdapter.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.thekhaeng.viewstatesavetest.R;

import java.util.List;

/**
 * Created by TheKhaeng
 */

public class UserAdapter extends RecyclerView.Adapter{
    private List<User> userList;
    private OnCheckedChangeListener checkChangeListener;
    private OnTextChangeListener textChangeListener;

    public interface OnTextChangeListener{
        void onTextChanged( String message, int position );
    }

    public interface OnCheckedChangeListener{
        void onCheckedChanged( boolean isChecked, int position );
    }

    public UserAdapter(){
    }

    public void setUserList( List<User> userList ){
        this.userList = userList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ){
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_user, parent, false );
        return new UserViewHolder( view );
    }

    @Override
    public void onBindViewHolder( final RecyclerView.ViewHolder holder, final int position ){
        if( holder instanceof UserViewHolder ){
            final UserViewHolder userViewHolder = (UserViewHolder) holder;
            final User user = userList.get( position );
            userViewHolder.name.setText( user.getName() );
            userViewHolder.age.setText( "" + user.getAge() );
            userViewHolder.sw.setCheck( user.isCheck() );
            userViewHolder.sw.setTextToEditText( user.getText() );
            userViewHolder.sw.setOnCheckChangeListener( new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged( CompoundButton buttonView, boolean isChecked ){
                    if( checkChangeListener != null ){
                        checkChangeListener.onCheckedChanged( isChecked, userViewHolder.getAdapterPosition() );
                    }
                }
            } );
            userViewHolder.sw.setTextChangedListener( new TextWatcher(){
                @Override
                public void beforeTextChanged( CharSequence s, int start, int count, int after ){

                }

                @Override
                public void onTextChanged( CharSequence s, int start, int before, int count ){
                    if( textChangeListener != null ){
                        textChangeListener.onTextChanged( s.toString(), userViewHolder.getAdapterPosition() );
                    }
                }

                @Override
                public void afterTextChanged( Editable s ){

                }
            } );
        }
    }

    public void setOnCheckedChangeListener( OnCheckedChangeListener listener ){
        checkChangeListener = listener;
    }

    public void setOnTextChangeListener( OnTextChangeListener listener ){
        textChangeListener = listener;
    }


    @Override
    public int getItemCount(){
        return userList != null ? userList.size() : 0;
    }

}
