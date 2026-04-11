package com.akshat.streaming.service;

import com.akshat.streaming.model.Video;
import com.akshat.streaming.util.RangeUtil;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class StreamingService {

    private static final int BUFFER_SIZE = 8 * 1024; // 8KB
    private static final long MAX_CHUNK_SIZE = 2 * 1024* 1024; // 2MB

    private final RangeUtil rangeUtil = new RangeUtil();

    public void streamVideo(Video v, String rangeHeader, HttpServletResponse response) throws IOException {

        File videoFile = new File(v.getUrl());

        if (!videoFile.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        long fileSize = videoFile.length();

        long start;
        long end;
        boolean isPartial = false;

        try {
            long[] range = rangeUtil.parseRangeHeader(rangeHeader, fileSize);
            start = range[0];
            end = range[1];

            if (rangeHeader != null) {
                isPartial = true;
            }

        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
            return;
        }

        // 🔥 Apply chunk cap ONLY for partial requests
        if (isPartial) {
            long maxEnd = start + MAX_CHUNK_SIZE - 1;
            if (end > maxEnd) {
                end = maxEnd;
            }
        }

        long contentLength = end - start + 1;
        System.out.println(contentLength);

        // 🔥 Headers FIRST
        response.setContentType("video/mp4");
        response.setHeader("Accept-Ranges", "bytes");

        if (isPartial) {
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            response.setHeader("Content-Range", "bytes " + start + "-" + end + "/" + fileSize);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }

        response.setHeader("Content-Length", String.valueOf(contentLength));

        // 🔥 Stream data
        try (RandomAccessFile raf = new RandomAccessFile(videoFile, "r");
             OutputStream out = response.getOutputStream()) {

            raf.seek(start);

            byte[] buffer = new byte[BUFFER_SIZE];
            long remaining = contentLength;

            while (remaining > 0) {

                int bytesToRead = (int) Math.min(buffer.length, remaining);
                int bytesRead = raf.read(buffer, 0, bytesToRead);

                if (bytesRead == -1) break;

                out.write(buffer, 0, bytesRead);
                remaining -= bytesRead;
            }

            out.flush();
        }
    }
}