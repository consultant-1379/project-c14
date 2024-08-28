package com.example.projectc14;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@SessionAttributes("myStore")
public class AppController {
    @Autowired
    private Map<Integer,Template> templates;

    AtomicInteger clientID = new AtomicInteger();

    @ModelAttribute("myStore")
    public ResponseStore myStore() {
        return new ResponseStore();
    }

    @GetMapping(value = "/")
    public String homePage(Model model) {
        model.addAttribute("myStore", new ResponseStore());
        return "index";
    }

    @GetMapping(value="/initQuestion")
    public String home(@ModelAttribute("Response") Response response, @ModelAttribute("myStore") ResponseStore responseStore, Model model, RedirectAttributes redirectAttributes, SessionStatus status){
        int qNum = responseStore.getQuestionNumber();

        if (qNum == 9){
            redirectAttributes.addFlashAttribute("ResponseStore", responseStore);
            status.setComplete();
            return "redirect:/graph";
        }

        if (responseStore.getQuestionNumber() != 0 && response.getResponse1() != null) responseStore.setResponse(
                qNum - 1,
                response.getResponse1(),
                response.getResponse2(),
                response.getResponse3(),
                response.getResponse4());
        
        model.addAttribute("Response", new Response());
        model.addAttribute("QuestionName",templates.get(qNum).getQuestionName());
        model.addAttribute("Header",templates.get(qNum).getHeader());
        model.addAttribute("Questions",templates.get(qNum).getInitQuestion());
        model.addAttribute("Redirect", "/followUpQuestion");

        return "questions";
    }

    @GetMapping(value="/followUpQuestion")
    public String handleCulture(@ModelAttribute("Response") Response response, @ModelAttribute("myStore") ResponseStore responseStore, Model model){
        int qNum = responseStore.getQuestionNumber();
        responseStore.increaseQuestionNumber();

        // Check if initial Question was yes, is so redirect back
        if (Objects.equals(response.getResponse1(), 1)) return "redirect:/initQuestion";

        model.addAttribute("Response", new Response());

        model.addAttribute("QuestionName",templates.get(qNum).getQuestionName());
        model.addAttribute("Header",templates.get(qNum).getHeader());
        model.addAttribute("Questions",templates.get(qNum).getQuestions());
        model.addAttribute("Redirect", "/initQuestion");

        return "questions";
    }
}
