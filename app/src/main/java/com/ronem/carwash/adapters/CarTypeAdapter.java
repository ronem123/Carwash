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
 * Created by ronem on 8/2/17.
 */

public class CarTypeAdapter extends BaseAdapter {
    private List<CarType> carTypes;
    private LayoutInflater inflater;

    public CarTypeAdapter(List<CarType> carTypes, Context context) {
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
            convertView = inflater.inflate(R.layout.single_row_car_type, parent, false);
            holder.carTypev = (TextView) convertView.findViewById(R.id.car_type);
            holder.priceV = (TextView) convertView.findViewById(R.id.price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.carTypev.setText(carTypes.get(position).getType());
        holder.priceV.setText(carTypes.get(position).getPrice());
        return convertView;
    }

    class ViewHolder {
        TextView carTypev,priceV;
    }

}
