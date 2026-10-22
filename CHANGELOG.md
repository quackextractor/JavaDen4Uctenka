# Changelog

Vsechny dulezite zmeny v projektu jsou zapsane v tomto souboru.

Format vychazi z [Keep a Changelog 1.1.0](https://keepachangelog.com/en/1.1.0/)
a projekt dodrzuje [Semantic Versioning 2.0.0](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Changed

- Obchod v `obchod.properties` je BILLA, Letenske namesti 38/7, Praha 7 (LostSoul).
- `Main` rozdelen do pomocnych metod (LostSoul).
- Beh programu presunut z `Main` do nove tridy `App`, `Main` jen spousti (LostSoul).

### Removed

- Argument s cestou k souboru obchodu, obchod se cte vzdy z `obchod.properties` (LostSoul).

### Fixed

- Castka bere jen cifry 0-9, bez znamenka a s cislem pred i za desetinnou carkou (LostSoul).
- Prilis dlouha castka uz neshodi tisk uctenky (LostSoul).
- Platbu jde zadat i kdyz ma celkem vic nez 15 cifer (LostSoul).

## [0.5.0] - 2026-10-22

### Changed

- Zaznamy (record) `Item` a `Shop` nahrazeny beznymi tridami s gettery (LostSoul).

## [0.4.8] - 2026-10-22

### Fixed

- Nastaveno kodovani filtrovanych vlastnosti pri buildu (LostSoul).

## [0.4.7] - 2026-10-22

### Added

- Test maximalniho poctu kusu (LostSoul).

## [0.4.6] - 2026-10-22

### Fixed

- Castka se zapisem s exponentem se odmita (LostSoul).

## [0.4.5] - 2026-10-22

### Fixed

- Preruseny nakup se hlasi jako preruseny (LostSoul).

## [0.4.4] - 2026-10-22

### Added

- README (LostSoul).

## [0.4.3] - 2026-10-22

### Added

- Testy zadavani vstupu (VietnameseDelegation).

## [0.4.2] - 2026-10-22

### Added

- Testy vypisu uctenky (nataliebenesova07).

## [0.4.1] - 2026-10-22

### Added

- Testy nacitani obchodu (f4kfv6z8sb-star).

## [0.4.0] - 2026-10-22

### Added

- Zadavani polozek z prikazove radky a tisk uctenky (LostSoul).

## [0.3.0] - 2026-10-22

### Added

- Polozky nakupu a vypis uctenky (nataliebenesova07).

## [0.2.0] - 2026-10-22

### Added

- Nacitani udaju obchodu ze souboru (f4kfv6z8sb-star).

## [0.1.0] - 2026-10-22

### Added

- Zakladni struktura projektu s Mavenem (LostSoul).

[Unreleased]: https://github.com/quackextractor/JavaDen4Uctenka/compare/v0.5.0...HEAD
[0.5.0]: https://github.com/quackextractor/JavaDen4Uctenka/compare/v0.4.8...v0.5.0
[0.4.8]: https://github.com/quackextractor/JavaDen4Uctenka/compare/v0.4.7...v0.4.8
[0.4.7]: https://github.com/quackextractor/JavaDen4Uctenka/compare/v0.4.6...v0.4.7
[0.4.6]: https://github.com/quackextractor/JavaDen4Uctenka/compare/v0.4.5...v0.4.6
[0.4.5]: https://github.com/quackextractor/JavaDen4Uctenka/compare/v0.4.4...v0.4.5
[0.4.4]: https://github.com/quackextractor/JavaDen4Uctenka/compare/v0.4.3...v0.4.4
[0.4.3]: https://github.com/quackextractor/JavaDen4Uctenka/compare/v0.4.2...v0.4.3
[0.4.2]: https://github.com/quackextractor/JavaDen4Uctenka/compare/v0.4.1...v0.4.2
[0.4.1]: https://github.com/quackextractor/JavaDen4Uctenka/compare/v0.4.0...v0.4.1
[0.4.0]: https://github.com/quackextractor/JavaDen4Uctenka/compare/v0.3.0...v0.4.0
[0.3.0]: https://github.com/quackextractor/JavaDen4Uctenka/compare/v0.2.0...v0.3.0
[0.2.0]: https://github.com/quackextractor/JavaDen4Uctenka/compare/v0.1.0...v0.2.0
[0.1.0]: https://github.com/quackextractor/JavaDen4Uctenka/releases/tag/v0.1.0
