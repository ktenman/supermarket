package kata.supermarket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static kata.supermarket.Discount.Type.BY_COUNT;
import static kata.supermarket.Discount.Type.PERCENTAGE;
import static kata.supermarket.Discount.Type.STATIC_PRICE;

public class Basket {
    private final List<Item> items;

    public Basket() {
        this.items = new ArrayList<>();
    }

    public void add(final Item item) {
        this.items.add(item);
    }

    List<Item> items() {
        return Collections.unmodifiableList(items);
    }

    public BigDecimal total() {
        return new TotalCalculator().calculate();
    }

    private class TotalCalculator {
        private final List<Item> items;

        TotalCalculator() {
            this.items = items();
        }

        private BigDecimal calculate() {
            Map<UUID, Long> itemsCounted = items.stream().collect(groupingBy(item -> item.product().getUuid(), counting()));
            Map<UUID, Item> itemByUuid = new HashMap<>();
            items.forEach(item -> itemByUuid.putIfAbsent(item.product().getUuid(), item));

            return itemsCounted.keySet().stream()
                    .map(uuid -> amountToPay(itemByUuid.get(uuid), itemsCounted.get(uuid)))
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO)
                    .setScale(2, RoundingMode.HALF_UP);
        }

        private BigDecimal amountToPay(Item item, Long itemCount) {
            Discount discount = item.discount();
            if (discount == null) {
                return item.price().multiply(BigDecimal.valueOf(itemCount));
            } else if (discount.getType() == BY_COUNT && itemCount >= discount.getAppliesAtAmount()) {
                int totalDiscountAmount = discount.getAppliesAtAmount() + discount.getFreeAmount();
                int notAppliedItemsCount = (int) (itemCount % (itemCount / totalDiscountAmount));
                int totalItemsToPay = (int) ((itemCount / totalDiscountAmount) * discount.getAppliesAtAmount() + notAppliedItemsCount);
                return item.price().multiply(BigDecimal.valueOf(totalItemsToPay));
            } else if (discount.getType() == STATIC_PRICE && itemCount >= discount.getAppliesAtAmount()) {
                int notAppliedItemsCount = (int) (itemCount % discount.getAppliesAtAmount());
                int totalItemsToPay = (int) (itemCount / discount.getAppliesAtAmount());
                BigDecimal totalDiscountedAmount = discount.getValue().multiply(BigDecimal.valueOf(totalItemsToPay));
                BigDecimal totalOriginalAmount = item.price().multiply(BigDecimal.valueOf(notAppliedItemsCount));
                return totalDiscountedAmount.add(totalOriginalAmount);
            } else if (discount.getType() == PERCENTAGE) {
                return item.price().multiply(BigDecimal.valueOf(itemCount)).multiply(discount.getValue());
            }
            return item.price().multiply(BigDecimal.valueOf(itemCount));
        }
    }
}
