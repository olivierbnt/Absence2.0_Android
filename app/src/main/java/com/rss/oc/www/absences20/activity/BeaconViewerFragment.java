

package com.rss.oc.www.absences20.activity;

import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelUuid;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.rss.oc.www.absences20.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class BeaconViewerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "BeaconViewerFragment";
    private static final int REQUEST_ENABLE_BLUETOOTH = 1;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 2;

    private static final Handler handler = new Handler(Looper.getMainLooper());

    // The Eddystone Service UUID, 0xFEAA.
    private static final ParcelUuid EDDYSTONE_SERVICE_UUID =
            ParcelUuid.fromString("0000FEAA-0000-1000-8000-00805F9B34FB");

    static final byte UID_FRAME_TYPE = 0x00;
    static final byte URL_FRAME_TYPE = 0x10;
    static final byte TLM_FRAME_TYPE = 0x20;

    /* from settings activity */
    static final String ON_LOST_TIMEOUT_SECS_KEY = "onLostTimeoutSecs";
    static final String SHOW_DEBUG_INFO_KEY = "showDebugInfo";

    // An aggressive scan for nearby devices that reports immediately.
    private static final ScanSettings SCAN_SETTINGS =
            new ScanSettings.Builder()
                    .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                    .setReportDelay(0)
                    .build();

    /* bl stuff */
    private BluetoothLeScanner scanner;
    //private List<ScanFilter> scanFilters;
    private ScanCallback scanCallback;
    private Map<String /* device address */, Beacon> deviceToBeaconMap = new HashMap<>();

    private ArrayList<Beacon> arrayList;
    public BeaconArrayAdapter arrayAdapter;

    private SharedPreferences sharedPreferences;
    private int onLostTimeoutMillis;




    public BeaconViewerFragment() {
        // Required empty public constructor

    }

    public BeaconArrayAdapter getMyList(){
        return arrayAdapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.i(TAG, "running init");
        init();
        ArrayList<Beacon> arrayList = new ArrayList<>();
        arrayAdapter = new BeaconArrayAdapter(getActivity(), R.layout.beacon_list_item, arrayList);
        scanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                ScanRecord scanRecord = result.getScanRecord();

                if (scanRecord == null) {
                    return;
                }

                String deviceAddress = result.getDevice().getAddress();

                // Selectin des beacons de la salle
                if (deviceAddress.contentEquals("F3:C6:86:1D:E0:4C")||deviceAddress.contentEquals("D4:8D:80:53:EE:AC")||deviceAddress.contentEquals("CD:BC:4F:E5:3E:78"))  {
                    Log.i(TAG, "DeviceAddress: " + deviceAddress);
                    Beacon beacon;
                    if (!deviceToBeaconMap.containsKey(deviceAddress)) {
                        int hash = deviceAddress.hashCode();
                        boolean amIhere = deviceToBeaconMap.containsKey(deviceAddress);
                        beacon = new Beacon(deviceAddress, result.getRssi());
                        Log.i(TAG, "putting device: " + deviceAddress + " to BeaconMap and arrayAdapter");
                        deviceToBeaconMap.put(deviceAddress, beacon);
                        arrayAdapter.add(beacon);
                    } else {
                        deviceToBeaconMap.get(deviceAddress).lastSeenTimestamp = System.currentTimeMillis();
                        deviceToBeaconMap.get(deviceAddress).rssi = result.getRssi();
                    }

                    byte[] serviceData = scanRecord.getServiceData(EDDYSTONE_SERVICE_UUID);
                    //Log.i(TAG,"serviceData: " + Utils.toHexString(serviceData));
                    handleServiceData(deviceAddress, serviceData);
                }
            }

            @Override
            public void onScanFailed(int errorCode) {
                switch (errorCode) {
                    case SCAN_FAILED_ALREADY_STARTED:
                        Log.i(TAG, "SCAN_FAILED_ALREADY_STARTED");
                        break;
                    case SCAN_FAILED_APPLICATION_REGISTRATION_FAILED:
                        Log.i(TAG, "SCAN_FAILED_APPLICATION_REGISTRATION_FAILED");
                        break;
                    case SCAN_FAILED_FEATURE_UNSUPPORTED:
                        Log.i(TAG, "SCAN_FAILED_FEATURE_UNSUPPORTED");
                        break;
                    case SCAN_FAILED_INTERNAL_ERROR:
                        Log.i(TAG, "SCAN_FAILED_INTERNAL_ERROR");
                        break;
                    default:
                        Log.i(TAG, "Scan failed, unknown error code");
                        break;
                }
            }
        };

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        onLostTimeoutMillis =
                sharedPreferences.getInt(ON_LOST_TIMEOUT_SECS_KEY, 5) * 1000;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
        return view;
    }


    @Override
    public void onPause() {
        super.onPause();
        if (scanner != null) {
            Log.i(TAG, "scanner paused");
            scanner.stopScan(scanCallback);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        handler.removeCallbacksAndMessages(null);

        int timeoutMillis =
                sharedPreferences.getInt(ON_LOST_TIMEOUT_SECS_KEY, 5) * 1000;

        if (timeoutMillis > 0) {  // 0 is special and means don't remove anything.
            onLostTimeoutMillis = timeoutMillis;
            setOnLostRunnable();
        }

        if (sharedPreferences.getBoolean(SHOW_DEBUG_INFO_KEY, false)) {
            Runnable updateTitleWithNumberSightedBeacons = new Runnable() {
                final String appName = getActivity().getString(R.string.app_name);

                @Override
                public void run() {
                    getActivity().setTitle(appName + " (" + deviceToBeaconMap.size() + ")");
                    handler.postDelayed(this, 1000);
                }
            };
            handler.postDelayed(updateTitleWithNumberSightedBeacons, 1000);
        } else {
            getActivity().setTitle(getActivity().getString(R.string.app_name));
        }

        if (scanner != null) {
            scanner.startScan(scanCallback);
        }
    }

    private void handleServiceData(String deviceAddress, byte[] serviceData) {
        Beacon beacon = deviceToBeaconMap.get(deviceAddress);
        if (beacon == null) {
            Log.e(TAG, "Cannot find device from beaconMap: " + deviceAddress);
            return;
        }
        if (serviceData == null) {
            Log.e(TAG, "Null serviceData");
            return;
        }

        switch (serviceData[0]) {
            case UID_FRAME_TYPE:
                Log.i(TAG, "Validting UID-FRAME");
                UidValidator.validate(deviceAddress, serviceData, beacon);
                break;
            case TLM_FRAME_TYPE:
                TlmValidator.validate(deviceAddress, serviceData, beacon);
                break;
            case URL_FRAME_TYPE:
                Log.i(TAG, "Validting URL-FRAME");
                UrlValidator.validate(deviceAddress, serviceData, beacon);
                break;
            default:
                String err = String.format("Invalid frame type byte %02X", serviceData[0]);
                beacon.frameStatus.invalidFrameType = err;
                logDeviceError(deviceAddress, err);
                break;
        }
        arrayAdapter.notifyDataSetChanged();
    }

    private void setOnLostRunnable() {
        Runnable removeLostDevices = new Runnable() {
            @Override
            public void run() {
                long time = System.currentTimeMillis();
                Iterator<Map.Entry<String, Beacon>> itr = deviceToBeaconMap.entrySet().iterator();
                while (itr.hasNext()) {
                    Beacon beacon = itr.next().getValue();
                    if ((time - beacon.lastSeenTimestamp) > onLostTimeoutMillis) {
                        itr.remove();
                        arrayAdapter.remove(beacon);
                    }
                }
                handler.postDelayed(this, onLostTimeoutMillis);
            }
        };
        handler.postDelayed(removeLostDevices, onLostTimeoutMillis);
    }


    /* private methods */
    private void init() {
        BluetoothManager manager = (BluetoothManager) getActivity().getApplicationContext()
                .getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter btAdapter = manager.getAdapter();
        if (btAdapter == null) {
            Log.i(TAG, "btAdapter null");
        } else if (!btAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            this.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BLUETOOTH);
        } else {
            scanner = btAdapter.getBluetoothLeScanner();
        }
    }

    private static void logDeviceError(String deviceAddress, String err) {
        Log.e(TAG, deviceAddress + ": " + err);
    }
}
