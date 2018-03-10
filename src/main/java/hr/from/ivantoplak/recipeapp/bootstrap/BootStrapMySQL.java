package hr.from.ivantoplak.recipeapp.bootstrap;

import hr.from.ivantoplak.recipeapp.domain.Categories;
import hr.from.ivantoplak.recipeapp.domain.UnitsOfMeasure;
import hr.from.ivantoplak.recipeapp.repositories.CategoriesRepository;
import hr.from.ivantoplak.recipeapp.repositories.UnitsOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile({"dev", "prod"})
public class BootStrapMySQL implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoriesRepository categoryRepository;
    private final UnitsOfMeasureRepository unitOfMeasureRepository;

    public BootStrapMySQL(CategoriesRepository categoriesRepository,
                          UnitsOfMeasureRepository unitsOfMeasureRepository) {
        this.categoryRepository = categoriesRepository;
        this.unitOfMeasureRepository = unitsOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if (categoryRepository.count() == 0L) {
            log.debug("Loading Categories");
            loadCategories();
        }

        if (unitOfMeasureRepository.count() == 0L) {
            log.debug("Loading UOMs");
            loadUom();
        }
    }

    private void loadCategories() {
        Categories cat1 = new Categories();
        cat1.setDescription("American");
        categoryRepository.save(cat1);

        Categories cat2 = new Categories();
        cat2.setDescription("Italian");
        categoryRepository.save(cat2);

        Categories cat3 = new Categories();
        cat3.setDescription("Mexican");
        categoryRepository.save(cat3);

        Categories cat4 = new Categories();
        cat4.setDescription("Fast Food");
        categoryRepository.save(cat4);
    }

    private void loadUom() {
        UnitsOfMeasure uom1 = new UnitsOfMeasure();
        uom1.setDescription("Teaspoon");
        unitOfMeasureRepository.save(uom1);

        UnitsOfMeasure uom2 = new UnitsOfMeasure();
        uom2.setDescription("Tablespoon");
        unitOfMeasureRepository.save(uom2);

        UnitsOfMeasure uom3 = new UnitsOfMeasure();
        uom3.setDescription("Cup");
        unitOfMeasureRepository.save(uom3);

        UnitsOfMeasure uom4 = new UnitsOfMeasure();
        uom4.setDescription("Pinch");
        unitOfMeasureRepository.save(uom4);

        UnitsOfMeasure uom5 = new UnitsOfMeasure();
        uom5.setDescription("Ounce");
        unitOfMeasureRepository.save(uom5);

        UnitsOfMeasure uom6 = new UnitsOfMeasure();
        uom6.setDescription("Each");
        unitOfMeasureRepository.save(uom6);

        UnitsOfMeasure uom7 = new UnitsOfMeasure();
        uom7.setDescription("Pint");
        unitOfMeasureRepository.save(uom7);

        UnitsOfMeasure uom8 = new UnitsOfMeasure();
        uom8.setDescription("Dash");
        unitOfMeasureRepository.save(uom8);
    }
}