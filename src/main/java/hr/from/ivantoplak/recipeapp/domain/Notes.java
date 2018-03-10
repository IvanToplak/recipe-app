package hr.from.ivantoplak.recipeapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(exclude = "recipe")
@ToString(exclude = {"recipe"})
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recipes recipe;

    @Lob
    private String recipeNotes;
}
