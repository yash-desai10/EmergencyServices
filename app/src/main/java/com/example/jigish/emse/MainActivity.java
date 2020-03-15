package com.example.jigish.emse;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button hb,fb,pb,lb,sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hb = (Button)findViewById(R.id.hospital1);
        fb=(Button)findViewById(R.id.fire2);
        pb=(Button)findViewById(R.id.police3);
        lb=(Button)findViewById(R.id.login);
        sb=(Button)findViewById(R.id.email_sign_in_button);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        hb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast t=Toast.makeText(getApplicationContext(),"request submitt",Toast.LENGTH_SHORT);
            t.show();

            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast t=Toast.makeText(getApplicationContext(),"request submitt",Toast.LENGTH_SHORT);
                t.show();
            }
        });

        pb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast t=Toast.makeText(getApplicationContext(),"request submitt",Toast.LENGTH_SHORT);
                t.show();
            }
        });
        lb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
        });
        sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),Signup.class);
                startActivity(i);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
