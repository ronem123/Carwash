package com.ronem.carwash.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ronem.carwash.R;
import com.ronem.carwash.model.Address;
import com.ronem.carwash.model.CarType;

import java.util.List;

/**
 * Created by ronem on 8/2/17.
 */

public class AddressAdapter extends BaseAdapter {
    private List<Address> addresses;
    private LayoutInflater inflater;

    public AddressAdapter(List<Address> addresses, Context context) {
        this.addresses = addresses;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return addresses.size();
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
            convertView = inflater.inflate(R.layout.single_row_address, parent, false);
            holder.addressV = (TextView) convertView.findViewById(R.id.address);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.addressV.setText(addresses.get(position).getAddress());
        return convertView;
    }

    class ViewHolder {
        TextView addressV;
    }

}
