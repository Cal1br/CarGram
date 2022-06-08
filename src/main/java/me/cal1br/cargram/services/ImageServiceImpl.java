package me.cal1br.cargram.services;

import me.cal1br.cargram.exceptions.InvalidModelException;
import me.cal1br.cargram.models.PhotoUpload;
import me.cal1br.cargram.utils.ImageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
        final URI fileLocation = URI.create("file:///home/calibri/Projects/Java/CarGram/storage/cars/" + UUID.randomUUID() + ".img");
        final File file = new File(fileLocation); //todo get format?

        try (final FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(photoUpload.getFile().getBytes());
        } catch (IOException ioException) {
            LOGGER.error("An exception has occurred while processing: {} from user: ",fileLocation);//todo
        }
        return fileLocation.toString();
    }

    @Override
    public InputStream getImageInputStreamByLink(final String link) {
        if(!StringUtils.hasText(link)){
            LOGGER.warn("Requested image with an empty link!");
            throw new IllegalArgumentException("The provided link is empty!");
        }
        //TODO CHECK FILE TYPE link.endsWith() and more!
        try{
            return new
        }
        catch (IOException exception){
            LOGGER.error("Input output exception for link: {}",link);
        }
        throw new IllegalStateException("Couldn't find the image you were looking for...");
    }
}
