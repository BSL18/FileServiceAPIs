package com.TypeFace.FileServiceAPIs.Repository;

import com.TypeFace.FileServiceAPIs.Entity.FileStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface FileRepository extends JpaRepository<FileStore,Long> {

    Optional<FileStore> findByFileId(String fileId);
}
