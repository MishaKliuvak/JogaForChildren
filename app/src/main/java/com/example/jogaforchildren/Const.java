package com.example.jogaforchildren;

import com.example.jogaforchildren.Poses.Pose;

import java.util.ArrayList;
import java.util.List;

public class Const {
    private final static List<Pose> listEasy = new ArrayList<>();
    private final static List<Pose> listMedium = new ArrayList<>();
    private final static List<Pose> listHigh = new ArrayList<>();
    private static float width = 0;
    private static float height = 0;

    public static float getWidth() {
        return width;
    }

    public static void setWidth(float w) {
        width = w;
    }

    public static float getHeight() {
        return height;
    }

    public static String getStringURL(String w, String h, String link){
        return  "<html><body style='margin:0;padding:0;'> <iframe width=\"" + w + "\" height=\"" + h + "\" src=\"" + link +
                "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
    }

    public static void setHeight(float h) {
        height = h;
    }

    public static void addItem(Pose p){
        if (p.getLevel().equals("Початковий")) listEasy.add(p);
        else if (p.getLevel().equals("Середній")) listMedium.add(p);
        else listHigh.add(p);
    }

    public static List<Pose> getListEasy(){
        return listEasy;
    }
    public static List<Pose> getListMedium(){
        return listMedium;
    }
    public static List<Pose> getListHigh(){
        return listHigh;
    }



}
