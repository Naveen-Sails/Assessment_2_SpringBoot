package online.store.services;

import online.store.mappers.ProductMapper;
import online.store.model.Product;
import online.store.model.dto.ProductDTO;
import online.store.repositories.ProductCategoryRepository;
import online.store.repositories.ProductRepository;
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
    private final ProductMapper productMapper;

    public ProductsService(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productMapper = productMapper;
    }

    public List<String> getAllSupportedCategories(){
        return productCategoryRepository.findAll()
                    .stream()
                    .map(productCategory -> productCategory.getCategory())
                    .collect(Collectors.toList());

    }

    public List<ProductDTO> getDealsOfTheDay(int atMostNumberOfProducts){
        return productRepository.findAtMostNumberOfProducts(atMostNumberOfProducts)
                .stream()
                .map(productMapper :: toDTO) // Converts each Product entity to a DTO
                .collect(Collectors.toList());
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

}
