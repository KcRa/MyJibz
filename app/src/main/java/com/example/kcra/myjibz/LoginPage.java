package com.example.kcra.myjibz;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import Database.UsrReg;

/**
 * Created by KcRa on 4/11/2018 AD.
 */

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page); //محتوای آبجکت  را ست کن با ایکس.ام.ال صفحه‌ی لاگین

        final Typeface yekan = Typeface.createFromAsset(getAssets(),"font/byekan.ttf"); // تایپ‌فیس درست کن با فونت فارسی یکان (از فولدر استز پیداکن) (

        final EditText usrNme = findViewById(R.id.username); // متغیر ادیت‌تکست تعریف کن به اسم یوزرنیم و مقدار بده به کمک فایند‌ویوبای‌آی‌دی
        usrNme.setTypeface(yekan); // به ادیت‌تکست یوزرنیم تایپ‌فیس یکان را نسبت بده

        final EditText pasWrd = findViewById(R.id.password); // متغیر ادیت‌تکست تعریف کن به اسم پس‌ورد و مقدار بده به کمک فایند‌ویوبای‌آی‌دی
        pasWrd.setTypeface(yekan); // به ادیت‌تکست پس‌ورد تایپ‌فیس یکان را نسبت بده


        Button reg = findViewById(R.id.register); //دکمه‌ی تعریف کن برای کلید رجیستری
        reg.setTypeface(yekan);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // که با کلیک بر آن:
                Intent intent = new Intent(LoginPage.this,RegisteryPage.class); // اینتنت بساز از این صفحه به صفحه‌ی نام‌نویسی
                startActivity(intent); //این اکتیویتی را از مسیر اینتنت استارت کن
            }
        });

        final Button ent = findViewById(R.id.login); // دکمه تعریف کن برای ورود و مقدار بده
        ent.setTypeface(yekan); // فونت کان را نسبت بده به دکمه
        ent.setOnClickListener(new View.OnClickListener() { // که با کلیک بر آن:
            @Override
            public void onClick(View view) {

                 List <UsrReg> usrZ = UsrReg.find(UsrReg.class,"USR_NAME=? AND PASS_WORD=?",
                         (String.valueOf(usrNme.getText())),(String.valueOf(pasWrd.getText())));

                 if (!usrZ.isEmpty()) {
                     Intent intent = new Intent (LoginPage.this,HomePage.class);
                     intent.putExtra("loggedUsrName",usrZ.get(0).usrName); // یوزرنیم خونه‌ی اول لیست یوزرز رو بگیر و به اسم تایتل سوار اینتنت کن
                     startActivity(intent);
                     finish();
                 }

                 else {
                     Toast.makeText(LoginPage.this,"نام کاربری یا شناسه‌ی کاربری نادرست است",Toast.LENGTH_LONG).show();
                 }
            }
        });
    }
}
