package com.example.thekhaeng.viewstatesavetest.testSavedStateCustomViewGroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.thekhaeng.viewstatesavetest.R;
import com.example.thekhaeng.viewstatesavetest.testSavedStateAdapter.TestSavedStateAdapterActivity;

/**
 * Created by TheKhaeng
 */

public class MainActivity extends AppCompatActivity{

    private android.widget.Button button;

    @Override
    protected void onCreate( Bundle savedInstanceState ){
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        button = findViewById( R.id.button );

        button.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick( View v ){
                Intent i = new Intent( MainActivity.this, TestSavedStateAdapterActivity.class );
                startActivity( i );
            }
        } );
    }
}
