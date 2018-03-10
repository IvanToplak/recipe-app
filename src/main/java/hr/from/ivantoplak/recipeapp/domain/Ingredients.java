package hr.from.ivantoplak.recipeapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@EqualsAndHashCode(exclude = {"recipe", "uom"})
@ToString(exclude = {"recipe", "uom"})
@NoArgsConstructor
public class Ingredients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private BigDecimal amount;

    @OneToOne
    private UnitsOfMeasure uom;

    @ManyToOne(fetch = FetchType.LAZY)
    private Recipes recipe;

    public Ingredients(String description, BigDecimal amount, UnitsOfMeasure uom, Recipes recipe) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
        this.recipe = recipe;
    }

    public Ingredients(String description, BigDecimal amount, UnitsOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }
}
