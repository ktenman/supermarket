package kata.supermarket;

import java.math.BigDecimal;
import java.util.UUID;

public class UnitProduct extends Product {

    private final BigDecimal pricePerUnit;

    public UnitProduct(final BigDecimal pricePerUnit, UUID uuid) {
        super(uuid);
        this.pricePerUnit = pricePerUnit;
    }

    public UnitProduct addDiscount(Discount discount){
        super.addDiscount(discount);
        return this;
    }

    BigDecimal pricePerUnit() {
        return pricePerUnit;
    }

    public Item oneOf() {
        return new ItemByUnit(this);
    }
}
