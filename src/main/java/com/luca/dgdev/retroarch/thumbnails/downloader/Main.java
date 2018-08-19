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

public class Main {

    private static final String FILE_URL = "https://raw.githubusercontent.com/libretro-thumbnails/MAME/master/Named_Boxarts/1941_%20Counter%20Attack%20(World).png";
    private static final String FILE_NAME = "c:/users/luca/desktop/1941_ Counter Attack (World).png";
    private static final int CONNECT_TIMEOUT = 0;
    private static final int READ_TIMEOUT = 0;
    private static final String PLAYLIST_FILE = "F:/data/games/Emulators/RetroArch-1.7.2/playlists/MAME.lpl";
    private static final String BOXARTS_THUMBNAILS_DIR = "F:/data/games/Emulators/RetroArch-1.7.2/thumbnails/MAME/Named_Boxarts/";
    private static final String TITLES_THUMBNAILS_DIR = "F:/data/games/Emulators/RetroArch-1.7.2/thumbnails/MAME/Named_Titles/";
    private static final String URL_ROOT_BOXARTS = "https://raw.githubusercontent.com/libretro-thumbnails/MAME/master/Named_Boxarts/";
    private static final String URL_ROOT_TITLES = "https://raw.githubusercontent.com/libretro-thumbnails/MAME/master/Named_Titles/";
    private static final String FAILED_DIR = "c:/users/luca/desktop/";

    public static void main(String[] args) {
        System.out.println("TEST");
        try {
            List<String> lines = FileUtils.readLines(new File(PLAYLIST_FILE), "UTF-8");
            List<String> failedBoxarts = new ArrayList<String>();
            List<String> failedTitles = new ArrayList<String>();
            for (int i = 0; i < lines.size(); i++) {
                if ((i - 1) % 6 == 0) {
                    System.out.println(lines.get(i));
                    try {
                        downloadBoxart(lines.get(i));
                    } catch (IOException e) {
                        failedBoxarts.add(lines.get(i));
                        e.printStackTrace();
                    }

                    try {
                        downloadTitle(lines.get(i));
                    } catch (IOException e) {
                        failedTitles.add(lines.get(i));
                        e.printStackTrace();
                    }
                }
            }

            if (!failedBoxarts.isEmpty()) {
                FileUtils.writeLines(new File(FAILED_DIR + "failed_boxarts_" + currentTimestamp() + ".txt"), failedBoxarts);
            }

            if (!failedTitles.isEmpty()) {
                FileUtils.writeLines(new File(FAILED_DIR + "failed_titles_" + currentTimestamp() + ".txt"), failedTitles);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void downloadBoxart(String fileName) throws IOException {

        String convertedFileName = convertFileName(fileName);
        String urlFileName = convertUrlFileName(convertedFileName);

        if (!Files.exists(Paths.get(BOXARTS_THUMBNAILS_DIR + convertedFileName))) {
            FileUtils.copyURLToFile(
                    new URL(URL_ROOT_BOXARTS + urlFileName),
                    new File(BOXARTS_THUMBNAILS_DIR + convertedFileName),
                    CONNECT_TIMEOUT,
                    READ_TIMEOUT);
        }
    }

    private static void downloadTitle(String fileName) throws IOException {

        String convertedFileName = convertFileName(fileName);
        String urlFileName = convertUrlFileName(convertedFileName);

        if (!Files.exists(Paths.get(TITLES_THUMBNAILS_DIR + convertedFileName))) {
            FileUtils.copyURLToFile(
                    new URL(URL_ROOT_TITLES + urlFileName),
                    new File(TITLES_THUMBNAILS_DIR + convertedFileName),
                    CONNECT_TIMEOUT,
                    READ_TIMEOUT);
        }
    }

    private static String convertFileName(String fileName) {
        return fileName.replace(":", "_").replace("/", "_") + ".png";
    }

    private static String convertUrlFileName(String fileName) throws UnsupportedEncodingException {
        return URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
    }

    private static void testDownload() throws IOException {
        FileUtils.copyURLToFile(
                new URL(FILE_URL),
                new File(FILE_NAME),
                CONNECT_TIMEOUT,
                READ_TIMEOUT);
    }

    private static String currentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

}
