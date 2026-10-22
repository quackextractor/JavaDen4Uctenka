package cz.vse.uctenka;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Uctenka s polozkami a jejim vytisknutim do textu.
 */
public class Receipt {
    public static final int WIDTH = 40;
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("d.M.yyyy HH:mm");

    private final Shop shop;
    private final List<Item> items = new ArrayList<>();

    public Receipt(Shop shop) {
        this.shop = shop;
    }

    static String money(BigDecimal value) {
        return String.format(Locale.forLanguageTag("cs-CZ"), "%.2f", value);
    }

    private static void row(StringBuilder sb, String left, String right) {
        left = cut(left, WIDTH - right.length() - 1);
        sb.append(left).append(" ".repeat(WIDTH - left.length() - right.length())).append(right).append('\n');
    }

    private static void center(StringBuilder sb, String text) {
        if (text.isEmpty()) {
            return;
        }
        text = cut(text, WIDTH);
        sb.append(" ".repeat((WIDTH - text.length()) / 2)).append(text).append('\n');
    }

    private static String cut(String text, int max) {
        return text.length() <= max ? text : text.substring(0, max);
    }

    public void add(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public BigDecimal total() {
        BigDecimal sum = BigDecimal.ZERO;
        for (Item item : items) {
            sum = sum.add(item.total());
        }
        return sum.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * @return DPH obsazena v celkove cene
     */
    public BigDecimal vat() {
        BigDecimal rate = BigDecimal.valueOf(shop.getVatRate());
        return total().multiply(rate).divide(rate.add(BigDecimal.valueOf(100)), 2, RoundingMode.HALF_UP);
    }

    /**
     * Vytvori text uctenky.
     *
     * @param time    cas nakupu
     * @param paid    zaplacena castka, nesmi byt mensi nez celkem
     * @param version verze programu do paticky
     * @return uctenka jako text
     */
    public String print(LocalDateTime time, BigDecimal paid, String version) {
        if (paid.compareTo(total()) < 0) {
            throw new IllegalArgumentException("Zaplaceno je mene nez celkem");
        }
        String line = "-".repeat(WIDTH);
        String doubleLine = "=".repeat(WIDTH);
        StringBuilder sb = new StringBuilder();

        sb.append(doubleLine).append('\n');
        center(sb, shop.getName());
        center(sb, shop.getAddress());
        String ids = (shop.getIco().isEmpty() ? "" : "ICO: " + shop.getIco() + "  ")
                + (shop.getDic().isEmpty() ? "" : "DIC: " + shop.getDic());
        center(sb, ids.trim());
        sb.append(doubleLine).append('\n');
        sb.append("Datum: ").append(time.format(DATE)).append('\n');
        sb.append(line).append('\n');

        for (Item item : items) {
            if (item.getQuantity() == 1) {
                row(sb, item.getName(), money(item.total()));
            } else {
                sb.append(cut(item.getName(), WIDTH)).append('\n');
                row(sb, "  " + item.getQuantity() + " x " + money(item.getUnitPrice()), money(item.total()));
            }
        }

        sb.append(line).append('\n');
        row(sb, "CELKEM", money(total()) + " Kc");
        row(sb, "z toho DPH " + shop.getVatRate() + " %", money(vat()));
        row(sb, "Zaplaceno", money(paid));
        row(sb, "Vraceno", money(paid.subtract(total())));
        sb.append(doubleLine).append('\n');
        center(sb, shop.getFooter());
        center(sb, "Uctenka v" + version);
        return sb.toString();
    }
}
