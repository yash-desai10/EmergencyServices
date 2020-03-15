package com.example.jigish.emse;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import pl.polak.clicknumberpicker.ClickNumberPickerListener;
import pl.polak.clicknumberpicker.ClickNumberPickerView;
import pl.polak.clicknumberpicker.PickerClickType;



public class    Doner extends Fragment implements AdapterView.OnItemSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button bt;
    //donorfile
    String sfna,scity,scountry,weg,height,age,sdate,bgrup,gender;
    //static
    String firstname,lastname,email,pass,copass,address,street,city,country,Mobile,family_no,first,second;
    Spinner spinner1;
    List list;
    Button sub;
    ClickNumberPickerView num1,num2,num3;
    int year_x,month_x,day_x;
    static  final  int DIALOG_ID=0;
    String weight;
    Button kg;
    RadioGroup radiosexgrup;
    static EditText datt;
    int flag=0;

    ProgressDialog p;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static String dbname = "emse.db";
    SQLiteDatabase mydb = null;
    String dbpath = "/data/data/com.example.jigish.emse/databases/";
    String mypath = dbpath + dbname;

    private OnFragmentInteractionListener mListener;


    public Doner() {
        }
    public static Doner newInstance(String param1, String param2) {
        Doner fragment = new Doner();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_doner, container, false);

        datt=(EditText)v.findViewById(R.id.date);
        Button submit=(Button)v.findViewById(R.id.submit);
         bt = (Button) v.findViewById(R.id.date1);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
//                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
            }
        });


        TextView tv = (TextView) v.findViewById(R.id.tv);
        // Spinner element

        final MaterialSpinner spinner1 = (MaterialSpinner) v.findViewById(R.id.spinner);
        spinner1.setItems("A+", "A-", "B+", "B-", "AB+","AB-","O+","O-");
        spinner1.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                bgrup=item;
//                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

//
        num1=(ClickNumberPickerView)v.findViewById(R.id.numberpicker1);
        num1.setClickNumberPickerListener(new ClickNumberPickerListener() {
            @Override
            public void onValueChange(float previousValue, float currentValue, PickerClickType pickerClickType) {
            }
        });
        num2=(ClickNumberPickerView)v.findViewById(R.id.numberpicker2);
        num2.setClickNumberPickerListener(new ClickNumberPickerListener() {
            @Override

            public void onValueChange(float previousValue, float currentValue, PickerClickType pickerClickType) {
            }
        });
        num3=(ClickNumberPickerView)v.findViewById(R.id.numberpicker3);
        num3.setClickNumberPickerListener(new ClickNumberPickerListener() {
            @Override
            public void onValueChange(float previousValue, float currentValue, PickerClickType pickerClickType) {
                //=pickerClickType.toString();
                weight =String.valueOf(currentValue);
//                Toast.makeText(getActivity(),"curre"+weight,Toast.LENGTH_SHORT).show();


            }
        });



        sub=(Button)v.findViewById(R.id.submit);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                sfna = personal1.sfna.getText().toString();
                lastname = personal1.slna.getText().toString();

                email = personal1.semail.getText().toString();
//                StringTokenizer tokens=new StringTokenizer(email,"@");
//                first=tokens.nextToken();
//                second=tokens.nextToken();
                pass=personal1.spass.getText().toString();
                address = personal2.sadd.getText().toString();
                Mobile=personal2.smob.getText().toString();
                street =  personal2.sstreet.getText().toString();
                city = personal2.scity.getText().toString();
                country = personal2.scountry.getText().toString();
                age=Float.toString( num3.getValue());
                weight=Float.toString(num1.getValue());
                height=Float.toString(num2.getValue());
                family_no=personal2.family_no.getText().toString();
                final String  sdate= datt.getText().toString();
                radiosexgrup=(RadioGroup) getView().findViewById(R.id.radiosexgrup);
                int selectedId = radiosexgrup.getCheckedRadioButtonId();
                final RadioButton radioSexButton = (RadioButton)getView().findViewById(selectedId);
                gender=radioSexButton.getText().toString();

                if(sfna.length()==0)
                {
                    flag=1;
                    Toast.makeText(getActivity(),"Name is required",Toast.LENGTH_LONG).show();
                }
                if(lastname.length()==0)
                {
                    flag=1;
                    Toast.makeText(getActivity(),"Username is required",Toast.LENGTH_LONG).show();
                }
                if(email.length()==0)
                {
                    flag=1;
                    Toast.makeText(getActivity(),"Email-ID is required",Toast.LENGTH_LONG).show();
                }
                if(pass.length()==0)
                {
                    flag=1;
                    Toast.makeText(getActivity(),"Password is required",Toast.LENGTH_LONG).show();
                }
                if(Mobile.length()==0)
                {
                    flag=1;
                    Toast.makeText(getActivity(),"Mobile number is required",Toast.LENGTH_LONG).show();
                }
                if(address.length()==0)
                {
                    flag=1;
                    Toast.makeText(getActivity(),"Address is required",Toast.LENGTH_LONG).show();
                }
                if(street.length()==0)
                {
                    flag=1;
                    Toast.makeText(getActivity(),"Street is required",Toast.LENGTH_LONG).show();
                }
                if(city.length()==0)
                {
                    flag=1;
                    Toast.makeText(getActivity(),"City is required",Toast.LENGTH_LONG).show();
                }
                if(country.length()==0)
                {
                    flag=1;
                    Toast.makeText(getActivity(),"Country is required",Toast.LENGTH_LONG).show();
                }
                if(family_no.length()==0)
                {
                    flag=1;
                    Toast.makeText(getActivity(),"Family number is required",Toast.LENGTH_LONG).show();
                }

                if(flag==0) {


                    onsubmit();
                }


            }
        });


        return v;
    }
     public void saveinfo(View view)
     {
         SharedPreferences sharedPrefer=this.getActivity().getSharedPreferences("saveinfo",Context.MODE_PRIVATE);
         SharedPreferences.Editor edit=sharedPrefer.edit();
         edit.putString("firstname",sfna);
         edit.putString("lastname",lastname);
         edit.putString("email",email);
         edit.putString("password",pass);
         edit.putString("mobile",Mobile);
         edit.putString("street",street);
         edit.putString("address",address);
         edit.putString("city",city);
         edit.putString("country",country);
         edit.putString("age",age);
         edit.putString("weight",weight);
         edit.putString("height",height);
         edit.putString("family_no",family_no);
         edit.putString("sdate",sdate);
         edit.putString("gender",gender);
         edit.putString("blood_grup",bgrup);
         edit.apply();
         Toast.makeText(getActivity(),"saved",Toast.LENGTH_SHORT).show();
         display(getView());

     }
    public  void display(View view)
    {
        SharedPreferences pref=this.getActivity().getSharedPreferences("saveinfo",Context.MODE_PRIVATE);
        String sharedemail=pref.getString("email","");
        String sharedpass=pref.getString("password","");
        Toast.makeText(getActivity(),"Data"+sharedemail+" "+sharedpass,Toast.LENGTH_SHORT).show();

    }



    public void onsubmit()
    {
        p=new ProgressDialog(getActivity());
        p.setCancelable(false);
        p.setMessage("please wait");
        p.setMax(100);
        p.show();
        //Toast.makeText(getActivity(),"user info"+sfna+" num3 "+bgrup+" sex "+radioSexButton.getText().toString() ,Toast.LENGTH_SHORT).show();
       // myRef.setValue(sfna);
        //myRef.setValue(lastname);
//this method check user is add or not
        myRef.child(lastname+"_"+pass).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==null)
                {
                    Map<String ,String> map= new LinkedHashMap<String, String>();
                    map.put("firstname",sfna);
                    map.put("lastname",lastname);
                    map.put("password",pass);
                    map.put("street",street);
                    map.put("city",city);
                    map.put("email",email);
                    map.put("country",country);
                    map.put("Mobile",Mobile);
                    map.put("age",age);
                    map.put("weight",weight);
                    map.put("height",height);
                    map.put("date",sdate);
                    map.put("blood",bgrup);
                    map.put("sex",gender);
                    map.put("Family_memeber_no",family_no);
                    map.put("Address",address);

//this method is for storeing the data
                    myRef.child(lastname+"_"+pass).setValue(map).addOnCompleteListener( new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            p.dismiss();
                            Toast.makeText(getActivity(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(getActivity(),Main2Activity.class);
                            startActivity(i);
//                           insert();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });


                }



                else {
                    p.dismiss();
//                    Toast.makeText(getActivity(),"Already exist",Toast.LENGTH_LONG).show();
                }
            }
            public void insert()
            {
                String qry="insert into userinfo(Firstname,Lastname,Email_Id,Password,Mobile,Address,Street,Country,Family,DOB,Gender,BooldGroup,Weight,Height,Age,city)"
                        +"values('"+sfna+"','"+lastname+"','"+email+"','"+pass+"','"+Mobile+"','"+address+"','"+street+"','"+country+"','"+family_no+"','"+sdate+"','"+gender+"','"+bgrup+"','"+weight+"','" +
                        "','"+height+"','"+age+"','"+city+"')";
                String qry1="insert into userinfo(Email_Id,Password) values('"+email+"','"+pass+"')";
                mydb = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
                mydb.rawQuery(qry1, null);

                Toast.makeText(getActivity(),"Hello "+email+""+pass,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        saveinfo(getView());


      //  getUrldata();
       // Toast.makeText(getActivity(),"user info: "+firstname+" ctname: "+ctname+" blood ",Toast.LENGTH_SHORT).show();
    }



    //webservies code
    private void getUrldata() {

        class GetImage extends AsyncTask<String,Void,String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //   loading = ProgressDialog.show(P_Call.this, "Please wait....", null, true,true);

              //  Main1Activity.loading.setCancelable(false);
              //  Main1Activity.loading.setTitle("Please Wait....");

               // loading.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
               // Main1Activity.loading.show();
            }

            @Override
            protected void onPostExecute(String st) {
                super.onPostExecute(st);
               // Main1Activity.loading.dismiss();

                Toast.makeText(getActivity(),"Data from server :"+st,Toast.LENGTH_SHORT).show();
                //getAllData(st);
                // UpdateTabel();


            }

            @Override
            protected String doInBackground(String... params) {

                String data2="";

                if (android.os.Build.VERSION.SDK_INT > 9)
                {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }


                Webservices ws=new Webservices();
                ws.setUrl("http://ydjs.co.nf/signin.php");
                ws.addParam(sfna,"sfirstname");
                ws.addParam(scity,"scity");
              /*  ws.addParam(slna,"slastname");
                ws.addParam(semail,"semail");
                ws.addParam(spass,"spass");
                ws.addParam(smob,"smobileno");
                ws.addParam(sadd,"saddress");
                ws.addParam(sstreet,"sstreet");

                ws.addParam(scountry,"scountry");*/
               // ws.addParam();
                ws.connect();
                //data2=ws.getData();
                String data=ws.getData();



                return data;
            }
        }
        GetImage gi = new GetImage();
        gi.execute();

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    //Performing action onItemSelected and onNothing selected
    @Override
    //spiner method  on select item
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // On selecting a spinner item
        String item = adapterView.getItemAtPosition(i).toString();
        // Showing selected spinner item
        Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

//some item  select
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
