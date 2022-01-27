package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TrelloMapperTestSuite {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    void mapToBoardTest() {
        // Given
        TrelloListDto trelloListDto1 = new TrelloListDto("1", "First", false);
        TrelloListDto trelloListDto2 = new TrelloListDto("2", "Second", false);
        TrelloListDto trelloListDto3 = new TrelloListDto("3", "Third", true);

        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("100", "Board100",
                Arrays.asList(trelloListDto1, trelloListDto2, trelloListDto3));

        // When
        TrelloBoard trelloBoard = trelloMapper.mapToBoard(trelloBoardDto);

        List<TrelloList> trelloList = trelloBoard.getLists().stream()
                .filter(c -> !c.isClosed())
                .collect(Collectors.toList());

        // Then
        assertEquals("100", trelloBoard.getId());
        assertEquals("Board100", trelloBoard.getName());
        assertEquals(2, trelloList.size());
    }

    @Test
    void mapToBoardsTest() {
        // Given
        TrelloListDto trelloListDto1 = new TrelloListDto("1", "First", false);
        TrelloListDto trelloListDto2 = new TrelloListDto("2", "Second", false);
        TrelloListDto trelloListDto3 = new TrelloListDto("3", "Third", true);

        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("100", "Board100",
                Arrays.asList(trelloListDto1, trelloListDto2, trelloListDto3));
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("200", "Board200",
                Arrays.asList(trelloListDto2, trelloListDto3, trelloListDto1));
        TrelloBoardDto trelloBoardDto3 = new TrelloBoardDto("300", "Board300",
                Arrays.asList(trelloListDto3, trelloListDto2, trelloListDto1));

        List<TrelloBoardDto> trelloBoardDtoList = Arrays.asList(trelloBoardDto1, trelloBoardDto2, trelloBoardDto3);

        // When
        List<TrelloBoard> trelloBoardList = trelloMapper.mapToBoards(trelloBoardDtoList);
        String name = trelloBoardList.get(0).getName();
        String id = trelloBoardList.get(2).getId();

        // Then
        assertEquals(3, trelloBoardList.size());
        assertEquals("Board100", name);
        assertEquals("300", id);
    }

    @Test
    void mapToBoardsDtoTest() {
        // Given
        TrelloList trelloList1 = new TrelloList("1", "First", false);
        TrelloList trelloList2 = new TrelloList("2", "Second", true);
        TrelloList trelloList3 = new TrelloList("3", "Third", false);

        List<TrelloList> trelloLists = Arrays.asList(trelloList1, trelloList2, trelloList3);

        TrelloBoard trelloBoard1 = new TrelloBoard("100", "Board100", trelloLists);
        TrelloBoard trelloBoard2 = new TrelloBoard("200", "Board200", trelloLists);
        TrelloBoard trelloBoard3 = new TrelloBoard("300", "Board300", trelloLists);

        List<TrelloBoard> trelloBoardList = Arrays.asList(trelloBoard1, trelloBoard2, trelloBoard3);

        // When
        List<TrelloBoardDto> trelloBoardDtoList = trelloMapper.mapToBoardsDto(trelloBoardList);
        String name = trelloBoardDtoList.get(0).getName();
        String id = trelloBoardDtoList.get(1).getId();

        // Then
        assertEquals(3, trelloBoardDtoList.size());
        assertEquals("Board100", name);
        assertEquals("200", id);
    }

    @Test
    void mapToListTest() {
        // Given
        TrelloListDto trelloListDto1 = new TrelloListDto("1", "First", false);
        TrelloListDto trelloListDto2 = new TrelloListDto("2", "Second", false);
        TrelloListDto trelloListDto3 = new TrelloListDto("3", "Third", true);

        List<TrelloListDto> trelloListDtos = Arrays.asList(trelloListDto1, trelloListDto2, trelloListDto3);

        // When
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDtos);
        String name = trelloLists.get(0).getName();
        boolean isClosed = trelloLists.get(2).isClosed();

        // Then
        assertEquals(3, trelloListDtos.size());
        assertEquals("First", name);
        assertTrue(isClosed);
    }

    @Test
    void mapToListDtoTest() {
        // Given
        TrelloList trelloList1 = new TrelloList("1", "First", false);
        TrelloList trelloList2 = new TrelloList("2", "Second", true);
        TrelloList trelloList3 = new TrelloList("3", "Third", false);

        List<TrelloList> trelloLists = Arrays.asList(trelloList1, trelloList2, trelloList3);

        // Whem
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloLists);
        boolean isClosed = trelloListDtos.get(1).isClosed();

        // Then
        assertEquals(3, trelloListDtos.size());
        assertTrue(isClosed);
    }

    @Test
    void mapToCardTest() {
        // Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Name", "Description", "Pos", "ListId");

        // Whem
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        String name = trelloCard.getName().toLowerCase();
        int length = trelloCard.getDescription().length();

        // Then
        assertEquals("name", name);
        assertEquals(11, length);
    }

    @Test
    void mapToCardDtoTest() {
        // Given
        TrelloCard trelloCard = new TrelloCard("NAME", "DESC", "POS", "LIST_ID");

        // When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        String desc = trelloCardDto.getDescription();

        // Then
        assertEquals("DESC", desc);
    }
}