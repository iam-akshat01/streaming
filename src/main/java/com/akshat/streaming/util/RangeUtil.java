package com.akshat.streaming.util;

public class RangeUtil {

    public long[] parseRangeHeader(String rangeHeader, long fileSize) {

        // 🔹 No range → full file
        if (rangeHeader == null || rangeHeader.isEmpty()) {
            return new long[]{0, fileSize - 1};
        }

        if (!rangeHeader.startsWith("bytes=")) {
            throw new IllegalArgumentException("Invalid Range header format");
        }

        String rangeValue = rangeHeader.substring(6);
        String[] parts = rangeValue.split("-");

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid Range format");
        }

        long start;
        long end;

        try {

            // 🔹 Case: bytes=500-1000
            if (!parts[0].isEmpty() && !parts[1].isEmpty()) {
                start = Long.parseLong(parts[0]);
                end = Long.parseLong(parts[1]);
            }

            // 🔹 Case: bytes=500-
            else if (!parts[0].isEmpty()) {
                start = Long.parseLong(parts[0]);
                end = fileSize - 1;
            }

            // 🔹 Case: bytes=-500 (last 500 bytes)
            else if (!parts[1].isEmpty()) {
                long suffixLength = Long.parseLong(parts[1]);
                start = fileSize - suffixLength;
                end = fileSize - 1;
            }

            else {
                throw new IllegalArgumentException("Invalid Range values");
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number in Range header");
        }

        // 🔥 VALIDATION

        if (start < 0) start = 0;

        if (end >= fileSize) end = fileSize - 1;

        if (start > end || start >= fileSize) {
            throw new IllegalArgumentException("Range Not Satisfiable");
        }

        return new long[]{start, end};
    }
}