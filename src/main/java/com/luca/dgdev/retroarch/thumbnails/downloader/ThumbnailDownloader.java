package com.luca.dgdev.retroarch.thumbnails.downloader;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class ThumbnailDownloader {

    protected String playlistPath;
    protected List<String> failedBoxarts = new ArrayList<>();
    protected List<String> failedTitles = new ArrayList<>();
    protected String boxArtsThumbnailsDir;
    protected String titlesThumbnailsDir;
    protected String urlRootBoxarts;
    protected String urlRootTitles;
    protected String faildeDir;
    protected int connectionTimeout = 0;
    protected int readTimeout = 0;

    public void download() throws IOException {
        List<String> lines = FileUtils.readLines(new File(playlistPath), "UTF-8");
        for (int i = 0; i < lines.size(); i++) {
            if ((i - 1) % 6 == 0) {
                System.out.println(lines.get(i));
                downloadBoxart(lines.get(i));
                downloadTitle(lines.get(i));
            }
        }

        if (!failedBoxarts.isEmpty()) {
            FileUtils.writeLines(new File(faildeDir + "failed_boxarts_" + currentTimestamp() + ".txt"), failedBoxarts);
        }

        if (!failedTitles.isEmpty()) {
            FileUtils.writeLines(new File(faildeDir + "failed_titles_" + currentTimestamp() + ".txt"), failedTitles);
        }
    }


    private void downloadBoxart(String fileName) throws IOException {
        String convertedFileName = convertFileName(fileName);
        String urlFileName = convertUrlFileName(convertedFileName);
        String downloadLink = urlRootBoxarts + urlFileName;
        try {
            if (!Files.exists(Paths.get(boxArtsThumbnailsDir + convertedFileName))) {
                FileUtils.copyURLToFile(
                        new URL(downloadLink),
                        new File(boxArtsThumbnailsDir + convertedFileName),
                        connectionTimeout,
                        readTimeout);
            }
        } catch (IOException e) {
            failedBoxarts.add(downloadLink);
            e.printStackTrace();
        }
    }

    private void downloadTitle(String fileName) throws IOException {
        String convertedFileName = convertFileName(fileName);
        String urlFileName = convertUrlFileName(convertedFileName);
        String downloadLink = urlRootTitles + urlFileName;
        try {
            if (!Files.exists(Paths.get(titlesThumbnailsDir + convertedFileName))) {
                FileUtils.copyURLToFile(
                        new URL(downloadLink),
                        new File(titlesThumbnailsDir + convertedFileName),
                        connectionTimeout,
                        readTimeout);
            }
        } catch (IOException e) {
            failedTitles.add(downloadLink);
            e.printStackTrace();
        }
    }

    private String convertFileName(String fileName) {
        return fileName.replace(":", "_").replace("/", "_") + ".png";
    }

    private String convertUrlFileName(String fileName) throws UnsupportedEncodingException {
        return URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
    }

    private String currentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }
}
