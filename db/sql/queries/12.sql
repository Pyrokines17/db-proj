SELECT f.id, COUNT(t.id)
FROM "Tickets" t
JOIN "Flights" f
ON t.flight_id = f.id
WHERE t.status = 1
AND t.flight_id = 327;

SELECT 
f.max_tickets_count - COUNT(t.id) AS available_seats
FROM "Flights" f
LEFT JOIN "Tickets" t
ON f.id = t.flight_id
WHERE f.id = 327
AND t.status IN (1, 2, 4)
GROUP BY (f.max_tickets_count, f.id);

SELECT f.id, COUNT(t.id)
FROM "Tickets" t
JOIN "Flights" f
ON t.flight_id = f.id
WHERE t.status = 1
AND f.departure_date > '2025-10-01'
GROUP BY f.id;

SELECT f.id,
f.max_tickets_count - COUNT(t.id) AS available_seats
FROM "Flights" f
LEFT JOIN "Tickets" t
ON f.id = t.flight_id
WHERE f.departure_date > '2025-10-01'
AND t.status IN (1, 2, 4)
GROUP BY (f.id, f.max_tickets_count);

SELECT f.id, COUNT(t.id)
FROM "Tickets" t
JOIN "Flights" f
ON t.flight_id = f.id
WHERE t.status = 1
AND f.route_id = 5
GROUP BY f.id;

SELECT f.id,
f.max_tickets_count - COUNT(t.id) AS available_seats
FROM "Flights" f
LEFT JOIN "Tickets" t
ON f.id = t.flight_id
WHERE f.route_id = 5
AND t.status IN (1, 2, 4)
GROUP BY (f.id, f.max_tickets_count);

SELECT f.id, COUNT(t.id)
FROM "Tickets" t
JOIN "Flights" f
ON t.flight_id = f.id
WHERE t.status = 1
AND f.ticket_price > 1000
GROUP BY f.id;

SELECT f.id,
f.max_tickets_count - COUNT(t.id) AS available_seats
FROM "Flights" f
LEFT JOIN "Tickets" t
ON f.id = t.flight_id
WHERE f.ticket_price > 1000
AND t.status IN (1, 2, 4)
GROUP BY (f.id, f.max_tickets_count);

SELECT f.id, COUNT(t.id)
FROM "Tickets" t
JOIN "Flights" f
ON t.flight_id = f.id
WHERE t.status = 1
AND f.departure_time < '10:00:00'
GROUP BY f.id;

SELECT f.id,
f.max_tickets_count - COUNT(t.id) AS available_seats
FROM "Flights" f
LEFT JOIN "Tickets" t
ON f.id = t.flight_id
WHERE f.departure_time < '10:00:00'
AND t.status IN (1, 2, 4)
GROUP BY (f.id, f.max_tickets_count);
