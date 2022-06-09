package me.cal1br.cargram.services;

import me.cal1br.cargram.exceptions.InvalidModelException;
import me.cal1br.cargram.models.PhotoUpload;
import me.cal1br.cargram.utils.ImageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);

    private final String imageLocationPrefix;

    public ImageServiceImpl(@Value("${image.location}") final String fileLocation) {
        this.imageLocationPrefix = fileLocation;
    }

    @Override
    public String saveImage(final PhotoUpload photoUpload, final ImageType type) {
        final String contentType = photoUpload.getFile().getContentType();
        if (photoUpload.getFile() == null || photoUpload.getFile().isEmpty()) {
            throw new InvalidModelException("Photo can't be empty!");
        }
        if (contentType == null || !contentType.contains("/")) {
            throw new InvalidModelException("Photo must contain content type!");
        }

        final String imgSuffix = type.getPath() + UUID.randomUUID() + '.' + contentType.substring(contentType.indexOf('/') + 1); //todo is this format good enough?
        final File file = new File(imageLocationPrefix + imgSuffix); //todo get format?

        try (final FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(photoUpload.getFile().getBytes());
        } catch (IOException ioException) {
            LOGGER.error("An exception has occurred while processing: {} from user: ", imgSuffix);//todo
        }
        return imgSuffix;
    }

    @Override
    public InputStream getImageInputStreamByLink(final String suffix) {
        if (!StringUtils.hasText(suffix)) {
            LOGGER.warn("Requested image with an empty link!");
            throw new IllegalArgumentException("The provided link is empty!");
        }
        //TODO CHECK FILE TYPE link.endsWith() and more!
        try {

            return new FileInputStream(new File("storage/" + suffix));
        } catch (IOException exception) {
            LOGGER.error("Input output exception for image: {}", suffix);
        }
        throw new IllegalStateException("Couldn't find the image you were looking for!");
    }

    @PostConstruct
    public void initializeDirectoryPath() throws IOException {
        //no side effects in streams
        final List<Path> paths = Arrays.stream(ImageType.values())
                .map(type -> Paths.get("storage" + File.separator + type.getPath()))
                .collect(Collectors.toList());
        for (Path path : paths) {
            Files.createDirectories(path);
        }
    }
}
