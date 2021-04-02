package io.labforward.challenge.service;

import io.labforward.challenge.model.FrequencyReport;
import io.labforward.challenge.model.NotebookEntry;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Mehdi Teymourlouie (mehdi.teymourlouie@gmail.com)
 * on 4/1/21.
 */
@Service
public class NotebookServiceImpl implements NotebookService {

    private final SimilarityService similarityService;

    public NotebookServiceImpl(SimilarityService similarityService) {
        this.similarityService = similarityService;
    }

    @Override
    public FrequencyReport getFrequencyReport(NotebookEntry entry, String term) {
        if (entry == null || entry.getText() == null) {
            throw new IllegalArgumentException("invalid entry to report is provided!");
        }

        if (term == null || term.isBlank()) {
            throw new IllegalArgumentException("invalid term to get report is provided!");
        }

        final String searchTerm = term.trim();
        if (searchTerm.matches(".*\\s+.*")) {
            throw new IllegalArgumentException("invalid term: only a one word term is allowed!");
        }


        int frequency = 0;

        // using Stream capabilities of new java versions could be easier to implement,
        // however, I chose to use old fashion algorithm to use less memory in O(n) time complexity.
        final String[] words = entry.getText()
                .split("\\s+");
        Set<String> uniqueWords = new HashSet<>();
        for (String word : words) {
            if (word.equals(searchTerm)) {
                frequency++;
            } else {
                // track all other words
                uniqueWords.add(word);
            }
        }

        // find similar terms
        List<String> similarTerms = uniqueWords.stream()
                .filter(word -> similarityService.isSimilar(word, searchTerm))
                .collect(Collectors.toList());


        // build return object
        return FrequencyReport.builder()
                .frequency(frequency)
                .term(searchTerm)
                .similarTerms(similarTerms)
                .build();
    }
}
