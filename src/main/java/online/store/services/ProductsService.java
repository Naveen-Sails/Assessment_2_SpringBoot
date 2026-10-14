package online.store.services;

import online.store.mappers.ProductMapper;
import online.store.model.Product;
import online.store.model.ProductDTO;
import online.store.repositories.ProductCategoryRepository;
import online.store.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public ProductsService(
            ProductRepository productRepository,
            ProductCategoryRepository productCategoryRepository,
            ProductMapper productMapper
    ) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productMapper = productMapper;
    }

    public List<String> getAllSupportedCategories() {
        return productCategoryRepository.findAll()
                .stream()
                .map(productCategory -> productCategory.getCategory())
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getDealsOfTheDay(int atMostNumberOfProducts) {
        return productRepository.findAtMostNumberOfProducts(atMostNumberOfProducts)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public Page<ProductDTO> getProducts(
            String category,
            Float minPrice,
            Float maxPrice,
            String search,
            int page,
            int limit
    ) {
        return productRepository.findProducts(
                        category,
                        minPrice,
                        maxPrice,
                        search,
                        PageRequest.of(page, limit)
                )
                .map(productMapper::toDto);
    }

    public List<ProductDTO> getProductsByCategory(String productCategory) {
        return productRepository.findByCategory(productCategory)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(long id) {
        return productMapper.toDto(getProductForOrder(id));
    }

    public Product getProductForOrder(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        String.format("Product with id %s doesn't exist", id)
                ));
    }

}
