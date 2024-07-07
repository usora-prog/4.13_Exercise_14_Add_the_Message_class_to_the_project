package com.example.demo.controller;

import com.example.demo.dto.Message;

import com.example.demo.dto.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class MessageController {

    private List<Message> messages = new ArrayList<>(Arrays.asList(
            new Message(1, "Заголовок_1", "Содержимое_1", LocalDateTime.of(2007, 12, 3, 10, 15, 30)),
            new Message(2, "Заголовок_2", "Содержимое_2", LocalDateTime.of(2007, 12, 3, 11, 15, 30)),
            new Message(3, "Заголовок_3", "Содержимое_3", LocalDateTime.of(2007, 12, 3, 12, 15, 30))
    ));

    // Возврат списка объектов Message
    @GetMapping("/message")
    public Iterable<Message> getMessage() {
        return messages;
    }

    // Возврат объекта Message по id
    @GetMapping("/message/{id}")
    public Optional<Message> findMessageById(@PathVariable int id) {
        return messages.stream().filter(m -> m.getId() == id).findFirst();
    }

    // Добавление объекта Message
    @PostMapping("/message")
    public Message addMessage(@RequestBody Message message) {
        messages.add(message);
        return message;
    }

    // Изменение объекта Message по id
    @PutMapping("/message/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable int id, @RequestBody Message message) {
        int index = - 1;
        for (Message m : messages) {
            if (m.getId() == id) {
                index = messages.indexOf(m);
                messages.set(index, message);
            }
        }
        return index == -1
                ? new ResponseEntity<>(addMessage(message), HttpStatus.CREATED)
                : new ResponseEntity<>(message, HttpStatus.OK);
    }

    // Удаление объекта Message по id
    @DeleteMapping("/message/{id}")
    public void deleteMessage(@PathVariable int id) {
        messages.removeIf(m -> m.getId() == id);
    }
}