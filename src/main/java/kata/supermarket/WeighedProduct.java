package kata.supermarket;

import java.math.BigDecimal;
import java.util.UUID;

public class WeighedProduct extends Product {

    private final BigDecimal pricePerKilo;

    public WeighedProduct(final BigDecimal pricePerKilo, UUID uuid) {
        super(uuid);
        this.pricePerKilo = pricePerKilo;
    }

    public WeighedProduct addDiscount(Discount discount){
        super.addDiscount(discount);
        return this;
    }

    BigDecimal pricePerKilo() {
        return pricePerKilo;
    }

    public Item weighing(final BigDecimal kilos) {
        return new ItemByWeight(this, kilos);
    }
}
