package cz.vse.uctenka;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Udaje o obchodu, ktere se na uctence opakuji.
 */
public record Shop(String name, String address, String ico, String dic, int vatRate, String footer) {

    /**
     * Nacte obchod ze souboru .properties.
     *
     * @param file cesta k souboru
     * @return nacteny obchod
     * @throws IOException kdyz soubor nejde precist
     */
    public static Shop load(Path file) throws IOException {
        Properties p = new Properties();
        try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            p.load(reader);
        }

        String name = p.getProperty("nazev", "").trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("V souboru " + file + " chybi nazev obchodu");
        }

        int vat;
        try {
            vat = Integer.parseInt(p.getProperty("dph", "21").trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Sazba DPH neni cele cislo");
        }
        if (vat < 0 || vat > 100) {
            throw new IllegalArgumentException("Sazba DPH musi byt 0 az 100");
        }

        return new Shop(name,
                p.getProperty("adresa", "").trim(),
                p.getProperty("ico", "").trim(),
                p.getProperty("dic", "").trim(),
                vat,
                p.getProperty("paticka", "").trim());
    }
}
