package com.ogi.myalarmmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.DialogDateListener, TimePickerFragment.DialogTimeListener {
    TextView tvOnceDate, tvOneTime, tvRepeatingTime;
    EditText edtOneMessage, edtRepeatingMessage;
    ImageButton ibtnOnceDate, ibtnOnceTime, ibtnRepeatingTime;
    Button btnSetAlarmOnce, btnSetAlarmRepeating, btnCancelAlarm;

    private AlarmReceiver alarmReceiver;

    final String DATE_PICKER_TAG = "DatePicker";
    final String TIME_PICKER_ONCE_TAG = "TimePickerOnce";
    final String TIME_PICKER_REPEAT_TAG = "TimePickerRepeat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvOnceDate = findViewById(R.id.tv_date_year_desc);
        tvOneTime = findViewById(R.id.tv_time_desc);
        ibtnOnceDate = findViewById(R.id.ib_date_year);
        ibtnOnceTime = findViewById(R.id.ib_time);
        edtOneMessage = findViewById(R.id.et_note);
        btnSetAlarmOnce = findViewById(R.id.btn_set_once_alarm);
        tvRepeatingTime = findViewById(R.id.tv_repeat_time_alarm);
        ibtnRepeatingTime = findViewById(R.id.ib_repeat_time);
        edtRepeatingMessage = findViewById(R.id.et_repeat_note);
        btnSetAlarmRepeating = findViewById(R.id.btn_set_repeat_alarm);
        btnCancelAlarm = findViewById(R.id.btn_cancel_alarm);

        ibtnOnceDate.setOnClickListener(this);
        ibtnOnceTime.setOnClickListener(this);
        btnSetAlarmOnce.setOnClickListener(this);
        ibtnRepeatingTime.setOnClickListener(this);
        btnSetAlarmRepeating.setOnClickListener(this);
        btnCancelAlarm.setOnClickListener(this);

        alarmReceiver = new AlarmReceiver();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_date_year:
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);
                break;
            case R.id.ib_time:
                TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(getSupportFragmentManager(), TIME_PICKER_ONCE_TAG);
                break;
            case R.id.btn_set_once_alarm:
                String onceDate = tvOnceDate.getText().toString();
                String onceTime = tvOneTime.getText().toString();
                String onceMessage = edtOneMessage.getText().toString();

                alarmReceiver.setOneTimeAlarm(this, AlarmReceiver.TYPE_ONE_TIME, onceDate, onceTime, onceMessage);
                break;
            case R.id.ib_repeat_time:
                TimePickerFragment timePickerRepeatFragment = new TimePickerFragment();
                timePickerRepeatFragment.show(getSupportFragmentManager(), TIME_PICKER_REPEAT_TAG);
                break;
            case R.id.btn_set_repeat_alarm:
                String repeatTime = tvRepeatingTime.getText().toString();
                String repeatMessage = edtRepeatingMessage.getText().toString();
                alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING, repeatTime, repeatMessage);
                break;
            case R.id.btn_cancel_alarm:
                alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING);
                break;
        }
    }

    @Override
    public void onDialogDateSet(String tag, int year, int month, int dayOfMonth){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        tvOnceDate.setText(dateFormat.format(calendar.getTime()));
    }

    @Override
    public void onDialogTimeSet(String tag, int hourOfDay, int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        switch (tag){
            case TIME_PICKER_ONCE_TAG:
                tvOneTime.setText(dateFormat.format(calendar.getTime()));
                break;
            case TIME_PICKER_REPEAT_TAG:
                tvRepeatingTime.setText(dateFormat.format(calendar.getTime()));
                break;
            default:
                break;
        }
    }
}
