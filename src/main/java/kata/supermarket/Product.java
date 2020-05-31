package kata.supermarket;

import java.util.UUID;

public class Product {

    private UUID uuid;
    private Discount discount;

    public Product(UUID uuid) {
        this.uuid = uuid;
    }

    public Discount getDiscount() {
        return discount;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Product addDiscount(Discount discount) {
        discount.validate();
        this.discount = discount;
        return this;
    }
}
