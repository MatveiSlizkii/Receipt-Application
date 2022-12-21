package by.clevertec.checks.demoArtifact.dao.converters;

import by.clevertec.checks.demoArtifact.dao.entity.FinalReceiptEntity;
import by.clevertec.checks.demoArtifact.dao.entity.SalesReceiptEntity;
import by.clevertec.checks.demoArtifact.model.dto.FinalReceipt;
import by.clevertec.checks.demoArtifact.model.dto.SalesReceipt;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FinalReceiptConverter implements Converter<FinalReceiptEntity, FinalReceipt> {


    @Override
    public FinalReceipt convert(FinalReceiptEntity source) {

        return FinalReceipt.Builder.createBuilder()
                .setId(source.getId())
                .setDiscountCard(source.getDiscountCard())
                .setSubtotal(source.getSubtotal())
                .setTotal(source.getTotal())
                .setDtCreate(source.getDtCreate())
                .build();
    }

    @Override
    public <U> Converter<FinalReceiptEntity, U> andThen(Converter<? super FinalReceipt, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}






