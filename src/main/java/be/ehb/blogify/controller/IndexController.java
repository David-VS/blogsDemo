package be.ehb.blogify.controller;

import be.ehb.blogify.dao.BlogDAO;
import be.ehb.blogify.model.BlogPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class IndexController {
    @Autowired
    private BlogDAO repo;

    @ModelAttribute("all")
    public Iterable<BlogPost> findAll(){
        return repo.findAllChronological();
    }

    @ModelAttribute("newPost")
    public BlogPost toSave(){
        return new BlogPost();
    }


    @RequestMapping(value = {"/", "/index"})
    public String showIndex(ModelMap map){
        return "index";
    }

    @RequestMapping(value = { "/about"})
    public String showAbout(){
        return "about";
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.POST)
    public String save(@ModelAttribute("newPost") @Valid BlogPost newPost,
                       BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "index";
        }
        repo.save(newPost);
        return "redirect:/index";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete( @PathVariable(value = "id") int id){
        repo.deleteById(id);
        return "redirect:/index";
    }
}