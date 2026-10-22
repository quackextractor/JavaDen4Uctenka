package cz.vse.uctenka;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptTest {
    private final Shop shop = new Shop("Trafika", "Praha 3", "123", "CZ123", 21, "Diky");
    private final LocalDateTime time = LocalDateTime.of(2026, 10, 22, 18, 30);

    private Receipt sample() {
        Receipt receipt = new Receipt(shop);
        receipt.add(new Item("Rohlik", 3, new BigDecimal("3.50")));
        receipt.add(new Item("Mleko", 1, new BigDecimal("24.90")));
        return receipt;
    }

    @Test
    void itemTotal() {
        assertEquals(new BigDecimal("10.50"), new Item("Rohlik", 3, new BigDecimal("3.50")).total());
    }

    @Test
    void itemRejectsBadValues() {
        assertThrows(IllegalArgumentException.class, () -> new Item(" ", 1, BigDecimal.ONE));
        assertThrows(IllegalArgumentException.class, () -> new Item("A", 0, BigDecimal.ONE));
        assertThrows(IllegalArgumentException.class, () -> new Item("A", 1, BigDecimal.ZERO));
    }

    @Test
    void totalAndVat() {
        Receipt receipt = sample();
        assertEquals(new BigDecimal("35.40"), receipt.total());
        assertEquals(new BigDecimal("6.14"), receipt.vat());
    }

    @Test
    void emptyReceiptIsZero() {
        assertEquals(new BigDecimal("0.00"), new Receipt(shop).total());
    }

    @Test
    void printContainsEverything() {
        String text = sample().print(time, new BigDecimal("50"), "1.0.0");
        assertTrue(text.contains("Trafika"));
        assertTrue(text.contains("ICO: 123  DIC: CZ123"));
        assertTrue(text.contains("Datum: 22.10.2026 18:30"));
        assertTrue(text.contains("  3 x 3,50"));
        assertTrue(text.contains("35,40 Kc"));
        assertTrue(text.contains("z toho DPH 21 %"));
        assertTrue(text.contains("14,60"));
        assertTrue(text.contains("Uctenka v1.0.0"));
    }

    @Test
    void linesFitWidth() {
        Receipt receipt = sample();
        receipt.add(new Item("Velmi dlouhy nazev polozky ktery se nevejde na radek", 1, new BigDecimal("99999.99")));
        for (String line : receipt.print(time, new BigDecimal("200000"), "1.0.0").split("\n")) {
            assertTrue(line.length() <= Receipt.WIDTH, line);
        }
    }

    @Test
    void rowsAlignRight() {
        String text = sample().print(time, new BigDecimal("35.40"), "1.0.0");
        for (String line : text.split("\n")) {
            if (line.startsWith("CELKEM") || line.startsWith("Vraceno")) {
                assertEquals(Receipt.WIDTH, line.length(), line);
            }
        }
        assertTrue(text.contains("Vraceno") && text.contains("0,00"));
    }

    @Test
    void notEnoughPaidThrows() {
        assertThrows(IllegalArgumentException.class, () -> sample().print(time, new BigDecimal("10"), "1.0.0"));
    }
}
