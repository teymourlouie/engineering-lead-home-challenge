package io.labforward.challenge.service;

import io.labforward.challenge.model.FrequencyReport;
import io.labforward.challenge.model.NotebookEntry;

/**
 * Created by Mehdi Teymourlouie (mehdi.teymourlouie@gmail.com)
 * on 4/1/21.
 */
public interface NotebookService {
    FrequencyReport getFrequencyReport(NotebookEntry entry, String term);
}
