package com.gallery.commn;

public enum FileUploadType {
    ARTIST_PROFILE("artist/profile"),
    ARTWORK("artist/artwork"),
    EXHIBITION("artist/exhibition"),
    ARTICLE("artist/article");

    private final String path;

    FileUploadType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
