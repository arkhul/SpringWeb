package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TaskMapperTest {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    void mapToTaskTest() {
        // Given
        TaskDto taskDto1 = new TaskDto(1L, "First", "Test1");
        TaskDto taskDto2 = new TaskDto(2L, "Second", "Test2");

        // When
        Task task1 = taskMapper.mapToTask(taskDto1);
        Task task2 = taskMapper.mapToTask(taskDto2);

        // Then
        assertEquals("First", task1.getTitle());
        assertEquals("Test2", task2.getContent());
    }

    @Test
    void mapToTaskDtoTest() {
        // Given
        Task task1 = new Task(1L, "First", "Test1");
        Task task2 = new Task(2L, "Second", "Test2");

        // When
        TaskDto taskDto1 = taskMapper.mapToTaskDto(task1);
        TaskDto taskDto2 = taskMapper.mapToTaskDto(task2);

        // Then
        assertThat(taskDto1.getId()).isEqualTo(1L);
        assertThat(taskDto2.getId()).isEqualTo(2L);
        assertThat(taskDto2.getTitle()).isEqualTo("Second");
    }

    @Test
    void mapToTaskDtoListTest() {
        // Given
        List<Task> taskList = List.of(new Task(1L, "First", "Test1"));

        // When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);

        // Then
        assertThat(taskDtoList).isNotNull();
        assertThat(taskDtoList.size()).isEqualTo(1);

        taskDtoList.forEach(taskDto -> {
                assertThat(taskDto.getId()).isEqualTo(1L);
                assertThat(taskDto.getTitle()).isEqualTo("First");
                assertThat(taskDto.getContent()).isEqualTo("Test1");
        });
    }
}