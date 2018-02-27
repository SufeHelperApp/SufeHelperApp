package com.example.sufehelperapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class My_RegisterThirdActivity_Interal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_register_third_interal);

        /*Spinner spinner1 = (Spinner) findViewById(R.id.spinner_address1);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner_address2);
        final List<String> datas1 = new ArrayList<>();
        datas1.add("武川校区");
        datas1.add("武东校区");
        datas1.add("国定校区");
        final List<String> datas2 = new ArrayList<>();
        datas2.add("静思园17号楼");
        datas2.add("静思园16号楼");
        datas2.add("静思园15号楼");
        datas2.add("静思园14号楼");
        datas2.add("静思园12号楼");
        datas2.add("宁远楼");

        AddressAdapter adapter1 = new AddressAdapter(this);
        AddressAdapter adapter2 = new AddressAdapter(this);
        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);

        adapter1.setDatas(datas1);
        adapter2.setDatas(datas2);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/
    }
}
