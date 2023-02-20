package com.example.upload_files.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

/*khởi tạo storage luwux truwx, luu file moi, load file
 *
 * */
public interface FilesStorageService {
    public void init();
    public  void save(MultipartFile file);
    public Resource load(String fileName);
    boolean delete(String filname);
    public void   deleteAlls();
    public Stream<Path> loadAlls();


}
