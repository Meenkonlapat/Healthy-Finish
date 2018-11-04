package com.example.lab203_43.healthy;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class SleepFragment extends android.support.v4.app.Fragment {
        SQLiteDatabase myDB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);
        myDB.execSQL("Create table if not exists sleep (id INTEGER PRIMARY KEY AUTOINCREMENT, date VARCHAR(10), toBedTime VARCHAR(5), awakeTime VARCHAR(5))");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InitBackButton();
        InitAddButton();
        getAndShowData();
    }
    private void InitBackButton()
    {
        Button addSleepButton = (Button) getView().findViewById(R.id.add_sleep_back_button);
        addSleepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new AddSleepFragment())
                        .addToBackStack(null).commit();

            }
        });
    }
    private void getAndShowData() {
        Cursor cursor = myDB.rawQuery("select id, date, toBedTime, awakeTime from sleep", null);
        final ArrayList<Sleep> sleepList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String date = cursor.getString(1);
            String toBedTime = cursor.getString(2);
            String awakeTime = cursor.getString(3);
            Sleep sleep = new Sleep();
            sleep.setId(id);
            sleep.setDate(date);
            sleep.setToBedTime(toBedTime);
            sleep.setAwakeTime(awakeTime);
            sleepList.add(sleep);
        }
        cursor.close();
        ListView sleepListView = (ListView) getView().findViewById(R.id.sleep_listview);
        SleepAdapter sleepAdapter = new SleepAdapter(getActivity(), R.layout.fragment_sleep_item_list, sleepList);
        sleepListView.setAdapter(sleepAdapter);
        sleepListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("sleep object", sleepList.get(position));
                android.support.v4.app.Fragment addSleepFragment = new AddSleepFragment();
                addSleepFragment.setArguments(bundle);
                android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.replace(R.id.main_view, addSleepFragment).addToBackStack(null).commit();
            }
        });
    }

    private void InitAddButton(){
        Button addSleepButton = (Button) getView().findViewById(R.id.sleep_add);
        addSleepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new AddSleepFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
