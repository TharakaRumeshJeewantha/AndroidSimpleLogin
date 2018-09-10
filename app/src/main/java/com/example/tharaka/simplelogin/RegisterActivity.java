package com.example.tharaka.simplelogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_name, et_username, et_password, et_cpassword;
    private String name, username, password, cpassword;
    Button regBtn;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_name = (EditText) findViewById(R.id.name);
        et_username = (EditText) findViewById(R.id.username);
        et_password = (EditText) findViewById(R.id.password);
        et_cpassword = (EditText) findViewById(R.id.cpPassword);
        regBtn = (Button) findViewById(R.id.regbtn);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    register();
            }
        });
        databaseHelper = new DatabaseHelper(RegisterActivity.this);
    }

    public void register() {
        intialize(); // initilizing the input variables
        if (!validate()) {
            Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();

        } else {
             Toast.makeText(this,"Registration Success!!!,Please Login with User Credential",Toast.LENGTH_SHORT).show();
            onSignupProcess();

        }
    }

    private void onSignupProcess() {
        boolean isUserExist = databaseHelper.searchUser(et_username.getText().toString());
        if (!isUserExist) {
            Toast.makeText(this, "Username already taken,try a different username!!!", Toast.LENGTH_SHORT).show();
            et_password.setText("");
            et_cpassword.setText("");
        } else {
            databaseHelper.insertNewUser(et_name.getText().toString(), et_username.getText().toString(), et_password.getText().toString());
            clear();
        }
    }

    public boolean validate() {
        boolean valid = true;
        if (name.isEmpty() || name.length() > 32) {
            et_name.setError("Please Enter a valid Name");
            valid = false;
        }

        if (username.isEmpty() || username.length() > 32) {
            et_username.setError("Please Enter a valid Username");
            valid = false;
        }

        if (password.isEmpty() || password.length() < 8) {
            et_password.setError("Please Enter a valid Password with 8 or more characters");
            valid = false;
        }
        if (cpassword.isEmpty() || !cpassword.equals(password)) {
            et_cpassword.setError("Password not matched ");
            valid = false;
        }
        return valid;
    }

    public void intialize() {

        name = et_name.getText().toString().trim();
        username = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();
        cpassword = et_cpassword.getText().toString().trim();
    }

    public void directToLogin(View view) {
        Intent startNewIntent = new Intent(this, MainActivity.class);
        startActivity(startNewIntent);
    }

    public void clear() {
        et_name.setText("");
        et_username.setText("");
        et_password.setText("");
        et_cpassword.setText("");
    }
}
