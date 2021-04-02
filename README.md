# Engineering Lead Take-Home Challenge

## Specifications

### User Story

- As a researcher, I would like to view the frequency of a given word and any similar words in my lab notebook entry.

### New Feature(s)

- Determine frequency of word occurrence in notebook Entry
- Determine similar words in notebook Entry

### Specification(s)

- Given a basic notebook entry with the text "Word Words Wor word"
  - When the frequency of the word "Word" is requested
  - Then the frequency is determined to be `1`
  - And the list of similar words is determined to be "Words", "Wor", "word "
- Given a basic notebook entry with the text "Word Word Word word"
  - When the frequency of the word "Word" is requested
  - Then the frequency is determined to be `3`
  - And the list of similar words is determined to be "word"

## Task

Please develop a simple Java REST API endpoint which satisfies the above user story. For this exercise, a word is
considered “similar” to another word if the Levenshtein distance is not more than 1.

## Requirements

For building and running the application you need:

- [JDK 11](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application on localhost:8080

```shell
mvn spring-boot:run
```

## API

### Request

```shell
POST /api/notebook/frequency?term=Word

body: 
{
  "text": "Word Words Wor word"
}
```

```shell
curl -X POST "http://localhost:8080/api/notebook/frequency?term=Word" \
  -H "Content-Type: application/json" \
  --data '{ "text" : "Word Words Wor word" }'
```

### Response

```json
{
  "term": "Word",
  "frequency": 1,
  "similar_terms": [
    "Words",
    "Wor",
    "word"
  ]
}
```

## Scope

This API is implemented using Java 11 and Spring boot 2. Currently, only the Levenshtein metric is implemented. However,
the structure of the application allows defining other metrics and configuring the metric using spring configuration
properties.

The security considerations are not implemented.

The input to the frequency and similarity algorithm is via POSTing data in the API call. This could be enhanced by
storing notes in a DB and referencing the notebook entries using their unique ids.

## User Interface to use the API

Similar words could be shown using a word cloud. The frequency of the search term could be used as a metric to determine
size of it the word cloud.

## How much time dedicated to develop this project?

less than 4 hours.
