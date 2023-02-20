package com.example.upload_files.controller;

import com.example.upload_files.model.FileInfo;
import com.example.upload_files.service.FilesStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller

public class FileController {
    @Autowired
    private FilesStorageServiceImpl filesStorageService;
    @GetMapping("/")
    public String homepage(){
        return "redirect:/files";
    }
    @GetMapping("/files/new")
    public String newFile(Model model){
        return "upload_form";
    }

    @PostMapping("/files/upload")
    public String uploadFile(Model model, @RequestParam("file")MultipartFile file){
        String message="";
        try{
            filesStorageService.save(file);
            message="upload the file successfully: "+file.getOriginalFilename();
            model.addAttribute("message",message);
        }catch (Exception e ){
            message ="could not upload the file: "+ file.getOriginalFilename() + ". Error: "+ e.getMessage();
            model.addAttribute("message",message);
        }
        return "upload_form";
    }
    @GetMapping("/files")
    public String getListFiles(Model model){
        List<FileInfo> fileInfos = filesStorageService.loadAlls().map(path->{
            String filename = path.getFileName().toString();
//            đại diện cho lớp FileControler,phuươg thưc getFile dc lấy từ ophíađưới, filename là tham  số truyền vào-> xay dung 1 url, sau do convert no sang chuoi
            String url = MvcUriComponentsBuilder.fromMethodName(FileController.class,"getFile",filename).build().toString();
            return new FileInfo(filename,url);// tra ve 1 cap kieu pair
        }).collect(Collectors.toList());
        model.addAttribute("files",fileInfos);
        return "files";
    }
//    phuc vu cho viec tai file ve, phục vụ cho cái hàm tren ở phường thức getFile
    @GetMapping("/files/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename){
        Resource file = filesStorageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\"" + file.getFilename()+"\"").body(file);

    }

    // xoa file
    @GetMapping("/files/delete/{filename}")
    public String deleteFile(@PathVariable String filename, Model model, RedirectAttributes redirectAttributes){
        try{
            boolean existed = filesStorageService.delete(filename);
            if(existed){
                redirectAttributes.addFlashAttribute("message","Delete the file successfully:" + filename);

            }else {
                redirectAttributes.addFlashAttribute("message","the file does not exist");
            }
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("message",
                    "could not delete the file: " + filename +". Error: " + e.getMessage());
        }
        return "redirect:/files";
    }








}
