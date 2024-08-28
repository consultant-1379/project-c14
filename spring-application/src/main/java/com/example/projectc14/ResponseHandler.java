package com.example.projectc14;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResponseHandler {
    private List<Double> resultMap;

    public ResponseHandler() {
        this.resultMap = new ArrayList<>();
    }

    public void addResults(Map<Integer, List<Integer>> responseMap) {
        for(Map.Entry<Integer, List<Integer>> entry: responseMap.entrySet()) {
            resultMap.add(calculateResult(entry.getValue()));
        }
    }

    public double calculateResult(List<Integer> responses) {
        if(responses.get(0) == 0 && responses.get(1) == 0 && responses.get(2) == 0 && responses.get(3) == 0) return 2.5;
        else if(responses.get(0) == 1 || responses.get(2) == 1) {
            if(responses.get(1) == 1 || responses.get(3) ==1) {
                return 2.5;
            } else {
                return 2;
            }
        } else return 3;
    }

    public List<Double> getResultMap() {
        return resultMap;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for(double num:resultMap) {
           if (result.toString().equals("")) {
               result.append(num);
           } else {
               result.append(",").append(num);
           }
        }
        return result.toString();
    }
}
