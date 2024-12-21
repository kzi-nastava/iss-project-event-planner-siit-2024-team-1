package com.example.eventplanner.services;

import com.example.eventplanner.model.merchandise.Merchandise;
import com.example.eventplanner.model.merchandise.MerchandisePhoto;
import com.example.eventplanner.model.user.BusinessPhoto;
import com.example.eventplanner.model.user.ServiceProvider;
import com.example.eventplanner.model.user.User;
import com.example.eventplanner.repositories.merchandise.MerchandisePhotoRepository;
import com.example.eventplanner.repositories.merchandise.MerchandiseRepository;
import com.example.eventplanner.repositories.user.BusinessPhotoRepository;
import com.example.eventplanner.repositories.user.ServiceProviderRepository;
import com.example.eventplanner.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private final BusinessPhotoRepository businessPhotoRepository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final MerchandiseRepository merchandiseRepository;
    private final MerchandisePhotoRepository merchandisePhotoRepository;
    private final UserRepository userRepository;
    @Value("${photo.storage.path}") // Path to the folder where photos are stored
    private String photoStoragePath;

    public Resource getPhoto(String filename) throws MalformedURLException {
        return loadPhotoAsResource(filename);
    }

    public String storeUserPhoto(MultipartFile file, int id) {
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String baseName = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
            String filename = originalFilename;
            Path path = Paths.get(photoStoragePath).resolve(filename);

            // Check if file already exists
            if (Files.exists(path)) {
                String timestamp = LocalTime.now().toString().replace(":", "-").replace(".", "-");
                filename = baseName + "-" + timestamp + extension;
                path = Paths.get(photoStoragePath).resolve(filename);
            }

            Files.copy(file.getInputStream(), path);
            if(id != -1){
                User user = userRepository.findById(id).orElseThrow();
                user.setPhoto(filename);
            }
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Could not store file " + file.getOriginalFilename(), e);
        }
    }

    public int storeBusinessPhoto(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String baseName = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
            String filename = originalFilename;
            Path path = Paths.get(photoStoragePath).resolve(filename);

            // Check if file already exists
            if (Files.exists(path)) {
                String timestamp = LocalTime.now().toString().replace(":", "-").replace(".", "-");
                filename = baseName + "-" + timestamp + extension;
                path = Paths.get(photoStoragePath).resolve(filename);
            }

            Files.copy(file.getInputStream(), path);
            BusinessPhoto businessPhoto = new BusinessPhoto();
            businessPhoto.setPhoto(filename);
            BusinessPhoto saved = businessPhotoRepository.save(businessPhoto);
            return saved.getId();
        } catch (IOException e) {
            throw new RuntimeException("Could not store file " + file.getOriginalFilename(), e);
        }
    }


    public int storeMerchandisePhoto(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String baseName = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
            String filename = originalFilename;
            Path path = Paths.get(photoStoragePath).resolve(filename);

            // Check if file already exists
            if (Files.exists(path)) {
                String timestamp = LocalTime.now().toString().replace(":", "-").replace(".", "-");
                filename = baseName + "-" + timestamp + extension;
                path = Paths.get(photoStoragePath).resolve(filename);
            }

            Files.copy(file.getInputStream(), path);
            MerchandisePhoto merchandisePhoto = new MerchandisePhoto();
            merchandisePhoto.setPhoto(filename);
            MerchandisePhoto saved = merchandisePhotoRepository.save(merchandisePhoto);
            return saved.getId();
        } catch (IOException e) {
            throw new RuntimeException("Could not store file " + file.getOriginalFilename(), e);
        }
    }

    public String deleteUserPhoto(int id) {
        if(id != -1){
            User user = userRepository.findById(id).orElseThrow();
            // Delete from the database
            String old = user.getPhoto();
            user.setPhoto(null);
            Path photoPath = Paths.get(photoStoragePath).resolve(user.getPhoto());
            try {
                Files.deleteIfExists(photoPath);
            } catch (IOException e) {
                throw new RuntimeException("Could not delete file: " + photoPath, e);
            }
            return old;
        }
        return "";
    }

    public int deleteMercById(int id, int mercId, boolean edit) {
        MerchandisePhoto merchandisePhoto = merchandisePhotoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Merchandise photo not found with id: " + id));

        if(mercId == -1 && !merchandiseRepository.existsByPhotoId(merchandisePhoto.getId())){
            // Delete the photo file
            Path photoPath = Paths.get(photoStoragePath).resolve(merchandisePhoto.getPhoto());
            try {
                Files.deleteIfExists(photoPath);
                merchandisePhotoRepository.delete(merchandisePhoto);
            } catch (IOException e) {
                throw new RuntimeException("Could not delete file: " + photoPath, e);
            }
        }
        // Delete from the database
        return merchandisePhoto.getId();
    }

    public int deleteBusinessById(int id, int spId, boolean edit) {
        BusinessPhoto businessPhoto = businessPhotoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Business photo not found with id: " + id));

        if (spId != -1) {
            ServiceProvider sp = serviceProviderRepository.findById(spId).orElseThrow(() -> new RuntimeException("Service provider not found with id: " + spId));
            sp.getPhotos().remove(businessPhoto);
        }

        Path photoPath = Paths.get(photoStoragePath).resolve(businessPhoto.getPhoto());
        try {
            Files.deleteIfExists(photoPath);
            businessPhotoRepository.delete(businessPhoto);
        } catch (IOException e) {
            throw new RuntimeException("Could not delete file: " + photoPath, e);
        }

        // Delete from the database
        businessPhotoRepository.delete(businessPhoto);
        return businessPhoto.getId();
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
