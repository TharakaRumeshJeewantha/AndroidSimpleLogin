package com.example.tharaka.simplelogin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    Button regBtn;
    EditText editUsername;
    EditText editPassword;
    DatabaseHelper databaseHelper;
    private int counter = 5;
    private TextView info;

    String un;
    String pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        regBtn=(Button)findViewById(R.id.btnSignup);
        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        info = (TextView) findViewById(R.id.tvinfo) ;
        info.setText("No of Attempts Remaining : 5");

        databaseHelper = new DatabaseHelper(MainActivity.this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                boolean isExist = databaseHelper.checkUserExist(editUsername.getText().toString(),editPassword.getText().toString());
                initialize();

                if(!validate()) {
                    Toast.makeText(MainActivity.this,"please enter valid data to login here",Toast.LENGTH_SHORT).show();
                }

                else {
                    if (isExist) {
                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        intent.putExtra("username", editUsername.getText().toString());
                        startActivity(intent);
                    } else {
                        counter--;
                        info.setText("No of Attempts Remaining : " + String.valueOf(counter));
                        if (counter == 0) {
                            btnLogin.setEnabled(false);
                   /*     info.setTextColor(R.color.attempt); */
                        }
                        editPassword.setText(null);
                        Toast.makeText(MainActivity.this, "Login Failed, Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        }

        public void initialize() {
            un = editUsername.getText().toString().trim();
            pw = editPassword.getText().toString().trim();
        }

    public boolean validate(){
        boolean valid = true;

        if(un.isEmpty()) {
            editUsername.setError("please enter valid username");
            valid = false;
        }
        if(pw.isEmpty()) {
            editPassword.setError("please enter valid password");
            valid = false;
        }
        return valid;
    }

    public void directToReg(View view) {
        Intent startNewIntent=new Intent(this,RegisterActivity.class);
        startActivity(startNewIntent);
    }

}
