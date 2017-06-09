package com.rss.oc.www.absences20.activity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.rss.oc.www.absences20.R;

import java.util.ArrayList;
import java.util.List;


public class BeaconArrayAdapter extends ArrayAdapter<Beacon> implements Filterable {
    private static final int DARK_GREEN = Color.argb(255, 0, 150, 0);
    private static final int DARK_RED = Color.argb(255, 150, 0, 0);
    private static final String TAG = "BeaconArrayAdapter";

    private List<Beacon> beaconList;

    public BeaconArrayAdapter(Context context, int resource, List<Beacon> beaconList) {
        super(context, resource, beaconList);
        this.beaconList = beaconList;
    }

    @Override
    public int getCount() {
        return beaconList.size();
    }

    @Override
    public Beacon getItem(int position) {
        return beaconList.get(position);
    }

    private double distanceFromRssi(int rssi, int txPower0m) {
        int pathLoss = txPower0m - rssi;
        return Math.pow(10, (pathLoss - 41) / 20.0);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.beacon_list_item, parent, false);
        }

        // Note: this is a listView and the convertView object here is likely to be
        // a recycled view of some other row that isn't in view. You need to set every
        // field regardless of emptiness to avoid displaying erroneous data.



       // ImageView dot = (ImageView) convertView.findViewById(R.id.dot);






        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<Beacon> filteredBeacons;
                if (constraint != null && constraint.length() != 0) {
                    filteredBeacons = new ArrayList<>();
                    for (Beacon beacon : beaconList) {
                        if (beacon.contains(constraint.toString())) {
                            filteredBeacons.add(beacon);
                        }
                    }
                } else {
                    filteredBeacons = beaconList;
                }
                results.count = filteredBeacons.size();
                results.values = filteredBeacons;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                beaconList = (List<Beacon>) results.values;
                if (results.count == 0) {
                    notifyDataSetInvalidated();
                } else {
                    notifyDataSetChanged();
                }
            }
        };
    }

    private void green(TextView v) { v.setTextColor(DARK_GREEN); }

    private void red(TextView v) {
        v.setTextColor(DARK_RED);
    }

    private void grey(TextView v) {
        v.setTextColor(Color.GRAY);
    }
}
