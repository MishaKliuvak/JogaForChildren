package com.example.jogaforchildren.Poses;

public class Pose {
    private String name;
    private String level;
    private String icon;
    private String time;
    private String video;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Pose() {
    }

    public Pose(String icon, String level, String name, String time, String video) {
        this.name = name;
        this.level = level;
        this.icon = icon;
        this.time = time;
        this.video = video;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String image) {
        this.icon = image;
    }
}
