package org.guuproject.application.controllers;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import org.guuproject.application.models.Image;
import org.guuproject.application.models.User;
import org.guuproject.application.repositories.ImageRepository;
import org.guuproject.application.repositories.UserRepository;
import org.guuproject.application.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;

@Controller
public class FileUploadController {

    public ImageRepository imageRepository;
    public UserService userService;
    public UserRepository userRepository;

    public FileUploadController(ImageRepository imageRepository, UserService userService, UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "upload", method= RequestMethod.GET)
    public String provideUploadInfo(){
        return "test";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name, @RequestParam("file")MultipartFile file){
        if (!file.isEmpty()){
            try{
                byte[] bytes = file.getBytes();
                Image image = new Image();
                User authenticatedUser = userRepository.findUserById(userService.getAuthenticatedUserId());

                imageRepository.save(image);
                image.setPath("/pictures/user_pictures/"+image.getId()+".png");
                image.setDate_of_creating(LocalDateTime.now());
                imageRepository.save(image);
                authenticatedUser.getImages().add(image);

                File saved_file = new File("src/main/resources/static/pictures/user_pictures/"+image.getId()+".png");
                saved_file.createNewFile();

                FileOutputStream stream = new FileOutputStream(saved_file);
                stream.write(bytes);

                stream.close();
                return "Вы удачно загрузили "+name+" в "+name+"-uploaded !";
            }catch (Exception e){
                return "Вам не удалось загрузить "+name+" потому что файл пустой.";
            }
        }else{
            return "Вам не удалось загрузить "+name+" потому что файл пустой.";
        }
    }

    @RequestMapping(path = "/upload/attach_avatar",method = RequestMethod.POST)
    public String attachNewAvatar(@RequestParam("file") MultipartFile file){
        if (!file.isEmpty()){
            try{
                byte[] bytes = file.getBytes();
                Image image = new Image();
                User authenticatedUser = userRepository.findUserById(userService.getAuthenticatedUserId());

                imageRepository.save(image);
                image.setPath("/storage/"+image.getId()+".png");
                image.setDate_of_creating(LocalDateTime.now());
                imageRepository.save(image);
                authenticatedUser.getImages().add(image);

                File saved_file = new File("/demetraUsersPictures/"+image.getId()+".png");
                saved_file.createNewFile();

                FileOutputStream stream = new FileOutputStream(saved_file);
                stream.write(bytes);

                stream.close();

                authenticatedUser.setAvatar(image);
                userRepository.save(authenticatedUser);

            }catch (Exception e){
            }
        }
        return "redirect:/profile/"+userService.getAuthenticatedUserId();
    }

}
