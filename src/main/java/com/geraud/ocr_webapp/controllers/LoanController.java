package com.geraud.ocr_webapp.controllers;

import com.geraud.ocr_webapp.model.Loan;
import com.geraud.ocr_webapp.utils.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class LoanController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/myLoans")
    public String listLoanFromMember(@ModelAttribute("identifiants")Login login,
                                     Model model){

        //création de l'url à appeler à partir des critères de recherche récupérés (email et carte membre)
        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:9092/loan" )
                .queryParam("email" , login.getEmail())
                .queryParam("cardnumber", login.getCardnumber())
                .toUriString();
        ResponseEntity<Loan[]> response =
                restTemplate.getForEntity(
                        url,
                        Loan[].class);
        Loan[] myLoans = response.getBody();

        model.addAttribute("myLoans" , myLoans);

        return "listMyLoans";
    }

    @RequestMapping("/loan/{id}/extend")
    public String extendLoan(@PathVariable(value = "id") Long id){

        Loan loan = new Loan();
        loan.setId(id);
        loan.setRefreshEndingCounter(1);
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        restTemplate.setRequestFactory(requestFactory);

        //création de l'url à appeler à partir des critères de recherche récupérés (email et carte membre)
        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:9092/loan/" + loan.getId())
                .toUriString();
        restTemplate.patchForObject(url,loan , Loan.class);
       return "redirect:/index";
    }
}
