package ielite.app.eventme;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;

import java.util.Calendar;

import ielite.app.eventme.ui.CategoryDialog;
import ielite.app.eventme.ui.MyDatePickerDialog;
import ielite.app.eventme.utils.DateUtils;
import ielite.app.eventme.utils.LocationUtils;
import ielite.app.eventme.utils.Utility;

public class FilterFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = FilterFragment.class.getSimpleName();
    private static final String DIALOG_FRAG_TAG = "category_dialog_fragment";

    private MyDatePickerDialog mDatePicker;
    private SeekBar mSeekBar;
    private TextView mSeekBarLabel;
    private int mRadius;

    public FilterFragment() {
        // Required empty public constructor
    }

    public static FilterFragment newInstance(String param1, String param2) {
        return new FilterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        mRadius = LocationUtils.getPreferredRadius(getActivity());

        mSeekBarLabel = (TextView) view.findViewById(R.id.seekbar_label);
        setSeekBarLabelText(mRadius);

        mSeekBar = (SeekBar) view.findViewById(R.id.seekbar_radius);
        mSeekBar.setProgress(mRadius);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mRadius = progress;
                    setSeekBarLabelText(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                Log.i(TAG, "onStopTrackingTouch called. Radius is " + mRadius);

                mSeekBar.setProgress(mRadius);
                LocationUtils.savePreferredRadius(getActivity(), mRadius);
            }
        });

        mSeekBar.setProgress(LocationUtils.getPreferredRadius(getActivity()));
        Button datePickerButton = (Button) view.findViewById(R.id.button_date_picker);

        long startMillis = Utility.getPreferredMillis(getActivity(), getString(R.string
                .pref_start_date_key), -1);

        Calendar startCal = Calendar.getInstance();
        long today = DateUtils.getTodayInMillis();

        if ((startMillis != -1) && (startMillis >= today)) {
            startCal.setTimeInMillis(startMillis);
        } else {
            startCal.setTimeInMillis(today);
        }
        mDatePicker = MyDatePickerDialog.newInstance(
                this,
                startCal.get(Calendar.YEAR),
                startCal.get(Calendar.MONTH),
                startCal.get(Calendar.DAY_OF_MONTH));

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePicker.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        Button categoryButton = (Button) view.findViewById(R.id.button_category_picker);
        categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryDialog cd = new CategoryDialog();
                cd.show(getFragmentManager(), DIALOG_FRAG_TAG);
            }
        });
        return view;
    }

    private void setSeekBarLabelText(int radius) {
        String s = radius > 1 ? " miles." : " mile.";
        mSeekBarLabel.setText("Search Radius: " + radius + s);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int
            yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        Calendar startCal = Calendar.getInstance();
        startCal.set(year, monthOfYear, dayOfMonth);

        Calendar endCal = Calendar.getInstance();
        endCal.set(yearEnd, monthOfYearEnd, dayOfMonthEnd);

        Log.i(TAG, "onDateSet called. Start: " + startCal.toString() + " end: " + endCal.toString
                ());

        Utility.savePreferredStartDate(getActivity(), startCal.getTimeInMillis());
        Utility.savePreferredEndDate(getActivity(), endCal.getTimeInMillis());
    }

}
