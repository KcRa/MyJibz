package com.example.kcra.myjibz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Database.JibzMoneyAccounts;
import Database.Transactionzz;

public class TransactionEntryPage extends AppCompatActivity {

    List<JibzMoneyAccounts> myRecordsAtTrnzEnt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_entry_page);

        myRecordsAtTrnzEnt = JibzMoneyAccounts.find(JibzMoneyAccounts.class , "JIBZ_USR_NAME = ?",
                getIntent().getStringExtra("LgUsNmToTrnzEntry"));

        ImageView closeTrnzEntry = findViewById(R.id.close_trnzEntry);
        closeTrnzEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView trnzLogdUsrName = findViewById(R.id.logd_usr_name);
        final Spinner trnzAccSpnr = findViewById(R.id.spiner_acc_name);
        final EditText trnzSourceTxVu = findViewById(R.id.TxtVu_src_name);
        final EditText trnzDateTxVu = findViewById(R.id.TxtVu_date);
        final EditText trnzDescribTxVu = findViewById(R.id.TxtVu_describe);
        final EditText trnzAmountTxVu = findViewById(R.id.TxtVu_amount);
        final SwitchCompat trnzImpOutSwch = findViewById(R.id.switch_impuot);

        trnzImpOutSwch.setChecked(true);

        trnzLogdUsrName.setText(getIntent().getStringExtra("LgUsNmToTrnzEntry"));

        final ArrayList<String> trnzStrings = new ArrayList<>();
        for(int i =0;i<myRecordsAtTrnzEnt.size();i++){
            trnzStrings.add(myRecordsAtTrnzEnt.get(i).jibzAccName);
        }
        ArrayAdapter<String> adapterToSpnr = new ArrayAdapter<>(TransactionEntryPage.this,android.R.layout.simple_spinner_dropdown_item,trnzStrings);
        adapterToSpnr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trnzAccSpnr.setAdapter(adapterToSpnr);


        TextView entTrnzData = findViewById(R.id.ent_trnz_data);
        entTrnzData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Transactionzz newTrnz = new Transactionzz();
                List<Transactionzz> trnzIdNo = Transactionzz.listAll(Transactionzz.class);
                if (trnzIdNo.isEmpty()) {
                    newTrnz.trnzID = 10001;
                }
                else {
                    newTrnz.trnzID = trnzIdNo.get(trnzIdNo.size()-1).trnzID+1;
                }
                newTrnz.trnzUsrName = String.valueOf(getIntent().getStringExtra("LgUsNmToTrnzEntry"));
                newTrnz.trnzSource = String.valueOf(trnzSourceTxVu.getText());
                newTrnz.trnzDate = String.valueOf(trnzDateTxVu.getText());
                newTrnz.trnzDescribe = String.valueOf(trnzDescribTxVu.getText());
                newTrnz.trnzAmount = Integer.parseInt(String.valueOf(trnzAmountTxVu.getText()));
                newTrnz.trnzAccName = trnzStrings.get(trnzAccSpnr.getSelectedItemPosition());

                List<JibzMoneyAccounts> jbz = JibzMoneyAccounts.find(JibzMoneyAccounts.class,"JIBZ_USR_NAME=? AND JIBZ_ACC_NAME=?",
                        getIntent().getStringExtra("LgUsNmToTrnzEntry"),trnzStrings.get(trnzAccSpnr.getSelectedItemPosition()));
                if (trnzImpOutSwch.isChecked()) {
                    newTrnz.trnzType = 1;
                    jbz.get(0).jibzBalance = (jbz.get(0).jibzBalance)+newTrnz.trnzAmount;
                }
                else {
                    newTrnz.trnzType = 2;
                    jbz.get(0).jibzBalance = (jbz.get(0).jibzBalance)-newTrnz.trnzAmount;
                }

                jbz.get(0).save();
                newTrnz.save();
                finish();
            }
        });
    }
}
