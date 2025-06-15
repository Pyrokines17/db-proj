SELECT f.id, f.plane_id
FROM "Flights" f
WHERE f.status IN (1, 2);

SELECT COUNT(f.id)
FROM "Flights" f
WHERE f.status IN (1, 2);

SELECT f.id, f.plane_id
FROM "Flights" f
WHERE f.status = 1;

SELECT COUNT(f.id)
FROM "Flights" f
WHERE f.status = 1;

SELECT f.id, f.plane_id
FROM "Flights" f
WHERE f.status IN (1, 2)
AND f.route_id = 2;

SELECT COUNT(f.id)
FROM "Flights" f
WHERE f.status IN (1, 2)
AND f.route_id = 2;

SELECT f.id, f.plane_id
FROM "Flights" f
WHERE f.id IN (
    SELECT t.flight_id
    FROM "Tickets" t
    WHERE t.status = 3
    GROUP BY t.flight_id
    HAVING COUNT(t.id) > 1
);

SELECT COUNT(f.id)
FROM "Flights" f
WHERE f.id IN (
    SELECT t.flight_id
    FROM "Tickets" t
    WHERE t.status = 3
    GROUP BY t.flight_id
    HAVING COUNT(t.id) > 1
);
