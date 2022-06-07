package me.cal1br.cargram.services;

import me.cal1br.cargram.models.PhotoUpload;
import me.cal1br.cargram.utils.ImageType;

public interface ImageService {
    /**
     * @returns path to file
     */
    String saveImage(final PhotoUpload photoUpload, ImageType type);
}
