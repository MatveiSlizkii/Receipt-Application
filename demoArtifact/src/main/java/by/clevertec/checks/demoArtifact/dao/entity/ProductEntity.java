package by.clevertec.checks.demoArtifact.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Table(name = "product", schema = "app")
public class ProductEntity {
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "barcode")
    private int barcode;
    @Column(name = "name")
    private String name;
    @Column(name = "cost")
    private double cost;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @Version
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;

    public ProductEntity() {
    }

    public ProductEntity(UUID id, int barcode, String name,
                         double cost, Integer quantity,
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

    public int getBarcode() {
        return barcode;
    }

    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
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

        public ProductEntity build() {
            return new ProductEntity(id, barcode, name, cost, quantity, dtCreate, dtUpdate);
        }
    }

}
