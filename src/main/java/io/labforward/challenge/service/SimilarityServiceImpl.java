package io.labforward.challenge.service;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by Mehdi Teymourlouie (mehdi.teymourlouie@gmail.com)
 * on 4/2/21.
 */
@Service
public class SimilarityServiceImpl implements SimilarityService {

    private final String metric;

    private final Integer levenshteinThreshold;

    public SimilarityServiceImpl(@Value("${app.similarity.metric}") String metric,
                                 @Value("${app.similarity.levenshtein.threshold}") Integer levenshteinThreshold) {
        this.metric = metric;
        this.levenshteinThreshold = levenshteinThreshold;
    }

    @Override
    public boolean isSimilar(String left, String right) {
        switch (metric) {
            case "levenshtein":
            default:
                return isSimilarBasedOnLevenshteinMetric(left, right);
        }
    }

    private boolean isSimilarBasedOnLevenshteinMetric(String left, String right) {
        LevenshteinDistance distance = new LevenshteinDistance(levenshteinThreshold);
        return distance.apply(left, right) != -1;
    }
}
