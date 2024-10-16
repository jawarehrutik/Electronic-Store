package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.exceptions.BadApiRequestException;
import com.lcwd.electronic.store.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadImage(MultipartFile file, String path) {

        String originalFilename = file.getOriginalFilename();
        logger.info("filename : {}",originalFilename);
        String filename = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = filename + extension;

        String fullPathWithFileName = path + fileNameWithExtension;
        logger.info("full image path {}",fullPathWithFileName);

        if(extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpeg")||extension.equalsIgnoreCase("jpg")){
            File folder = new File(path);
            if(!folder.exists())
            {
                folder.mkdirs();
            }

            try {
                Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return fileNameWithExtension;
        }else{
            throw new BadApiRequestException("File with this "+extension+" not allowed");
        }
    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullpath = path+File.separator+name;
        InputStream inputStream = new FileInputStream(fullpath);
        return inputStream;
    }
}
