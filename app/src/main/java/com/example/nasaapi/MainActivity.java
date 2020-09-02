package com.example.nasaapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    private TextView textView;
    private Button btnDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDate=(Button)findViewById(R.id.button);
        textView=(TextView) findViewById(R.id.textView);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker=new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");

            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
       // String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        String datLeftFormatted=String.format(Locale.getDefault(),
              "%d-%02d-%02d",year,month+1,dayOfMonth);
        textView.setText(datLeftFormatted);
       Intent intent=new Intent(MainActivity.this,ApodDisplay.class);
        intent.putExtra("date",datLeftFormatted);

       //finish();
       startActivity(intent);


    }
}