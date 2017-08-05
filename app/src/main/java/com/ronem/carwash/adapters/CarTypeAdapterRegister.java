package com.ronem.carwash.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ronem.carwash.R;
import com.ronem.carwash.model.CarType;

import java.util.List;

/**
 * Created by ram on 8/5/17.
 */

public class CarTypeAdapterRegister extends BaseAdapter {
    private List<CarType> carTypes;
    private LayoutInflater inflater;

    public CarTypeAdapterRegister(List<CarType> carTypes, Context context) {
        this.carTypes = carTypes;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return carTypes.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.single_row_item_car_type_register, parent, false);
            holder.carTypev = (TextView) convertView.findViewById(R.id.r_car_type);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.carTypev.setText(carTypes.get(position).getType());
        return convertView;
    }

    class ViewHolder {
        TextView carTypev;
    }

}

