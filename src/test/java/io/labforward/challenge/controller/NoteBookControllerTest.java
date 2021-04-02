package io.labforward.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.labforward.challenge.model.FrequencyReport;
import io.labforward.challenge.model.NotebookEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Mehdi Teymourlouie (mehdi.teymourlouie@gmail.com)
 * on 4/2/21.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class NoteBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void testNoBody() throws Exception {
        mockMvc
                .perform(
                        post("/api/notebook/frequency")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("term", "something"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testNoSearchTerm() throws Exception {
        NotebookEntry entry = new NotebookEntry();
        entry.setText("some text goes here");
        mockMvc
                .perform(
                        post("/api/notebook/frequency")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(entry)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSingleWordSearchTerm() throws Exception {
        NotebookEntry entry = new NotebookEntry();
        entry.setText("some text goes here");
        mockMvc
                .perform(
                        post("/api/notebook/frequency")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(entry))
                                .queryParam("term", "some word"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSampleFrequency() throws Exception {
        testSampleFrequencies("Word Words Wor word", "Word", 1, "Words", "Wor", "word");

        testSampleFrequencies("Word Word Word word", "Word", 3, "word");
    }

    private void testSampleFrequencies(String entryText, String term, int expectedFrequency, String... similarWords) throws Exception {
        NotebookEntry entry = new NotebookEntry();
        entry.setText(entryText);

        FrequencyReport report = mapper.readValue(
                mockMvc
                        .perform(
                                post("/api/notebook/frequency")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(mapper.writeValueAsString(entry))
                                        .queryParam("term", term))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                FrequencyReport.class);


        assertThat(report, is(notNullValue()));
        assertThat(report.getFrequency(), is(expectedFrequency));
        assertThat(report.getTerm(), is(term));
        assertThat(report.getSimilarTerms(), containsInAnyOrder(similarWords));
    }
}
