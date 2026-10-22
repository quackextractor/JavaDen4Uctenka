# Uctenka

Jednoduchy program pro tisk uctenky z prikazove radky. Aktualni verze je v `pom.xml`.

## Autori

- Miro Slezak (LostSoul)
- Natalie Benesova (nataliebenesova07)
- Klaudia Onderova (f4kfv6z8sb-star)
- Vu Anh (VietnameseDelegation)

## Spusteni

Potreba je Java 17 nebo novejsi.

```
mvnw.cmd package
java -jar target/uctenka-0.5.0.jar
```

Na Linuxu a macOS se misto `mvnw.cmd` pouzije `sh mvnw`. Maven neni potreba instalovat, stahne ho wrapper.

Program se zepta na polozky (nazev, pocet, cena za kus). Prazdny nazev seznam ukonci, pak se zada zaplacena castka.

## Udaje obchodu

Udaje, ktere se na uctence opakuji, jsou v `obchod.properties` (UTF-8): `nazev` (povinny), `adresa`, `ico`, `dic`, `dph` (sazba v %, vychozi 21), `paticka`. Ceny se zadavaji vcetne DPH.

## Nova verze

1. Zmenit `<version>` v `pom.xml` (semver: oprava = patch, nova funkce = minor, nekompatibilni zmena = major).
2. `mvnw.cmd clean package` spusti testy a vytvori `target/uctenka-X.Y.Z.jar`.
3. Commit, tag `vX.Y.Z` a push.
