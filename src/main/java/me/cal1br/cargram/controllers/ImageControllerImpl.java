package me.cal1br.cargram.controllers;

import me.cal1br.cargram.services.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class ImageControllerImpl implements ImageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageControllerImpl.class);

    private final int bufferSize;
    private final ImageService service;

    @Autowired
    public ImageControllerImpl(final ImageService service,
                               @Value("${image.buffer_size:1024}") final int bufferSize) {
        this.service = service;
        this.bufferSize = bufferSize;
    }

    @Override
    public void serveImage(final String imgPath, final HttpServletResponse resp) {
        resp.setContentType("text/plain");
        resp.setHeader("Content-disposition", "attachment; filename=snimka.jpg");

        byte[] buffer = new byte[bufferSize];
        try (final InputStream in = service.getImageInputStreamByLink(imgPath)) {
            int numBytesRead;
            final ServletOutputStream outputStream = resp.getOutputStream();
            while ((numBytesRead = in.read(buffer)) > 0) {
                outputStream.write(buffer, 0, numBytesRead);
            }
        } catch (IOException e) {
            LOGGER.error();
        }
    }
}
