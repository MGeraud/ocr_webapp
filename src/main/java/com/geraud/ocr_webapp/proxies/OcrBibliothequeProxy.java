package com.geraud.ocr_webapp.proxies;

import com.geraud.ocr_webapp.model.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "bibliotheque" , url = "http://localhost:9090")
public interface OcrBibliothequeProxy {

    @GetMapping(path = "/books")
    PagedModel<EntityModel<Book>> showAllWithPagination(@RequestParam("page") int page);


}
