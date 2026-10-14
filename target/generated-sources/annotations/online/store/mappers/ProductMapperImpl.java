package online.store.mappers;

import javax.annotation.Generated;
import online.store.model.Product;
import online.store.model.ProductDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-14T10:19:06+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.20 (Oracle Corporation)"
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
