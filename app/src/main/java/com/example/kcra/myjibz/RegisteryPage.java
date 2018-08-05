package com.example.kcra.myjibz;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.LinkedHashSet;
import java.util.List;

import Database.UsrReg;

/**
 * Created by KcRa on 4/20/2018 AD.
 */

public class RegisteryPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registery_page); //محتوای آبجکت  را ست کن با ایکس.ام.ال صفحه‌ی نام نویسی

        ImageView bakToLog = findViewById(R.id.bak_to_login);
        bakToLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final EditText usrName = findViewById(R.id.name);
        final EditText usrFamilyName = findViewById(R.id.family_name);
        final EditText phoneNumb = findViewById(R.id.phone_number);
        final EditText eMail = findViewById(R.id.email);
        final EditText passWord = findViewById(R.id.password);
        final EditText rePassWord = findViewById(R.id.re_password);

        final Button entData = findViewById(R.id.entData);
        entData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                List<UsrReg> usrZ = UsrReg.find(UsrReg.class, "USR_NAME=?", String.valueOf(usrName.getText()));

                if (usrZ.isEmpty()) {
                    UsrReg usr = new UsrReg();
                    usr.usrName = String.valueOf(usrName.getText());
                    usr.usrFamilyName = String.valueOf(usrFamilyName.getText());
                    usr.phoneNumb = String.valueOf(phoneNumb.getText());
                    usr.eMail = String.valueOf(eMail.getText());
                    usr.passWord = String.valueOf(passWord.getText());
                    usr.save();
                    Toast.makeText(RegisteryPage.this, "داده‌ها درج شدند", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 3000);
                } else {
                    Toast.makeText(RegisteryPage.this, "نام کاربری دیگری امتخاب کنید", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}


