SELECT f.id, f.plane_id
FROM "Flights" f
WHERE f.route_id = 1;

SELECT COUNT(f.id)
FROM "Flights" f
WHERE f.route_id = 1;

SELECT f.id, f.plane_id
FROM "Flights" f
WHERE f.arrival_time - f.departure_time > INTERVAL '2 hours';

SELECT COUNT(f.id)
FROM "Flights" f
WHERE f.arrival_time - f.departure_time > INTERVAL '2 hours';

SELECT f.id, f.plane_id
FROM "Flights" f
WHERE f.ticket_price > 7000;

SELECT COUNT(f.id)
FROM "Flights" f
WHERE f.ticket_price > 7000;

SELECT f.id, f.plane_id
FROM "Flights" f
WHERE f.route_id = 1
AND f.arrival_time - f.departure_time > INTERVAL '2 hours'
AND f.ticket_price > 7000;

SELECT COUNT(f.id)
FROM "Flights" f
WHERE f.route_id = 1
AND f.arrival_time - f.departure_time > INTERVAL '2 hours'
AND f.ticket_price > 7000;
