package com.example.demo.nasa.service.impl;

import com.example.demo.nasa.controller.dto.PictureRequest;
import com.example.demo.nasa.service.NasaPicturesService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Comparator;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class NasaPicturesRestServiceImpl implements NasaPicturesService {

    private static final String URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos";
    private static final String API_KEY = "TA2TXlpk7hFRTInrsRUd1adceSR4rGPizAp3Xze3";
    private static final String SOL = "14";

    private final RestTemplate restTemplate;

    @Override
    public byte[] getLargestNasaPicture(PictureRequest request) {
        final var uri = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("api_key", API_KEY)
                .queryParam("sol", SOL)
                .build()
                .toUri();
        final var photosNode = restTemplate.getForObject(uri, JsonNode.class);
        final var imageData = StreamSupport.stream(photosNode.get("photos").spliterator(), false)
                .map(node -> node.get("img_src").asText())
                .toList()
                .parallelStream()
                .map(restTemplate::headForHeaders)
                .map(HttpHeaders::getLocation)
                .map(location -> new ImageData(location, restTemplate.headForHeaders(location).getContentLength()))
                .max(Comparator.comparing(ImageData::size))
                .orElseThrow();

        return restTemplate.getForObject(imageData.uri, byte[].class);
    }

    public record ImageData(URI uri, Long size){}
}
