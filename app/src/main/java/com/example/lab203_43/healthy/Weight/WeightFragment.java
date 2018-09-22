package com.example.lab203_43.healthy.Weight;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.lab203_43.healthy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by LAB203_44 on 27/8/2561.
 */

public class WeightFragment extends Fragment
{
    FirebaseAuth mAuth;
    FirebaseFirestore mdb;
    ArrayList<Weight> _weight = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        _weight.add(new Weight("01 Jan 2018", 63, "UP"));
//        _weight.add(new Weight("02 Jan 2018", 64, "DOWN"));
//        _weight.add(new Weight("03 Jan 2018", 63, "UP"));

        initAddWeightBtn();
        initDateCall();

    }

    private void initAddWeightBtn(){
        Button _addWeight = (Button) getView().findViewById(R.id.add_weight_btn);
        _addWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new WeightFromFragment())
                        .addToBackStack(null)
                        .commit();
                Log.d("USER", "Go To Weight");
            }
        });
    }
    private void initDateCall(){

        mdb = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        final ListView  _weightlist = (ListView) getView().findViewById(R.id.weight_list);
        final WeightAdapter _weightAdapter = new WeightAdapter(
                getActivity(),
                R.layout.fragment_weight_item,
                _weight);
        _weightlist.setAdapter(_weightAdapter);
        mdb.collection("myfitness").document(mAuth.getUid()).collection("weight").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                _weightAdapter.clear();
                for (QueryDocumentSnapshot doc: queryDocumentSnapshots)
                    _weight.add(doc.toObject(Weight.class));
                Collections.reverse(_weight);
                initAssignStatus();

                _weightAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initAssignStatus(){
        for (int i = 0 ; i < _weight.size() - 1; i++){
            if (_weight.get(i).getWeight() > _weight.get(i+1).getWeight()){
                _weight.get(i).setStatus("UP");
            }
            else if (_weight.get(i).getWeight() < _weight.get(i+1).getWeight()){
                _weight.get(i).setStatus("DOWN");
            }
        }
    }
}
