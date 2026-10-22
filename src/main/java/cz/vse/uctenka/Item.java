package cz.vse.uctenka;

import java.math.BigDecimal;

/**
 * Jedna polozka nakupu.
 */
public record Item(String name, int quantity, BigDecimal unitPrice) {

    public Item {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nazev polozky je prazdny");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Pocet musi byt kladny");
        }
        if (unitPrice == null || unitPrice.signum() <= 0) {
            throw new IllegalArgumentException("Cena musi byt kladna");
        }
    }

    /**
     * @return cena za vsechny kusy
     */
    public BigDecimal total() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
