package me.cal1br.cargram.models;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PhotoUpload {
    private String title;
    private MultipartFile file;
}