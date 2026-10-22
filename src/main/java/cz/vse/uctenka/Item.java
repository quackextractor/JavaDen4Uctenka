package cz.vse.uctenka;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Jedna polozka nakupu.
 */
public final class Item {
    private final String name;
    private final int quantity;
    private final BigDecimal unitPrice;

    public Item(String name, int quantity, BigDecimal unitPrice) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nazev polozky je prazdny");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Pocet musi byt kladny");
        }
        if (unitPrice == null || unitPrice.signum() <= 0) {
            throw new IllegalArgumentException("Cena musi byt kladna");
        }
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * @return cena za vsechny kusy
     */
    public BigDecimal total() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Item other
                && name.equals(other.name)
                && quantity == other.quantity
                && unitPrice.equals(other.unitPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quantity, unitPrice);
    }
}
