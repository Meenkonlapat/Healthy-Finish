package com.example.lab203_43.healthy;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lab203_43.healthy.Weight.WeightFragment;
import com.example.lab203_43.healthy.Weight.WeightFromFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * Created by LAB203_44 on 27/8/2561.
 */

public class MenuFragment extends Fragment{
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ArrayList<String> _menu = new ArrayList<>();

    public MenuFragment() {
        _menu.add("BMI");
        _menu.add("Weight");
        _menu.add("Sleep");
        _menu.add("Post");
        _menu.add("Sign Out");
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        final ArrayAdapter<String> _menuAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                _menu
        );

        ListView _menuList = (ListView) getView().findViewById(R.id.menu_list);
        _menuList.setAdapter(_menuAdapter);
        _menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("MENU", "Click on menu = " + _menu.get(i));
                switch (_menu.get(i)){
                    case "BMI":
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new BMIfragment())
                                .addToBackStack(null)
                                .commit();
                        Log.d("USER", "GO TO BMI");
                        break;
                    case "Weight" :
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new WeightFragment())
                                .addToBackStack(null)
                                .commit();
                        Log.d("USER", "GO TO WEIGHT");
                        break;
                    case "Sleep" :
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new SleepFragment())
                                .addToBackStack(null)
                                .commit();
                        Log.d("USER", "GO TO SLEEP");
                        break;
                    case "Post" :
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new PostFragment())
                                .addToBackStack(null)
                                .commit();
                        Log.d("USER", "GO TO POST");
                        break;
                    case "Sign Out" :
                        mAuth.signOut();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new LoginFragment())
                                .commit();
                        Log.d("USER", "SIGN OUT");
                        break;
                    default: break;
                }

            }
        });
    }
}
