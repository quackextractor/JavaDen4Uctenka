package cz.vse.uctenka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Properties;

/**
 * Spousteci trida. Prvni argument je cesta k souboru obchodu.
 */
public class Main {

    public static void main(String[] args) {
        System.exit(run(args, new BufferedReader(new InputStreamReader(System.in))));
    }

    static int run(String[] args, BufferedReader in) {
        String version = version();
        System.out.println("Uctenka v" + version);

        Path file = Path.of(args.length > 0 ? args[0] : "obchod.properties");
        try {
            Shop shop = Shop.load(file);
            Receipt receipt = new Receipt(shop);
            ReceiptReader reader = new ReceiptReader(in, System.out);

            if (!reader.readItems(receipt) || receipt.getItems().isEmpty()) {
                System.out.println();
                System.out.println("Zadna polozka, uctenka se netiskne.");
                return 1;
            }
            BigDecimal paid = reader.readPaid(receipt.total());
            if (paid == null) {
                System.out.println();
                System.out.println("Platba nebyla zadana, uctenka se netiskne.");
                return 1;
            }
            System.out.println();
            System.out.print(receipt.print(LocalDateTime.now(), paid, version));
            return 0;
        } catch (NoSuchFileException e) {
            System.err.println("Soubor obchodu nenalezen: " + file.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Chyba cteni: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Chyba: " + e.getMessage());
        }
        return 1;
    }

    static String version() {
        Properties p = new Properties();
        try (InputStream is = Main.class.getResourceAsStream("/app.properties")) {
            if (is != null) {
                p.load(is);
            }
        } catch (IOException ignored) {
        }
        return p.getProperty("verze", "?");
    }
}
