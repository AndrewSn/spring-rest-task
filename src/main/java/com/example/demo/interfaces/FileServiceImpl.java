package com.example.demo.interfaces;

import com.example.demo.entity.FileInformation;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileServiceImpl {
    boolean upload(MultipartFile multipartFile);

    Resource getFile(String filename);

    boolean deleteFile(String filename);

    boolean updateFile(String existNameFile, String newNameFile);

    FileInformation getInfoAboutFile(String file);

}
