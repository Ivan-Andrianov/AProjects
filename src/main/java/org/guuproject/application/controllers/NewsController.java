package org.guuproject.application.controllers;

import org.guuproject.application.models.Image;
import org.guuproject.application.models.News;
import org.guuproject.application.models.User;
import org.guuproject.application.repositories.ImageRepository;
import org.guuproject.application.repositories.NewsRepository;
import org.guuproject.application.repositories.UserRepository;
import org.guuproject.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@Controller
public class NewsController {

    public ImageRepository imageRepository;
    public UserRepository userRepository;
    public UserService userService;
    public NewsRepository newsRepository;

    @Autowired
    public NewsController(ImageRepository imageRepository, UserRepository userRepository, UserService userService, NewsRepository newsRepository) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.newsRepository = newsRepository;
    }

    @PostMapping("/createNews")
    public String createNews(@RequestParam(name = "file") MultipartFile file,@RequestParam(name = "topic") String topic){
        if (file!=null){
            try {
                byte[] bytes = file.getBytes();
                Image image = new Image();
                User authenticatedUser = userRepository.findUserById(userService.getAuthenticatedUserId());

                imageRepository.save(image);
                image.setPath("/storage/"+image.getId()+".png");
                image.setDate_of_creating(LocalDateTime.now());
                authenticatedUser.getImages().add(image);

                File saved_file = new File("/demetraUsersPictures/"+image.getId()+".png");
                saved_file.createNewFile();

                FileOutputStream stream = new FileOutputStream(saved_file);
                stream.write(bytes);
                stream.close();

                News news = new News();
                news.setTopic(topic);
                news.setWriter(authenticatedUser);
                newsRepository.save(news);
                authenticatedUser.getNews().add(news);
                userRepository.save(authenticatedUser);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "redirect:/profile/"+userService.getAuthenticatedUserId();
    }
}
