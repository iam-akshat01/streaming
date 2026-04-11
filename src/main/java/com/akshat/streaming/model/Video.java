package com.akshat.streaming.model;

public class Video {
    private int videoId;
    private String description;
    private String title;
    private String url;
    private String creator;
    private double duration; 
    private double size;

    // REQUIRED for Spring (JSON parsing)
    public Video() {}

    public Video(int videoId, String description, String title, String url, String creator, double duration, double size) {
        this.videoId = videoId;
        this.description = description;
        this.title = title;
        this.url = url;
        this.creator = creator;
        this.duration = duration;
        this.size = size;
    }

    public int getVideoId() { return videoId; }
    public String getDescription() { return description; }
    public String getTitle() { return title; }
    public String getUrl() { return url; }
    public String getCreator() { return creator; }
    public double getDuration() { return duration; }
    public double getSize() { return size; }

    public void setVideoId(int videoId) { this.videoId = videoId; }
    public void setDescription(String description) { this.description = description; }
    public void setTitle(String title) { this.title = title; }
    public void setUrl(String url) { this.url = url; }
    public void setCreator(String creator) { this.creator = creator; }
    public void setDuration(double duration) { this.duration = duration; }
    public void setSize(double size) { this.size = size; }

    @Override
    public String toString() {
        return videoId + "," + title + "," + description + "," + url + "," + creator + "," + duration + "," + size;
    }
}