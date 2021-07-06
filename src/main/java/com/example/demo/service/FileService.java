package com.example.demo.service;

import com.example.demo.entity.FileInformation;
import com.example.demo.interfaces.FileServiceImpl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService implements FileServiceImpl {

    private final Path root = Paths.get("directory");


    @Override
    public void upload(MultipartFile multipartFile) {
        try {
            Files.copy(multipartFile.getInputStream(), this.root.resolve(Objects.requireNonNull(multipartFile.getOriginalFilename())));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public Resource getFile(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Not read file");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteFile(String filename) {
        Path file = root.resolve(filename);
        try {
            Files.deleteIfExists(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateFile(String existFileString, String newFileName) {
       Path file = root.resolve(existFileString);
       Path newFile = root.resolve(newFileName);
        if(getFile(existFileString).exists()){
        try {
            Files.move(file, newFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }}
        else {
            System.out.println("not found file");
        }
    }

    @Override
    public FileInformation getInfoAboutFile(String file) {
        Resource resource = getFile(file);
        FileInformation fileInformation1 = new FileInformation();
        if(resource.exists()){
        try {
            File file1 = resource.getFile();
            fileInformation1.setPathOfFile(file1.getAbsolutePath());
            fileInformation1.setNameOfFile(file1.getName());
            fileInformation1.setSize(file1.length());
        } catch (IOException e) {
            e.printStackTrace();
        }}
        else {fileInformation1 = null;}

        return fileInformation1;
    }
}
