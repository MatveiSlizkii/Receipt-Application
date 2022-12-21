package by.clevertec.checks.demoArtifact.model.dto;

import by.clevertec.checks.demoArtifact.model.serializer.CustomLocalDateTimeDeserializer;
import by.clevertec.checks.demoArtifact.model.serializer.CustomLocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.UUID;

public class Product {
    private UUID id;
    private Integer barcode;
    private String name;
    private Double cost;
    private Integer quantity;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime dtCreate;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime dtUpdate;

    public Product(UUID id, Integer barcode, String name,
                   Double cost, Integer quantity,
                   LocalDateTime dtCreate, LocalDateTime dtUpdate) {
        this.id = id;
        this.barcode = barcode;
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getBarcode() {
        return barcode;
    }

    public void setBarcode(Integer barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public static class Builder {
        private UUID id;
        private int barcode;
        private String name;
        private double cost;
        private Integer quantity;
        private LocalDateTime dtCreate;
        private LocalDateTime dtUpdate;

        private Builder() {
        }

        public Builder setId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder setBarcode(int barcode) {
            this.barcode = barcode;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setCost(double cost) {
            this.cost = cost;
            return this;
        }

        public Builder setQuantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setDtCreate(LocalDateTime dtCreate) {
            this.dtCreate = dtCreate;
            return this;
        }

        public Builder setDtUpdate(LocalDateTime dtUpdate) {
            this.dtUpdate = dtUpdate;
            return this;
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public Product build() {
            return new Product(id, barcode, name, cost, quantity, dtCreate, dtUpdate);
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", barcode=" + barcode +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", quantity=" + quantity +
                ", dtCreate=" + dtCreate +
                ", dtUpdate=" + dtUpdate +
                '}';
    }
}
