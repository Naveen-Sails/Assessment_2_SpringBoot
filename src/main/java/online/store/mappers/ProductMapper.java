package online.store.mappers;

import online.store.model.Product;
import online.store.model.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.category", target = "categoryName")
    ProductDTO toDTO(Product product);
}
