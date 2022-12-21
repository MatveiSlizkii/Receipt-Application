package by.clevertec.checks.demoArtifact.model.dto;

import by.clevertec.checks.demoArtifact.model.serializer.CustomLocalDateTimeDeserializer;
import by.clevertec.checks.demoArtifact.model.serializer.CustomLocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.UUID;

public class FinalReceipt {
    private UUID id;
    private UUID discountCard;
    private Double subtotal;
    private Double total;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime dtCreate;


    public FinalReceipt(UUID id, UUID discountCard, Double subtotal,
                        Double total, LocalDateTime dtCreate) {
        this.id = id;
        this.discountCard = discountCard;
        this.subtotal = subtotal;
        this.total = total;
        this.dtCreate = dtCreate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getDiscountCard() {
        return discountCard;
    }

    public void setDiscountCard(UUID discountCard) {
        this.discountCard = discountCard;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }


    public static class Builder {
        private UUID id;
        private UUID discountCard;
        private Double subtotal;
        private Double total;
        private LocalDateTime dtCreate;

        private Builder() {
        }

        public Builder setId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder setDiscountCard(UUID discountCard) {
            this.discountCard = discountCard;
            return this;
        }

        public Builder setSubtotal(Double subtotal) {
            this.subtotal = subtotal;
            return this;
        }

        public Builder setTotal(Double total) {
            this.total = total;
            return this;
        }

        public Builder setDtCreate(LocalDateTime dtCreate) {
            this.dtCreate = dtCreate;
            return this;
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public FinalReceipt build() {
            return new FinalReceipt(id, discountCard, subtotal, total, dtCreate);
        }
    }
}
