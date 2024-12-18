package com.example.eventplanner.controllers.photo;

import com.example.eventplanner.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getPhoto(@PathVariable String filename) throws MalformedURLException {
        Resource resource = photoService.getPhoto(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

//    @PostMapping("/user")
//    public ResponseEntity<Integer> uploadUserPhoto(@RequestParam("file") MultipartFile file) {
//        int photoId = photoService.storeUserPhoto(file);
//        return ResponseEntity.ok(photoId);
//    }

    @PostMapping("/business")
    public ResponseEntity<Integer> uploadBusinessPhoto(@RequestParam("file") MultipartFile file) {
        int photoId = photoService.storeBusinessPhoto(file);
        return ResponseEntity.ok(photoId);
    }

    @PostMapping("/merchandise")
    public ResponseEntity<Integer> uploadMerchandisePhoto(@RequestParam("file") MultipartFile file) {
        int photoId = photoService.storeMerchandisePhoto(file);
        return ResponseEntity.ok(photoId);
    }

    @RequestMapping(value = "/{mercId}/merchandise/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Integer> deleteMerchandisePhoto(@PathVariable int id, @PathVariable int mercId,
                                                          @RequestParam boolean edit) {
        try {
            photoService.deleteMercById(id, mercId, edit);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/{spId}/business/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Integer> deleteBusinessPhoto(@PathVariable int id, @PathVariable int spId,
                                                       @RequestParam boolean edit) {
        try {
            photoService.deleteBusinessById(id, spId, edit);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}