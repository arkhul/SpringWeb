package com.crud.tasks.controller;


import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper taskMapper;

    @Autowired
    private TaskController taskController;

    @Test
    void getTasksTest() throws Exception {
        // Given
        when(taskController.getTasks()).thenReturn(List.of(new TaskDto(1L, "First", "Task_1")));

        // When & Then
        mockMvc
                .perform(get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("First")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("Task_1")));
    }

    @Test
    void getTaskTest() throws TaskNotFoundException, Exception {
        // Given
        when(Optional.of(taskController.getTask(any(Long.class))))
                .thenReturn(Optional.ofNullable(Optional.of(new TaskDto(1L, "First", "Task_1")).orElseThrow(TaskNotFoundException::new)));

        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/{taskId}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("First")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Task_1")));
    }

    @Test
    void deleteTaskTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateTaskTest() throws Exception {
        // Given
        TaskDto taskDto = new TaskDto(22L, "Title22", "Content22");
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        when(taskController.updateTask(any(TaskDto.class))).thenReturn(taskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(22)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Title22")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Content22")));
    }

    @Test
    void createTaskTest() throws Exception {
    // Given
//        TaskDto taskDto = new TaskDto(22L, "Title22", "Content22");
//        when(taskController.createTask(any(TaskDto.class))).thenReturn(taskDto);
//        Gson gson = new Gson();
//        String jsonContent = gson.toJson(taskDto);
//
//        //When & Then
//        mockMvc
//                .perform(MockMvcRequestBuilders
//                        .post("/v1/tasks")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("UTF-8")
//                        .content(jsonContent))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(22)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Title22")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Content22")));
    }
}