package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import com.crud.tasks.trello.facade.TrelloFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/trello")
public class TrelloController {

    @Autowired
    private TrelloFacade trelloFacade;

    @RequestMapping(method = RequestMethod.GET, value = "/boards")
    public List<TrelloBoardDto> getTrelloBoards() {
        return trelloFacade.fetchTrelloBoards();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cards")
    public CreatedTrelloCardDto createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return trelloFacade.createCard(trelloCardDto);
    }

//    @GetMapping("getBadges")
//    public void getBadges() {
//        trelloClient.getTrelloBadges();
//        List<CreatedTrelloCardDto> trelloCards = trelloClient.getTrelloBadges();
//        trelloCards.forEach(card -> {
//            System.out.println("id: " + card.getId());
//            System.out.println("badges:");
//            System.out.println("  votes: " + card.getBadges().getVotes());
//            System.out.println("  attachmentsByType:");
//            System.out.println("    trello:");
//            System.out.println("      card: " + card.getBadges().getAttachments().getTrello().getCard());
//            System.out.println("      board: " + card.getBadges().getAttachments().getTrello().getBoard());
//        });
//    }
}



