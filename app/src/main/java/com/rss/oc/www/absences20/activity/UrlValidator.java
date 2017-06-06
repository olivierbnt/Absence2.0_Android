package com.rss.oc.www.absences20.activity;

/**
 * Created by anttiv on 28/02/16.
 */

import android.util.Log;

import java.util.Arrays;

import static com.rss.oc.www.absences20.activity.Constants.MAX_EXPECTED_TX_POWER;
import static com.rss.oc.www.absences20.activity.Constants.MIN_EXPECTED_TX_POWER;

/**
 * Basic validation of an Eddystone-URL frame. <p>
 *
 * @see <a href="https://github.com/google/eddystone/eddystone-url">URL frame specification</a>
 */
class UrlValidator {

    private static final String TAG = UrlValidator.class.getSimpleName();

    private UrlValidator() {
    }

    static void validate(String deviceAddress, byte[] serviceData, Beacon beacon) {
        beacon.hasUrlFrame = true;

        // Tx power should have reasonable values.
        int txPower = (int) serviceData[1];
        if (txPower < MIN_EXPECTED_TX_POWER || txPower > MAX_EXPECTED_TX_POWER) {
            String err = String.format("Expected URL Tx power between %d and %d, got %d",
                    MIN_EXPECTED_TX_POWER, MAX_EXPECTED_TX_POWER, txPower);
            beacon.urlStatus.txPower = err;
            logDeviceError(deviceAddress, err);
        }

        // The URL bytes should not be all zeroes.
        byte[] urlBytes = Arrays.copyOfRange(serviceData, 2, 20);
        if (Utils.isZeroed(urlBytes)) {
            String err = "URL bytes are all 0x00";
            beacon.urlStatus.urlNotSet = err;
            logDeviceError(deviceAddress, err);
        }

        beacon.urlServiceData = serviceData;
        beacon.urlStatus.urlValue = UrlUtils.decodeUrl(serviceData);
    }

    private static void logDeviceError(String deviceAddress, String err) {
        Log.e(TAG, deviceAddress + ": " + err);
    }
}