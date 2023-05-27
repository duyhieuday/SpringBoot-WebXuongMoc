package com.example.admin.presentation.controller.api;


import com.example.admin.presentation.internalmodel.Resource;
import com.example.admin.presentation.internalmodel.Response;
import com.example.admin.presentation.service.storage.StorageFactory;
import com.example.admin.presentation.service.storage.StorageService;
import com.example.library.utils.Pair;
import com.sun.istack.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/resource")
public class ResourceController {
    private final StorageService storageService;

    public ResourceController() {
        this.storageService = StorageFactory.createStorageService();
    }

    /**
     * Upload a file. Depend on {@link ResourceConfig#SYSTEM_RESOURCE_TYPE}
     *
     * @param file a file part
     * @param type type of Path {@link Resource.Path#ordinal() }
     * @param name name expect of the file
     * @return success => 200
     * @see Resource.Path
     */
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam(name = "file") MultipartFile file,
                                        @RequestParam(name = "type") int type,
                                        @RequestParam(name = "name", required = false) String name) {
        if (type < 0 || type >= Resource.Path.values().length) {
            Response response = Response.error(HttpStatus.BAD_REQUEST, String.format("Type from 0 to %d", Resource.Path.values().length - 1));
            return ResponseEntity.badRequest().body(response.body());
        }
        Resource.Path path = Resource.Path.values()[type];
        Pair<Boolean, String> valid = path.validFile(file);
        if (valid.getFirst() == null || !valid.getFirst()) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(Response.error(HttpStatus.UNSUPPORTED_MEDIA_TYPE, valid.getSecond()).body());
        }
        if (name != null) {
            name = name.substring(0, name.lastIndexOf("."));
        }
        if (!isValidUUID(name)) {
            name = UUID.randomUUID().toString();
        }
        // add the ext before upload
        String realName = name + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
        return storageService.uploadFile(file, path, realName)
                ? ResponseEntity.ok().body(Response.ok()
                .put("path", path.getPath())
                .put("name", realName)
                .put("url", Resource.getUrl(path, name))
                .body())
                : ResponseEntity.internalServerError().body(Response.error(HttpStatus.INTERNAL_SERVER_ERROR, "Fail to upload file.").body());
    }

    /**
     * Delete a file. Depend on {@link ResourceConfig#SYSTEM_RESOURCE_TYPE}
     *
     * @param type type of Path {@link Resource.Path#ordinal() }
     * @param name name of File
     * @return success => 200
     * @see Resource.Path
     */
    @PostMapping("/delete")
    public ResponseEntity<?> deleteFile(@RequestParam(name = "type") int type,
                                        @RequestParam(name = "name") String name) {
        if (type < 0 || type >= Resource.Path.values().length) {
            Response response = Response.error(HttpStatus.BAD_REQUEST, String.format("Type from 0 to %d", Resource.Path.values().length - 1));
            return ResponseEntity.badRequest().body(response.body());
        }
        if (name == null) {
            return ResponseEntity.badRequest().body(Response.error(HttpStatus.BAD_REQUEST, "Name is null!"));
        }
        Resource.Path path = Resource.Path.values()[type];
        return storageService.deleteFile(path, name)
                ? ResponseEntity.ok().body(Response.ok().put("path", path.getPath()).put("name", name).body())
                : ResponseEntity.internalServerError().body(Response.error(HttpStatus.INTERNAL_SERVER_ERROR, "Fail to delete file.").body());
    }

    /**
     * Get url to access file. Depend on {@link ResourceConfig#SYSTEM_RESOURCE_TYPE}
     *
     * @param type type of Path {@link Resource.Path#ordinal() }
     * @param name name of File
     * @return success => 200
     * @see Resource.Path
     */
    @RequestMapping("/get")
    public ResponseEntity<?> getUrl(@RequestParam(name = "type") int type,
                                    @RequestParam(name = "name") String name) {
        if (type < 0 || type >= Resource.Path.values().length) {
            Response response = Response.error(HttpStatus.BAD_REQUEST, String.format("Type from 0 to %d", Resource.Path.values().length - 1));
            return ResponseEntity.badRequest().body(response.body());
        }
        Resource.Path path = Resource.Path.values()[type];
        return ResponseEntity.ok(Response.ok()
                .put("url", Resource.getUrl(path, name))
                .put("path", path.getPath())
                .put("name", name)
                .body());
    }

    private boolean isValidUUID(@Nullable String uuid) {
        if (uuid == null) {
            return false;
        }
        Pattern UUID_REGEX = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
        return UUID_REGEX.matcher(uuid).matches();
    }

}
