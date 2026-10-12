package online.store.controllers;
import online.store.model.wrappers.ProductWrapper;
import online.store.services.ProductsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.stream.Collectors;
@RestController
public class HomepageController {
    private final ProductsService productService;
 
    public HomepageController(ProductsService productService) {
        this.productService = productService;
    }

    @GetMapping("/categories")
    public String getProductCategories(){
        return productService.getAllSupportedCategories()
                .stream()
                .collect(Collectors.joining(","));
    }

    @GetMapping("/deals_of_the_day/{number_of_products}")
    public ProductWrapper getDealsOfTheDay(@PathVariable(name = "number_of_products") int numberOfProducts){
        return new ProductWrapper(productService.getDealsOfTheDay(numberOfProducts));
    }

    @GetMapping("/products")
    public ProductWrapper getProductsForCategory(@RequestParam(name = "category", required = false) String category) {
        if (category != null && !category.isEmpty()) {
            return new ProductWrapper(productService.getProductsByCategory(category));
        }
        return new ProductWrapper(productService.getAllProducts());
    }


}