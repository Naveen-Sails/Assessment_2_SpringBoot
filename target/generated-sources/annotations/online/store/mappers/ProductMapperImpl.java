package online.store.mappers;

import javax.annotation.Generated;
import online.store.model.Product;
import online.store.model.ProductDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-15T23:31:40+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.46.200.v20260930-0211, environment: Java 25.0.4.1 (Eclipse Adoptium)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDTO toDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        if ( product.getDescription() != null ) {
            productDTO.setDescription( product.getDescription() );
        }
        else {
            productDTO.setDescription( "No description available" );
        }
        productDTO.setId( product.getId() );
        productDTO.setName( product.getName() );
        productDTO.setImageFileName( product.getImageFileName() );
        productDTO.setPriceUSD( product.getPriceUSD() );
        productDTO.setCategory( product.getCategory() );

        return productDTO;
    }
}
