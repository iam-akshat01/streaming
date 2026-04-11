package com.akshat.streaming.controller;

import com.akshat.streaming.service.VideoService;
import com.akshat.streaming.model.Video;
import com.akshat.streaming.service.StreamingService;

import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/videos")
public class VideoController {

    private final VideoService service;
    private final StreamingService streamingService;

    public VideoController(VideoService service, StreamingService streamingService) {
        this.service = service;
        this.streamingService = streamingService;
    }

    @GetMapping
    public String test() {
        return "API working 🚀";
    }

    @PostMapping
    public String addVideo(@RequestBody Video video) {
        service.addVideo(video);
        return "Video added successfully";
    }

    @GetMapping("/all")
    public ArrayList<Video> getAllVideos() {
        return service.getAllVideos();
    }

    @GetMapping("/{id}")
    public Video getVideoById(@PathVariable int id) {
        return service.getVideoById(id);
    }

    // ✅ FIXED STREAM ENDPOINT
    @GetMapping("/{id}/stream")
    public void streamVideo(
            @PathVariable int id,
            @RequestHeader(value = "Range", required = false) String range,
            HttpServletResponse response
    ) throws IOException {

        Video v = service.getVideoById(id);

        if (v == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        streamingService.streamVideo(v, range, response);
        
    }
}