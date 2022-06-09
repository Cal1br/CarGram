package me.cal1br.cargram.utils;

import java.io.File;

public enum ImageType {

    MOD_IMAGE("mods"),
    CAR_IMAGE("cars"),
    USER_IMAGE("users");
    private final String path;

    ImageType(final String path) {
        this.path = path + File.separator;
    }

    public String getPath() {
        return path;
    }
}
