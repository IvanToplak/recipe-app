package hr.from.ivantoplak.recipeapp.controllers;

import hr.from.ivantoplak.recipeapp.commands.RecipesCommand;
import hr.from.ivantoplak.recipeapp.exceptions.NotFoundException;
import hr.from.ivantoplak.recipeapp.services.RecipesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
public class RecipesController {

    private final RecipesService recipesService;

    @Autowired
    public RecipesController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }

    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model) {

        model.addAttribute("recipe", recipesService.findById(Long.valueOf(id)));

        return "recipe/show";
    }

    @GetMapping("/recipe/new")
    public String newRecipe(Model model) {

        model.addAttribute("recipe", new RecipesCommand());

        return "recipe/recipeform";
    }

    @GetMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {

        model.addAttribute("recipe", recipesService.findCommandById(Long.valueOf(id)));

        return "recipe/recipeform";
    }

    @PostMapping("/recipe")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipesCommand command, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "recipe/recipeform";
        }

        RecipesCommand savedCommand = recipesService.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/recipe/{id}/delete")
    public String deleteById(@PathVariable String id) {

        log.debug("Deleting recipe with id: " + id);

        recipesService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception e) {

        log.error("Handling not found exception");
        log.error(e.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", e);

        return modelAndView;
    }
}
