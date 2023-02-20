package com.example.upload_files;

import com.example.upload_files.service.FilesStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class UploadFilesApplication  implements CommandLineRunner {
	@Resource
	FilesStorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(UploadFilesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		storageService.init();
	}
}
