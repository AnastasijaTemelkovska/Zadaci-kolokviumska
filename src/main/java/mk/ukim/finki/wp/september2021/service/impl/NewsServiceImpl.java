package mk.ukim.finki.wp.september2021.service.impl;

import mk.ukim.finki.wp.september2021.model.News;
import mk.ukim.finki.wp.september2021.model.NewsCategory;
import mk.ukim.finki.wp.september2021.model.NewsType;
import mk.ukim.finki.wp.september2021.model.exceptions.InvalidNewsCategoryIdException;
import mk.ukim.finki.wp.september2021.model.exceptions.InvalidNewsIdException;
import mk.ukim.finki.wp.september2021.repository.NewsCategoryRepository;
import mk.ukim.finki.wp.september2021.repository.NewsRepository;
import mk.ukim.finki.wp.september2021.service.NewsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final NewsCategoryRepository newsCategoryRepository;
    private final PasswordEncoder passwordEncoder;

    public NewsServiceImpl(NewsRepository newsRepository, NewsCategoryRepository newsCategoryRepository, PasswordEncoder passwordEncoder) {
        this.newsRepository = newsRepository;
        this.newsCategoryRepository = newsCategoryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<News> listAllNews() {
        return this.newsRepository.findAll();
    }

    @Override
    public News findById(Long id) {
        return this.newsRepository.findById(id).orElseThrow(InvalidNewsIdException::new);
    }

    @Override
    public News create(String name, String description, Double price, NewsType type, Long category) {
        NewsCategory categories = this.newsCategoryRepository.findById(category).orElseThrow(InvalidNewsCategoryIdException::new);
        News news = new News(name, description,price,type, categories);
        return this.newsRepository.save(news);
    }

    @Override
    public News update(Long id, String name, String description, Double price, NewsType type, Long category) {
        News news = this.findById(id);
        news.setName(name);
        news.setDescription(description);
        news.setPrice(price);
        news.setType(type);
        NewsCategory categories = this.newsCategoryRepository.findById(category).orElseThrow(InvalidNewsCategoryIdException::new);
        news.setCategory(categories);
    return this.newsRepository.save(news);
    }

    @Override
    public News delete(Long id) {
        News news = this.findById(id);
        this.newsRepository.delete(news);
        return news;
    }

    @Override
    public News like(Long id) {
        News news = this.findById(id);
        news.setLikes(news.getLikes()+1);
        newsRepository.save(news);
        return news;
    }

    @Override
    public List<News> listNewsWithPriceLessThanAndType(Double price, NewsType type) {
        return new ArrayList<>();
    }
}
