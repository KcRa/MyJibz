package com.example.kcra.myjibz;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Database.JibzMoneyAccounts;
import Database.Transactionzz;

import static java.lang.String.valueOf;

public class TransactionPage extends AppCompatActivity {

    List<Transactionzz> myTransactionZ;
    LayoutInflater lIF;
    TransactionAdapter trnzAdptr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        lIF = getLayoutInflater();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_page);

        myTransactionZ = Transactionzz.find(Transactionzz.class,"TRNZ_USR_NAME=?",getIntent().getStringExtra("LgUsNm"));

        ListView lV = findViewById(R.id.list_transaction);
        trnzAdptr = new TransactionAdapter();
        lV.setAdapter(trnzAdptr);

        ImageView bakToHome = findViewById(R.id.bak_to_home);
        bakToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final FloatingActionButton floatButAddTrnz = findViewById(R.id.butn_add_trnz);
        floatButAddTrnz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<JibzMoneyAccounts> AccZ = JibzMoneyAccounts.find(JibzMoneyAccounts.class,"JIBZ_USR_NAME=?",
                        getIntent().getStringExtra("LgUsNm"));
                ArrayList<String> isInsertYet = new ArrayList<>();
                for (int i = 0 ; i<AccZ.size() ; i++) {
                    isInsertYet.add(AccZ.get(i).jibzAccName);
                }
                if (isInsertYet.isEmpty()) {
                    Toast.makeText(TransactionPage.this,"دست‌گم یک حساب بسازید",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(TransactionPage.this,TransactionEntryPage.class);
                    intent.putExtra("LgUsNmToTrnzEntry",getIntent().getStringExtra("LgUsNm"));
                    startActivity(intent);

                }
            }
        });


    }

    class TransactionAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return myTransactionZ.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @TargetApi(Build.VERSION_CODES.O)
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public View getView(final int position, View CardVU, ViewGroup parent) {

            VuHldr vh = new VuHldr();
            if (CardVU==null) {
                CardVU = lIF.inflate(R.layout.card_transaction,null);
                vh.trnzDateTxVu = CardVU.findViewById(R.id.TxtVu_TrnzDate);
                vh.trnzAmountTxVu = CardVU.findViewById(R.id.TxtVu_TrnzAmnt);
                vh.trnzAccNameTxVu = CardVU.findViewById(R.id.TxtVu_TrnzAccName);
                vh.trnzSourceTxVu = CardVU.findViewById(R.id.TxtVu_TrnzSource);
                vh.trnzDescribeTxVu = CardVU.findViewById(R.id.TxtVu_TrnzDescribe);
                vh.trnzDel = CardVU.findViewById(R.id.del_record);
                vh.trnzEdt = CardVU.findViewById(R.id.edit_record);
                vh.CardVuColor = CardVU.findViewById(R.id.card);
                CardVU.setTag(vh);
            }
            else {
                vh = (VuHldr) CardVU.getTag();
            }

            vh.trnzDateTxVu.setText(myTransactionZ.get(position).trnzDate);
            vh.trnzAmountTxVu.setText(String.valueOf(myTransactionZ.get(position).trnzAmount));
            vh.trnzAccNameTxVu.setText(myTransactionZ.get(position).trnzAccName);
            vh.trnzSourceTxVu.setText(myTransactionZ.get(position).trnzSource);
            vh.trnzDescribeTxVu.setText(myTransactionZ.get(position).trnzDescribe);

            switch (myTransactionZ.get(position).trnzType) {
                case 1 : vh.CardVuColor.setBackgroundColor(Color.parseColor("#145465"));break;
                case 2 : vh.CardVuColor.setBackgroundColor(Color.parseColor("#286475"));break;
                case 3 : vh.CardVuColor.setBackgroundColor(Color.parseColor("#345465"));break;
            }

            vh.trnzDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<Transactionzz> trnzDel = Transactionzz.find(Transactionzz.class,"TRNZ_USR_NAME=?", String.valueOf(myTransactionZ.get(position).trnzUsrName));
                    trnzDel.get(0).delete();
                    myTransactionZ = Transactionzz.find(Transactionzz.class,"TRNZ_USR_NAME=?",getIntent().getStringExtra("LgUsNm"));
                    trnzAdptr.notifyDataSetChanged();
                }
            });

//            vh.trnzEdt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(TransactionPage.this,TransactionEntryPage.class);
//                    intent.putExtra("LgUsNmToTrnzEntry",getIntent().getStringExtra("LgUsNm"));
//                    startActivity(intent);
////                    myTransactionZ = Transactionzz.find(Transactionzz.class,"TRNZ_USR_NAME=?",getIntent().getStringExtra("LgUsNm"));
////                    trnzAdptr.notifyDataSetChanged();
//                }
//            });

            return CardVU;
        }

        class VuHldr {
            TextView trnzDateTxVu;
            TextView trnzAmountTxVu;
            TextView trnzAccNameTxVu;
            TextView trnzSourceTxVu;
            TextView trnzDescribeTxVu;
            CardView CardVuColor;
            ImageView trnzDel;
            ImageView trnzEdt;

        }
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        myTransactionZ = Transactionzz.find(Transactionzz.class,"TRNZ_USR_NAME=?",
                getIntent().getStringExtra("LgUsNm"));
        trnzAdptr.notifyDataSetChanged();
    }
}
