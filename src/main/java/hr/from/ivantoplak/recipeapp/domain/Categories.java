package hr.from.ivantoplak.recipeapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"recipes"})
@ToString(exclude = {"recipes"})
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipes> recipes;
}
