package com.example.joousope.bakdinle;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joousope.bakdinle.Common.Common;
import com.example.joousope.bakdinle.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.joousope.bakdinle.SongAdapter.*;

public class MainActivity extends AppCompatActivity{
    private ArrayList<SongInfo> songs=new ArrayList <SongInfo>();
    RecyclerView recyclerView;
    SeekBar seekBar;
    SharedPreferences mPrefs;
    static final String PREFS_NAME="PrefsFile";
    EditText editNewUser,editNewPassword,editNewEmail;// for Kayıt
    EditText editUser,editPassword;//for giriş
    CheckBox btnRemember;
    TextView forgotPassword;
    Button signUp;
    Button btnSignUp, btnSignIn;
    FirebaseDatabase database;
    DatabaseReference users;
    SongAdapter songAdapter;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPrefs=getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        //songAdapter=new SongAdapter(this,songs);
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.navigation);
        database=FirebaseDatabase.getInstance();
        users=database.getReference("Users");
        editUser=(EditText) findViewById(R.id.editUser);
        forgotPassword=(TextView)findViewById(R.id.forgotPassword);
        editPassword=(EditText) findViewById(R.id.editPassword);
        btnSignIn=(Button)findViewById(R.id.btn_sign_in);
        btnRemember=(CheckBox)findViewById(R.id.btnRememberMe);
        btnSignUp=(Button)findViewById(R.id.btn_sign_up);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUpDialog();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            { signIn(editUser.getText().toString(),editPassword.getText().toString());
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //  startActivity(new Intent(MainActivity.this,ResetPasswordActivity.class));
            }
        });
        getPreferencesData();


    }

    private void getPreferencesData() {
        SharedPreferences sp=getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        if(sp.contains("pref_name")){
            String u=sp.getString("pref_name","not found");
            editUser.setText(u.toString());
        }
        if(sp.contains("pref_pass")){
            String p=sp.getString("pref_pass","not found");
            editPassword.setText(p.toString());
        }
        if(sp.contains("pref_check")){
            Boolean b=sp.getBoolean("pref_check",false);
            btnRemember.setChecked(b);
        }
    }

    private void signIn(final String user, final String pwd) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(user).exists())
                {
                    if(!user.isEmpty())
                    {
                        User login=dataSnapshot.child(user).getValue(User.class);
                        if(login.getPassword().equals(pwd))
                        {
                            if(btnRemember.isChecked()){
                                Boolean boolIsChecked=btnRemember.isChecked();
                                SharedPreferences.Editor editor=mPrefs.edit();
                                editor.putString("pref_name",editUser.getText().toString());
                                editor.putString("pref_pass",editPassword.getText().toString());
                                editor.putBoolean("pref_check",boolIsChecked);
                                editor.apply();

                            }
                            openActivitiy();
                        }
                        else{Toast.makeText(MainActivity.this,"Wrong Password!",Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {

                        Toast.makeText(MainActivity.this,"Please enter your user name!",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {

                    Toast.makeText(MainActivity.this,"User is not exist!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void openActivitiy() {
        Intent intent=new Intent(MainActivity.this,Home.class);
        startActivity(intent);
        editUser.getText().clear();
        editPassword.getText().clear();
        finish();
    }
    private void showSignUpDialog() {
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainActivity.this,R.style.MyTheme);
                alertDialog.setTitle("Sign Up");
                alertDialog.setMessage("Please fill full information");

                LayoutInflater inflater=this.getLayoutInflater();
                View sign_up_layout=inflater.inflate(R.layout.activity_singup_theme,null,false);
                editNewUser=(EditText)sign_up_layout.findViewById(R.id.editNewUserName);
                editNewPassword=(EditText)sign_up_layout.findViewById(R.id.editNewPassword);
                editNewEmail=(EditText)sign_up_layout.findViewById(R.id.editNewEmail);
                signUp=(Button)sign_up_layout.findViewById(R.id.btn_sign_upp);
                alertDialog.setView(sign_up_layout);
                alertDialog.setIcon(R.mipmap.ic_cant_hear);
                signUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final User user=new User(editNewUser.getText().toString(),editNewPassword.getText().toString(),editNewEmail.getText().toString());
                        users.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.child(user.getUserName()).exists())
                                    Toast.makeText(MainActivity.this,"User already exists!",Toast.LENGTH_SHORT).show();
                                else {
                                    users.child(user.getUserName()).setValue(user);
                                    Toast.makeText(MainActivity.this,"User registration success!",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
                alertDialog.show();
            }

    }

