package com.example.projectc14;

import java.util.List;

public class Template {
    private String initQuestion;
    private List<String> header;
    private List<String> questions;
    private String questionName;

    public Template(String initQuestion, List<String> header, List<String> questions, String questionName) {
        this.initQuestion = initQuestion;
        this.header = header;
        this.questions = questions;
        this.questionName = questionName;
    }

    public String getInitQuestion() {
        return initQuestion;
    }

    public List<String> getHeader() {
        return header;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public String getQuestionName() { return questionName; }
}