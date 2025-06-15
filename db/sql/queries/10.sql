SELECT f.id, f.plane_id
FROM "Flights" f
WHERE f.flight_type = 1;

SELECT COUNT(f.id)
FROM "Flights" f
WHERE f.flight_type = 1;

SELECT f.id, f.plane_id
FROM "Flights" f
WHERE f.plane_id = 1;

SELECT COUNT(f.id)
FROM "Flights" f
WHERE f.plane_id = 1;
