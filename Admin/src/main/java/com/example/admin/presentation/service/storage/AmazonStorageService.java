package com.example.admin.presentation.service.storage;


import com.example.admin.presentation.internalmodel.Resource;
import com.sun.istack.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class AmazonStorageService extends StorageService {

    AmazonStorageService() {
    }

    @Override
    protected void init() {
    }

    @Override
    public boolean uploadFile(@NotNull MultipartFile file, @NotNull Resource.Path path, String name) {
//        try {
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentType(file.getContentType());
//            metadata.setContentLength(file.getSize());
//            PutObjectRequest request = new PutObjectRequest(
//                    ResourceConfig.getInstance().getAwsBucketName(),
//                    keyOf(path, name),
//                    file.getInputStream(),
//                    metadata)
//                    .withCannedAcl(CannedAccessControlList.PublicRead);
//            getAmazonS3().putObject(request);
//            return true;
//        } catch (IOException e) {
//            return false;
//        }
        return false;
    }

    @Override
    public boolean deleteFile(@NotNull Resource.Path path, String name) {
//        getAmazonS3().deleteObject(ResourceConfig.getInstance().getAwsBucketName(), keyOf(path, name));
//        return true;
        return false;
    }

    @Nullable
    public static String getUrl(@NotNull Resource.Path path, String name) {
//        Date exp = new Date(System.currentTimeMillis() + Resource.URL_EXP);
//        return getAmazonS3()
//                .generatePresignedUrl(ResourceConfig.getInstance().getAwsBucketName(), keyOf(path, name), exp)
//                .toString();
        return "";
    }

    ///////////////////////////////////////////////////////////////////////////
    // Private area
    ///////////////////////////////////////////////////////////////////////////

//    private static AmazonS3 amazonS3;
//
//    public static AmazonS3 getAmazonS3() {
//        if (amazonS3 == null) {
//            ResourceConfig config = ResourceConfig.getInstance();
//            AWSCredentialsProvider credentials = new AWSCredentialsProvider() {
//                @Override
//                @NotNull
//                public AWSCredentials getCredentials() {
//                    return new BasicAWSCredentials(config.getAwsAccessKey(), config.getAwsSecretKey());
//                }
//
//                @Override
//                public void refresh() {
//                }
//            };
//            amazonS3 = AmazonS3ClientBuilder
//                    .standard()
//                    .withCredentials(credentials)
//                    .withRegion(config.getAwsRegionName())
//                    .build();
//        }
//        return amazonS3;
//    }
//
//    @NotNull
//    private static String keyOf(@NotNull Resource.Path path, String name) {
//        return path.getPath() + name;
//    }

}
