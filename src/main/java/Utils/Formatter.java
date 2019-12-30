package Utils;

import Data.CommonValues;
import Data.Data;

public class Formatter {

    private static CommonValues cv = Data.getInstance().getCommonValues();

    public enum modeState {simple, explicit}

    public static modeState mode = modeState.explicit;

    public static String coordinates(double x, double y) {
        String string;
        switch (mode) {
            case simple:
                string = coordinates_simple(x, y);
                break;
            case explicit:
                string = coordinates_explicit(x, y);
                break;
            default:
                string = coordinates_simple(x, y);
                break;
        }
        return string;
    }

    public static String coordinates(double x, double y, double theta) {
        String string;
        switch (mode) {
            case simple:
                string = coordinates_simple(x, y, theta);
                break;
            case explicit:
                string = coordinates_explicit(x, y, theta);
                break;
            default:
                string = coordinates_simple(x, y, theta);
                break;
        }
        return string;
    }


    public static String coordinates_simple(double x, double y, double theta) {
        return "[" + cv.decimalFormat.format(x) + "|" + cv.decimalFormat.format(y) + "|" + cv.decimalFormat.format(theta) + "]";
    }

    public static String coordinates_simple(double x, double y) {
        return "[" + cv.decimalFormat.format(x) + "|" + cv.decimalFormat.format(y) + "]";
    }

    public static String coordinates_explicit(double x, double y, double theta) {
        return "x: " + cv.decimalFormat.format(x) + ", y: " + cv.decimalFormat.format(y) + ", t: " + cv.decimalFormat.format(theta);
    }

    public static String coordinates_explicit(double x, double y) {
        return "x: " + cv.decimalFormat.format(x) + ", y: " + cv.decimalFormat.format(y);
    }


}
