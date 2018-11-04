package com.example.lab203_43.healthy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddSleepFragment extends Fragment{
    SQLiteDatabase myDB;
    Sleep sleep;
    String status;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);
        myDB.execSQL("CREATE TABLE IF NOT EXISTS sleep (id INTEGER PRIMARY KEY AUTOINCREMENT, date VARCHAR(10), toBedTime VARCHAR(5), awakeTime VARCHAR(5))");

        Bundle bundle = getArguments();
        try
        {
            sleep = (Sleep) bundle.getSerializable("sleep object");
            status = "edit";
        }
        catch (NullPointerException e)
        {
            if (sleep == null)
            {
                status = "new";
            }
            else{
                Log.d("USER", "null pointer : " + e.getMessage());
            }
        }
        Log.d("USER", "status : " + status);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_sleep, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (status.equals("edit"))
        {
            setValue();
        }
        InitBackButton();
        InitAddButton();
    }

    private void setValue(){
        EditText date = (EditText) getView().findViewById(R.id.add_sleep_date);
        EditText toBedTime = (EditText) getView().findViewById(R.id.add_sleep_toBedTime);
        EditText awakeTime = (EditText) getView().findViewById(R.id.add_sleep_awakeTime);
        date.setText(sleep.getDate());
        toBedTime.setText(sleep.getToBedTime());
        awakeTime.setText(sleep.getAwakeTime());
        Button addButton = (Button) getView().findViewById(R.id.add_sleep_add_button);
        addButton.setText("edit");
    }
    private void InitBackButton(){
        Button backButton = (Button) getView().findViewById(R.id.add_sleep_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }

    private void InitAddButton(){
        Button addButton = (Button) getView().findViewById(R.id.add_sleep_add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText date = (EditText)getView().findViewById(R.id.add_sleep_date);
                EditText toBedTime = (EditText) getView().findViewById(R.id.add_sleep_toBedTime);
                EditText awakeTime = (EditText) getView().findViewById(R.id.add_sleep_awakeTime);
                String dateStr = date.getText().toString();
                String toBedTimeStr = toBedTime.getText().toString();
                String awakeTimeStr = awakeTime.getText().toString();
                ContentValues row = new ContentValues();
                row.put("date", dateStr);
                row.put("toBedTime", toBedTimeStr);
                row.put("awakeTime", awakeTimeStr);
                if (status.equals("new"))
                {
                    myDB.insert("sleep", null, row);
                }
                else if (status.equals("edit"))
                {
                    myDB.update("sleep", row, "id="+sleep.getId(), null);
                }
                Toast.makeText(getContext(), "Add New Sleep", Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStack();
            }
        });
    }
}
