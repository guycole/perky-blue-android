package net.braingang.perkyblue;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 *
 */
public class BtArrayAdapter extends ArrayAdapter<BluetoothDevice> {

    public BtArrayAdapter(Context context, List<BluetoothDevice> deviceList) {
        super(context, 0, deviceList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BluetoothDevice item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_bt, parent, false);
        }

        TextView tvAddress = (TextView) convertView.findViewById(R.id.tvAddress01);
        tvAddress.setText(item.getAddress());

        TextView tvName = (TextView) convertView.findViewById(R.id.tvName01);
        tvName.setText(item.getName());

        return convertView;
    }
}
