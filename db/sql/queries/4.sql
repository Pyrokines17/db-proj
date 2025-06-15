SELECT p.id, p.model
FROM "Planes" p
JOIN "Plane_places" pp ON p.id = pp.plane_id
WHERE pp.place = 'Davidfort';

SELECT COUNT(p.id)
FROM "Planes" p
JOIN "Plane_places" pp ON p.id = pp.plane_id
WHERE pp.place = 'Davidfort';

SELECT p.id, p.model
FROM "Flights" f
JOIN "Planes" p ON p.id = f.plane_id
JOIN "Routes" r ON r.id = f.route_id
WHERE r.departure_airport = 'Bowerston' 
AND '20:20:11' NOT BETWEEN f.departure_time AND f.arrival_time;

SELECT COUNT(p.id)
FROM "Flights" f
JOIN "Planes" p ON p.id = f.plane_id
JOIN "Routes" r ON r.id = f.route_id
WHERE r.departure_airport = 'Bowerston' 
AND '20:20:11' NOT BETWEEN f.departure_time AND f.arrival_time;

SELECT p.id, p.model
FROM "Flights" f
JOIN "Planes" p ON p.id = f.plane_id
WHERE f.arrival_time > '10:00:00';

SELECT COUNT(p.id)
FROM "Flights" f
JOIN "Planes" p ON p.id = f.plane_id
WHERE f.arrival_time > '10:00:00';

SELECT p.id, p.model
FROM "Planes" p
WHERE p.flights_count > 50;

SELECT COUNT(p.id)
FROM "Planes" p
WHERE p.flights_count > 50;