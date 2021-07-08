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
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService implements FileServiceImpl {

    private final Path root = Paths.get("directory");


    @Override
    public boolean upload(MultipartFile multipartFile) {
        boolean create = true;
        try {
            Files.copy(multipartFile.getInputStream(), this.root.resolve(Objects.requireNonNull(multipartFile.getOriginalFilename())));
        } catch (IOException e) {
            e.printStackTrace();
            create = false;
        }
        return create;

    }

    @Override
    public Resource getFile(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("File is not readable");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteFile(String filename) {
        boolean isDelete = true;
        Path file = root.resolve(filename);
        try {
            if (Files.deleteIfExists(file)) {
                isDelete = true;
            } else {
                isDelete = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isDelete;
    }

    @Override
    public boolean updateFile(String existFileName, String newFileName) {
        Path file = root.resolve(existFileName);
        Path newFile = root.resolve(newFileName);
        boolean isUpdate = true;
        if (getFile(existFileName).exists()) {
            try {
                Files.move(file, newFile, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                isUpdate = false;
            }
        } else {
            isUpdate = false;
        }
        return isUpdate;
    }

    @Override
    public FileInformation getInfoAboutFile(String file) {
        Resource resource = getFile(file);
        FileInformation fileInformation1 = new FileInformation();
        if (resource.exists()) {
            try {
                File file1 = resource.getFile();
                fileInformation1.setPathFile(file1.getAbsolutePath());
                fileInformation1.setNameFile(file1.getName());
                fileInformation1.setSize(file1.length());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            fileInformation1 = null;
        }

        return fileInformation1;
    }
}
