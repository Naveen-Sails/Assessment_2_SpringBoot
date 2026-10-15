package online.store.mappers;

import online.store.model.Product;
import online.store.model.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    // Use a readable fallback when a product has no description.
    @Mapping(target = "description", source = "description", defaultValue = "No description available")
    ProductDTO toDto(Product product);
}
