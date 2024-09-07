package com.TypeFace.FileServiceAPIs.Controller;

import com.TypeFace.FileServiceAPIs.Entity.FileStore;
import com.TypeFace.FileServiceAPIs.Service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

//    @Autowired
    private final FileService fileService;

    @PostMapping("/upload")
        public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            FileStore fileStore = fileService.uploadFile(file);

            return new ResponseEntity<>("File uploaded successfully with FileId: " + fileStore.getFileId(), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("File upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{fileId}")
    public ResponseEntity<?> getFile(@PathVariable String fileId) {
        Optional<FileStore> optionalFile = fileService.getFileByFileId(fileId);

        if (optionalFile.isPresent()) {
            FileStore file = optionalFile.get();
            try {
                byte[] fileContent = Files.readAllBytes(Paths.get(file.getFilePath()));
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"");
                headers.add(HttpHeaders.CONTENT_TYPE, file.getFileType());

                return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }
    }


//    @GetMapping("/{fileId}")
//    public ResponseEntity<byte[]> getFile(@PathVariable String fileId) {
//        return (ResponseEntity<byte[]>) fileService.getFileByFileId(fileId)
//                .map(file -> {
//                    HttpHeaders headers = new HttpHeaders();
//                    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"");
//                    headers.add(HttpHeaders.CONTENT_TYPE, file.getFileType());
//
//                    try {
//                        byte[] fileContent = Files.readAllBytes(Paths.get(file.getFilePath()));
//                        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
//                    } catch (IOException e) {
//                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//                    }
//                })
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

//    @GetMapping("/{fileId}")
//    public ResponseEntity<byte[]> getFile(@PathVariable Long fileId) {
//        return fileService.getFileById(fileId)
//                .map(file -> {
//                    HttpHeaders headers = new HttpHeaders();
//                    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"");
//                    headers.add(HttpHeaders.CONTENT_TYPE, file.getFileType());
//
//                    try {
//                        byte[] fileContent = Files.readAllBytes(Paths.get(file.getFilePath()));
//                        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
//                    } catch (IOException e) {
//                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//                    }
//                })
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

    @PatchMapping(path = "/{fileId}")
    public ResponseEntity<String> updateFile(@PathVariable String fileId, @RequestParam("file") MultipartFile file) {
        try {
            FileStore fileStore = fileService.updateFile(fileId, file);
            return new ResponseEntity<>("File updated successfully with FileId: " + fileStore.getFileId(), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("File update failed", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileId) {
        fileService.deleteFile(fileId);
        return new ResponseEntity<>("File deleted successfully", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FileStore>> listAllFiles() {
        List<FileStore> files = fileService.listAllFiles();

        return new ResponseEntity<>(files, HttpStatus.OK);
    }
}
