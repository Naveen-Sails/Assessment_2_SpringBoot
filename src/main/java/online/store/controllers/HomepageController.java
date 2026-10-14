package online.store.controllers;

import online.store.model.ProductDTO;
import online.store.model.wrappers.ProductWrapper;
import online.store.services.ProductsService;
import org.springframework.data.domain.Page;
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
    public ProductWrapper<ProductDTO> getDealsOfTheDay(
            @PathVariable(name = "number_of_products") int numberOfProducts
    ) {
        return new ProductWrapper<>(productService.getDealsOfTheDay(numberOfProducts));
    }

    @GetMapping("/products")
    public ProductWrapper<ProductDTO> getProducts(
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "minPrice", required = false) Float minPrice,
            @RequestParam(name = "maxPrice", required = false) Float maxPrice,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "20") int limit
    ) {
        Page<ProductDTO> products = productService.getProducts(
                category,
                minPrice,
                maxPrice,
                search,
                page,
                limit
        );
        return new ProductWrapper<>(products);
    }


}