SELECT COUNT(t.id)
FROM "Tickets" t
WHERE t.flight_id = 365
AND t.status = 3;

SELECT COUNT(t.id)
FROM "Tickets" t
WHERE t.status_date > '2025-10-01'
AND t.status = 3;

SELECT COUNT(t.id)
FROM "Tickets" t
JOIN "Flights" f ON t.flight_id = f.id
WHERE f.route_id = 1
AND t.status = 3;

SELECT COUNT(t.id)
FROM "Tickets" t
JOIN "Flights" f ON t.flight_id = f.id
WHERE f.ticket_price > 1000
AND t.status = 3;

SELECT COUNT(t.id)
FROM "Tickets" t
JOIN "Passengers" p ON t.passenger_id = p.id
WHERE AGE(NOW(), p.birthday) > INTERVAL '18 years'
AND t.status = 3;

SELECT COUNT(t.id)
FROM "Tickets" t
JOIN "Passengers" p ON t.passenger_id = p.id
WHERE p.gender = 'woman'
AND t.status = 3;
