package dev.langchain4j.store.embedding;

import dev.langchain4j.Experimental;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.filter.MetadataFilter;
import lombok.Builder;

import static dev.langchain4j.internal.Utils.getOrDefault;
import static dev.langchain4j.internal.ValidationUtils.*;

/**
 * Represents a request to search in an {@link EmbeddingStore}.
 * Contains all search criteria.
 */
@Experimental
public class EmbeddingSearchRequest {

    private final Embedding queryEmbedding;
    private final int maxResults;
    private final double minScore;
    private final MetadataFilter metadataFilter;

    /**
     * Creates an instance of an EmbeddingSearchRequest.
     *
     * @param queryEmbedding The embedding used as a reference. Found embeddings should be similar to this one.
     *                       This is a mandatory parameter.
     * @param maxResults     The maximum number of embeddings to return. This is an optional parameter. Default: 3
     * @param minScore       The minimum score, ranging from 0 to 1 (inclusive).
     *                       Only embeddings with a score >= minScore will be returned.
     *                       This is an optional parameter. Default: 0
     * @param metadataFilter The {@link Metadata} filter to be applied during search.
     *                       Only {@link TextSegment}s whose {@link Metadata}
     *                       matches the {@link MetadataFilter} will be returned.
     *                       Please note that not all {@link EmbeddingStore}s support this feature yet.
     *                       This is an optional parameter. Default: no filtering
     */
    @Builder
    @Experimental
    public EmbeddingSearchRequest(Embedding queryEmbedding, Integer maxResults, Double minScore, MetadataFilter metadataFilter) {
        this.queryEmbedding = ensureNotNull(queryEmbedding, "queryEmbedding");
        this.maxResults = ensureGreaterThanZero(getOrDefault(maxResults, 3), "maxResults");
        this.minScore = ensureBetween(getOrDefault(minScore, 0.0), 0.0, 1.0, "minScore");
        this.metadataFilter = metadataFilter;
    }

    @Experimental
    public Embedding queryEmbedding() {
        return queryEmbedding;
    }

    @Experimental
    public int maxResults() {
        return maxResults;
    }

    @Experimental
    public double minScore() {
        return minScore;
    }

    @Experimental
    public MetadataFilter metadataFilter() {
        return metadataFilter;
    }
}
