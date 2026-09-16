package com.smartparking.assistant.controller;

import com.smartparking.assistant.dto.ChatRequest;
import com.smartparking.assistant.dto.ChatResponse;
import com.smartparking.assistant.service.AssistantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assistant")
@RequiredArgsConstructor
public class AssistantController {

    private final AssistantService assistantService;

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {
        return ResponseEntity.ok(assistantService.chat(request.message())
        );
    }
}