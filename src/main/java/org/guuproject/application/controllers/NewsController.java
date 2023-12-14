package org.guuproject.application.controllers;

import org.guuproject.application.models.Comment;
import org.guuproject.application.models.Image;
import org.guuproject.application.models.News;
import org.guuproject.application.models.User;
import org.guuproject.application.repositories.CommentRepository;
import org.guuproject.application.repositories.ImageRepository;
import org.guuproject.application.repositories.NewsRepository;
import org.guuproject.application.repositories.UserRepository;
import org.guuproject.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Controller
public class NewsController {

    public ImageRepository imageRepository;
    public UserRepository userRepository;
    public UserService userService;
    public NewsRepository newsRepository;
    public CommentRepository commentRepository;

    @Autowired
    public NewsController(ImageRepository imageRepository, UserRepository userRepository, UserService userService, NewsRepository newsRepository, CommentRepository commentRepository) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.newsRepository = newsRepository;
        this.commentRepository = commentRepository;
    }

    @RequestMapping("/news")
    public String getNewsPage(){
        return "news";
    }

    @GetMapping("/getNews")
    @ResponseBody
    public List<News> getNews(){
        List<News> news_list = new ArrayList<>();

        User user = userRepository.findUserById(userService.getAuthenticatedUserId());
        List<User> friends = user.getFriends();

        for (User friend:friends){
            news_list.addAll(friend.getNews());
        }

        news_list.sort(Comparator.comparing(News::getDate_of_creating));
        return news_list;
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
                news.setImage(image);
                news.setDate_of_creating(LocalDateTime.now());

                newsRepository.save(news);
                authenticatedUser.getNews().add(news);
                userRepository.save(authenticatedUser);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "redirect:/profile/"+userService.getAuthenticatedUserId();
    }

    @PostMapping("/createComment/{newsId}/{text}")
    @ResponseBody
    public User createComment(@PathVariable Long newsId, @PathVariable String text){
        News news = newsRepository.findById(newsId).get();
        Comment comment = new Comment();
        User writer = userRepository.findUserById(userService.getAuthenticatedUserId());
        comment.setSender(writer);
        comment.setMessage(text);
        commentRepository.save(comment);
        news.getComments().add(comment);
        newsRepository.save(news);
        return writer;
    }

    @PostMapping("/createLike/{newsId}")
    @ResponseBody
    public Boolean createLike(@PathVariable Long newsId){
        News news = newsRepository.findById(newsId).get();
        if (news.getLikes().contains(userService.getAuthenticatedUserId())) return false;
        else {
            news.getLikes().add(userService.getAuthenticatedUserId());
            newsRepository.save(news);
            return true;
        }
    }
}
