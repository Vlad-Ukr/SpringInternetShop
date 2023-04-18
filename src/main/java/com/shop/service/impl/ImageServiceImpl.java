package com.shop.service.impl;

import com.shop.model.User;
import com.shop.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
@PropertySource("classpath:application.properties")
public class ImageServiceImpl implements ImageService {
    private static final String UPLOAD_PATH_PROPERTY = "${upload.path}";
    private static final String PNG_SUFFIX = ".png";
    private static final String FILE_SEPARATOR = "/";
    private static final String SPLIT_REGEX = "\\.";
    private static final String LOGO_PICTURE_NAME = "logo.png";
    @Value(UPLOAD_PATH_PROPERTY)
    private String uploadPath;

    @Override
    public void saveProfilePicture(User user, MultipartFile file) {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = user.getId();
            try {
                file.transferTo(new File(uploadPath + FILE_SEPARATOR + uuidFile + PNG_SUFFIX));
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }

        }
    }

    @Override
    public byte[] findProfilePictureById(String id) {
        try {
            return fileToImageByteArray(findFileById(id, uploadPath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return new byte[0];
    }

    private byte[] fileToImageByteArray(File file) throws IOException {
        BufferedImage bImage = ImageIO.read(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, file.getName().split(SPLIT_REGEX)[1], bos);
        return bos.toByteArray();
    }

    private File findFileById(String id, String path) {
        File[] files = new File(path).listFiles();
        for (File file : Objects.requireNonNull(files)) {
            if (!file.isDirectory() && file.getName().split(SPLIT_REGEX)[0].equals(id)) {
                return file;
            }
        }
        return new File(path + LOGO_PICTURE_NAME);
    }
}
