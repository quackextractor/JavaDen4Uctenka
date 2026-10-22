package cz.vse.uctenka;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ShopTest {

    @TempDir
    Path dir;

    private Path write(String content) throws IOException {
        Path file = dir.resolve("obchod.properties");
        Files.writeString(file, content);
        return file;
    }

    @Test
    void loadsAllFields() throws IOException {
        Shop shop = Shop.load(write("nazev=Trafika\nadresa=Praha 3\nico=123\ndic=CZ123\ndph=12\npaticka=Diky\n"));
        assertEquals(new Shop("Trafika", "Praha 3", "123", "CZ123", 12, "Diky"), shop);
    }

    @Test
    void usesDefaults() throws IOException {
        Shop shop = Shop.load(write("nazev=Trafika\n"));
        assertEquals(21, shop.getVatRate());
        assertEquals("", shop.getAddress());
    }

    @Test
    void loadsTheBundledFile() throws IOException {
        assertEquals("Trafika U Rohu", Shop.load(Path.of("obchod.properties")).getName());
    }

    @Test
    void rejectsMissingName() throws IOException {
        Path file = write("adresa=Praha\n");
        assertThrows(IllegalArgumentException.class, () -> Shop.load(file));
    }

    @Test
    void rejectsBadVat() throws IOException {
        Path text = write("nazev=A\ndph=hodne\n");
        assertThrows(IllegalArgumentException.class, () -> Shop.load(text));
        Path negative = write("nazev=A\ndph=-5\n");
        assertThrows(IllegalArgumentException.class, () -> Shop.load(negative));
    }

    @Test
    void missingFileThrows() {
        assertThrows(IOException.class, () -> Shop.load(dir.resolve("neni.properties")));
    }
}
