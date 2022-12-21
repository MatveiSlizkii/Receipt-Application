package by.clevertec.checks.demoArtifact.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Table(name = "discount_card", schema = "app")
public class DiscountCardEntity {
    @Id
    @Column (name = "id")
    private UUID id;
    @Column (name = "barcode")
    private Integer barcode;
    @Column (name = "discount")
    private Integer discount;
    @Column (name = "dt_create")
    private LocalDateTime dtCreate;
    @Version
    @Column (name = "dt_update")
    private LocalDateTime dtUpdate;

    public DiscountCardEntity() {
    }

    public DiscountCardEntity(UUID id, Integer barcode, Integer discount,
                              LocalDateTime dtCreate, LocalDateTime dtUpdate) {
        this.id = id;
        this.barcode = barcode;
        this.discount = discount;
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

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
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
        private Integer barcode;
        private Integer discount;
        private LocalDateTime dtCreate;
        private LocalDateTime dtUpdate;

        private Builder() {
        }

        public Builder setId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder setBarcode(Integer barcode) {
            this.barcode = barcode;
            return this;
        }

        public Builder setDiscount(Integer discount) {
            this.discount = discount;
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

        public DiscountCardEntity build() {
            return new DiscountCardEntity(id, barcode, discount, dtCreate, dtUpdate);
        }
    }
}
