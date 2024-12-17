package com.example.eventplanner.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PhotoService {
    @Value("${photo.storage.path}") // Path to the folder where photos are stored
    private String photoStoragePath;

    public Resource getPhoto(String filename) throws MalformedURLException {
        return loadPhotoAsResource(filename);
    }

    private Resource loadPhotoAsResource(String filename) throws MalformedURLException {
        Path photoPath = Paths.get(photoStoragePath).resolve(filename);
        Resource resource = new UrlResource(photoPath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("Photo not found: " + filename);
        }
    }
}
