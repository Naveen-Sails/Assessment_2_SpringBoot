package online.store.services;

import online.store.model.Product;
import online.store.model.ProductDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductsService {
    List<String> getAllSupportedCategories();

    String getSupportedCategoriesResponse();

    List<ProductDTO> getDealsOfTheDay(int atMostNumberOfProducts);

    Page<ProductDTO> getProducts(
            String category,
            Float minPrice,
            Float maxPrice,
            String search,
            int page,
            int limit
    );

    List<ProductDTO> getProductsByCategory(String productCategory);

    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(long id);

    Product getProductForOrder(long id);
}
