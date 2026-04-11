package com.akshat.streaming.service;

import com.akshat.streaming.model.Video;
import com.akshat.streaming.util.RangeUtil;

import jakarta.servlet.http.HttpServletResponse;

import java.io.*;

import org.springframework.stereotype.Service;
@Service
public class StreamingService {

    private static final int BUFFER_SIZE = 8 * 1024; // 8KB
    private static final long MAX_CHUNK_SIZE = 2 * 1024 * 1024; // 2MB

    public void streamVideo(Video v, String range, HttpServletResponse response) throws IOException {

        File videoFile = new File(v.getUrl());

        if (!videoFile.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        long fileSize = videoFile.length();

        long start = 0;
        long end = fileSize - 1;
        boolean isPartial = false;

        // 🔥 Handle Range
        if (range != null && range.startsWith("bytes=")) {
            isPartial = true;

            String[] parts = range.substring(6).split("-");
            try {
                if (!parts[0].isEmpty()) {
                    start = Long.parseLong(parts[0]);
                }
                if (!parts[1].isEmpty()) {
                    end = Long.parseLong(parts[1]);
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // 🔥 Validation
            if (start >= fileSize || start > end) {
                response.setStatus(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                return;
            }

            // 🔥 Cap chunk size
            long maxEnd = start + MAX_CHUNK_SIZE - 1;
            if (end > maxEnd) {
                end = maxEnd;
            }

            if (end >= fileSize) {
                end = fileSize - 1;
            }
        }

        long contentLength = end - start + 1;

        // 🔥 Set headers BEFORE streaming
        response.setContentType("video/mp4");
        response.setHeader("Accept-Ranges", "bytes");

        if (isPartial) {
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            response.setHeader("Content-Range", "bytes " + start + "-" + end + "/" + fileSize);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }

        response.setHeader("Content-Length", String.valueOf(contentLength));

        // 🔥 Streaming
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