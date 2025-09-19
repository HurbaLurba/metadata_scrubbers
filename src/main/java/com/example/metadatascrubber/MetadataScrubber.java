package com.example.metadatascrubber;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MetadataScrubber {

    public void process(File input, File outputDir, boolean recursive) throws IOException {
        if (input.isDirectory()) {
            processDirectory(input, outputDir, recursive);
        } else {
            processFile(input, outputDir);
        }
    }

    private void processDirectory(File dir, File outputDir, boolean recursive) throws IOException {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory() && recursive) {
                    File newOutputDir = outputDir != null ? new File(outputDir, file.getName()) : null;
                    processDirectory(file, newOutputDir, recursive);
                } else if (file.isFile()) {
                    processFile(file, outputDir);
                }
            }
        }
    }

    private void processFile(File file, File outputDir) throws IOException {
        String extension = getFileExtension(file).toLowerCase();

        File outputFile;
        if (outputDir != null) {
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            outputFile = new File(outputDir, file.getName());
        } else {
            outputFile = file; // Overwrite original
        }

        if (isImage(extension)) {
            scrubImageMetadata(file, outputFile);
        } else if (isVideo(extension)) {
            scrubVideoMetadata(file, outputFile);
        } else {
            System.out.println("Unsupported file type: " + file.getName());
        }
    }

    private boolean isImage(String extension) {
        return extension.matches("jpg|jpeg|png|gif|bmp|tiff|webp");
    }

    private boolean isVideo(String extension) {
        return extension.matches("mp4|avi|mkv|mov|wmv|flv");
    }

    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndex = name.lastIndexOf('.');
        return lastIndex > 0 ? name.substring(lastIndex + 1) : "";
    }

    private void scrubImageMetadata(File input, File output) throws IOException {
        try {
            // Read image using ImageIO (automatically strips metadata)
            BufferedImage image = ImageIO.read(input);

            if (image == null) {
                throw new IOException("Cannot read image: " + input.getName());
            }

            // Determine format
            String extension = getFileExtension(input).toLowerCase();
            String formatName = extension.equals("jpg") ? "jpeg" : extension;

            // Write image without metadata
            boolean success = ImageIO.write(image, formatName, output);
            if (!success) {
                throw new IOException("Failed to write image: " + output.getName());
            }

            System.out.println("Scrubbed image: " + output.getName());
        } catch (Exception e) {
            throw new IOException("Error scrubbing image metadata: " + e.getMessage(), e);
        }
    }

    private void scrubVideoMetadata(File input, File output) throws IOException {
        try {
            // Use FFmpeg to strip metadata
            ProcessBuilder pb = new ProcessBuilder(
                "ffmpeg", "-i", input.getAbsolutePath(),
                "-map_metadata", "-1",
                "-c:v", "copy",
                "-c:a", "copy",
                "-y", // Overwrite output files
                output.getAbsolutePath()
            );

            pb.redirectErrorStream(true);
            Process process = pb.start();

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Scrubbed video: " + output.getName());
            } else {
                throw new IOException("FFmpeg failed with exit code: " + exitCode);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted while scrubbing video", e);
        } catch (Exception e) {
            throw new IOException("Error scrubbing video metadata: " + e.getMessage(), e);
        }
    }
}
