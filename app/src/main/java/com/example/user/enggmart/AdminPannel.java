package com.example.user.enggmart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdminPannel extends AppCompatActivity {
     Button admin_login;
    EditText input_email;
    EditText input_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pannel);

        input_email = (EditText) findViewById(R.id.Name);
        input_password = (EditText) findViewById(R.id.Password);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setTitle("Only for Admin");
        admin_login=(Button)findViewById(R.id.button);
        admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(input_email.getText().toString(),input_password.getText().toString());
            }
        });


    }


    private void validate(String userName, String userPassword){
        if((userName.equals("Arjit")) && (userPassword.equals("1234"))){
            Intent intent = new Intent(AdminPannel.this, AdminConsole.class);
            startActivity(intent);

        }else{

            admin_login.setEnabled(false);
        }
    }
}
