package com.geraud.ocr_webapp.controllers;

import com.geraud.ocr_webapp.model.Book;
import com.geraud.ocr_webapp.utils.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;


@Controller
public class IndexController {

    @Autowired
    RestTemplate restTemplate;
    @RequestMapping({"" , "/" , "/index" ,"/index.html"})
    public String index(Model model ){

        EntityModel<Book> books = restTemplate.getForObject("http://localhost:9090/books/" , EntityModel.class);
        model.addAttribute("books" , books);
        model.addAttribute("identifiants", new Login());

        return "index";
    }
}
