package xyz.myard.vapecalc;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
//import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class BatteriesFragment extends ListFragment {
    public static Battery[] batteries = new Battery[]{
        new Battery("AW (button top)",    "AW",          "18350", 800,  5),
        new Battery("AWT",                "AWT",         "18350", 800,  5),
        new Battery("Basen Black",        "Basen",       "18350", 800,  7),
        new Battery("Keep Power",         "Keep Power",  "18350", 800,  6),
        new Battery("LG HB2",             "LG",          "18650", 1500, 30),
        new Battery("LG HB4",             "LG",          "18650", 1500, 30),
        new Battery("LG HB6",             "LG",          "18650", 1500, 30),
        new Battery("LG HD2",             "LG",          "18650", 2000, 25),
        new Battery("LG HD2C",            "LG",          "18650", 2200, 25),
        new Battery("LG HD4",             "LG",          "18650", 2100, 25),
        new Battery("LG HE4",             "LG",          "18650", 2500, 20),
        new Battery("LG HG2",             "LG",          "18650", 3000, 18),
        new Battery("Samsung 20R",        "Samsung",     "18650", 2000, 22),
        new Battery("Samsung 25R5",       "Samsung",     "18650", 2500, 20),
        new Battery("Samsung 30Q",        "Samsung",     "18650", 3000, 20),
        new Battery("Sanyo UR 18650 NSX", "Sanyo",       "18650", 2500, 22),
        new Battery("Sanyo NCR 18650 GA", "Sanyo",       "18650", 3300, 10),
        new Battery("Sony VTC3",          "Sony",        "18650", 1500, 28),
        new Battery("Sony VTC4",          "Sony",        "18650", 2100, 23),
        new Battery("Sony VTC5",          "Sony",        "18650", 2600, 20),
        new Battery("Sony VTC5A",         "Sony",        "18650", 2500, 25),
        new Battery("Sony VTC6",          "Sony",        "18650", 3000, 19),
        new Battery("AWT Yellow",         "AWT",         "26650", 4500, 25),
        new Battery("Basen Black",        "Basen",       "26650", 4500, 23),
        new Battery("Brillipower Green",  "Brillipower", "26650", 4500, 23),
        new Battery("Efest Green",        "Efest",       "26650", 4200, 23),
        new Battery("Efest Purple",       "Efest",       "26650", 4200, 23),
        new Battery("iJoy",               "iJoy",        "26650", 4200, 30),
    };

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        BatteriesAdapter adapter = new BatteriesAdapter(getActivity(),
                android.R.layout.simple_list_item_1, batteries);

        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView list_view, View view, int position, long id) {
        Battery battery = batteries[position];

        //Log.d(Constants.log, "Battery: " + battery.capacity.toString() + "mAh / " +
        //        battery.current.toString() + "A");

        // That's right, we're controlling the pager from a fragment.

        MainActivity activity = (MainActivity) getActivity();
        PagerAdapter adapter = (PagerAdapter) activity.pager.getAdapter();

        activity.pager.setCurrentItem(0);

        if (adapter.calculator == null) {
            return;
        }

        adapter.calculator.disabled = true;

        adapter.calculator.set_int(R.id.capacity, battery.capacity.floatValue());
        adapter.calculator.set_int(R.id.discharge, battery.current.floatValue());

        adapter.calculator.disabled = false;

        adapter.calculator.compute_2();
    }
}
