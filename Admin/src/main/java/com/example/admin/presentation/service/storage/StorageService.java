package com.example.admin.presentation.service.storage;

import com.example.admin.presentation.internalmodel.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public abstract class StorageService {

    StorageService() {
        init();
    }

    protected abstract void init();

    public abstract boolean uploadFile(@NotNull MultipartFile file, @NotNull Resource.Path path, String name);

    public abstract boolean deleteFile(@NotNull Resource.Path path, String name);

}
