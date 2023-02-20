package com.example.upload_files.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;


@ControllerAdvice
public class FileUploadExceptionAdvice {
    @ExceptionHandler(MaxUploadSizeExceededException.class)

    /*@ExceptionHandler dc dung de handler exception
     *@ControllerAdvice dc dùng de dinh nghia ra mot noi tap trung  xu li exception
     * */
    public String handleMaxSizeException(Model model,MaxUploadSizeExceededException e ){
        model.addAttribute("message","File is too large!");
        return "upload_form";
    }
}
