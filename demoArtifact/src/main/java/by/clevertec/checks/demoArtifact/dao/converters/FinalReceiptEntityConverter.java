package by.clevertec.checks.demoArtifact.dao.converters;

import by.clevertec.checks.demoArtifact.dao.entity.FinalReceiptEntity;
import by.clevertec.checks.demoArtifact.dao.entity.SalesReceiptEntity;
import by.clevertec.checks.demoArtifact.model.dto.FinalReceipt;
import by.clevertec.checks.demoArtifact.model.dto.SalesReceipt;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FinalReceiptEntityConverter implements Converter<FinalReceipt, FinalReceiptEntity> {


    @Override
    public FinalReceiptEntity convert(FinalReceipt source) {

        return FinalReceiptEntity.Builder.createBuilder()
                .setId(source.getId())
                .setDiscountCard(source.getDiscountCard())
                .setSubtotal(source.getSubtotal())
                .setTotal(source.getTotal())
                .setDtCreate(source.getDtCreate())
                .build();
    }

    @Override
    public <U> Converter<FinalReceipt, U> andThen(Converter<? super FinalReceiptEntity, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}






