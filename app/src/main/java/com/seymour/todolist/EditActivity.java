package com.seymour.todolist;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.seymour.todolist.DB.DBAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText editTitle,editContent;
    private TextView textDate;
    private String extraId,extraTitle,extraContent,extraDate;
    private boolean isNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initView();
        initButton();
        initData();
    }


    private void initView(){
        editTitle = (EditText)findViewById(R.id.edit_title);
        editContent = (EditText)findViewById(R.id.edit_content);
        textDate = (TextView)findViewById(R.id.text_date);
    }

    private void initButton(){
        findViewById(R.id.button_datepicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        findViewById(R.id.button_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textDate.getText().toString().isEmpty())
                    setToday();
                    saveTask(editTitle.getText().toString(), editContent.getText().toString(), textDate.getText().toString());
            }
        });
        findViewById(R.id.button_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNew==false)
                    rewritePreviousData();

                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void rewritePreviousData() {
        saveTask(extraTitle,extraContent,extraDate);
    }

    private void setToday() {
        final DateFormat df = new SimpleDateFormat("yyyyMMdd");
        final Date today = new Date(System.currentTimeMillis());
        textDate.setText(df.format(today));
    }

    private void initData(){
        Intent intent =getIntent();

        if(intent.getStringExtra("extra_type").equals("new")){
            isNew = true;
        }else if(intent.getStringExtra("extra_type").equals("rewrite")){
            isNew = false;
        }
        extraId = String.valueOf(intent.getStringExtra("extra_id"));
        extraTitle = String.valueOf(intent.getStringExtra("extra_title"));
        extraContent = String.valueOf(intent.getStringExtra("extra_content"));
        extraDate = String.valueOf(intent.getStringExtra("extra_date"));

        if(isNew==false) {
            editTitle.setText(extraTitle);
            editContent.setText(extraContent);
            textDate.setText(extraDate);
        }else if(isNew==true) {
            setToday();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (monthOfYear <= 8 && dayOfMonth <= 8) {
            textDate.setText( String.valueOf(year)+"0"+String.valueOf(monthOfYear + 1)+"0"+String.valueOf(dayOfMonth) );
        } else if (monthOfYear <= 8) {
            textDate.setText( String.valueOf(year)+"0"+String.valueOf(monthOfYear + 1)+String.valueOf(dayOfMonth) );
        } else if (dayOfMonth <= 8)
            textDate.setText( String.valueOf(year)+String.valueOf(monthOfYear + 1)+"0"+String.valueOf(dayOfMonth) );
    }

    private void saveTask(String title,String content,String date){
        DBAdapter db=new DBAdapter(this);
        db.openDB();
        if(title.isEmpty()||content.isEmpty()) {
            Toast.makeText(this,"NULL",Toast.LENGTH_SHORT).show();
        }else if(db.add(title,content,date)){
                Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
        }
        db.closeDB();
    }
}
