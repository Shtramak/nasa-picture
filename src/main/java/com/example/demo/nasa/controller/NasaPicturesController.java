package com.example.demo.nasa.controller;


import com.example.demo.nasa.controller.dto.PictureRequest;
import com.example.demo.nasa.service.NasaPicturesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("nasa/pictures")
@RequiredArgsConstructor
public class NasaPicturesController {

    private final NasaPicturesService picturesService;

    @GetMapping(
            value = "/largest",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] getLargestNasaPicture() {
        return picturesService.getLargestNasaPicture(new PictureRequest());
    }
}
