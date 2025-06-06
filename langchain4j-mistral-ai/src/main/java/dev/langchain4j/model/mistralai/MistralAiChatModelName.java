package dev.langchain4j.model.mistralai;

/**
 * Represents the available chat completion models for Mistral AI.
 *
 * <p>
 * The chat completion models are used to generate responses for chat-based applications.
 * Each model has a specific power and capability level.
 * </p>
 *
 * <p>
 * The available chat completion models are:
 * </p>
 * <ul>
 *   <li>{@link #OPEN_MISTRAL_7B} - aka mistral-tiny-2312</li>
 *   <li>{@link #OPEN_MIXTRAL_8x7B} - aka mistral-small-2312</li>
 *   <li>{@link #MISTRAL_SMALL_LATEST} - aka mistral-small-2402</li>
 *   <li>{@link #MISTRAL_MEDIUM_LATEST} - aka mistral-medium-2312</li>
 *   <li>{@link #MISTRAL_LARGE_LATEST} - aka mistral-large-2402</li>
 * </ul>
 *
 * @see <a href="https://docs.mistral.ai/guides/model-selection/">Mistral Model Selection</a>
 */
public enum MistralAiChatModelName {
    OPEN_MISTRAL_7B("open-mistral-7b"), // aka mistral-tiny-2312

    OPEN_MIXTRAL_8x7B("open-mixtral-8x7b"), // aka mistral-small-2312
    OPEN_MIXTRAL_8X22B("open-mixtral-8x22b"), // aka open-mixtral-8x22b

    MISTRAL_SMALL_LATEST("mistral-small-latest"), // aka mistral-small-2402

    MISTRAL_MEDIUM_LATEST("mistral-medium-latest"), // aka mistral-medium-2312

    MISTRAL_LARGE_LATEST("mistral-large-latest"), // aka mistral-large-2402

    MISTRAL_MODERATION_LATEST("mistral-moderation-latest"),

    OPEN_MISTRAL_NEMO("open-mistral-nemo"), // aka open-mistral-nemo-2407

    CODESTRAL_LATEST("codestral-latest"); // aka codestral-latest

    private final String value;

    MistralAiChatModelName(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}
