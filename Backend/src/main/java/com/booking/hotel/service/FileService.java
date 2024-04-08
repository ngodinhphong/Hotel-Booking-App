package com.booking.hotel.service;

import com.booking.hotel.service.imp.FileServiceImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService implements FileServiceImp {

    @Value("${upload.file.path}")
    private String path;

    @Override
    public void saveFile(MultipartFile file) {
        try {
            Path root = Paths.get(path);
//            Kiem tra duong dan co ton tai khong, neu khong thi tao Directory ra.
            if (!Files.exists(root)){
                Files.createDirectory(root);
            }
            // Copy File vào đường dẫn root.
//            TODO: root.resolve: Tuỳ vào hdh nào để thêm vào .
//            root.resolve(file.getOriginalFilename()): Lấy path.
            Files.copy(file.getInputStream(),root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception e){
            throw new RuntimeException("Loi luu file1: " + e.getClass());
        }

    }

    @Override
    public Resource load(String fileName) {

        try {
            // Lấy đuờng dẫn folder lưu trữ file
            Path root = Paths.get(path);
            // Đường dẫn file trong folder upload
            Path file = root.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists()){
                return resource;
            }else {
                throw new RuntimeException("Không tìm thấy file");
            }
        }catch (Exception e){
            throw new RuntimeException("Không tìm thấy file " + e.getMessage());
        }
    }

    @Override
    public void deleteFile(String fileName) {

        try {
            // Lấy đuờng dẫn folder lưu trữ file
            Path root = Paths.get(path);
            // Đường dẫn file trong folder upload
            Path file = root.resolve(fileName);
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }


    }


}
