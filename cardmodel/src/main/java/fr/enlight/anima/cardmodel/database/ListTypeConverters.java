package fr.enlight.anima.cardmodel.database;


import android.arch.persistence.room.TypeConverter;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListTypeConverters {

    private static final String SEPARATOR = ";";

    @TypeConverter
    public static String listIntToString(List<Integer> listInt){
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Integer integer : listInt) {
            if(!first){
                result.append(SEPARATOR);
            }
            first = false;
            result.append(integer);

        }
        return result.toString();
    }

    @TypeConverter
    public static List<Integer> stringToListInt(String str){
        if(TextUtils.isEmpty(str)){
            return Collections.emptyList();
        }

        List<Integer> result = new ArrayList<>();
        String[] strSplit = str.split(SEPARATOR);
        for (String intStr : strSplit) {
            result.add(Integer.parseInt(intStr));
        }
        return result;
    }
}
