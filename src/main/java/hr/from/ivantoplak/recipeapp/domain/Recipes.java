package hr.from.ivantoplak.recipeapp.domain;

import hr.from.ivantoplak.recipeapp.enums.Difficulty;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Recipes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe", orphanRemoval = true)
    private Set<Ingredients> ingredients = new HashSet<>();

    @Lob
    private Byte[] image;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @ManyToMany
    @JoinTable(name = "recipes_categories",
            joinColumns = @JoinColumn(name = "recipes_id"),
            inverseJoinColumns = @JoinColumn(name = "categories_id"))
    private Set<Categories> categories = new HashSet<>();

    public void setNotes(Notes notes) {
        if (notes != null) {
            this.notes = notes;
            notes.setRecipe(this);
        }
    }

    public Recipes addIngredient(Ingredients ingredient) {
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }
}
