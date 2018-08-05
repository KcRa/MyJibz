package com.example.kcra.myjibz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import Database.JibzMoneyAccounts;
import Database.Transactionzz;
import Database.UsrReg;

public class Splash extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // تایتل این صفحه‌ را بردار
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); // این صفحه را فول‌اسکرین کن
        setContentView(R.layout.activity_splash);

        SharedPreferences myJibzPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE); // متغیر تعریف کن از جنس شردپریفرنسز به اسم مای‌جیبزپرفز و مقداربده

        if (myJibzPrefs.getBoolean("isFirst",true)) { // با آبجکت مای‌جیبزپرفز یک سوییچ روشن درست می‌کنیم به اسم ایزفرست و آن‌گاه:
            Toast.makeText(Splash.this,"خوش آمدید",Toast.LENGTH_LONG).show(); // جمله‌ی خوش آمدید را تُست کن
            UsrReg u = new UsrReg();
            JibzMoneyAccounts j = new JibzMoneyAccounts();
            Transactionzz t = new Transactionzz();
            SharedPreferences.Editor myJibzEdit = getSharedPreferences("MyPrefs",Context.MODE_PRIVATE).edit(); // متغیر تعریف کن از همان کلاس قبلی با ادیتور که قایل ادیت‌کردن باشد
            myJibzEdit.putBoolean("isFirst",false); // همان سوییچ ایز فرست را خاموش کن
            myJibzEdit.apply(); // آبجکت دوم را اعمال کن

        }



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent (Splash.this,LoginPage.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }

}