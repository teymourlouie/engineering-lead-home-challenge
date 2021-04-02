package io.labforward.challenge.controller;

import io.labforward.challenge.model.FrequencyReport;
import io.labforward.challenge.model.NotebookEntry;
import io.labforward.challenge.service.NotebookService;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Mehdi Teymourlouie (mehdi.teymourlouie@gmail.com)
 * on 4/1/21.
 */
@RestController
@RequestMapping(NoteBookController.BASE_URL)
public class NoteBookController {

    protected static final String BASE_URL = "/api/notebook";

    private final NotebookService notebookService;

    public NoteBookController(NotebookService notebookService) {
        this.notebookService = notebookService;
    }


    @PostMapping("/frequency")
    public FrequencyReport getFrequencyAndSimilarWords(@RequestBody NotebookEntry entry,
                                                       @RequestParam("term") String term) {
        return notebookService.getFrequencyReport(entry, term);
    }
}
