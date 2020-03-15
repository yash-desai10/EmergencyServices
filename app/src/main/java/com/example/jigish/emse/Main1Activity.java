package com.example.jigish.emse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main1Activity extends AppCompatActivity {
    Button hb,fb,pb,lb,sb,login;
    static String dbname = "emse.db";
    SQLiteDatabase mydb = null;
    String dbpath = "/data/data/com.example.jigish.emse/databases/";
    String mypath = dbpath + dbname;
    static Context ct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        //MySQL my = new MySQL(getApplicationContext());
        //my.checkMydb();


        SharedPreferences pref=this.getSharedPreferences("saveinfo",Context.MODE_PRIVATE);
        String sharedemail=pref.getString("email","");
        String sharedpass=pref.getString("password","");
        MyApp.myphone=pref.getString("mobile","");
        MyApp.family_no=pref.getString("family_no","");

        if(!sharedemail.equals("") && !sharedpass.equals("")){
            Toast.makeText(getApplicationContext(),"Login with "+sharedemail,Toast.LENGTH_LONG).show();
            Intent i=new Intent(getApplication(),Main2Activity.class);
            startActivity(i);
            finish();
        }

        //checkLogin();
        hb = (Button)findViewById(R.id.hospital1);
        fb=(Button)findViewById(R.id.fire2);
        pb=(Button)findViewById(R.id.police3);
        lb=(Button)findViewById(R.id.login);
        sb=(Button)findViewById(R.id.email_sign_in_button);
        login=(Button)findViewById(R.id.login);

        ct=this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        hb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast t=Toast.makeText(getApplicationContext(),"request submitt",Toast.LENGTH_SHORT);
//                t.show();
//
//            }
//        });
//        fb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast t=Toast.makeText(getApplicationContext(),"request submitt",Toast.LENGTH_SHORT);
//                t.show();
//            }
//        });

//        pb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast t=Toast.makeText(getApplicationContext(),"request submitt",Toast.LENGTH_SHORT);
//                t.show();
//            }
//        });
//        MagicButton magicButton=(MagicButton)findViewById(R.id.magic_button);
//        magicButton.setMagicButtonClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplication(),"hi",Toast.LENGTH_LONG).show();
//                Intent i= new Intent(getApplicationContext(),Signup.class);
//                startActivity(i);
//
//            }
//        });
//        MagicButton button=(MagicButton)findViewById(R.id.magic_button2);
//        button.setMagicButtonClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i= new Intent(getApplicationContext(),LoginActivity.class);
//                startActivity(i);
//
//            }
//        });

        sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplication(),"hi",Toast.LENGTH_LONG).show();
                Intent i= new Intent(getApplicationContext(),Signup.class);
                startActivity(i);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
        });


//        lb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i= new Intent(getApplicationContext(),LoginActivity.class);
//                startActivity(i);
//            }
//        });
//        sb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i= new Intent(getApplicationContext(),Signup.class);
//                startActivity(i);
//            }
//        });


    }
    void checkLogin() {
        try {
            mydb = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cur = mydb.rawQuery("select * from userinfo", null);
            int len = cur.getCount();
            String email = "", password = "", name = "";

            while (cur.moveToNext()) {
               // name = cur.getString(cur.getColumnIndex("Firstname"));
                email = cur.getString(cur.getColumnIndex("Email_Id"));
                password = cur.getString(cur.getColumnIndex("Password"));
            }

//            Toast.makeText(getApplicationContext(),len + "  Email :" + email + " pass: " + password,Toast.LENGTH_LONG).show();
            if (!email.equals("") && !password.equals("")) {
                Intent i1 = new Intent(getApplicationContext(), Main2Activity.class);
//                i1.putExtra("name", name);
//                i1.putExtra("email", email);
                startActivity(i1);
                finish();
            }
            mydb.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
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
