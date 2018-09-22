package com.example.lab203_43.healthy.Weight;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lab203_43.healthy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LAB203_44 on 27/8/2561.
 */

public class WeightAdapter extends ArrayAdapter<Weight> {

        List<Weight> weights = new ArrayList<Weight>();
        Context context;

    public WeightAdapter(Context context, int resouce, List<Weight> objects){
        super(context, resouce, objects);
        this.weights = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View _weightItem = LayoutInflater.from(context).inflate(R.layout.fragment_weight_item, parent, false);
        TextView _date = (TextView) _weightItem.findViewById(R.id.weight_item_date);
        TextView _weight = (TextView) _weightItem.findViewById(R.id.weight_item_weight);
        TextView _status = (TextView) _weightItem.findViewById(R.id.weight_item_status);


        _date.setText(weights.get(position).getDate());
        _weight.setText(Integer.toString((weights.get(position)).getWeight()));
        _status.setText(weights.get(position).getStatus());

        return _weightItem;
    }
}
