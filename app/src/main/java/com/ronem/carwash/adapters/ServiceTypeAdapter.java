package com.ronem.carwash.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ronem.carwash.R;
import com.ronem.carwash.model.ServiceType;

import java.util.List;

/**
 * Created by ronem on 8/8/17.
 */

public class ServiceTypeAdapter extends BaseAdapter {
    private List<ServiceType> serviceTypes;
    private LayoutInflater inflater;

    public ServiceTypeAdapter(List<ServiceType> serviceTypes, Context context) {
        this.serviceTypes = serviceTypes;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return serviceTypes.size();
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
            holder.serviceTypeV = (TextView) convertView.findViewById(R.id.car_type);
            holder.priceV = (TextView) convertView.findViewById(R.id.price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.serviceTypeV.setText(serviceTypes.get(position).getServiceType());
        holder.priceV.setText(serviceTypes.get(position).getServiceCharge());
        return convertView;
    }

    class ViewHolder {
        TextView serviceTypeV,priceV;
    }

}
