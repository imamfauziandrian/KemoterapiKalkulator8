package com.kemoterapi.android.kalkulatorkemoterapi;

final class GfrUtils {

    private GfrUtils() {
    }

    static double getSelectedGfr(double gfrNormal, double gfrObese, boolean isObese) {
        return isObese ? gfrObese : gfrNormal;
    }
}
