package com.example.springproject.web;


import com.example.springproject.domain.Repo;
import com.example.springproject.service.RepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;


/**
 * The type Repo controller.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/repo/")
public class RepoController {
    @Autowired
    private RepoService repoService;

    /**
     * Get info optional.
     *
     * @param repo the repo
     * @return the optional
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "getInfo", method= RequestMethod.GET)
    public Optional<Repo> getInfo(String repo){
        return repoService.FindByRepoName(repo);
    }

}
