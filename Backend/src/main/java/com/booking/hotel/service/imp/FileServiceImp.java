package com.booking.hotel.service.imp;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileServiceImp {
    void saveFile(MultipartFile file);
    Resource load(String fileName);
    void deleteFile(String fileName);
}
