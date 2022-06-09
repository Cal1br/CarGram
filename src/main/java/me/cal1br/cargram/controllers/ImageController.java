package me.cal1br.cargram.controllers;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@RequestMapping(value = "/api/v1/image", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE})
public interface ImageController {

    /**
     * Appends an output stream to the response
     *
     * @param imgPath
     */
    @GetMapping("/{imgPath}")
    void serveImage(@PathVariable String imgPath, HttpServletResponse response);

    @GetMapping()
    void serveImageQueryParam(@RequestParam(name = "imgPath") String imgPath, HttpServletResponse response);
}
