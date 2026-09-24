CREATE TABLE spherical_horse
(
    id               TEXT PRIMARY KEY,
    name             TEXT NOT NULL,
    mass             REAL NOT NULL CHECK (mass > 0),
    radius           REAL NOT NULL CHECK (radius > 0),
    minimum_pressure REAL NOT NULL CHECK (minimum_pressure >= 0),
    status           TEXT NOT NULL
        CHECK (status IN ('AVAILABLE', 'UNAVAILABLE', 'RETIRED'))
);
