package io.labforward.challenge.service;

import io.labforward.challenge.model.FrequencyReport;
import io.labforward.challenge.model.NotebookEntry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Mehdi Teymourlouie (mehdi.teymourlouie@gmail.com)
 * on 4/1/21.
 */
class NotebookServiceTest {

    private NotebookService notebookService;

    private AutoCloseable closeable;

    @Mock
    private SimilarityService similarityService;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        notebookService = new NotebookServiceImpl(similarityService);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testGetFrequencyReportInvalidParams() {
        // null entry
        assertThrows(IllegalArgumentException.class, () -> {
            notebookService.getFrequencyReport(null, "word");
        });

        // null text in entry
        assertThrows(IllegalArgumentException.class, () -> {
            final NotebookEntry entry = new NotebookEntry();
            notebookService.getFrequencyReport(entry, "word");
        });

        // null term
        assertThrows(IllegalArgumentException.class, () -> {
            final NotebookEntry entry = new NotebookEntry();
            entry.setText("some text to search");
            notebookService.getFrequencyReport(entry, null);
        });

        // blank term
        assertThrows(IllegalArgumentException.class, () -> {
            final NotebookEntry entry = new NotebookEntry();
            entry.setText("some text to search");
            notebookService.getFrequencyReport(entry, "    ");
        });

        // two word term
        assertThrows(IllegalArgumentException.class, () -> {
            final NotebookEntry entry = new NotebookEntry();
            entry.setText("some text to search");
            notebookService.getFrequencyReport(entry, "some   word");
        });
    }

    @Test
    void testCallSimilarityForUniqueWordsOnly() {
        final NotebookEntry entry = new NotebookEntry();
        entry.setText("Word Word Word word");
        final String term = "word";
        final FrequencyReport report = notebookService.getFrequencyReport(entry, term);

        assertThat(report, is(notNullValue()));
        assertThat(report.getFrequency(), is(1));
        assertThat(report.getTerm(), is(term));

        verify(similarityService, times(1)).isSimilar(any(), any());
        verify(similarityService, times(0)).isSimilar(term, term);
    }
}
