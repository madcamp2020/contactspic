package com.example.madcamp2020;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Fragment3 extends Fragment {
    private ArrayList<Contacts> list = ContactsList.getInstance();
    private Spinner spinner;
    private ArrayList<String> namelist;
    private ArrayAdapter<String> arrayAdapter;
    private int idx;
    public Fragment3() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_3, container, false);
        spinner = v.findViewById(R.id.spinner2);
        namelist = new ArrayList<>();
        int count = 0;
        while (list.size()>count) {
            Contacts item = list.get(count);
            namelist.add("("+ item.nickname+") " +item.name);
            count++;
        }

        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, namelist);

        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), namelist.get(position)+" was chosen", Toast.LENGTH_SHORT).show();
                idx = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ImageView imageView = v.findViewById(R.id.addimage);
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity().getApplicationContext(), DrawActivity.class);
                startActivity(intent);
            }
        });

        TextView send = (TextView) v.findViewById(R.id.sender);
        EditText textSMS = (EditText) v.findViewById(R.id.message);
        send.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String phoneNo = list.get(idx).phNumbers;
                String sms = textSMS.getText().toString();

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                    Toast.makeText(getActivity().getApplicationContext(), "전송 완료!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "SMS failed, please try again later!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        return v;
    }
}