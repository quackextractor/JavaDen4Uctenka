package cz.vse.uctenka;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppTest {
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final ByteArrayOutputStream err = new ByteArrayOutputStream();

    @BeforeEach
    void capture() {
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));
    }

    @AfterEach
    void restore() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    private int run(String input) {
        return run(input, "obchod.properties");
    }

    private int run(String input, String file) {
        return App.run(Path.of(file), new BufferedReader(new StringReader(input)));
    }

    @Test
    void versionComesFromPom() {
        assertTrue(App.version().matches("\\d+\\.\\d+\\.\\d+.*"), App.version());
    }

    @Test
    void printsReceipt() {
        assertEquals(0, run("Rohlik\n3\n3,50\n\n20\n"));
        assertTrue(out.toString().contains("BILLA, spol. s r.o."));
        assertTrue(out.toString().contains("Vraceno"));
    }

    @Test
    void noItemsFails() {
        assertEquals(1, run("\n"));
        assertTrue(out.toString().contains("Zadna polozka"));
    }

    @Test
    void interruptedInputFails() {
        assertEquals(1, run("Rohlik\n1\n5"));
        assertTrue(out.toString().contains("Vstup skoncil"));
    }

    @Test
    void noPaymentFails() {
        assertEquals(1, run("Rohlik\n1\n5\n\n"));
        assertTrue(out.toString().contains("Platba nebyla zadana"));
    }

    @Test
    void missingShopFileFails() {
        assertEquals(1, run("", "neexistuje.properties"));
        assertTrue(err.toString().contains("Soubor obchodu nenalezen"));
    }
}
