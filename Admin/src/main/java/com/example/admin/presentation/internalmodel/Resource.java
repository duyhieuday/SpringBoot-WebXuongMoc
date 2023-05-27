package com.example.admin.presentation.internalmodel;


import com.example.admin.config.ResourceConfig;

import com.example.admin.presentation.service.storage.AmazonStorageService;
import com.example.admin.presentation.service.storage.FirebaseStorageService;
import com.example.admin.presentation.service.storage.LocalStorageService;
import com.example.library.utils.FileUtils;
import com.example.library.utils.Pair;
import com.sun.istack.Nullable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

public class Resource {

    public static final long URL_EXP = TimeUnit.DAYS.toMillis(7) - 60 * 1000; // (max 7Day)

    public static String getUrl(Path path, String name) {
        return getUrl(ResourceConfig.SYSTEM_RESOURCE_TYPE, path, name);
    }

    @Nullable
    public static String getUrl(@NotNull Type type, Path path, String name) {
        switch (type) {
            case AWS3:
                return AmazonStorageService.getUrl(path, name);
            case GCP:
                return FirebaseStorageService.getUrl(path, name);
            case LOCAL:
                return LocalStorageService.getUrl(path, name);
        }
        return null;
    }

    private static String BASE_URL;

    public static String getBaseUrl() {
        if (BASE_URL != null) {
            return BASE_URL;
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String scheme = request.getScheme();
            String host = request.getServerName();
            int port = request.getServerPort();
            BASE_URL = scheme + "://" + host + ":" + port + "/";
        }
        return BASE_URL;
    }

    public enum Type {
        AWS3,       // Amazon S3
        GCP,        // Google cloud platform
        LOCAL,      // My computer
    }

    public enum Path {
        PRODUCT("images/product/");

        private final String path;

        Path(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }

        public boolean isImagePath() {
            return true;
        }

        public Pair<Boolean, String> validFile(MultipartFile file) {
            return FileUtils.validFile(isImagePath(), file);
        }

    }

    public enum Layout {
        MasterCommon("templates/master/common_layout.html"),
        MasterAjax("templates/master/common_ajax_layout.html"),
        MasterAdmin("templates/master/admin_layout.html"),
        MasterAdminBlank("templates/master/admin_blank_layout.html"),
        MasterClient("templates/master/client_layout.html"),
        SharedCommon("templates/common/shared.html");

        private final String name;

        Layout(@NotNull String name) {
            this.name = verifyLayout(name);
        }

        @NotNull
        public String getName() {
            return name;
        }

        @NotNull
        public static String verifyLayout(@NotNull String layout) {
            if (layout.startsWith("templates/")) {
                layout = layout.substring("templates/".length());
            }
            if (layout.endsWith(".html")) {
                layout = layout.substring(0, layout.lastIndexOf("."));
            }
            return layout;
        }
    }

    public enum Icon {
        AppLogo("static/public/admin/assets/img/favicon/favicon.png");
        private final String name;

        Icon(@NotNull String name) {
            this.name = name.substring("static".length());
        }

        @NotNull
        public String getName() {
            return name;
        }
    }
}
