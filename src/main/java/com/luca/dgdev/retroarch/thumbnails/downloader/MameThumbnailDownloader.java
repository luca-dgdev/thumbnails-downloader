package com.luca.dgdev.retroarch.thumbnails.downloader;

public class MameThumbnailDownloader extends ThumbnailDownloader {

    public MameThumbnailDownloader() {
        playlistPath = "F:/data/games/Emulators/RetroArch/playlists/MAME.lpl";
        boxArtsThumbnailsDir = "F:/data/games/Emulators/RetroArch/thumbnails/MAME/Named_Boxarts/";
        titlesThumbnailsDir = "F:/data/games/Emulators/RetroArch/thumbnails/MAME/Named_Titles/";
        urlRootBoxarts = "https://raw.githubusercontent.com/libretro-thumbnails/MAME/master/Named_Boxarts/";
        urlRootTitles = "https://raw.githubusercontent.com/libretro-thumbnails/MAME/master/Named_Titles/";
        faildeDir = "c:/users/luca/desktop/mame_failed/";
    }
}
