package com.example.library.utils;


import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class FileUtils {

    private static final int MB = 1024 * 1024;
    public static final int MAX_IMAGE_SIZE = 3 * MB;
    public static final int MAX_AUDIO_SIZE = 10 * MB;

    public static Pair<Boolean, String> validFile(boolean isImage, @NotNull MultipartFile file) {
        return isImage ? validImage(file) : validAudio(file);
    }

    @NotNull
    public static Pair<Boolean, String> validImage(@NotNull MultipartFile file) {
        // check ext
        String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
        if (ext == null) {
            return new Pair<>(false, "File ext invalid!");
        }
        if (!Arrays.asList("png", "jpg", "jpeg").contains(ext.toLowerCase())) {
            return new Pair<>(false, "File ext isn't support!");
        }
        // check size
        if (file.getSize() > MAX_IMAGE_SIZE) {
            return new Pair<>(false, String.format("File size is too large! (max size is %s)", MAX_IMAGE_SIZE));
        }
        // check if file is empty
        if (file.isEmpty()) {
            return new Pair<>(false, "File is empty!");
        }
        // check if file contains valid image data
        try {
            if (ImageIO.read(file.getInputStream()) == null) {
                return new Pair<>(false, "File is not a valid image!");
            }
        } catch (IOException e) {
            return new Pair<>(false, e.getMessage());
        }
        return new Pair<>(true, null);
    }

    @NotNull
    public static Pair<Boolean, String> validAudio(@NotNull MultipartFile file) {
        // check ext
        String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
        if (ext == null) {
            return new Pair<>(false, "File ext invalid!");
        }
        if (!Objects.equals("mp3", ext.toLowerCase())) {
            return new Pair<>(false, "File ext isn't support!");
        }
        // check size
        if (file.getSize() > MAX_AUDIO_SIZE) {
            return new Pair<>(false, String.format("File size is too large! (max size is %s)", MAX_IMAGE_SIZE));
        }
        // check if file is empty
        if (file.isEmpty()) {
            return new Pair<>(false, "File is empty!");
        }
        /*/
        // check if file contains valid audio data
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file.getInputStream());
            if (audioInputStream == null) {
                return new Pair<>(false, "File is not a valid audio file!");
            }
        } catch (UnsupportedAudioFileException | IOException e) {
            return new Pair<>(false, e.getMessage());
        }
        /*/
        return new Pair<>(true, null);
    }
}
