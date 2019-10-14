package growthcraft.core.shared.utils;

import java.util.Arrays;

public class StringUtils {
    private StringUtils() {
    }

    public static String capitalize(String str) {
        if (str.length() <= 1) {
            return str.toUpperCase();
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
    }

    public static String inspect(Object obj) {
        if (obj == null) {
            return "@null";
        } else if (obj.getClass().isArray()) {
            if (obj.getClass().getComponentType().isArray()) {
                return Arrays.deepToString((Object[][]) obj);
            } else {
                return Arrays.toString((Object[]) obj);
            }
        }
        return obj.toString();
    }

    public static boolean isIDInList(int id, String list) {
        final String[] itemArray = list.split(";");
        for (int i = 0; i < itemArray.length; i++) {
            final String[] values = itemArray[i].split(",");
            final int tempID = parseInt(values[0], 2147483647);

            if (tempID != 2147483647) {
                if (tempID == id) return true;
            }
        }
        return false;
    }

    public static int parseInt(String string, int defaultValue) {
        try {
            return Integer.parseInt(string.trim());
        } catch (NumberFormatException ex) {
        }
        return defaultValue;
    }
}