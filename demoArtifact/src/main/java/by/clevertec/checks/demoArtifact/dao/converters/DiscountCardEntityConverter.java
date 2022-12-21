package by.clevertec.checks.demoArtifact.dao.converters;

import by.clevertec.checks.demoArtifact.dao.entity.DiscountCardEntity;
import by.clevertec.checks.demoArtifact.model.dto.DiscountCard;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DiscountCardEntityConverter implements Converter<DiscountCard, DiscountCardEntity> {


    @Override
    public DiscountCardEntity convert(DiscountCard source) {

        return DiscountCardEntity.Builder.createBuilder()
                .setId(source.getId())
                .setBarcode(source.getBarcode())
                .setDiscount(source.getDiscount())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .build();
    }

    @Override
    public <U> Converter<DiscountCard, U> andThen(Converter<? super DiscountCardEntity, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}






