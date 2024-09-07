package com.TypeFace.FileServiceAPIs.Entity;

import jakarta.persistence.*;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "file_store")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    uniquely Identify
    private String fileId;
    private String fileName;
    private LocalDateTime createdAt;
    private long size;
    private String fileType;
    private String filePath;

}
