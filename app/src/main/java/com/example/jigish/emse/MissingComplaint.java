package com.example.jigish.emse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class MissingComplaint extends AppCompatActivity {
    EditText fullname, age, height, mobile, address, physical_desc, disappearence, m_fullname;

    TextView tv_dob, tv_img;
    Button btn_sub, btn_select,btn_upload;
    ProgressDialog p;
    ImageView img;

    String Ufullname, Uage, Uheight, Umobile, Uaddress, Uphysical, Udisappearence, Umfullname;
    int year, month, mDay, nmonth;
    private static final int PICK_IMAGE_REQUEST = 1234;
    private Uri filePath;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("mcf");
    StorageReference mStorageRef;


    public static final String STORAGE_PATH = "image/";
    public static final String DATABASE_PATH = "image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_complaint);

        mStorageRef= FirebaseStorage.getInstance().getReference();

        fullname = (EditText) findViewById(R.id.fullname);
        m_fullname = (EditText) findViewById(R.id.m_fullname);
        age = (EditText) findViewById(R.id.age);
        height = (EditText) findViewById(R.id.height);
        mobile = (EditText) findViewById(R.id.mobile);
        address = (EditText) findViewById(R.id.address);
        physical_desc = (EditText) findViewById(R.id.physical_desc);
        disappearence = (EditText) findViewById(R.id.disappearence);
//        tv_dob=(TextView)findViewById(R.id.tv_dob);
        btn_sub = (Button) findViewById(R.id.btn_sub);
        btn_select = (Button) findViewById(R.id.btn_select);
//        btn_upload = (Button) findViewById(R.id.btn_upload);
        img = (ImageView) findViewById(R.id.img);

        Calendar cc = Calendar.getInstance();
        year = cc.get(Calendar.YEAR);
        month = cc.get(Calendar.MONTH);
        nmonth = month + 1;
        mDay = cc.get(Calendar.DAY_OF_MONTH);

//        btn_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                DialogFragment newFragment = new DateFragment();
//                newFragment.show(getSupportFragmentManager(), "DatePicker");
////                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
//
//        btn_upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                uploadFile();
//            }
//        });



        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onSubmit();
                uploadFile();
            }
      });
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void onSubmit(final String url,final ProgressDialog p) {
        Ufullname = fullname.getText().toString();
//                Udate=date.getText().toString();
        Umfullname = m_fullname.getText().toString();
        Uage = age.getText().toString();
        Uheight = height.getText().toString();
        Umobile = mobile.getText().toString();
        Uaddress = address.getText().toString();
        Uphysical = physical_desc.getText().toString();
        Udisappearence = disappearence.getText().toString();

//                p=new ProgressDialog(getApplicationContext());
//                p.setCancelable(false);
//                p.setMessage("please wait");
//                p.setMax(100);
//                p.show();

        myRef.child(Ufullname + "_" + Umobile).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    Map<String, String> map = new LinkedHashMap<String, String>();
                    map.put("complainer's name", Ufullname);
//                            map.put("date of birth",Udate);
                    map.put("missing person's name", Umfullname);
                    map.put("age", Uage);
                    map.put("height", Uheight);
                    map.put("mobile", Umobile);
                    map.put("address", Uaddress);
                    map.put("physical description", Uphysical);
                    map.put("dissapereance", Udisappearence);
                    map.put("url",url);

//this method is for storeing the data
                    myRef.child(Ufullname + "_" + Umobile).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
//                                    p.dismiss();
                            Toast.makeText(getApplicationContext(), "Successfully file missing complaint", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                            startActivity(i);
                            p.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            p.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                img.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadFile() {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.setCancelable(false);
            progressDialog.show();
            Random r =new Random();


            StorageReference riversRef =  mStorageRef.child("images").child(year+"_"+nmonth+"_"+mDay+"pic.jpg");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                           // progressDialog.dismiss();

                            progressDialog.setMessage("Almost done.... !");
                            //and displaying a success toast
                            Uri url=taskSnapshot.getMetadata().getDownloadUrl();
                            onSubmit(""+url,progressDialog);


                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }
}
