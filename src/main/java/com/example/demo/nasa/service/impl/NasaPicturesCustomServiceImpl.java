package com.example.demo.nasa.service.impl;

import com.example.demo.nasa.controller.dto.PictureRequest;
import com.example.demo.nasa.service.NasaPicturesService;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Objects;

@Service
@Primary
public class NasaPicturesCustomServiceImpl implements NasaPicturesService {
    private static final String NASA_PICTURE_PATH = "nasa-picture.jpg";

    @Override
    @SneakyThrows
    public byte[] getLargestNasaPicture(PictureRequest request) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(NASA_PICTURE_PATH)) {
            Objects.requireNonNull(inputStream);
            return inputStream.readAllBytes();
        }
    }
}
