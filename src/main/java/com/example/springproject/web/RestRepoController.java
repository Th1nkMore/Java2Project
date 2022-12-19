package com.example.springproject.web;

import com.example.springproject.service.RepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/")
public class RestRepoController {
    @Autowired
    private RepoService repoService;

    @RequestMapping(value = "solveavg", method = RequestMethod.GET)
    public String SolveAvg() {
        return repoService.SolveAvgSolTime();
    }

    @RequestMapping(value = "solvemostactive", method = RequestMethod.GET)
    public ArrayList<String> SolveMostActive() {
        return repoService.SolveMostActive();
    }

    @RequestMapping(value = "solveactivetime", method = RequestMethod.GET)
    public ArrayList<String> SolveActiveTime() {
        return repoService.SolveActiveTime();
    }
}
