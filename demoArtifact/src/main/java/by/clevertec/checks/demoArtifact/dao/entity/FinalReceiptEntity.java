package by.clevertec.checks.demoArtifact.dao.entity;

import by.clevertec.checks.demoArtifact.model.serializer.CustomLocalDateTimeDeserializer;
import by.clevertec.checks.demoArtifact.model.serializer.CustomLocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Table(name = "final_receipt", schema = "app")
public class FinalReceiptEntity {
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "discount_card")
    private UUID discountCard;
    @Column(name = "subtotal")
    private Double subtotal;
    @Column(name = "total")
    private Double total;
    @Column (name = "dt_create")
    private LocalDateTime dtCreate;

    public FinalReceiptEntity() {
    }

    public FinalReceiptEntity(UUID id, UUID discountCard, Double subtotal,
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

        public FinalReceiptEntity build() {
            return new FinalReceiptEntity(id, discountCard, subtotal, total, dtCreate);
        }
    }
}
