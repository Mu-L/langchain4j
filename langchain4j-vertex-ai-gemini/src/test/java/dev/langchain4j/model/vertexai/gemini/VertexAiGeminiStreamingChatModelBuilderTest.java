package dev.langchain4j.model.vertexai.gemini;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

class VertexAiGeminiStreamingChatModelBuilderTest {

    @Test
    void setCustomHeader() {
        VertexAiGeminiStreamingChatModel model = VertexAiGeminiStreamingChatModel.builder()
                .project("does-not-matter")
                .location("does-not-matter")
                .modelName("does-not-matter")
                .customHeaders(Map.of("foo", "bar"))
                .build();

        final String actualFooHeader = model.vertexAI().getHeaders().getOrDefault("foo", "error");
        assertThat(actualFooHeader).isEqualTo("bar");
    }

    @Test
    void setDefaultUserAgent() {
        VertexAiGeminiStreamingChatModel model = VertexAiGeminiStreamingChatModel.builder()
                .project("does-not-matter")
                .location("does-not-matter")
                .modelName("does-not-matter")
                .build();

        final String actualFooHeader = model.vertexAI().getHeaders().getOrDefault("user-agent", "error");
        assertThat(actualFooHeader).contains("LangChain4j");
    }

    @Test
    void overwriteDefaultUserAgent() {
        VertexAiGeminiStreamingChatModel model = VertexAiGeminiStreamingChatModel.builder()
                .project("does-not-matter")
                .location("does-not-matter")
                .modelName("does-not-matter")
                .customHeaders(Map.of("user-agent", "my-custom-user-agent"))
                .build();

        final String actualFooHeader = model.vertexAI().getHeaders().getOrDefault("user-agent", "error");
        assertThat(actualFooHeader).contains("my-custom-user-agent");
    }
}
