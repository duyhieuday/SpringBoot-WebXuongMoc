package com.example.admin.presentation.service.storage;


import com.example.admin.config.ResourceConfig;
import com.example.admin.presentation.internalmodel.Resource;
import com.sun.istack.Nullable;


import javax.validation.constraints.NotNull;

public class StorageFactory {

    public static StorageService createStorageService() {
        return createStorageService(ResourceConfig.SYSTEM_RESOURCE_TYPE);
    }

    @Nullable
    public static StorageService createStorageService(@NotNull Resource.Type type) {
        switch (type) {
            case AWS3:
                return new AmazonStorageService();
            case GCP:
                return new FirebaseStorageService();
            case LOCAL:
                return new LocalStorageService();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public static <T extends StorageService> T createStorageService(Class<T> clazz) {
        if (clazz == AmazonStorageService.class) {
            return (T) new AmazonStorageService();
        }
        if (clazz == FirebaseStorageService.class) {
            return (T) new FirebaseStorageService();
        }
        if (clazz == LocalStorageService.class) {
            return (T) new LocalStorageService();
        }
        return null;
    }
}
