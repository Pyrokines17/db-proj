SELECT f.id, f.plane_id
FROM "Flights" f
WHERE f.plane_id = 1;

SELECT COUNT(f.id)
FROM "Flights" f
WHERE f.plane_id = 1;

SELECT COUNT(t.id)::float / 5
FROM "Tickets" t
JOIN "Flights" f ON t.flight_id = f.id
WHERE f.route_id IN (2, 5, 7, 10, 11);

SELECT f.id, f.plane_id
FROM "Flights" f
WHERE f.departure_time = '10:00:00';

SELECT COUNT(f.id)
FROM "Flights" f
WHERE f.departure_time = '10:00:00';
