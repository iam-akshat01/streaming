package com.akshat.streaming.util;

public class RangeUtil {

    public long[] parseRangeHeader(String rangeHeader, long fileSize) {

        if (rangeHeader == null || rangeHeader.isEmpty()) {
            return new long[]{0, fileSize - 1};
        }

        if (!rangeHeader.startsWith("bytes=")) {
            throw new IllegalArgumentException("Invalid Range prefix");
        }

        String[] parts = rangeHeader.substring(6).split("-",2);

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid Range format");
        }

        long start;
        long end;

        try {

            // bytes=500-1000
            if (!parts[0].isEmpty() && !parts[1].isEmpty()) {
                start = Long.parseLong(parts[0]);
                end = Long.parseLong(parts[1]);
            }

            // bytes=500-
            else if (!parts[0].isEmpty()) {
                start = Long.parseLong(parts[0]);
                end = fileSize - 1;
            }

            // bytes=-500
            else if (!parts[1].isEmpty()) {
                long suffixLength = Long.parseLong(parts[1]);

                if (suffixLength <= 0) {
                    throw new IllegalArgumentException("Invalid suffix length");
                }

                suffixLength = Math.min(suffixLength, fileSize);

                start = fileSize - suffixLength;
                end = fileSize - 1;
            }

            else {
                throw new IllegalArgumentException("Invalid Range values");
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric values in Range");
        }

        // 🔥 STRICT VALIDATION

        if (start < 0 || start >= fileSize) {
            throw new IllegalArgumentException("Range start out of bounds");
        }

        if (end < start) {
            throw new IllegalArgumentException("Range end before start");
        }

        if (end >= fileSize) {
            end = fileSize - 1;
        }

        return new long[]{start, end};
    }
}