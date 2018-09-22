package com.example.lab203_43.healthy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by LAB203_43 on 20/8/2561.
 */

public class BMIfragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bmi, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button _loginBtn = (Button) getView().findViewById(R.id.bmi_calculate_btn);
        _loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText _userHeight = (EditText) getView().findViewById(R.id.user_height);
                EditText _userWeight = (EditText) getView().findViewById(R.id.user_weight);
                Float _userHeightFlt = Float.parseFloat(_userHeight.getText().toString());
                Float _userWeightFlt = Float.parseFloat(_userWeight.getText().toString());

                if (_userHeightFlt.isNaN() || _userWeightFlt.isNaN()) {
                    if (_userHeightFlt.isNaN()) {
                        Log.d("USER", "FIELD HEIGHT IS EMPTY");
                    }
                    if (_userWeightFlt.isNaN()) {
                        Log.d("USER", "FIELD WEIGHT IS EMPTY");
                    }
                    Toast.makeText(
                            getActivity(),
                            "กรุณาระบุข้อมูลให้ครบถ้วน",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    String _userBMI = Double.toString(_userWeightFlt / Math.pow(_userHeightFlt/100, 2.0));
                    TextView userBMI = (TextView) getView().findViewById(R.id.user_bmi);
                    userBMI.setText(_userBMI);
                    Log.d("USER", "BMI IS VALUE");
                }
            }
        });
        Button _backBtn = (Button) getView().findViewById(R.id.button_back);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MenuFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
