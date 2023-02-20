package com.example.upload_files.service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;


@Service

public class FilesStorageServiceImpl  implements  FilesStorageService{
    private final Path root = Paths.get("./uploads");

    @Override
    public void init() {
        try{
            Files.createDirectories(root);
        }catch(Exception e ){
            throw new RuntimeException(e.getMessage());
        }

    }
    @Override
    public void save(MultipartFile file) {
        try{
            Files.copy(file.getInputStream(),this.root.resolve(root));

        }catch (Exception e ){
            throw new RuntimeException("file nay co ten da ton tai");
        }
    }

    @Override
    public Resource load(String fileName) {
        try{
            Path file = root.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }else {
                throw  new RuntimeException("ko ton tai ");
            }
        }catch(Exception e ){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean delete(String filname) {
        try{
            Path file = root.resolve(filname);
            return  Files.deleteIfExists(file);
        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteAlls() {
        try{
            FileSystemUtils.deleteRecursively(root.toFile());
        }catch (Exception e ){
            throw  new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Stream<Path> loadAlls() {
        try{
            return Files.walk(this.root,1).filter(path -> !path.equals(root)).map(this.root::relativize);

        }catch (Exception e ){
            throw  new RuntimeException(e.getMessage());
        }
    }
}
