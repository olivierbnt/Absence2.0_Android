package com.rss.oc.www.absences20.activity;

/**
 * Created by anttiv on 09/02/16.
 */
class Beacon {
    private static final String BULLET = "● ";
    final String deviceAddress;
    int rssi;
    // TODO: rename to make explicit the validation intent of this timestamp. We use it to
    // remember a recent frame to make sure that non-monotonic TLM values increase.
    long timestamp = System.currentTimeMillis();

    // Used to remove devices from the listview when they haven't been seen in a while.
    long lastSeenTimestamp = System.currentTimeMillis();

    byte[] uidServiceData;
    byte[] tlmServiceData;
    byte[] urlServiceData;

    class UidStatus {
        String uidFrameValue;
        //Strings below are substrings of uidFrameValue
        String uidNamespaceValue;
        String uidInstanceValue;
        String uidRfuValue;

        int txPower;

        String errTx;
        String errUid;
        String errRfu;
        String errNamespace;
        String errInstance;

        public String getErrors() {
            StringBuilder sb = new StringBuilder();
            if (errTx != null) {
                sb.append(BULLET).append(errTx).append("\n");
            }
            if (errNamespace != null) {
                sb.append(BULLET).append(errNamespace).append("\n");
            }
            if (errInstance != null) {
                sb.append(BULLET).append(errInstance).append("\n");
            }
            if (errUid != null) {
                sb.append(BULLET).append(errUid).append("\n");
            }
            if (errRfu != null) {
                sb.append(BULLET).append(errRfu).append("\n");
            }
            return sb.toString().trim();
        }
    }

    class TlmStatus {
        String version;
        String voltage;
        String temp;
        String advCnt;
        String secCnt;

        String errIdentialFrame;
        String errVersion;
        String errVoltage;
        String errTemp;
        String errPduCnt;
        String errSecCnt;
        String errRfu;

        public String getErrors() {
            StringBuilder sb = new StringBuilder();
            if (errIdentialFrame != null) {
                sb.append(BULLET).append(errIdentialFrame).append("\n");
            }
            if (errVersion != null) {
                sb.append(BULLET).append(errVersion).append("\n");
            }
            if (errVoltage != null) {
                sb.append(BULLET).append(errVoltage).append("\n");
            }
            if (errTemp != null) {
                sb.append(BULLET).append(errTemp).append("\n");
            }
            if (errPduCnt != null) {
                sb.append(BULLET).append(errPduCnt).append("\n");
            }
            if (errSecCnt != null) {
                sb.append(BULLET).append(errSecCnt).append("\n");
            }
            if (errRfu != null) {
                sb.append(BULLET).append(errRfu).append("\n");
            }
            return sb.toString().trim();
        }

        @Override
        public String toString() {
            return getErrors();
        }
    }

    class UrlStatus {
        String urlValue;
        String urlNotSet;
        String txPower;

        public String getErrors() {
            StringBuilder sb = new StringBuilder();
            if (txPower != null) {
                sb.append(BULLET).append(txPower).append("\n");
            }
            if (urlNotSet != null) {
                sb.append(BULLET).append(urlNotSet).append("\n");
            }
            return sb.toString().trim();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (urlValue != null) {
                sb.append(urlValue).append("\n");
            }
            return sb.append(getErrors()).toString().trim();
        }
    }

    class FrameStatus {
        String nullServiceData;
        String tooShortServiceData;
        String invalidFrameType;

        public String getErrors() {
            StringBuilder sb = new StringBuilder();
            if (nullServiceData != null) {
                sb.append(BULLET).append(nullServiceData).append("\n");
            }
            if (tooShortServiceData != null) {
                sb.append(BULLET).append(tooShortServiceData).append("\n");
            }
            if (invalidFrameType != null) {
                sb.append(BULLET).append(invalidFrameType).append("\n");
            }
            return sb.toString().trim();
        }

        @Override
        public String toString() {
            return getErrors();
        }
    }

    boolean hasUidFrame;
    UidStatus uidStatus = new UidStatus();

    boolean hasTlmFrame;
    TlmStatus tlmStatus = new TlmStatus();

    boolean hasUrlFrame;
    UrlStatus urlStatus = new UrlStatus();

    FrameStatus frameStatus = new FrameStatus();

    Beacon(String deviceAddress, int rssi) {
        this.deviceAddress = deviceAddress;
        this.rssi = rssi;
    }

    /**
     * Performs a case-insensitive contains test of s on the device address (with or without the
     * colon separators) and/or the UID value, and/or the URL value.
     */
    boolean contains(String s) {
        return s == null
                || s.isEmpty()
                || deviceAddress.replace(":", "").toLowerCase().contains(s.toLowerCase())
                || (uidStatus.uidFrameValue != null
                && uidStatus.uidFrameValue.toLowerCase().contains(s.toLowerCase()))
                || (urlStatus.urlValue != null
                && urlStatus.urlValue.toLowerCase().contains(s.toLowerCase()));
    }
}