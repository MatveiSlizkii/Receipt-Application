package by.clevertec.checks.demoArtifact.model.dto;

import by.clevertec.checks.demoArtifact.model.serializer.CustomLocalDateTimeDeserializer;
import by.clevertec.checks.demoArtifact.model.serializer.CustomLocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class SalesReceipt {
    private UUID id;
    private UUID idFinalReceipt;
    private UUID idProduct;
    private Double price;
    private Integer quantity;
    private Double subtotal;
    private Double quantityDiscount;
    private Double total;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime dtCreate;


    public SalesReceipt(UUID id, UUID idFinalReceipt, UUID idProduct, Double price,
                        Integer quantity, Double subtotal,
                        Double quantityDiscount, Double total, LocalDateTime dtCreate) {
        this.id = id;
        this.idFinalReceipt = idFinalReceipt;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
        this.quantityDiscount = quantityDiscount;
        this.total = total;
        this.dtCreate = dtCreate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdFinalReceipt() {
        return idFinalReceipt;
    }

    public void setIdFinalReceipt(UUID idFinalReceipt) {
        this.idFinalReceipt = idFinalReceipt;
    }

    public UUID getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(UUID idProduct) {
        this.idProduct = idProduct;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getQuantityDiscount() {
        return quantityDiscount;
    }

    public void setQuantityDiscount(Double quantityDiscount) {
        this.quantityDiscount = quantityDiscount;
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

        private UUID idFinalReceipt;

        private UUID idProduct;

        private Integer quantity;
        private Double price;
        private Double subtotal;
        private Double quantityDiscount;
        private Double total;
        private LocalDateTime dtCreate;
        private Builder() {
        }

        public Builder setId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder setIdFinalReceipt(UUID idFinalReceipt) {
            this.idFinalReceipt = idFinalReceipt;
            return this;
        }

        public Builder setIdProduct(UUID idProduct) {
            this.idProduct = idProduct;
            return this;
        }

        public Builder setQuantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setPrice(Double price) {
            this.price = price;
            return this;}

        public Builder setSubtotal(Double subtotal) {
            this.subtotal = subtotal;
            return this;
        }

        public Builder setQuantityDiscount(Double quantityDiscount) {
            this.quantityDiscount = quantityDiscount;
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

        public SalesReceipt build() {
            return new SalesReceipt(id, idFinalReceipt,idProduct,price, quantity, subtotal, quantityDiscount, total, dtCreate);
        }
    }

}
