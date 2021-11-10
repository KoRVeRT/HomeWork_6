package com.dataart.javaschool.news.controllers;

import com.dataart.javaschool.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news")
    public String newsMain(Model model) {
        model.addAttribute("news", newsService.getNews());
        return "news-main";
    }


    @GetMapping("/news/upload")
    public String newsUpload() {
        return "news-upload";
    }

    @PostMapping("/news/upload")
    public String uploadNewNews(@RequestParam("file") MultipartFile file) {
        newsService.uploadNews(file);
        return "redirect:/news";
    }

    @GetMapping("/news/add")
    public String newsAdd() {
        return "news-add";
    }

    @PostMapping("/news/add")
    public String addNewNews(
            @RequestParam String title,
            @RequestParam String anons,
            @RequestParam String full_text) {
        newsService.addNews(title, anons, full_text);
        return "redirect:/news";
    }

    @GetMapping("/news/{id}")
    public String newsDetails(@PathVariable Long id, Model model) {
        model.addAttribute("news", newsService.getDetails(id));
        return "news-details";
    }

    @GetMapping("/news/{id}/edit")
    public String newsEdit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("news", newsService.getDetails(id));
        return "news-edit";
    }

    @PostMapping("/news/{id}/edit")
    public String newsUpdate(
            @PathVariable("id") Long id,
            @RequestParam String title,
            @RequestParam String anons,
            @RequestParam String full_text) {
        newsService.updateNews(id, title, anons, full_text);
        return "redirect:/news";
    }

    @PostMapping("/news/{id}/delete")
    public String newsPostDelete(@PathVariable("id") Long id) {
        newsService.deleteNews(id);
        return "redirect:/news";
    }
}