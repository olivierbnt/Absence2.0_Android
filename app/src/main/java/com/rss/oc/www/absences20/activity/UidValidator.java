// Copyright 2015 Google Inc. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.rss.oc.www.absences20.activity;

import android.util.Log;

import java.util.Arrays;

import static com.rss.oc.www.absences20.activity.Constants.MAX_EXPECTED_TX_POWER;
import static com.rss.oc.www.absences20.activity.Constants.MIN_EXPECTED_TX_POWER;

/**
 * Created by anttiv on 27/02/16.
 */
public class UidValidator {

    private static final String TAG = UidValidator.class.getSimpleName();

    private UidValidator() {
    }

    static void validate(String deviceAddress, byte[] serviceData, Beacon beacon)  {
        beacon.hasUidFrame = true;

        // Tx power should have reasonable values.
        int txPower = (int) serviceData[1];
        beacon.uidStatus.txPower = txPower;
        if (txPower < MIN_EXPECTED_TX_POWER || txPower > MAX_EXPECTED_TX_POWER) {
            String err = String
                    .format("Expected UID Tx power between %d and %d, got %d", MIN_EXPECTED_TX_POWER,
                            MAX_EXPECTED_TX_POWER, txPower);
            beacon.uidStatus.errTx = err;
            logDeviceError(deviceAddress, err);
        }

        // The namespace and instance bytes should not be all zeroes.
        byte[] uidBytes = Arrays.copyOfRange(serviceData, 2, 18);
        byte[] namespaceBytes = Arrays.copyOfRange(serviceData, 2, 12);
        byte[] instanceBytes = Arrays.copyOfRange(serviceData, 12, 18);
        //the whole frame as string
        beacon.uidStatus.uidFrameValue = Utils.toHexString(serviceData);
        //subsections of the frame
        beacon.uidStatus.uidNamespaceValue = Utils.toHexString(Arrays.copyOfRange(serviceData, 2, 12));
        beacon.uidStatus.uidInstanceValue = Utils.toHexString(Arrays.copyOfRange(serviceData, 12, 18));

        if (Utils.isZeroed(namespaceBytes)) {
            String err = "UID bytes are all 0x00";
            beacon.uidStatus.errNamespace = err;
            logDeviceError(deviceAddress, err);
        }

        if (Utils.isZeroed(instanceBytes)) {
            String err = "UID bytes are all 0x00";
            beacon.uidStatus.errInstance = err;
            logDeviceError(deviceAddress, err);
        }

        // If we have a previous frame, verify the ID isn't changing.
        if (beacon.uidServiceData == null) {
            beacon.uidServiceData = serviceData.clone();
        } else {
            byte[] previousUidBytes = Arrays.copyOfRange(beacon.uidServiceData, 2, 18);
            if (!Arrays.equals(uidBytes, previousUidBytes)) {
                String err = String.format("UID should be invariant.\nLast: %s\nthis: %s",
                        Utils.toHexString(previousUidBytes),
                        Utils.toHexString(uidBytes));
                beacon.uidStatus.errUid = err;
                logDeviceError(deviceAddress, err);
                beacon.uidServiceData = serviceData.clone();
            }
        }

        // Last two bytes in frame are RFU and should be zeroed.
        byte[] rfu = Arrays.copyOfRange(serviceData, 18, 20);
        beacon.uidStatus.uidRfuValue = Utils.toHexString(rfu);

        if (rfu[0] != 0x00 || rfu[1] != 0x00) {
            String err = "Last two bytes of UID frame are expected to be 0x0000, were " + Utils.toHexString(rfu);
            beacon.uidStatus.errRfu = err;
            logDeviceError(deviceAddress, err);
        }
    }

    private static void logDeviceError(String deviceAddress, String err) {
        Log.e(TAG, deviceAddress + ": " + err);
    }
}