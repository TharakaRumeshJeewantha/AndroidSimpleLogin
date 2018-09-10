package com.example.tharaka.simplelogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


/**
 * Created by tharaka on 30-Mar-18.
 */

public class summary extends AppCompatActivity {


    TextView User;
    TextView la1;
    TextView lo1;
    TextView la2;
    TextView lo2;
    TextView costs;

    TextView start_point;
    TextView end_point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);


        User = (TextView) findViewById(R.id.helloUser);
        Bundle extras = getIntent().getExtras();
        String new_user = null;

        if (extras != null) {
            new_user = extras.getString("username");
            User.setText(new_user);
        }


        start_point = (TextView) findViewById(R.id.tot);
        Bundle extras1 = getIntent().getExtras();
        String start_lati = null;
        String start_longi = null;


        if (extras1 != null) {
            start_lati = extras1.getString("latitude1");
            start_longi = extras1.getString("longitude1");
            start_point.setText("Start Point - ( " + start_lati + " , " + start_longi + " )");
        }

        end_point = (TextView) findViewById(R.id.dis);
        Bundle extras2 = getIntent().getExtras();
        String end_lati = null;
        String end_longi = null;

        if (extras2 != null) {
            end_lati = extras2.getString("latitude2");
            end_longi = extras2.getString("longitude2");
            end_point.setText("End Point   - ( " + end_lati + " , " + end_longi + " )");
        }


        costs = (TextView) findViewById(R.id.cal);
        Bundle extras3 = getIntent().getExtras();
        String cost_new1 = null;

        if (extras3 != null) {
            cost_new1 = extras3.getString("cost1");

           // double cost_new2 = Double.parseDouble(String.format("%.2f",cost_new1));
           // String cost_new3 = Double.toString(cost_new2);

            costs.setText("Amount LKR / Km - LKR " + cost_new1);
        }

        System.out.println(start_lati + " " + start_longi+ " / " +end_lati + " " + end_longi);

        double num1 = Double.parseDouble(start_lati.toString());
        double num2 = Double.parseDouble(start_longi.toString());
        double num3 = Double.parseDouble(cost_new1.toString());
        double num1_1 = Double.parseDouble(end_lati.toString());
        double num2_1 = Double.parseDouble(end_longi.toString());

        System.out.println(num1+" "+num2 +" "+num3+" "+num1_1+" "+num2_1);

        double val_n = Caldistance(num1,num2,num1_1,num2_1);

        double Total_cost = num3 * val_n ;
        double deci_cost = Double.parseDouble(String.format("%.2f",Total_cost));
        String deci_tot_cost = Double.toString(deci_cost);
        TextView total = (TextView) findViewById(R.id.deci_total);
        total.setText("LKR " + deci_tot_cost);

        double val = Double.parseDouble(String.format("%.2f",Caldistance(num1,num2,num1_1,num2_1)));
        String cal_dist = Double.toString(val);
        TextView t1 = (TextView) findViewById(R.id.calTot);
        t1.setText(cal_dist + " Km");

    }


    private double Caldistance(double userlati1, double userlongi1, double userlati2, double userlongi2) {
        double thetaval = userlongi1 - userlongi2;
        double distance = Math.sin(degreeToradius(userlati1)) * Math.sin(degreeToradius(userlati2)) + Math.cos(degreeToradius(userlati1)) * Math.cos(degreeToradius(userlati2)) * Math.cos(degreeToradius(thetaval));
        distance = Math.acos(distance);
        distance = radiusTodegree(distance);
        distance = distance * 60 * 1.1515;
        System.out.println("aa" + distance);
        return (distance);

    }

    private double degreeToradius (double degree) {
        return (degree * Math.PI / 180.0);
    }

    private double radiusTodegree (double radius) {
        return (radius * 180.0 / Math.PI);
    }


}
