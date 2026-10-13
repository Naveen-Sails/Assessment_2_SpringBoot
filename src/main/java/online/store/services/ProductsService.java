package online.store.services;

import online.store.model.Product;
import online.store.repositories.ProductCategoryRepository;
import online.store.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Michael Pogrebinsky - www.topdeveloperacademy.com
 * Integrates with the database API and handles products and categories business logic
 */
@Service
public class ProductsService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public ProductsService(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    public List<String> getAllSupportedCategories(){
        return productCategoryRepository.findAll()
                    .stream()
                    .map(productCategory -> productCategory.getCategory())
                    .collect(Collectors.toList());

    }

    public List<Product> getDealsOfTheDay(int atMostNumberOfProducts){
        return productRepository.findAtMostNumberOfProducts(atMostNumberOfProducts);
    }

    public List<Product> getProductsByCategory(String productCategory){
        return productRepository.findByCategory(productCategory);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(long id){
        return productRepository.findById(id)
                .orElseThrow(()-> new IllegalStateException(String.format("Product with id %s doesn't exist", id)));
    }

    public Page<Product> getProducts(int pageNumber, int limit){
        // Convert 1-based user input to 0-based Spring input
        int actualPage = pageNumber > 0 ? pageNumber -1 : 0;

        //Create the Pageable object
        Pageable pageable = PageRequest.of(actualPage, limit);

        //Fetch and return the page of Products
        return productRepository.findAll(pageable);
    }

}
