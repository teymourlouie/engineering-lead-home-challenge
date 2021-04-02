package io.labforward.challenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by Mehdi Teymourlouie (mehdi.teymourlouie@gmail.com)
 * on 4/1/21.
 */
@Data
@Builder
public class FrequencyReport {

    private String term;

    private int frequency;

    @JsonProperty("similar_terms")
    private List<String> similarTerms;
}
