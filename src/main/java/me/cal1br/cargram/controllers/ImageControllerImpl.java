package me.cal1br.cargram.controllers;

import me.cal1br.cargram.services.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class ImageControllerImpl implements ImageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageControllerImpl.class);
    private final ImageService service;

    @Autowired
    public ImageControllerImpl(final ImageService service) {
        this.service = service;
    }

    @Override
    public void serveImage(final String imgPath, final HttpServletResponse resp) {
        resp.setContentType(MediaType.IMAGE_JPEG_VALUE); //todo fix
        try (final InputStream imgStream = service.getImageInputStreamByLink(imgPath)) {
            StreamUtils.copy(imgStream, resp.getOutputStream());
        } catch (IOException e) {
            LOGGER.error("IO Exception for {}", imgPath);
        }
    }

    @Override
    public void serveImageQueryParam(String imgPath, HttpServletResponse response) {
        this.serveImage(imgPath, response);
    }
}
