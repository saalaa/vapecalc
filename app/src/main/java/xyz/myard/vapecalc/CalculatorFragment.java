package xyz.myard.vapecalc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CalculatorFragment extends Fragment {
    public Boolean disabled = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        return inflater.inflate(R.layout.calculator, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextWatcher compute_1_watcher = new TextWatcher () {
            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                compute_1();
            }
        };

        bind(R.id.resistance, compute_1_watcher);
        bind(R.id.tension_high, compute_1_watcher);
        bind(R.id.tension_low, compute_1_watcher);

        TextWatcher compute_2_watcher = new TextWatcher () {
            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                compute_2();
            }
        };

        bind(R.id.capacity, compute_2_watcher);
        bind(R.id.discharge, compute_2_watcher);
        bind(R.id.puffs_duration, compute_2_watcher);

        // Effectively links the two computations together
        bind(R.id.current_low, compute_2_watcher);

        // Kick-start things
        compute_1();
    }

    public void bind(int id, TextWatcher f) {
        View view = getView();

        if (view == null) {
            return;
        }

        EditText target = (EditText) view.findViewById(id);
        target.addTextChangedListener(f);
    }

    public float get_float (int id) {
        View view = getView();

        if (view == null) {
            //Log.d(
            //        Constants.log, "Could not get_float(), view is missing"
            //);

            throw new NumberFormatException();
        }

        EditText field = (EditText) view.findViewById(id);
        Editable editable = field.getText();

        return Float.valueOf(
                editable.toString()
        );
    }

    public void set_text (int id, String value) {
        View view = getView();

        if (view == null) {
            return;
        }

        EditText field = (EditText) view.findViewById(id);

        field.setText(value);
    }

    public void unset (int id) {
        set_text(id, "");
    }

    public void set_int (int id, float value) {
        set_float(id, value, "#");
    }

    public void set_float (int id, float value) {
        set_float(id, value, "#.##");
    }

    public void set_float (int id, float value, String format) {
        DecimalFormat decimal_format = new DecimalFormat(format);
        decimal_format.setRoundingMode(RoundingMode.CEILING);

        set_text(id, decimal_format.format(value));
    }

    public void warn () {
        if (disabled) {
            return;
        }

        View view = getView();

        //Log.d(Constants.log, "Dangerous setup");

        if (view == null) {
            return;
        }

        Snackbar.make(view, "this is a dangerous setup", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
    }

    public void compute_1 () {
        float resistance;

        try {
            resistance = get_float(R.id.resistance);
        } catch (NumberFormatException e) {
            unset(R.id.power_high);
            unset(R.id.current_high);
            unset(R.id.power_low);
            unset(R.id.current_low);

            return;
        }

        try {
            float tension_high = get_float(R.id.tension_high);

            float power_high = tension_high * tension_high / resistance;
            float current_high = tension_high / resistance;

            set_float(R.id.power_high, power_high);
            set_float(R.id.current_high, current_high);
        } catch (NumberFormatException e) {
            unset(R.id.power_high);
            unset(R.id.current_high);
        }

        try {
            float tension_low = get_float(R.id.tension_low);

            float power_low = tension_low * tension_low / resistance;
            float current_low = tension_low / resistance;

            set_float(R.id.power_low, power_low);
            set_float(R.id.current_low, current_low);
        } catch (NumberFormatException e) {
            unset(R.id.power_low);
            unset(R.id.current_low);
        }
    }

    public void compute_2 () {
        float capacity, current, discharge, puffs_duration;

        boolean danger = false;

        try {
            capacity = get_float(R.id.capacity);
            current = get_float(R.id.current_low);

            float runtime = capacity / 1000 * 60 / current;

            set_int(R.id.runtime, runtime);
        } catch (NumberFormatException e) {
            unset(R.id.runtime);
            unset(R.id.puffs_number);

            return;
        }

        try {
            discharge = get_float(R.id.discharge);
        } catch (NumberFormatException e) {
            discharge = 0;
        }

        try {
            if (discharge != 0) {
                float current_high = get_float(R.id.current_high);

                if (current_high > discharge) {
                    DecimalFormat decimal_format = new DecimalFormat("#.##");
                    decimal_format.setRoundingMode(RoundingMode.CEILING);

                    //Log.d(Constants.log, "Danger: high / " + decimal_format.format(current_high)
                    //        + " vs " + decimal_format.format(discharge));

                    danger = true;
                }
            }
        } catch (NumberFormatException e) {
            // pass
        }

        try {
            if (discharge != 0) {
                float current_low = get_float(R.id.current_low);

                if (current_low > discharge) {
                    DecimalFormat decimal_format = new DecimalFormat("#.##");
                    decimal_format.setRoundingMode(RoundingMode.CEILING);

                    //Log.d(Constants.log, "Danger: low / " + decimal_format.format(current_low)
                    //        + " vs " + decimal_format.format(discharge));

                    danger = true;
                }
            }
        } catch (NumberFormatException e) {
            // pass
        }

        if (danger) {
            warn();
        }

        try {
            puffs_duration = get_float(R.id.puffs_duration);

            float puffs_number = capacity / 1000 * 60 / current * 60 / puffs_duration;

            set_int(R.id.puffs_number, puffs_number);
        } catch (NumberFormatException e) {
            unset(R.id.puffs_number);
        }
    }
}
