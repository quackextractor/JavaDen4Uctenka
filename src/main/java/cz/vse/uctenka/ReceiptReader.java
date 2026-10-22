package cz.vse.uctenka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;

/**
 * Cte polozky nakupu a platbu od uzivatele.
 */
public class ReceiptReader {
    public static final int MAX_QUANTITY = 999;

    private final BufferedReader in;
    private final PrintStream out;

    public ReceiptReader(BufferedReader in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    /**
     * Prevede text na castku, bere carku i tecku. Povolene jsou jen cifry 0-9,
     * bez znamenka a exponentu.
     *
     * @param maxDigits max. pocet cifer pred carkou
     * @return castka nebo null kdyz to neni cislo s max. 2 desetinnymi misty
     */
    static BigDecimal parseMoney(String text, int maxDigits) {
        String number = text.trim().replace(',', '.');
        // \d je v Jave jen ASCII, BigDecimal by jinak bral i arabske nebo plnosirkove cifry
        if (!number.matches("\\d+(\\.\\d{1,2})?")) {
            return null;
        }
        BigDecimal value = new BigDecimal(number);
        return value.precision() - value.scale() <= maxDigits ? value : null;
    }

    /**
     * Nacita polozky, dokud uzivatel nezada prazdny nazev.
     *
     * @return false kdyz vstup skoncil
     */
    public boolean readItems(Receipt receipt) throws IOException {
        while (true) {
            String name = ask("Nazev polozky (prazdne = konec): ");
            if (name == null) {
                return false;
            }
            if (name.isEmpty()) {
                return true;
            }
            Integer quantity = readQuantity();
            if (quantity == null) {
                return false;
            }
            BigDecimal price = readMoney("Cena za kus: ", BigDecimal.ZERO, false, 7);
            if (price == null) {
                return false;
            }
            receipt.add(new Item(name, quantity, price));
        }
    }

    /**
     * @return zaplacena castka nebo null kdyz vstup skoncil
     */
    public BigDecimal readPaid(BigDecimal total) throws IOException {
        // limit nesmi byt mensi nez celkem, jinak by zadna platba neprosla
        int maxDigits = Math.max(15, total.precision() - total.scale());
        return readMoney("Zaplaceno (celkem " + Receipt.money(total) + "): ", total, true, maxDigits);
    }

    private Integer readQuantity() throws IOException {
        while (true) {
            String text = ask("Pocet kusu [1]: ");
            if (text == null) {
                return null;
            }
            if (text.isEmpty()) {
                return 1;
            }
            try {
                int quantity = Integer.parseInt(text);
                if (quantity > 0 && quantity <= MAX_QUANTITY) {
                    return quantity;
                }
            } catch (NumberFormatException ignored) {
            }
            out.println("Zadejte cele cislo 1 az " + MAX_QUANTITY + ".");
        }
    }

    private BigDecimal readMoney(String prompt, BigDecimal min, boolean allowMin, int maxDigits) throws IOException {
        while (true) {
            String text = ask(prompt);
            if (text == null) {
                return null;
            }
            BigDecimal value = parseMoney(text, maxDigits);
            if (value != null) {
                int cmp = value.compareTo(min);
                if (cmp > 0 || (allowMin && cmp == 0)) {
                    return value;
                }
            }
            out.println(allowMin ? "Zadejte castku aspon " + Receipt.money(min) + "." : "Zadejte kladnou castku.");
        }
    }

    private String ask(String prompt) throws IOException {
        out.print(prompt);
        String line = in.readLine();
        return line == null ? null : line.trim();
    }
}
