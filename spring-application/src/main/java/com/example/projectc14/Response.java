package com.example.projectc14;

import org.springframework.stereotype.Component;

@Component
public class Response {

    private Integer response1;
    private Integer response2;
    private Integer response3;
    private Integer response4;

    public Response() {
        //Empty constructor for functionality
    }

    public Integer getResponse1() {
        return response1;
    }

    public Integer getResponse2() {
        return response2;
    }

    public Integer getResponse3() {
        return response3;
    }

    public Integer getResponse4() {
        return response4;
    }

    public void setResponse1(int response1) {
        this.response1 = response1;
    }

    public void setResponse2(int response2) {
        this.response2 = response2;
    }

    public void setResponse3(int response3) {
        this.response3 = response3;
    }

    public void setResponse4(int response4) {

        this.response4 = response4;
    }
}
