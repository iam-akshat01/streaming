package com.akshat.streaming.service;

import com.akshat.streaming.repository.VideoRepository;
import com.akshat.streaming.model.Video;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class VideoService {

    private final VideoRepository repo;

    public VideoService(VideoRepository repo) {
        this.repo = repo;
    }

    public void addVideo(Video video) {
        repo.saveVideo(video);
    }

    public ArrayList<Video> getAllVideos() {
        return repo.getAllVideos();
    }
    public Video getVideoById(int id) {
        return repo.getVideoById(id);
    }
}