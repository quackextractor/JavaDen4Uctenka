package cz.vse.uctenka;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;

/**
 * Spousteci trida. Obchod se cte ze souboru obchod.properties v aktualni slozce.
 */
public class Main {

    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.exit(App.run(Path.of("obchod.properties"), in));
    }
}
