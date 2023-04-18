package com.shop.service;

import com.shop.model.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Part;
import java.nio.file.FileAlreadyExistsException;

public interface ImageService {
    void saveProfilePicture(User user, MultipartFile file) throws FileAlreadyExistsException;

    byte[] findProfilePictureById(String id);

}
