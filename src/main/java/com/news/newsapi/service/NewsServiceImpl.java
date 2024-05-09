package com.news.newsapi.service;

import com.news.newsapi.entity.News;
import com.news.newsapi.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

//create class NewsServiceImpl with implementd NewsService interface
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    //override method findAll from NewsService interface
    @Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }
    //override method findById from NewsService interface
    @Override
    public News findById(int id) {
        return newsRepository.findById(id).orElse(null);
    }
    //override method save from NewsService interface
    @Override
    public News save(News news) {
        return newsRepository.save(news);
    }
    //override method deleteById from NewsService interface
    @Override
    public void deleteById(int id) {
        newsRepository.deleteById(id);
    }
    //override method for update from NewsService interface
    @Override
    public News update(News news, Integer id) {
        News news1 = newsRepository.findById(id).orElse(null);
        if (Objects.nonNull(news1)) {
            news1.setTitle(news.getTitle());
            news1.setAuthor(news.getAuthor());
            news1.setContent(news.getContent());
            news1.setCategory(news.getCategory());
            return newsRepository.save(news1);
        }
        return null;
    }
}