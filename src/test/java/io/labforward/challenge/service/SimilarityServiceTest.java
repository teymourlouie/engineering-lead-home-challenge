package io.labforward.challenge.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by Mehdi Teymourlouie (mehdi.teymourlouie@gmail.com)
 * on 4/2/21.
 */
class SimilarityServiceTest {

    private SimilarityService similarityService;

    @BeforeEach
    void setUp() {
        similarityService = new SimilarityServiceImpl("levenshtein", 1);
    }

    @Test
    void testIsSimilar() {
        assertThat(similarityService.isSimilar("word", "Word"), is(true));
        assertThat(similarityService.isSimilar("word", "Wordss"), is(false));

        assertThat(similarityService.isSimilar("string", "$tring"), is(true));

    }
}
