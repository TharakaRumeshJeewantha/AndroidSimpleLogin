package com.example.tharaka.simplelogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main2Activity extends AppCompatActivity {

    TextView helloUser;

    EditText las1;
    EditText las2;
    EditText los1;
    EditText los2;
    EditText cos;

    String latitudes1;
    String latitudes2;
    String longitudes1;
    String longitudes2;
    String costperkms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        helloUser = (TextView) findViewById(R.id.helloUser);

        las1 = (EditText) findViewById(R.id.lati);
        las2 = (EditText) findViewById(R.id.lati1);
        los1 = (EditText) findViewById(R.id.longi);
        los2 = (EditText) findViewById(R.id.longi1);
        cos  = (EditText) findViewById(R.id.cost);

        Bundle extras = getIntent().getExtras();
        String username = null;

        if (extras != null) {
            username = extras.getString("username");
            helloUser.setText("Welcome " + username);
        }
    }

    public void onBtnClick(View v) {

         EditText e_la1 = (EditText) findViewById(R.id.lati);
         EditText e_la2 = (EditText) findViewById(R.id.longi);
         EditText e_lo1 = (EditText) findViewById(R.id.lati1);
         EditText e_lo2 = (EditText) findViewById(R.id.longi1);
         EditText e_cost = (EditText) findViewById(R.id.cost);

        initialize();

        if(!validate()) {

            Toast.makeText(Main2Activity.this,"please enter valid data to calculation",Toast.LENGTH_SHORT).show();

        }
        else {

         //  if(checkNo(e_la1.getText().toString()) || checkNo(e_la2.getText().toString()) || checkNo(e_lo1.getText().toString()) || checkNo(e_lo2.getText().toString()) || checkNo(e_cost.getText().toString())) {

                TextView un = (TextView) findViewById(R.id.helloUser);
                EditText l1 = (EditText) findViewById(R.id.lati);
                EditText l2 = (EditText) findViewById(R.id.longi);
                EditText c1 = (EditText) findViewById(R.id.cost);

                EditText n_l1 = (EditText) findViewById(R.id.lati1);
                EditText n_l2 = (EditText) findViewById(R.id.longi1);

                Intent intent = new Intent(Main2Activity.this, summary.class);
                intent.putExtra("username", helloUser.getText().toString());
                intent.putExtra("latitude1", l1.getText().toString());
                intent.putExtra("longitude1", l2.getText().toString());
                intent.putExtra("latitude2", n_l1.getText().toString());
                intent.putExtra("longitude2", n_l2.getText().toString());
                intent.putExtra("cost1", c1.getText().toString());
                startActivity(intent);
            }
          //  else {

          //      Toast.makeText(Main2Activity.this,"please enter only Numbers",Toast.LENGTH_SHORT).show();

          //  }
  //      }
   }


    public Boolean checkNo(String sn) {
        Boolean check = false;
        String no = "\\d*\\.?\\d+";
        CharSequence inputStr = sn;

        Pattern pte = Pattern.compile(no,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pte.matcher(inputStr);
        if (matcher.matches()) {
            check = true;
        }
        return check;
    }



    public void initialize() {
        latitudes1 = las1.getText().toString().trim();
        latitudes2 = las2.getText().toString().trim();
        longitudes1 = los1.getText().toString().trim();
        longitudes2 = los2.getText().toString().trim();
        costperkms = cos.getText().toString().trim();
    }

    public boolean validate(){
        boolean valid = true;
        if(latitudes1.isEmpty()) {
            las1.setError("please enter valid start point latitude");
            valid = false;
        }
        if(latitudes2.isEmpty()) {
            las2.setError("please enter valid end point latitude");
            valid = false;
        }
        if(longitudes1.isEmpty()) {
            los1.setError("please enter valid start point longitude");
            valid = false;
        }
        if(longitudes2.isEmpty()) {
            los2.setError("please enter valid end point longitude");
            valid = false;
        }
        if(costperkms.isEmpty()) {
            cos.setError("please enter valid price");
            valid = false;
        }
        return valid;
    }


}
