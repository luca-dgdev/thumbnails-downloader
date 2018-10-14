package com.luca.dgdev.retroarch.thumbnails.downloader;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        MameThumbnailDownloader mameThumbnailDownloader = new MameThumbnailDownloader();
        PlayStationThumbnailDownloader playStationThumbnailDownloader = new PlayStationThumbnailDownloader();
        try {
            // mameThumbnailDownloader.download();
            playStationThumbnailDownloader.download();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
