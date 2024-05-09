package com.news.newsapi.service;


import com.news.newsapi.entity.News;

import java.util.List;

//create interface NewsService with four crud operation
public interface NewsService {
    List<News> findAll();

    News findById(int id);

    //save method  return News and one param News
    News save(News news);

    void deleteById(int id);

    //update method  return News and two News and Integer
    News update(News news, Integer id);
}
