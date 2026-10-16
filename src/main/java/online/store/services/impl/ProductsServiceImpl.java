package online.store.services.impl;

import online.store.exceptions.InvalidRequestException;
import online.store.exceptions.ProductNotFoundException;
import online.store.mappers.ProductMapper;
import online.store.model.Product;
import online.store.model.ProductDTO;
import online.store.repositories.ProductCategoryRepository;
import online.store.repositories.ProductRepository;
import online.store.services.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductMapper productMapper;

    @Override
    public List<String> getAllSupportedCategories() {
        return productCategoryRepository.findAll()
                .stream()
                .map(productCategory -> productCategory.getCategory())
                .collect(Collectors.toList());
    }

    @Override
    public String getSupportedCategoriesResponse() {
        // Keep the comma-separated response used by the existing API.
        return String.join(",", getAllSupportedCategories());
    }

    @Override
    public List<ProductDTO> getDealsOfTheDay(int atMostNumberOfProducts) {
        if (atMostNumberOfProducts <= 0) {
            throw new InvalidRequestException("Number of products must be greater than zero");
        }
        return productRepository.findAtMostNumberOfProducts(atMostNumberOfProducts)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductDTO> getProducts(
            String category,
            Float minPrice,
            Float maxPrice,
            String search,
            int page,
            int limit
    ) {
        if (page < 0) {
            throw new InvalidRequestException("Page must not be negative");
        }
        if (limit <= 0) {
            throw new InvalidRequestException("Limit must be greater than zero");
        }
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            throw new InvalidRequestException("Minimum price must not exceed maximum price");
        }
        // Pass filtering and pagination to the database instead of loading all products.
        return productRepository.findProducts(
                        category,
                        minPrice,
                        maxPrice,
                        search,
                        PageRequest.of(page, limit)
                )
                .map(productMapper::toDto);
    }

    @Override
    public List<ProductDTO> getProductsByCategory(String productCategory) {
        return productRepository.findByCategory(productCategory)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(long id) {
        return productMapper.toDto(getProductForOrder(id));
    }

    @Override
    public Product getProductForOrder(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        String.format("Product with id %s doesn't exist", id)
                ));
    }

}
