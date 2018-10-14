package com.luca.dgdev.retroarch.thumbnails.downloader;

public class PlayStationThumbnailDownloader extends ThumbnailDownloader {

    public PlayStationThumbnailDownloader() {
        playlistPath = "F:/data/games/Emulators/RetroArch/playlists/Sony - PlayStation.lpl";
        boxArtsThumbnailsDir = "F:/data/games/Emulators/RetroArch/thumbnails/Sony - PlayStation/Named_Boxarts/";
        titlesThumbnailsDir = "F:/data/games/Emulators/RetroArch/thumbnails/Sony - PlayStation/Named_Titles/";
        urlRootBoxarts = "https://raw.githubusercontent.com/libretro-thumbnails/Sony_-_PlayStation/master/Named_Boxarts/";
        urlRootTitles = "https://raw.githubusercontent.com/libretro-thumbnails/Sony_-_PlayStation/master/Named_Titles/";
        faildeDir = "c:/users/luca/desktop/play_station_failed/";
    }
}
