package com.example.admin.config;


import com.example.admin.presentation.internalmodel.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResourceConfig {

    public static final Resource.Type SYSTEM_RESOURCE_TYPE = Resource.Type.LOCAL;

    @Value("${cloud.local.resource.path}")
    private String localResourcePath;
    @Value("${cloud.local.resource.suffix}")
    private String localResourceSuffix;

    private String gcpProjectId;
    private String gcpBucketName;
    private String gcpCredentialsPath;

    private String awsBucketName;
    private String awsAccessKey;
    private String awsSecretKey;
    private String awsRegionName;

    ///////////////////////////////////////////////////////////////////////////
    // Getter
    ///////////////////////////////////////////////////////////////////////////

    public String getLocalResourcePath() {
        return localResourcePath;
    }

    public String getLocalResourceSuffix() {
        return localResourceSuffix;
    }

    public String getGcpProjectId() {
        return gcpProjectId;
    }

    public String getGcpBucketName() {
        return gcpBucketName;
    }

    public String getGcpCredentialsPath() {
        return gcpCredentialsPath;
    }

    public String getAwsBucketName() {
        return awsBucketName;
    }

    public String getAwsAccessKey() {
        return awsAccessKey;
    }

    public String getAwsSecretKey() {
        return awsSecretKey;
    }

    public String getAwsRegionName() {
        return awsRegionName;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Singleton
    ///////////////////////////////////////////////////////////////////////////

    private static ResourceConfig instance;

    public static ResourceConfig getInstance() {
        return instance;
    }

    ResourceConfig() {
        instance = this;
    }

}
