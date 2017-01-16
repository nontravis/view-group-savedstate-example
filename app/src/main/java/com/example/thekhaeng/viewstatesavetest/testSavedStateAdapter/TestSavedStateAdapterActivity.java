package com.example.thekhaeng.viewstatesavetest.testSavedStateAdapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.thekhaeng.viewstatesavetest.R;
import com.example.thekhaeng.viewstatesavetest.testSavedStateAdapter.adapter.User;
import com.example.thekhaeng.viewstatesavetest.testSavedStateAdapter.adapter.UserAdapter;

import java.util.ArrayList;

/**
 * Created by TheKhaeng
 */

public class TestSavedStateAdapterActivity extends AppCompatActivity
        implements UserAdapter.OnCheckedChangeListener, UserAdapter.OnTextChangeListener{
    private ArrayList<User> userList;
    private RecyclerView rv;
    private UserAdapter adapter;

    @Override
    protected void onCreate( Bundle savedInstanceState ){
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_test_saved_state_adapter );

        rv = (RecyclerView) findViewById( R.id.rv_switch_view_group );
        adapter = new UserAdapter();
        adapter.setOnCheckedChangeListener( this );
        adapter.setOnTextChangeListener( this );
        rv.setLayoutManager( new LinearLayoutManager( this ) );
        rv.setAdapter( adapter );

        if( savedInstanceState == null ){
            userList = new ArrayList<>();
            userList.add( new User( "Name 1", 1 ) );
            userList.add( new User( "Name 2", 2 ) );
            userList.add( new User( "Name 3", 3 ) );
            userList.add( new User( "Name 4", 4 ) );
            userList.add( new User( "Name 5", 5 ) );
            userList.add( new User( "Name 6", 6 ) );
            userList.add( new User( "Name 7", 7 ) );
            userList.add( new User( "Name 8", 8 ) );
            userList.add( new User( "Name 9", 9 ) );
            userList.add( new User( "Name 10", 10 ) );
            userList.add( new User( "Name 11", 11 ) );
            userList.add( new User( "Name 12", 12 ) );
            userList.add( new User( "Name 13", 13 ) );
            userList.add( new User( "Name 14", 14 ) );
            userList.add( new User( "Name 15", 15 ) );
            userList.add( new User( "Name 16", 16 ) );
            userList.add( new User( "Name 17", 17 ) );

            adapter.setUserList( userList );
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ){
        super.onSaveInstanceState( outState );
        outState.putParcelableArrayList( "data", userList );
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ){
        super.onRestoreInstanceState( savedInstanceState );
        userList = savedInstanceState.getParcelableArrayList( "data" );
        adapter.setUserList( userList );
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.menu_main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if( id == R.id.action_settings ){
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    public void onCheckedChanged( boolean isChecked, int position ){
        userList.get( position ).setCheck( isChecked );
    }

    @Override
    public void onTextChanged( String message, int position ){
        Log.i("TestSavedStateAdapterAc", message);
        userList.get( position ).setText( message );
    }
}
