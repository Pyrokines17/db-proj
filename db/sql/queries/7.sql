SELECT f.id, f.plane_id
FROM "Flights" f
WHERE f.status = 3;

SELECT COUNT(f.id)
FROM "Flights" f
WHERE f.status = 3;

SELECT f.id, f.plane_id
FROM "Flights" f
WHERE f.status = 3
AND f.route_id = 1;

SELECT COUNT(f.id)
FROM "Flights" f
WHERE f.status = 3
AND f.route_id = 1;

SELECT f.id, f.plane_id
FROM "Flights" f
WHERE f.id IN (
    SELECT t.flight_id
    FROM "Tickets" t
    GROUP BY t.flight_id
    HAVING COUNT(t.id) < 2
);

SELECT COUNT(f.id)
FROM "Flights" f
WHERE f.id IN (
    SELECT t.flight_id
    FROM "Tickets" t
    GROUP BY t.flight_id
    HAVING COUNT(t.id) < 2
);

SELECT f.id, f.plane_id
FROM "Flights" f
WHERE (
    SELECT COUNT(t.id)::FLOAT / f.max_tickets_count
    FROM "Tickets" t
    WHERE t.flight_id = f.id
) < 0.5; 

SELECT COUNT(f.id)
FROM "Flights" f
WHERE (
    SELECT COUNT(t.id)::FLOAT / f.max_tickets_count
    FROM "Tickets" t
    WHERE t.flight_id = f.id
) < 0.5; 
