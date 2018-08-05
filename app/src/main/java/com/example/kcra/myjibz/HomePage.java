package com.example.kcra.myjibz;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Database.JibzMoneyAccounts;

/**
 * Created by kasraebrahimi on 4/28/2018 AD.
 */

public class HomePage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        final TextView titleUsrName = findViewById(R.id.title_usr_name);
        titleUsrName.setText(getIntent().getStringExtra("loggedUsrName"));


        ImageButton btmDrwr = findViewById(R.id.button_drawer); // آبجکت دکمه بساز به کمک فایندویو (برای باز و بسته کردن دراور)
        final DrawerLayout drwr = findViewById(R.id.drawer); // آبجکت دراور بساز
        final LinearLayout lnr = findViewById(R.id.linear_drawer); // آبجکت کشو بساز

        btmDrwr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // با کلیک بر دگمه‌ی سه‌خط مربوطه :
                if (drwr.isDrawerOpen(lnr)) { // اگر کشوی ال‌ان‌آر از دراور باز باشد، آن‌گاه:
                    drwr.closeDrawer(lnr); // آن را ببند
                }
                else { // و اگر نه، آن‌گاه:
                    drwr.openDrawer(lnr); // آن را باز کن
                }
            }
        });

        TextView toIncome = findViewById(R.id.go_to_income);
        toIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,IncomePage.class);
                intent.putExtra("LgUsNm",getIntent().getStringExtra("loggedUsrName"));
                startActivity(intent);
            }
        });

        TextView toTransaction = findViewById(R.id.btn_to_transaction);
        toTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,TransactionPage.class);
                intent.putExtra("LgUsNm",getIntent().getStringExtra("loggedUsrName"));
                startActivity(intent);
            }
        });


        TextView newAccount = findViewById(R.id.btn_new_record);
        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog acntMakeDialog = new Dialog(HomePage.this);
                acntMakeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                acntMakeDialog.setContentView(R.layout.dialog_enter_new_account);

                final EditText acntJibzName = acntMakeDialog.findViewById(R.id.account_name);
                final EditText acntJibzBlnce = acntMakeDialog.findViewById(R.id.balance);

                TextView entAcntData = acntMakeDialog.findViewById(R.id.ent_acnt_data);
                entAcntData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        List <JibzMoneyAccounts> jibzAccZ = JibzMoneyAccounts.find(JibzMoneyAccounts.class,"JIBZ_ACC_NAME=?",
                                (String.valueOf(acntJibzName.getText())));

                        if (jibzAccZ.isEmpty()) {

                            JibzMoneyAccounts newJib = new JibzMoneyAccounts();
                            newJib.jibzAccName = String.valueOf(acntJibzName.getText());
                            newJib.jibzBalance = Integer.parseInt(String.valueOf(acntJibzBlnce.getText()));
                            newJib.jibzUsrName = getIntent().getStringExtra("loggedUsrName");
                            newJib.save();
                            Toast.makeText(HomePage.this,"داده‌ها درج شدند",Toast.LENGTH_LONG).show();
                            acntMakeDialog.dismiss();
                        }

                        else {
                            Toast.makeText(HomePage.this,"برای این حساب نام دیگری انتخاب کنید",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                acntMakeDialog.show();
            }
        });

        TextView cardToCard = findViewById(R.id.card_to_card);
        cardToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this,CardToCardPage.class);
                startActivity(intent);
            }
        });



        RelativeLayout logOut = findViewById(R.id.log_out);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,LoginPage.class);
                startActivity(intent);
                intent.putExtra("LgUsNm",getIntent().getStringExtra("loggedUsrName"));
                finish();
            }
        });

        RelativeLayout exitOut = findViewById(R.id.exit_out);
        exitOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
