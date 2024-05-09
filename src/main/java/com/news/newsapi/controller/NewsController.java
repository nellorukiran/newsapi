package com.news.newsapi.controller;

import com.news.newsapi.entity.News;
import com.news.newsapi.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//create a class NewsController import @restController annotation
@RestController
@CrossOrigin
@RequestMapping("/v2/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/allNews")
    @PreAuthorize("hasAuthority('ADMIN_ROLES') or hasAuthority('USER_ROLES')")
    public List<News> getAllNews() {
        return newsService.findAll();
    }
    //create a PostMapping method updateNews with two params @RequestBody News news and @PathVariable Long id
    @PostMapping("/updateNews/{id}")
    @PreAuthorize("hasAuthority('USER_ROLES')")
    public News updateNews(@RequestBody News news, @PathVariable Integer id) {
        return newsService.update(news, id);
    }
    //create a PostMapping method saveNews with one params @RequestBody News news
    @PostMapping("/addNews")
    @PreAuthorize("hasAuthority('ADMIN_ROLES') or hasAuthority('USER_ROLES')")
    public News saveNews(@RequestBody News news) {
        return newsService.save(news);
    }
    //create a DeleteMapping method deleteNews with one params @PathVariable Integer id
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN_ROLES') or hasAuthority('USER_ROLES')")
    public void deleteNews(@PathVariable Integer id) {
        newsService.deleteById(id);
    }
}

