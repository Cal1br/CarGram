package me.cal1br.cargram.services;

import me.cal1br.cargram.exceptions.InvalidModelException;
import me.cal1br.cargram.models.PhotoUpload;
import me.cal1br.cargram.utils.ImageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Override
    public String saveImage(final PhotoUpload photoUpload, final ImageType type) {
        if (photoUpload.getFile() == null || photoUpload.getFile().isEmpty()) {
            throw new InvalidModelException("Photo can't be empty!");
        }
        final URI fileLocation = URI.create("storage/cars/" + UUID.randomUUID() + ".img");
        final File file = new File(fileLocation); //todo get format?

        try (final FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(photoUpload.getFile().getBytes());
        } catch (IOException ioException) {
            LOGGER.error("An exception has occurred while processing: " + fileLocation + " from user: ");//todo
        }
        return fileLocation.toString();
    }
}
