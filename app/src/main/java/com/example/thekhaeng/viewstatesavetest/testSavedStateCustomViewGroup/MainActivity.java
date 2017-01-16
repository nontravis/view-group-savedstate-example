package com.example.thekhaeng.viewstatesavetest.testSavedStateCustomViewGroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.thekhaeng.viewstatesavetest.R;
import com.example.thekhaeng.viewstatesavetest.testSavedStateAdapter.TestSavedStateAdapterActivity;
import com.example.thekhaeng.viewstatesavetest.view.SwitchViewGroup;

/**
 * Created by TheKhaeng
 */

public class MainActivity extends AppCompatActivity{

    private android.widget.Button button;
    private SwitchViewGroup switch1;
    private SwitchViewGroup switch2;
    private SwitchViewGroup switch3;

    @Override
    protected void onCreate( Bundle savedInstanceState ){
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        switch3 = (SwitchViewGroup) findViewById( R.id.switch3 );
        switch2 = (SwitchViewGroup) findViewById( R.id.switch2 );
        switch1 = (SwitchViewGroup) findViewById( R.id.switch1 );
        button = (Button) findViewById( R.id.button );

        button.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick( View v ){
                Intent i = new Intent( MainActivity.this, TestSavedStateAdapterActivity.class );
                startActivity( i );
            }
        } );
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ){
        super.onSaveInstanceState( outState );
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ){
        super.onRestoreInstanceState( savedInstanceState );
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
}
