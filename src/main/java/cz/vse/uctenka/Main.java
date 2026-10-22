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

        Path file = shopFile(args);
        try {
            Shop shop = Shop.load(file);
            return checkout(shop, new ReceiptReader(in, System.out), version);
        } catch (NoSuchFileException e) {
            return error("Soubor obchodu nenalezen: " + file.toAbsolutePath());
        } catch (IOException e) {
            return error("Chyba cteni: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return error("Chyba: " + e.getMessage());
        }
    }

    private static Path shopFile(String[] args) {
        return Path.of(args.length > 0 ? args[0] : "obchod.properties");
    }

    /**
     * Nacte nakup a platbu a vytiskne uctenku.
     *
     * @return 0 kdyz se uctenka vytiskla, jinak 1
     */
    private static int checkout(Shop shop, ReceiptReader reader, String version) throws IOException {
        Receipt receipt = new Receipt(shop);
        if (!reader.readItems(receipt)) {
            return cancel("Vstup skoncil");
        }
        if (receipt.getItems().isEmpty()) {
            return cancel("Zadna polozka");
        }
        BigDecimal paid = reader.readPaid(receipt.total());
        if (paid == null) {
            return cancel("Platba nebyla zadana");
        }
        System.out.println();
        System.out.print(receipt.print(LocalDateTime.now(), paid, version));
        return 0;
    }

    private static int cancel(String reason) {
        System.out.println();
        System.out.println(reason + ", uctenka se netiskne.");
        return 1;
    }

    private static int error(String message) {
        System.err.println(message);
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
