package com.example.kcra.myjibz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Database.JibzMoneyAccounts;
import Database.Transactionzz;

public class CardToCardPage extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_to_card_page);


        final Spinner fromCard = findViewById(R.id.spiner_acc_name_from);
        final Spinner toCard = findViewById(R.id.spiner_acc_name_to);
        TextView cardToCardAmnt = findViewById(R.id.TxtVu_ctoc_amount);

        final List<Transactionzz> cardToCard = Transactionzz.find(Transactionzz.class , "JIBZ_USR_NAME = ?",
                getIntent().getStringExtra("LgUsNmToTrnzEntry"));

        final ArrayList<String> accStringsFrom = new ArrayList<>();
        for(int i =0;i<cardToCard.size();i++){
            accStringsFrom.add(cardToCard.get(i).trnzAccName);
        }
        ArrayAdapter<String> adapterToSpnr = new ArrayAdapter<>(CardToCardPage.this,android.R.layout.simple_spinner_dropdown_item,accStringsFrom);
        adapterToSpnr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromCard.setAdapter(adapterToSpnr);
        final ArrayList<String> accStringsTo = new ArrayList<>();
        final ArrayAdapter<String> adapterFromSpnr = new ArrayAdapter<>(CardToCardPage.this,android.R.layout.simple_spinner_dropdown_item,accStringsTo);
        adapterToSpnr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toCard.setAdapter(adapterFromSpnr);


        fromCard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                accStringsTo.clear();
                for (int j=0 ; j<cardToCard.size() ; j++) {
                    if(!accStringsFrom.get(position).equals(cardToCard.get(j).trnzAccName)) {
                        accStringsTo.add(cardToCard.get(j).trnzAccName);
                    }
                }
                adapterFromSpnr.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        














    }
}
