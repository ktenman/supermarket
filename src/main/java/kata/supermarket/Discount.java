package kata.supermarket;

import java.math.BigDecimal;

import static kata.supermarket.Discount.Type.BY_COUNT;
import static kata.supermarket.Discount.Type.PERCENTAGE;
import static kata.supermarket.Discount.Type.STATIC_PRICE;

public class Discount {

    private Type type;
    private BigDecimal value;
    private Integer appliesAtAmount;
    private Integer freeAmount;

    enum Type {
        BY_COUNT,
        PERCENTAGE,
        STATIC_PRICE
    }

    public Discount type(Type type) {
        this.type = type;
        return this;
    }

    public Discount value(BigDecimal value) {
        this.value = value;
        return this;
    }

    public Discount appliesAtAmount(Integer appliesAtAmount) {
        this.appliesAtAmount = appliesAtAmount;
        return this;
    }

    public Discount freeAmount(Integer freeAmount) {
        this.freeAmount = freeAmount;
        return this;
    }

    public Type getType() {
        return type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Integer getAppliesAtAmount() {
        return appliesAtAmount;
    }

    public Integer getFreeAmount() {
        return freeAmount;
    }

    public void validate() {
        if (type == BY_COUNT && (freeAmount == null || appliesAtAmount == null)) {
            throw new IllegalArgumentException(String.format("BY_COUNT has not configured properly: " +
                    "freeCount is %s and appliesAtAmount is %s", freeAmount, appliesAtAmount));
        } else if (type == STATIC_PRICE && (value == null || appliesAtAmount == null)) {
            throw new IllegalArgumentException(String.format("%s has not configured properly: " +
                    "value is %s and appliesAtAmount is %s", type, value, appliesAtAmount));
        } else if (type == PERCENTAGE  && value == null) {
            throw new IllegalArgumentException("PERCENTAGE has not configured properly: value is null");
        } else if (type == null) {
            throw new IllegalArgumentException("Discount type is null");
        }
    }

}
