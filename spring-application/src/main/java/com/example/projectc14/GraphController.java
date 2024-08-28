package com.example.projectc14;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Controller
public class GraphController {

    @Autowired
    private Map<Integer, Template> templates;

    @Autowired
    private FileHandler fileHandler;

    @Value( "${resources.storeFolderPath}" )
    private String exportDirectory;

    @GetMapping(value = "/graph")
    public String graph(@ModelAttribute("ResponseStore") ResponseStore responseStore, Model model) throws IOException {
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.addResults(responseStore.getResponses());
        String fileName = fileHandler.saveGraph(responseHandler.toString(), exportDirectory);
        model.addAttribute("GraphId", fileName);
        model.addAttribute("LongValues", responseHandler.getResultMap());
        model.addAttribute("Culture", templates.get(0).getHeader());
        model.addAttribute("Product", templates.get(1).getHeader());
        model.addAttribute("Team", templates.get(2).getHeader());
        model.addAttribute("Process", templates.get(3).getHeader());
        model.addAttribute("Architecture", templates.get(4).getHeader());
        model.addAttribute("Maintenance", templates.get(5).getHeader());
        model.addAttribute("Delivery", templates.get(6).getHeader());
        model.addAttribute("Provisioning", templates.get(7).getHeader());
        model.addAttribute("Infrastructure", templates.get(8).getHeader());
        return "graph";
    }

    @GetMapping(value = "/loadGraph")
    public String loadGraph(Model model, @RequestParam String graphID) throws IOException {
        List<Double> list = fileHandler.loadGraph(graphID, exportDirectory);
        model.addAttribute("GraphId", graphID);
        model.addAttribute("LongValues", list);
        model.addAttribute("Culture", templates.get(0).getHeader());
        model.addAttribute("Product", templates.get(1).getHeader());
        model.addAttribute("Team", templates.get(2).getHeader());
        model.addAttribute("Process", templates.get(3).getHeader());
        model.addAttribute("Architecture", templates.get(4).getHeader());
        model.addAttribute("Maintenance", templates.get(5).getHeader());
        model.addAttribute("Delivery", templates.get(6).getHeader());
        model.addAttribute("Provisioning", templates.get(7).getHeader());
        model.addAttribute("Infrastructure", templates.get(8).getHeader());
        return  "graph";
    }
}
