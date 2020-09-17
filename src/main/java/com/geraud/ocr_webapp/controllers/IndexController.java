package com.geraud.ocr_webapp.controllers;

import com.geraud.ocr_webapp.domain.Book;
import com.geraud.ocr_webapp.proxies.OcrBibliothequeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class IndexController {

    @Autowired
    OcrBibliothequeProxy ocrBibliothequeProxy;

    @RequestMapping({"" , "/" , "/index" ,"/index.html"})
    public String index(Model model){

        PagedModel<EntityModel<Book>> books = ocrBibliothequeProxy.showAllWithPagination();

        model.addAttribute("books" , books);

        return "index";
    }
}
