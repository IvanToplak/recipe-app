package hr.from.ivantoplak.recipeapp.controllers;

import hr.from.ivantoplak.recipeapp.commands.IngredientsCommand;
import hr.from.ivantoplak.recipeapp.commands.RecipesCommand;
import hr.from.ivantoplak.recipeapp.commands.UnitsOfMeasureCommand;
import hr.from.ivantoplak.recipeapp.services.IngredientsService;
import hr.from.ivantoplak.recipeapp.services.RecipesService;
import hr.from.ivantoplak.recipeapp.services.UnitsOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class IngredientsController {

    private final RecipesService recipesService;
    private final IngredientsService ingredientsService;
    private final UnitsOfMeasureService unitsOfMeasureService;

    @Autowired
    public IngredientsController(RecipesService recipesService, IngredientsService ingredientsService, UnitsOfMeasureService unitsOfMeasureService) {
        this.recipesService = recipesService;
        this.ingredientsService = ingredientsService;
        this.unitsOfMeasureService = unitsOfMeasureService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model) {

        log.debug("Getting ingredients list for recipe id: " + recipeId);
        model.addAttribute("recipe", recipesService.findCommandById(Long.valueOf(recipeId)));

        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {

        log.debug("Getting ingredient by recipe id: " + recipeId + " and ingredient id: " + id);
        model.addAttribute("ingredient", ingredientsService.findCommandByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));

        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model) {

        //checking validity of received recipe ID
        log.debug("Getting recipe by id: " + recipeId);
        RecipesCommand recipeCommand = recipesService.findCommandById(Long.valueOf(recipeId));

        //creating model object
        IngredientsCommand ingredientCommand = new IngredientsCommand();
        ingredientCommand.setRecipeId(recipeCommand.getId());
        ingredientCommand.setUom(new UnitsOfMeasureCommand());

        model.addAttribute("ingredient", ingredientCommand);
        log.debug("Getting list of all uoms");
        model.addAttribute("uomList", unitsOfMeasureService.listAll());

        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String updateIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {

        log.debug("Getting ingredient by recipe id: " + recipeId + " and ingredient id: " + id);
        model.addAttribute("ingredient", ingredientsService.findCommandByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));

        log.debug("Getting list of all uoms");
        model.addAttribute("uomList", unitsOfMeasureService.listAll());

        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientsCommand command) {

        IngredientsCommand savedCommand = ingredientsService.saveIngredientCommand(command);

        log.debug("saved receipe id:" + savedCommand.getRecipeId());
        log.debug("saved ingredient id:" + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteById(@PathVariable String recipeId, @PathVariable String id) {

        log.debug("deleting ingredient id:" + id);
        ingredientsService.deleteById(Long.valueOf(recipeId), Long.valueOf(id));

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
