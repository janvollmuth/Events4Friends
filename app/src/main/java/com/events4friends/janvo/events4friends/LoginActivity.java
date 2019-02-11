package com.events4friends.janvo.events4friends;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.events4friends.janvo.events4friends.Utils.FireDBHelper;
import com.events4friends.janvo.events4friends.Utils.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    //Variables
    private User user;
    private User databaseUser;

    //Firebase
    private FireDBHelper fireDBHelper;
    private DatabaseReference myRef = FireDBHelper.getDatabaseReference();

    // UI references.
    private EditText mUserName;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Init firebase
        fireDBHelper = new FireDBHelper();

        // Set up the login form.
        mUserName = findViewById(R.id.txtUserName);
        mPasswordView = findViewById(R.id.txtPassword);

        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        Button mEmailRegister = findViewById(R.id.email_register_button);

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = mUserName.getText().toString();
                String password = mPasswordView.getText().toString();

                //Set up user
                user = new User(userName, password);
                myRef.child("Userlist").child(user.username).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        databaseUser = dataSnapshot.getValue(User.class);
                        if(databaseUser != null){
                            if(user.username.equals(databaseUser.username) && user.password.equals(databaseUser.password)) {
                                Intent intent = new Intent(LoginActivity.this, ListActivity.class);
                                startActivity(intent);
                                Toast.makeText(LoginActivity.this, "Du hast dich erfolgreich angemeldet", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Benutzname oder Passwort falsch.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

        mEmailRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = mUserName.getText().toString();
                String password = mPasswordView.getText().toString();

                user = new User(userName, password);
                myRef.child("Userlist").child(user.username).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        databaseUser = dataSnapshot.getValue(User.class);
                        if(databaseUser == null){
                            fireDBHelper.addUserToDatabase(user);
                            Intent intent = new Intent(LoginActivity.this, ListActivity.class);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "Sie wurden erfolgreich registriert und angemeldet.", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Benutzername schon vergeben.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            }
        });
    }
}

