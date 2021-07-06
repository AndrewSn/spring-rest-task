package com.example.demo.controller;

import com.example.demo.entity.FileInformation;
import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;

@RestController
@RequestMapping("/file")
public class FileController {

    FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile multipartFile) {
        String message = "";
        fileService.upload(multipartFile);
        message = "upload is success";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/")
    public ResponseEntity<Resource> getFile(@RequestParam("file") String filename) {
        Resource file = fileService.getFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteFile(@RequestParam("file") String filename) {
        String message = "";
        fileService.deleteFile(filename);
         message = "delete is success";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PutMapping("/")
    public ResponseEntity<String> updateFile(@RequestParam("file") String filename, @RequestParam("newFile") String newFileName) {
        String message = "";
        fileService.updateFile(filename, newFileName);
        message = "update is success";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/getInfo")
    public FileInformation getInformation(@RequestParam("file") String fileName) {
        return fileService.getInfoAboutFile(fileName);
    }
}
