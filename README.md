# Účtenka

Jednoduchý program pro tisk účtenky z příkazové řádky. Aktuální verze je v `pom.xml`.

## Autoři

- Miro Slezák (LostSoul)
- Natálie Benešová (nataliebenesova07)
- Klaudia Onderová (f4kfv6z8sb-star)
- Vu Anh (VietnameseDelegation)

## Spuštění

Potřeba je Java 17 nebo novější.

```
mvnw.cmd package
java -jar target/uctenka-0.6.0.jar
```

Na Linuxu a macOS se místo `mvnw.cmd` použije `sh mvnw`. Maven není potřeba instalovat, stáhne ho wrapper.

Program se zeptá na položky (název, počet, cena za kus). Prázdný název seznam ukončí, pak se zadá zaplacená částka.

## Údaje obchodu

Údaje, které se na účtence opakují, jsou v `obchod.properties` (UTF-8): `nazev` (povinný), `adresa`, `ico`, `dic`, `dph` (sazba v %, výchozí 21), `paticka`. Ceny se zadávají včetně DPH.

## Nová verze

1. Změnit `<version>` v `pom.xml` (semver: oprava = patch, nová funkce = minor, nekompatibilní změna = major).
2. `mvnw.cmd clean package` spustí testy a vytvoří `target/uctenka-X.Y.Z.jar`.
3. Do `CHANGELOG.md` přidat sekci `## [X.Y.Z]`, commit a push do `master`. GitHub Actions vytvoří tag `vX.Y.Z` a release se zipem (jar, `obchod.properties`, `release/info.txt`, `release/uctenka.cmd`).
