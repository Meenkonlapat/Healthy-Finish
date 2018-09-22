package com.example.lab203_43.healthy.Weight;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab203_43.healthy.R;
import com.example.lab203_43.healthy.Weight.WeightFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by LAB203_44 on 27/8/2561.
 */

public class WeightFromFragment extends Fragment {
    FirebaseFirestore mdb = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weightfrom , container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initsaveBtn();
        initBackBtn();
    }
    private void initsaveBtn(){
        final String uid = mAuth.getUid();
        Button _saveBtn = (Button) getView().findViewById(R.id.save_btn);
        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText _weight = (EditText) getView().findViewById(R.id.weight_from_day);
                EditText _date = (EditText) getView().findViewById(R.id.date);
                int weightInt = Integer.parseInt(_weight.getText().toString());
                String _dateStr = _date.getText().toString();
                _dateStr = _dateStr.replace("/", "-");
                Weight weightObj = new Weight(_dateStr, weightInt);
                mdb.collection("myfitness").document(uid).collection("weight").document(_dateStr).set(weightObj).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new WeightFragment())
                                .addToBackStack(null).commit();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                Toast.makeText(getActivity(), "บันทึกแล้ว", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void initBackBtn(){
        Button _backBtn = (Button) getView().findViewById(R.id.weight_from_back_btn);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new WeightFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
