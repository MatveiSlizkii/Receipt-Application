package by.clevertec.checks.demoArtifact.dao.converters;

import by.clevertec.checks.demoArtifact.dao.entity.ProductEntity;
import by.clevertec.checks.demoArtifact.model.dto.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter implements Converter<ProductEntity, Product> {


    @Override
    public Product convert(ProductEntity source) {

        return Product.Builder.createBuilder()
                .setId(source.getId())
                .setBarcode(source.getBarcode())
                .setName(source.getName())
                .setCost(source.getCost())
                .setQuantity(source.getQuantity())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .build();
    }

    @Override
    public <U> Converter<ProductEntity, U> andThen(Converter<? super Product, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}






