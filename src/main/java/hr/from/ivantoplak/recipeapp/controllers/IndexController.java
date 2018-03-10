package hr.from.ivantoplak.recipeapp.controllers;

import hr.from.ivantoplak.recipeapp.services.RecipesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class IndexController {

    private final RecipesService recipesService;

    @Autowired
    public IndexController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }

    @GetMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {

        log.debug("Getting Index page.");
        model.addAttribute("recipes", recipesService.getRecipes());
        return "index";
    }
}
