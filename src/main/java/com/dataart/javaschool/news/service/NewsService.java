package com.dataart.javaschool.news.service;


import com.dataart.javaschool.news.models.News;
import com.dataart.javaschool.news.repo.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.zip.ZipInputStream;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<News> getNews() {
        return newsRepository.findByOrderByDateDesc();
    }

    public void addNews(String title, String anons, String full_text) {
        News news = new News(title, anons, full_text);
        newsRepository.save(news);
    }

    public void uploadNews(MultipartFile file) {
        int countLine = 0;
        String title = "";
        String anons = "";
        StringBuilder message = new StringBuilder();
        try (ZipInputStream zip = new ZipInputStream((file.getInputStream()))) {
            while (zip.getNextEntry() != null) {
                Scanner textFile = new Scanner(zip, StandardCharsets.UTF_8);
                while (textFile.hasNext()) {
                    String line = textFile.nextLine();
                    if (countLine == 0) {
                        title = line;
                    } else if (countLine == 1) {
                        anons = line;
                        message.append(line);
                    } else {
                        message.append(line);
                    }
                    countLine++;
                }
            }
            if (countLine > 1) {
                News news = new News(title, anons, message.toString());
                newsRepository.save(news);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public News getDetails(Long id) {
        Optional<News> news = newsRepository.findById(id);
        return news.orElseGet(news::get);
    }

    @Transactional
    public void updateNews(Long id, String title, String anons, String full_text) {
        News news = newsRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "news with id " + id + " does not exist"));
        news.setTitle(title);
        news.setAnons(anons);
        news.setFull_text(full_text);
    }

    public void deleteNews(Long id) {
        boolean exists = newsRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException(
                    "news with id " + id + " does not exist");
        }
        newsRepository.deleteById(id);
    }
}