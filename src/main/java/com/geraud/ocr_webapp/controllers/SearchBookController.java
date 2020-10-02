package com.geraud.ocr_webapp.controllers;

import com.geraud.ocr_webapp.model.Book;
import com.geraud.ocr_webapp.utils.SearchBookParameters;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class SearchBookController {





    @RequestMapping("/search")
    public String searchForm(Model model) {

        model.addAttribute("searchBookParameters" , new SearchBookParameters());
        return "searchBook";
    }

    @RequestMapping("/foundBook")
    public String foundBook(@ModelAttribute("searchBookParameters") SearchBookParameters searchBookParameters , Model model){



        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:9090/books/" + searchBookParameters.getSearchType())
               .queryParam("queryparam" , searchBookParameters.getSearchInput())
                .toUriString();


        RestTemplate restTemplate = new RestTemplate();
        PagedModel<EntityModel<Book>> foundBooks = restTemplate.getForObject(url , PagedModel.class);
        EntityModel<Book> foundentities = restTemplate.getForObject(url , EntityModel.class);


        model.addAttribute("entities" , foundentities);
        model.addAttribute("trouve" , foundBooks);
        return "searchBook";
    }
}
