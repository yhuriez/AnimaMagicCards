package fr.enlight.anima.cardmodel.database;


import android.annotation.SuppressLint;
import android.arch.persistence.room.TypeConverter;
import android.content.Intent;
import android.text.TextUtils;
import android.util.SparseIntArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListTypeConverters {

    private static final String SEPARATOR = ";";
    private static final String VALUE_SEPARATOR = "_";

    @TypeConverter
    public static String listIntToString(List<Integer> listInt) {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Integer integer : listInt) {
            if (!first) {
                result.append(SEPARATOR);
            }
            first = false;
            result.append(integer);

        }
        return result.toString();
    }

    @TypeConverter
    public static List<Integer> stringToListInt(String str) {
        if (TextUtils.isEmpty(str)) {
            return Collections.emptyList();
        }

        List<Integer> result = new ArrayList<>();
        String[] strSplit = str.split(SEPARATOR);
        for (String intStr : strSplit) {
            result.add(Integer.parseInt(intStr));
        }
        return result;
    }

    @TypeConverter
    public static String mapIntToString(Map<Integer, Integer> mapInt) {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Integer key : mapInt.keySet()) {
            Integer value = mapInt.get(key);

            if (!first) {
                result.append(SEPARATOR);
            }
            first = false;
            result.append(key);
            result.append(VALUE_SEPARATOR);
            result.append(value);
        }
        return result.toString();
    }

    @SuppressLint("UseSparseArrays")
    @TypeConverter
    public static Map<Integer, Integer> stringToMapInt(String str) {
        Map<Integer, Integer> result = new HashMap<>();
        if (TextUtils.isEmpty(str)) {
            return result;
        }

        String[] strSplit = str.split(SEPARATOR);
        for (String intStr : strSplit) {
            String[] keyValueSplit = intStr.split(VALUE_SEPARATOR);
            int key = Integer.parseInt(keyValueSplit[0]);
            int value = Integer.parseInt(keyValueSplit[1]);
            result.put(key, value);
        }
        return result;
    }
}
