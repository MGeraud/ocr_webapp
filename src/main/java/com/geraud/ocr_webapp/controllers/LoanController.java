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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class LoanController {

    @Autowired
    RestTemplate restTemplate;

    /**
     * Liste les livres empruntés
     * @param login identifiants de l'emprunteur
     * @param model model pour la vue, regroupe la liste des livres empruntés sous l áttribut "myLoans"
     * @return page ffichant l'historique des livres empruntés
     */
    @RequestMapping("/myLoans")
    public String listLoanFromMember(@ModelAttribute("identifiants")Login login,
                                     Model model){

        //création de l'url à appeler à partir des critères de recherche récupérés (email et carte membre)
        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:9092/loan" )
                .queryParam("email" , login.getEmail())
                .queryParam("cardnumber", login.getCardnumber())
                .toUriString();
        // appel l'API gérnt les emprunts puis récupération des livres emprunté sous forme de tableau
        ResponseEntity<Loan[]> response = restTemplate.getForEntity(url,Loan[].class);
        Loan[] myLoans = response.getBody();

        model.addAttribute("myLoans" , myLoans);

        return "listMyLoans";
    }

    /**
     * méthode pour prolonger la période de prêt
     * @param id id de l'emprunt à prolonger
     * @param redirectAttributes récupération des identifiantsde l'emprunteur pour ne pas les lui redemander lors de la redirection
     * @return redirection vers l'historique des emprunts mis à jour
     */
    @RequestMapping("/loan/{id}/extend")
    public String extendLoan(@PathVariable(value = "id") Long id ,
                             RedirectAttributes redirectAttributes){
        //Création d'un loan temporaire qui sera envoyé à l'API gérant les emprunts en ne précisant que son id et l'incrémentation du compteur de prolongation
        Loan loan = new Loan();
        loan.setId(id);
        loan.setRefreshEndingCounter(1);

        //Utilisation de la factory client request d'Apache pour prise en compte du verbe http PATCH avec restTemplate
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        restTemplate.setRequestFactory(requestFactory);

        //création de l'url à appeler à partir de l'Id de l'emprunt à modifier
        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:9092/loan/" + loan.getId())
                .toUriString();
        //envoi de la requête et récupération de l'emprunt modifié
        Loan patchedLoan = restTemplate.patchForObject(url,loan , Loan.class);
        //attribution des identifiants de l'emprunt modifié pour envoyer directement au @ModelAttribute de la méthode du controller de l'historique des emprunts
        Login login = new Login();
        login.setCardnumber(patchedLoan.getMember().getCardnumber());
        login.setEmail(patchedLoan.getMember().getEmail());
        redirectAttributes.addFlashAttribute("identifiants", login);
        //redirection vers l'historique des emprunts
       return "redirect:/myLoans";
    }
}
