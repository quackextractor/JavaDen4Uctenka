package cz.vse.uctenka;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptReaderTest {
    private final Shop shop = new Shop("Trafika", "", "", "", 21, "");
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    private ReceiptReader reader(String input) {
        return new ReceiptReader(new BufferedReader(new StringReader(input)), new PrintStream(output));
    }

    @Test
    void parseMoney() {
        assertEquals(new BigDecimal("12.5"), ReceiptReader.parseMoney("12,5", 7));
        assertEquals(new BigDecimal("3.50"), ReceiptReader.parseMoney(" 3.50 ", 7));
        assertNull(ReceiptReader.parseMoney("abc", 7));
        assertNull(ReceiptReader.parseMoney("1.234", 7));
        assertNull(ReceiptReader.parseMoney("", 7));
        assertNull(ReceiptReader.parseMoney("1e50", 7));
        assertNull(ReceiptReader.parseMoney("12345678", 7));
        assertEquals(new BigDecimal("9999999.99"), ReceiptReader.parseMoney("9999999,99", 7));
    }

    @Test
    void readsItemsUntilEmptyLine() throws IOException {
        Receipt receipt = new Receipt(shop);
        assertTrue(reader("Rohlik\n3\n3,50\nMleko\n\n24.90\n\n").readItems(receipt));
        assertEquals(2, receipt.getItems().size());
        assertEquals(new Item("Rohlik", 3, new BigDecimal("3.50")), receipt.getItems().get(0));
        assertEquals(1, receipt.getItems().get(1).quantity());
    }

    @Test
    void asksAgainOnBadInput() throws IOException {
        Receipt receipt = new Receipt(shop);
        assertTrue(reader("Rohlik\nx\n-2\n1000\n2\n0\nabc\n4\n\n").readItems(receipt));
        assertEquals(new Item("Rohlik", 2, new BigDecimal("4")), receipt.getItems().get(0));
        String text = output.toString();
        assertTrue(text.contains("Zadejte cele cislo 1 az 999."));
        assertTrue(text.contains("Zadejte kladnou castku."));
    }

    @Test
    void endOfInputStops() throws IOException {
        Receipt receipt = new Receipt(shop);
        assertFalse(reader("Rohlik\n3\n").readItems(receipt));
        assertTrue(receipt.getItems().isEmpty());
    }

    @Test
    void paidMustCoverTotal() throws IOException {
        assertEquals(new BigDecimal("50"), reader("10\n50\n").readPaid(new BigDecimal("35.40")));
        assertTrue(output.toString().contains("Zadejte castku aspon 35,40."));
        assertEquals(new BigDecimal("35.40"), reader("35,40\n").readPaid(new BigDecimal("35.40")));
        assertNull(reader("").readPaid(BigDecimal.ONE));
    }
}
