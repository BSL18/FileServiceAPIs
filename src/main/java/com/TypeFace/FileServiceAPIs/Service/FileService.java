package com.TypeFace.FileServiceAPIs.Service;

import com.TypeFace.FileServiceAPIs.Entity.FileStore;
import com.TypeFace.FileServiceAPIs.Repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FileService {

//    @Autowired
    private final FileRepository fileRepository;

    @Value("${file.upload-dir}")  // Inject file upload directory from application.properties
    private String uploadDir;

    public FileStore uploadFile(MultipartFile file) throws IOException {
        String fileId = UUID.randomUUID().toString();
        String fileName = fileId.substring(0, 5) + "_" + file.getOriginalFilename();

        Path filePath = Paths.get(uploadDir, fileName);
        Files.write(filePath, file.getBytes());

        FileStore fileStore = new FileStore();
        fileStore.setFileId(fileId);
        fileStore.setFileName(fileName);
        fileStore.setCreatedAt(LocalDateTime.now());
        fileStore.setSize(file.getSize());
        fileStore.setFileType(file.getContentType());
        fileStore.setFilePath(filePath.toString());

        return fileRepository.save(fileStore);
    }

    public Optional<FileStore> getFileByFileId(String fileId) {
        return fileRepository.findByFileId(fileId);
    }

    public FileStore updateFile(String fileId, MultipartFile file) throws IOException {
        FileStore fileStore = fileRepository.findByFileId(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        String fileName = UUID.randomUUID().toString().substring(0, 5) + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.write(filePath, file.getBytes());

        // Delete the old file
        Files.deleteIfExists(Paths.get(fileStore.getFilePath()));

        fileStore.setFileName(fileName);
        fileStore.setSize(file.getSize());
        fileStore.setFileType(file.getContentType());
        fileStore.setFilePath(filePath.toString());

        return fileRepository.save(fileStore);
    }

    public void deleteFile(String fileId) {
        FileStore fileStore = fileRepository.findByFileId(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        try {
            Files.deleteIfExists(Paths.get(fileStore.getFilePath()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file", e);
        }

        fileRepository.delete(fileStore);
    }

    public List<FileStore> listAllFiles() {
        return fileRepository.findAll();
    }

}
