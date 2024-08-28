package com.example.projectc14;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseStore {

    private int questionNumber = 0;
    private Map<Integer, List<Integer>> responses;

    public ResponseStore() {
        this.responses = new HashMap<>();
        responses.put(0, Arrays.asList(1,1,1,1));
        responses.put(1, Arrays.asList(1,1,1,1));
        responses.put(2, Arrays.asList(1,1,1,1));
        responses.put(3, Arrays.asList(1,1,1,1));
        responses.put(4, Arrays.asList(1,1,1,1));
        responses.put(5, Arrays.asList(1,1,1,1));
        responses.put(6, Arrays.asList(1,1,1,1));
        responses.put(7, Arrays.asList(1,1,1,1));
        responses.put(8, Arrays.asList(1,1,1,1));
    }

    public void setResponse(int key, int r1, int r2, int r3, int r4 ) {
        responses.replace( key, Arrays.asList(r1, r2, r3, r4));
    }

    public void increaseQuestionNumber() {
        questionNumber++;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public Map<Integer, List<Integer>> getResponses() {
        return responses;
    }
}
