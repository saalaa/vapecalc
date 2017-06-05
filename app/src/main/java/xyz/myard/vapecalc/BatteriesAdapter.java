package xyz.myard.vapecalc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

class BatteriesAdapter extends ArrayAdapter<Battery> {
    BatteriesAdapter(Context context, int id, Battery[] items) {
        super(context, id, items);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.batteries_item, parent, false);
        }

        Battery battery = (Battery) getItem(position);

        if (battery != null) {
            TextView tt1 = (TextView) view.findViewById(R.id.firstLine);
            TextView tt2 = (TextView) view.findViewById(R.id.secondLine);
            TextView tt3 = (TextView) view.findViewById(R.id.thirdLine);

            if (tt1 != null) {
                tt1.setText(battery.name);
            }

            if (tt2 != null) {
                tt2.setText(battery.capacity + " mAh / " + battery.current + " A");
            }

            if (tt3 != null) {
                tt3.setText(battery.size);
            }
        }

        return view;
    }
}
