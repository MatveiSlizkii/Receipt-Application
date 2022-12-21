package by.clevertec.checks.demoArtifact.dao.converters;

import by.clevertec.checks.demoArtifact.dao.entity.DiscountCardEntity;
import by.clevertec.checks.demoArtifact.model.dto.DiscountCard;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DiscountCardConverter implements Converter<DiscountCardEntity, DiscountCard> {


    @Override
    public DiscountCard convert(DiscountCardEntity source) {

        return DiscountCard.Builder.createBuilder()
                .setId(source.getId())
                .setBarcode(source.getBarcode())
                .setDiscount(source.getDiscount())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .build();
    }

    @Override
    public <U> Converter<DiscountCardEntity, U> andThen(Converter<? super DiscountCard, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}






