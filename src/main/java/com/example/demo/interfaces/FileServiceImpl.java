package com.example.demo.interfaces;

import com.example.demo.entity.FileInformation;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileServiceImpl {
    public void upload(MultipartFile multipartFile);
    public Resource getFile(String filename);
    public void deleteFile(String filename);
    public void updateFile(String existNameFile, String newNameFile);
    public FileInformation getInfoAboutFile(String file);

}
