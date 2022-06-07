package me.cal1br.cargram.utils;

public enum ImageType {

    MOD_IMAGE("/mods"),
    CAR_IMAGE("/cars"),
    USER_IMAGE("/users");
    private final String path;

    ImageType(final String path) {
        this.path = path;
    }
}
