package fr.enlight.anima.cardmodel.database;


import android.annotation.SuppressLint;
import android.arch.persistence.room.TypeConverter;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListTypeConverters {

    private static final String SEPARATOR = ";";
    private static final String KEY_SEPARATOR = "=";
    private static final String VALUE_SEPARATOR = "_";

    @TypeConverter
    public static String mapListIntToString(Map<Integer, List<Integer>> mapInt) {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Integer key : mapInt.keySet()) {
            List<Integer> value = mapInt.get(key);

            if (!first) {
                result.append(SEPARATOR);
            }
            first = false;
            result.append(key);
            result.append(KEY_SEPARATOR);
            for (int index = 0; index < value.size(); index++) {
                if(index != 0){
                    result.append(VALUE_SEPARATOR);
                }
                Integer integer = value.get(index);
                result.append(integer);
            }
        }
        return result.toString();
    }

    @SuppressLint("UseSparseArrays")
    @TypeConverter
    public static Map<Integer, List<Integer>> stringToMapListInt(String str) {
        Map<Integer, List<Integer>> result = new HashMap<>();
        if (TextUtils.isEmpty(str)) {
            return result;
        }

        String[] strSplit = str.split(SEPARATOR);
        for (String intStr : strSplit) {
            String[] keyValueSplit = intStr.split(KEY_SEPARATOR);
            int key = Integer.parseInt(keyValueSplit[0]);
            String[] valueSplit = keyValueSplit[1].split(VALUE_SEPARATOR);

            List<Integer> value = new ArrayList<>();
            for (String valueIntStr : valueSplit) {
                value.add(Integer.parseInt(valueIntStr));
            }
            result.put(key, value);
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
