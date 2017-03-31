package com.weiruanit.lifepro.module;

import com.github.promeg.pinyinhelper.Pinyin;
import com.weiruanit.lifepro.module.ticket.city.City;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * 获取城市的工具类
 * Created by Administrator on 2016/11/15 0015.
 */

public class CityGetUtils {

    /**
     *
     * 获取文件输入流
     *
     * @param inputStream
     * @return
     */
    public static String getCityInfo(InputStream inputStream){
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<City> getCityList(String data){

        ArrayList<City> cityList = new ArrayList<>();
        if (data != null){
            try {
                JSONArray jsonArray = new JSONArray(data);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    City city = new City();
                    city.setCityName(jsonObject1.getString("cityZh"));
                    city.setCityPinyin(transformPinYin(jsonObject1.getString("cityZh")));
                    cityList.add(city);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return cityList;
        }
       return null;
    }

    //拼音的转换
    public static String transformPinYin(String character) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < character.length(); i++) {
            buffer.append(Pinyin.toPinyin(character.charAt(i)));
        }
        return buffer.toString();
    }
}
