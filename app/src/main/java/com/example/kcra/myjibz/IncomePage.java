package com.example.kcra.myjibz;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Database.JibzMoneyAccounts;
import Model.IncomeCount;

/**
 * Created by KcRa on 5/16/2018 AD.
 */

public class IncomePage extends AppCompatActivity {

    List<JibzMoneyAccounts> myRecords;
    LayoutInflater lIF;
    IncomeAdapter IncAdap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        lIF = getLayoutInflater(); // متغیر تعریف شده در لی‌آوت‌این‌فلتر را مقدار دهی‌کن
        //View cardView = lIF.inflate(R.layout.card_incomes,null);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_page);

        myRecords = JibzMoneyAccounts.find(JibzMoneyAccounts.class , "JIBZ_USR_NAME = ?",
                    getIntent().getStringExtra("LgUsNm"));

        ListView lV = findViewById(R.id.list_income); // آبجکت لیست‌ویو بساز و به لیست اینکام نسبت بده
        IncAdap = new IncomeAdapter();
        lV.setAdapter(IncAdap); // یک آبجکت تازه از کلاس آداپتور بساز و آن را ست کن برای آبجکت لیست‌ویو

        ImageView bakToHome = findViewById(R.id.bak_to_home);
        bakToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //ImageView delRecord = cardView.findViewById(R.id.del_record);



    }


    class IncomeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return myRecords.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View cardView, ViewGroup parent) {
            ViewHolder vh = new ViewHolder();
            if (cardView == null) {
                cardView = lIF.inflate(R.layout.card_incomes,null);
                vh.Acc = cardView.findViewById(R.id.TxtVu_JibzAccName);
                vh.Bal = cardView.findViewById(R.id.TxtVu_JibzBlnce);
                vh.Del = cardView.findViewById(R.id.del_record);
                vh.Edt = cardView.findViewById(R.id.edit_record);
                cardView.setTag(vh);
            }

            vh = (ViewHolder) cardView.getTag();


            vh.Acc.setText(myRecords.get(position).jibzAccName);
            vh.Bal.setText(String.valueOf(myRecords.get(position).jibzBalance));
            vh.Del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<JibzMoneyAccounts> AccZ = JibzMoneyAccounts.find(JibzMoneyAccounts.class,"JIBZ_USR_NAME=? AND JIBZ_ACC_NAME=?",
                            (myRecords.get(position).jibzUsrName),(myRecords.get(position).jibzAccName));
                    AccZ.get(0).delete();
                    myRecords = JibzMoneyAccounts.find(JibzMoneyAccounts.class , "JIBZ_USR_NAME = ?",
                            getIntent().getStringExtra("LgUsNm"));
                    IncAdap.notifyDataSetChanged();
                }
            });
            vh.Edt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog acntEditDialog = new Dialog(IncomePage.this);
                    acntEditDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    acntEditDialog.setContentView(R.layout.dialog_edit_account);

                    final EditText editAccJibzName = acntEditDialog.findViewById(R.id.account_name);
                    final EditText editAccJibzBlnce = acntEditDialog.findViewById(R.id.balance);

                    editAccJibzName.setText(myRecords.get(position).jibzAccName);
                    editAccJibzBlnce.setText(String.valueOf(myRecords.get(position).jibzBalance));

                    final TextView editAccData = acntEditDialog.findViewById(R.id.edit_acnt_data);
                    editAccData.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            List<JibzMoneyAccounts> editAccZ = JibzMoneyAccounts.find(JibzMoneyAccounts.class,"JIBZ_USR_NAME=? AND JIBZ_ACC_NAME=?",
                                    (myRecords.get(position).jibzUsrName),(myRecords.get(position).jibzAccName));
                            editAccZ.get(0).jibzAccName = String.valueOf(editAccJibzName.getText());
                            editAccZ.get(0).jibzBalance = Integer.parseInt(String.valueOf(editAccJibzBlnce.getText()));
                            editAccZ.get(0).save();
                            acntEditDialog.dismiss();
                            myRecords = JibzMoneyAccounts.find(JibzMoneyAccounts.class , "JIBZ_USR_NAME = ?",
                                    getIntent().getStringExtra("LgUsNm"));
                            IncAdap.notifyDataSetChanged();
                        }
                    });
                    acntEditDialog.show();
                }
            });

            return cardView;
        }

        class ViewHolder {
            TextView Acc;
            TextView Bal;
            ImageView Del;
            ImageView Edt;

        }
    }



}
