package com.bonoarts.assignment.controller;

import com.bonoarts.assignment.exceptions.UsernameNotFoundException;
import com.bonoarts.assignment.model.Car;
import com.bonoarts.assignment.model.Car;
import com.bonoarts.assignment.model.Client;
import com.bonoarts.assignment.service.CarService;
import com.bonoarts.assignment.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

import static java.lang.Integer.parseInt;

@RestController
@RequestMapping(value = "/cars")
public class CarController {

    @Autowired
    private CarService carService;

    private ClientService clientService;

    @GetMapping(value = "")
    public ResponseEntity<Object> getCars() {
        return ResponseEntity.ok().body(carService.getCars());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getCar(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(carService.getCar(id));
    }

    @GetMapping(value = "/receipt/{id}")
    public ResponseEntity<Object> getReceipt(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(carService.getCar(id));
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> createCar(@RequestBody Car car) {
        Integer newId = carService.createCar(car);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();

        return ResponseEntity.created(location).body(car);
    }

    @PostMapping("/upload")
    public ResponseEntity multiUpload(@RequestParam("files") MultipartFile[] files) {
        List<Object> fileDownloadUrls = new ArrayList<>();
        Arrays.asList(files)
                .stream()
                .forEach(file -> fileDownloadUrls.add(uploadToLocalFileSystem(file).getBody()));
        return ResponseEntity.ok(fileDownloadUrls);
    }

    // Can be used as a seperate url
    private ResponseEntity uploadToLocalFileSystem(@RequestParam("file") MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path path = Paths.get("files/" + fileName);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/cars/download/")
                .path(fileName)
                .toUriString();
        return ResponseEntity.ok(fileDownloadUri);
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity downloadFileFromLocal(@PathVariable String fileName) {
        Path path = Paths.get("files/" + fileName);
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateCar(@PathVariable("id") Integer id, @RequestBody Car car) {
        carService.updateCar(id, car);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateCarPartial(@PathVariable("id") Integer id, @RequestBody Map<String, String> fields) {
        carService.partialUpdateCar(id, fields);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteCar(@PathVariable("id") Integer id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}
