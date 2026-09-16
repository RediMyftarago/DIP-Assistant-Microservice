package com.smartparking.assistant.service;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.JsonNode;
import com.smartparking.assistant.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AssistantService {

    private final FaqService faqService;
    private final ObjectMapper objectMapper;

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    private final RestClient restClient =
            RestClient.create();

    private static final String SYSTEM_CONTEXT = """
            You are the virtual assistant of a Smart Parking
            application for urban parking.

            Your purpose is only to help users understand
            and use the Smart Parking system.

            The system works as follows:

            - Users can view parking spots on a map.
            - Parking spots have three states:
              FREE, OCCUPIED and OUT_OF_SERVICE.

            - A parking spot can receive its occupancy
              information either from an IoT sensor
              or from a Computer Vision camera system.

            - FREE parking spots can be selected by users.

            - Some urban parking spots require payment.

            - Payments are processed through Stripe.

            - The user can press Navigate after choosing
              a parking spot.

            - The system calculates the shortest route
              to the selected parking spot.

            Answer briefly and clearly.

            Do not invent parking availability,
            prices, locations or live occupancy data.

            If the user asks about information that is
            not available to you, explain that they should
            check the Smart Parking map.

            Prefer answers of 1-4 sentences.

            Answer in the same language as the user.
            """;

    public ChatResponse chat(String message) {

        // fillimisht shihet nqs pyetja e perdoruesit eshte ne faq

        String faqAnswer = faqService.getAnswer(message);

        if (faqAnswer != null) {
            return new ChatResponse(faqAnswer, "FAQ");
        }
        // Perdor modelin nqs nuk ndodhet
        return askOpenAI(message);
    }

    private ChatResponse askOpenAI(String message) {
        try {

            Map<String, Object> requestBody =
                    Map.of(
                            "model", model,
                            "messages", List.of(Map.of("role", "system", "content", SYSTEM_CONTEXT), Map.of("role", "user", "content", message)), "max_tokens", 200, "temperature", 0.3);

            String response =
                    restClient.post()
                            .uri(
                                    "https://api.openai.com/v1/chat/completions"
                            )
                            .header(
                                    HttpHeaders.AUTHORIZATION,
                                    "Bearer " + apiKey
                            )
                            .contentType(
                                    MediaType.APPLICATION_JSON
                            )
                            .body(requestBody).retrieve().body(String.class);

            JsonNode json = objectMapper.readTree(response);

            String answer = json.get("choices").get(0).get("message").get("content").asText();
            return new ChatResponse(answer, "GPT");
        } catch (Exception e) {
            throw new RuntimeException("Deshtoi servisi", e);
        }
    }
}