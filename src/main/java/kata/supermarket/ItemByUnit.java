package kata.supermarket;

import java.math.BigDecimal;

public class ItemByUnit implements Item {

    private final UnitProduct unitProduct;

    ItemByUnit(final UnitProduct unitProduct) {
        this.unitProduct = unitProduct;
    }

    public BigDecimal price() {
        return unitProduct.pricePerUnit();
    }

    @Override
    public Discount discount() {
        return unitProduct.getDiscount();
    }

    @Override
    public Product product() {
        return unitProduct;
    }

    public ItemByUnit addDiscount(Discount discount) {
        unitProduct.addDiscount(discount);
        return this;
    }
}
