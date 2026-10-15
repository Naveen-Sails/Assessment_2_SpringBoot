package online.store.controllers;

import online.store.model.ProductDTO;
import online.store.model.wrappers.ProductWrapper;
import online.store.services.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomepageController {
    private final ProductsService productService;

    @GetMapping("/categories")
    public String getProductCategories() {
        return productService.getSupportedCategoriesResponse();
    }

    @GetMapping("/deals_of_the_day/{number_of_products}")
    public ProductWrapper<ProductDTO> getDealsOfTheDay(
            @PathVariable(name = "number_of_products") int numberOfProducts
    ) {
        return ProductWrapper.fromList(productService.getDealsOfTheDay(numberOfProducts));
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
        return ProductWrapper.fromPage(products);
    }


}