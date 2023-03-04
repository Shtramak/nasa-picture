package com.example.demo.nasa.service;

import com.example.demo.nasa.controller.dto.PictureRequest;

public interface NasaPicturesService {
    byte[] getLargestNasaPicture(PictureRequest request);
}
