package com.example.admin.presentation.service.storage;


import com.example.admin.presentation.internalmodel.Resource;
import com.sun.istack.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;

public class FirebaseStorageService extends StorageService {

    FirebaseStorageService() {
    }

    @Override
    protected void init() {
//        ResourceConfig config = ResourceConfig.getInstance();
//        try {
//            FirebaseApp.getInstance(config.getGcpProjectId());
//        } catch (IllegalStateException e) {
//            FirebaseOptions options = FirebaseOptions.builder()
//                    .setCredentials(getCredentials())
//                    .setProjectId(config.getGcpProjectId())
//                    .setStorageBucket(config.getGcpBucketName())
//                    .build();
//            FirebaseApp.initializeApp(options, config.getGcpProjectId());
//        }
    }

    @Override
    public boolean uploadFile(@NotNull MultipartFile file, @NotNull Resource.Path path, String name) {
//        try {
//            getStorage().create(BlobInfo.newBuilder(blobOf(path, name))
//                    .setContentType(file.getContentType())
//                    .build(), file.getBytes());
//            return true;
//        } catch (IOException e) {
//            return false;
//        }
        return false;
    }

    @Override
    public boolean deleteFile(@NotNull Resource.Path path, String name) {
//        return getStorage().delete(blobOf(path, name));
        return false;
    }

//    @TestOnly
//    public boolean uploadFile(String contentType, @NotNull File file, @NotNull Resource.Path path, String name) {
////        try {
////            getStorage().create(BlobInfo.newBuilder(blobOf(path, name))
////                    .setContentType(contentType)
////                    .build(), Files.readAllBytes(file.toPath()));
////            return true;
////        } catch (IOException e) {
////            return false;
////        }
//        return false;
//    }

    @Nullable
    public static String getUrl(@NotNull Resource.Path path, String name) {
//        Blob blob;
//        if ((blob = getStorage().get(blobOf(path, name))) == null) {
//            return null;
//        }
//        return blob.signUrl(Resource.URL_EXP, TimeUnit.MILLISECONDS).toString();
        return "";
    }


    ///////////////////////////////////////////////////////////////////////////
    // Private area
    ///////////////////////////////////////////////////////////////////////////

//    private static Storage storage;
//
//    private static Storage getStorage() {
//        if (storage == null) {
//            storage = StorageOptions.newBuilder().setCredentials(getCredentials()).build().getService();
//        }
//        return storage;
//    }
//
//    private static GoogleCredentials getCredentials() {
//        try {
//            return GoogleCredentials.fromStream(Files.newInputStream(Paths.get(ResourceConfig.getInstance().getGcpCredentialsPath())));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @NotNull
//    private static BlobId blobOf(@NotNull Resource.Path path, String name) {
//        return BlobId.of(ResourceConfig.getInstance().getGcpBucketName(), path.getPath() + name);
//    }
}
