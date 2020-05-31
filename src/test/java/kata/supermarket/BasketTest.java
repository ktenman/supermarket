package kata.supermarket;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Stream;

import static java.math.BigDecimal.ONE;
import static kata.supermarket.Discount.Type.BY_COUNT;
import static kata.supermarket.Discount.Type.PERCENTAGE;
import static kata.supermarket.Discount.Type.STATIC_PRICE;
import static org.assertj.core.api.Assertions.assertThat;

class BasketTest {

    @DisplayName("basket provides its total value when containing...")
    @MethodSource(value = "basketProvidesTotalValue")
    @ParameterizedTest(name = "{0}")
    void basketProvidesTotalValue(String description, BigDecimal expectedTotal, Iterable<Item> items) {
        final Basket basket = new Basket();

        items.forEach(basket::add);

        assertThat(basket.total()).as(description).isEqualByComparingTo(expectedTotal);
    }

    static Stream<Arguments> basketProvidesTotalValue() {
        return Stream.of(
                multipleItemsPricedByWeightAndUnit(),
                multipleItemsPricedPerUnit(),
                noItems(),
                aSingleItemPricedPerUnit(),
                aSingleItemPricedByWeight(),
                multipleItemsPricedByWeight(),
                oneItemWithDiscount(),
                discountedItemsForOnePound(),
                discountedTwoItemsAndOneForOriginalPrice(),
                discountedFourItems(),
                discountedWeightedItem()
        );
    }

    private static Arguments aSingleItemPricedByWeight() {
        return Arguments.of("a single weighed item", "1.25", Collections.singleton(twoFiftyGramsOfAmericanSweets()));
    }

    private static Arguments multipleItemsPricedByWeight() {
        return Arguments.of("multiple weighed items", "1.85",
                Arrays.asList(twoFiftyGramsOfAmericanSweets(), twoHundredGramsOfPickAndMix())
        );
    }

    private static Arguments multipleItemsPricedByWeightAndUnit() {
        return Arguments.of("multiple weighed and unit items", "4.38",
                Arrays.asList(twoFiftyGramsOfAmericanSweets(),
                        twoHundredGramsOfPickAndMix(),
                        aPackOfDigestives(),
                        aPintOfBeer(),
                        aPintOfBeer(),
                        aPintOfBeer())
        );
    }

    private static Arguments oneItemWithDiscount() {
        return Arguments.of("one unit item with discount 'buy two for one pound'", "0.69",
                Collections.singletonList(aPintOfCider())
        );
    }

    private static Arguments discountedItemsForOnePound() {
        return Arguments.of("two unit items with discount 'buy two for one pound'", "1.00",
                Arrays.asList(aPintOfCider(), aPintOfCider())
        );
    }

    private static Arguments discountedTwoItemsAndOneForOriginalPrice() {
        return Arguments.of("three unit items with discount 'buy two for one pound'", "1.69",
                Arrays.asList(aPintOfCider(), aPintOfCider(), aPintOfCider())
        );
    }

    private static Arguments discountedFourItems() {
        return Arguments.of("four unit items with discount 'buy two for one pound'", "2.00",
                Arrays.asList(aPintOfCider(), aPintOfCider(), aPintOfCider(),  aPintOfCider())
        );
    }

    private static Arguments discountedWeightedItem() {
        return Arguments.of("one weighted item with discount 'buy one kilo of vegetables for half price'", "2.50",
                Collections.singletonList(aKiloOfVegetablesForHalfPrice()));
    }

    private static Arguments multipleItemsPricedPerUnit() {
        return Arguments.of("multiple items priced per unit", "2.04",
                Arrays.asList(aPackOfDigestives(), aPintOfMilk()));
    }

    private static Arguments aSingleItemPricedPerUnit() {
        return Arguments.of("a single item priced per unit", "0.49", Collections.singleton(aPintOfMilk()));
    }

    private static Arguments noItems() {
        return Arguments.of("no items", "0.00", Collections.emptyList());
    }

    private static Item aPintOfMilk() {
        UUID uuid = UUID.fromString("18a88c1b-4e9f-40b0-b7aa-095d6278fa69");
        return new UnitProduct(new BigDecimal("0.49"), uuid).oneOf();
    }

    private static Item aPintOfBeer() {
        UUID uuid = UUID.fromString("18a88c1b-4e9f-40b0-b7aa-095d6278fa70");
        Discount discount = new Discount().type(BY_COUNT).freeAmount(1).appliesAtAmount(2);
        return new UnitProduct(new BigDecimal("0.49"), uuid)
                .addDiscount(discount)
                .oneOf();
    }

    private static Item aPintOfCider() {
        UUID uuid = UUID.fromString("18a88c1b-4e9f-40b0-b7aa-095d6278fa71");
        Discount discount = new Discount().type(STATIC_PRICE).value(ONE).appliesAtAmount(2);
        return new UnitProduct(new BigDecimal("0.69"), uuid)
                .addDiscount(discount)
                .oneOf();
    }

    private static Item aPackOfDigestives() {
        UUID uuid = UUID.fromString("c8dbd03f-1d10-4692-aaec-1a5c75d7193b");
        return new UnitProduct(new BigDecimal("1.55"), uuid).oneOf();
    }

    private static WeighedProduct aKiloOfAmericanSweets() {
        UUID uuid = UUID.fromString("eadfb143-de06-44de-bed7-4bcb45e45d53");
        return new WeighedProduct(new BigDecimal("4.99"), uuid);
    }

    private static Item twoFiftyGramsOfAmericanSweets() {
        return aKiloOfAmericanSweets().weighing(new BigDecimal(".25"));
    }

    private static Item aKiloOfVegetablesForHalfPrice() {
        UUID uuid = UUID.fromString("eadfb143-de06-44de-bed7-4bcb45e45d47");
        WeighedProduct vegetables = new WeighedProduct(new BigDecimal("4.99"), uuid);
        Discount fiftyPercentDiscount = new Discount().type(PERCENTAGE).value(BigDecimal.valueOf(0.5));
        return vegetables.addDiscount(fiftyPercentDiscount)
                .weighing(ONE);
    }

    private static WeighedProduct aKiloOfPickAndMix() {
        UUID uuid = UUID.fromString("68d7777f-0526-4099-9371-fd3af6c1548f");
        return new WeighedProduct(new BigDecimal("2.99"), uuid);
    }

    private static Item twoHundredGramsOfPickAndMix() {
        return aKiloOfPickAndMix().weighing(new BigDecimal(".2"));
    }
}