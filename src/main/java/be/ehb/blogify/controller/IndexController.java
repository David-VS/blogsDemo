package be.ehb.blogify.controller;

import be.ehb.blogify.dao.BlogDAO;
import be.ehb.blogify.model.BlogPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class IndexController {

    private final BlogDAO repo;

    @Autowired
    public IndexController(BlogDAO repo) {
        this.repo = repo;
    }

    @ModelAttribute("all")
    public Iterable<BlogPost> findAll(){
        return repo.findAllChronological();
    }

    @ModelAttribute("newPost")
    public BlogPost toSave(){
        return new BlogPost();
    }

    @GetMapping(value = {"/", "/index"})
    public String showIndex(ModelMap map){
        return "index";
    }

    @GetMapping(value = { "/about"})
    public String showAbout(){
        return "about";
    }

    @PostMapping(value = {"/", "/index"})
    public String save(@ModelAttribute("newPost") @Valid BlogPost newPost,
                       BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "index";
        repo.save(newPost);
        return "redirect:/index";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete( @PathVariable(value = "id") int id){
        repo.deleteById(id);
        return "redirect:/index";
    }
}