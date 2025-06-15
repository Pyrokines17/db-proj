SELECT p.id, p.first_name, p.last_name
FROM "Passengers" p
JOIN "Tickets" t ON p.id = t.passenger_id
JOIN "Flights" f ON t.flight_id = f.id
WHERE f.route_id = 1;

SELECT COUNT(p.id)
FROM "Passengers" p
JOIN "Tickets" t ON p.id = t.passenger_id
JOIN "Flights" f ON t.flight_id = f.id
WHERE f.route_id = 1;

SELECT p.id, p.first_name, p.last_name
FROM "Passengers" p
JOIN "Tickets" t ON p.id = t.passenger_id
WHERE t.status NOT IN (3, 5)
AND t.status_date = '2023-10-01';

SELECT COUNT(p.id)
FROM "Passengers" p
JOIN "Tickets" t ON p.id = t.passenger_id
WHERE t.status NOT IN (3, 5)
AND t.status_date = '2023-10-01';

SELECT p.id, p.first_name, p.last_name
FROM "Passengers" p
JOIN "Tickets" t ON p.id = t.passenger_id
WHERE t.luggage is TRUE;

SELECT COUNT(p.id)
FROM "Passengers" p
JOIN "Tickets" t ON p.id = t.passenger_id
WHERE t.luggage is TRUE;

SELECT p.id, p.first_name, p.last_name
FROM "Passengers" p
WHERE p.gender = 'woman';

SELECT COUNT(p.id)
FROM "Passengers" p
WHERE p.gender = 'woman';

SELECT p.id, p.first_name, p.last_name
FROM "Passengers" p
WHERE AGE(NOW(), p.birthday) > INTERVAL '34 years';

SELECT COUNT(p.id)
FROM "Passengers" p
WHERE AGE(NOW(), p.birthday) > INTERVAL '34 years';