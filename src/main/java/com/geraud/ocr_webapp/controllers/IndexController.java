package com.geraud.ocr_webapp.controllers;

import com.geraud.ocr_webapp.model.Book;
import com.geraud.ocr_webapp.proxies.OcrBibliothequeProxy;
import com.geraud.ocr_webapp.utils.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexController {

    @Autowired
    OcrBibliothequeProxy ocrBibliothequeProxy;

    @RequestMapping({"" , "/" , "/index" ,"/index.html"})
    public String index(Model model , Integer page){

        if (page == null ){page = 0;};
        PagedModel<EntityModel<Book>> books = ocrBibliothequeProxy.showAllWithPagination(page);
        model.addAttribute("books" , books);
        model.addAttribute("identifiants", new Login());

        return "index";
    }
}
