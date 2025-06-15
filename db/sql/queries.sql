SELECT e.last_name, e.first_name
FROM "Employees" e;

SELECT COUNT(e.last_name)
FROM "Employees" e;

SELECT e.last_name, e.first_name
FROM "Employees" e
WHERE e.director = TRUE;

SELECT COUNT(e.last_name)
FROM "Employees" e
WHERE e.director = TRUE;

SELECT e.last_name, e.first_name
FROM "Employees" e
JOIN "Brigades" b ON b.id = e.brigade_id 
WHERE b.department_id = 1;

SELECT COUNT(e.last_name)
FROM "Employees" e
JOIN "Brigades" b ON b.id = e.brigade_id 
WHERE b.department_id = 1;

SELECT e.last_name, e.first_name
FROM "Employees" e
WHERE AGE(NOW(), e.employment) > INTERVAL '10 years';

SELECT COUNT(e.last_name)
FROM "Employees" e
WHERE AGE(NOW(), e.employment) > INTERVAL '10 years';

SELECT e.last_name, e.first_name
FROM "Employees" e
WHERE e.gender = 'woman';

SELECT COUNT(e.last_name)
FROM "Employees" e
WHERE e.gender = 'woman';

SELECT e.last_name, e.first_name
FROM "Employees" e
WHERE AGE(NOW(), e.birthday) > INTERVAL '34 years';

SELECT COUNT(e.last_name)
FROM "Employees" e
WHERE AGE(NOW(), e.birthday) > INTERVAL '34 years';

SELECT e.last_name, e.first_name
FROM "Employees" e
WHERE e.child_count = 0;

SELECT COUNT(e.last_name)
FROM "Employees" e
WHERE e.child_count = 0;

SELECT e.last_name, e.first_name
FROM "Employees" e
WHERE e.salary > 70000;

SELECT COUNT(e.last_name)
FROM "Employees" e
WHERE e.salary > 70000;

-- 1 end

SELECT e.last_name, e.first_name
FROM "Employees" e
WHERE e.brigade_id = 5;

SELECT COUNT(e.last_name)
FROM "Employees" e
WHERE e.brigade_id = 5;

SELECT e.last_name, e.first_name
FROM "Employees" e
JOIN "Brigades" b ON e.brigade_id = b.id
JOIN "Planes" p ON b.id IN (p.brigade_id, p.pilot_brigade_id, p.engineer_brigade_id)
JOIN "Flights" f ON p.id = f.plane_id
WHERE f.id = 301;

SELECT COUNT(e.last_name)
FROM "Employees" e
JOIN "Brigades" b ON e.brigade_id = b.id
JOIN "Planes" p ON b.id IN (p.brigade_id, p.pilot_brigade_id, p.engineer_brigade_id)
JOIN "Flights" f ON p.id = f.plane_id
WHERE f.id = 301;

SELECT e.last_name, e.first_name
FROM "Employees" e
JOIN (
        SELECT 
            b.id AS brigade_id, 
            AVG(e.salary) AS average_salary
        FROM "Employees" e
        JOIN "Brigades" b ON e.brigade_id = b.id
        GROUP BY b.id
    ) avg_salaries ON e.brigade_id = avg_salaries.brigade_id
WHERE e.salary > avg_salaries.average_salary
ORDER BY e.brigade_id, e.salary DESC;

-- 2 end

SELECT DISTINCT e.last_name, e.first_name
FROM "Pilots_medical_check" p
JOIN "Employees" e ON e.id = p.employee_id
WHERE EXTRACT(YEAR FROM p.medical_check_date) = 2013;

SELECT COUNT(DISTINCT e.last_name)
FROM "Pilots_medical_check" p
JOIN "Employees" e ON e.id = p.employee_id
WHERE EXTRACT(YEAR FROM p.medical_check_date) = 2013;

SELECT e.last_name, e.first_name
FROM "Employees" e
WHERE 
    e.id NOT IN (
        SELECT DISTINCT pmc.employee_id
        FROM "Pilots_medical_check" pmc
        WHERE EXTRACT(YEAR FROM pmc.medical_check_date) = 2013
    );

SELECT COUNT(e.last_name)
FROM "Employees" e
WHERE 
    e.id NOT IN (
        SELECT DISTINCT pmc.employee_id
        FROM "Pilots_medical_check" pmc
        WHERE EXTRACT(YEAR FROM pmc.medical_check_date) = 2013
    );

SELECT e.last_name, e.first_name
FROM "Employees" e
WHERE e.gender = 'woman' and e.position_id = 1;

SELECT COUNT(e.last_name)
FROM "Employees" e
WHERE e.gender = 'woman' and e.position_id = 1;

SELECT e.last_name, e.first_name
FROM "Employees" e
WHERE AGE(NOW(), e.birthday) > INTERVAL '34 years' and e.position_id = 1;

SELECT COUNT(e.last_name)
FROM "Employees" e
WHERE AGE(NOW(), e.birthday) > INTERVAL '34 years' and e.position_id = 1;

SELECT e.last_name, e.first_name
FROM "Employees" e
WHERE e.salary > 70000 and e.position_id = 1;

SELECT COUNT(e.last_name)
FROM "Employees" e
WHERE e.salary > 70000 and e.position_id = 1;

-- 3 end

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

-- 4 end

SELECT p.id, p.model
FROM "Planes" p
WHERE p.last_checkup_date BETWEEN '2020-10-10' AND '2025-12-12';

SELECT COUNT(p.id)
FROM "Planes" p
WHERE p.last_checkup_date BETWEEN '2020-10-10' AND '2025-12-12';

SELECT p.id, p.model
FROM "Planes" p
WHERE p.status = 2
AND p.last_checkup_date BETWEEN '2020-10-10' AND '2025-12-12';

SELECT COUNT(p.id)
FROM "Planes" p
WHERE p.status = 2
AND p.last_checkup_date BETWEEN '2020-10-10' AND '2025-12-12';;

SELECT p.id, p.model
FROM "Planes" p
WHERE p.repairs_count > 0;

SELECT COUNT(p.id)
FROM "Planes" p
WHERE p.repairs_count > 0;

SELECT p.id, p.model
FROM "Planes" p
WHERE (p.flights_count - p.flights_count_after_last_repair) > 10;

SELECT COUNT(p.id)
FROM "Planes" p
WHERE (p.flights_count - p.flights_count_after_last_repair) > 10;

SELECT p.id, p.model
FROM "Planes" p
WHERE AGE(NOW(), p.creation_date) > INTERVAL '5 years';

SELECT COUNT(p.id)
FROM "Planes" p
WHERE AGE(NOW(), p.creation_date) > INTERVAL '5 years';

-- 5 end

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

-- 6 end

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

-- 7 end

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

-- 8 end

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

-- 9 end

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

-- 10 end

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

-- 11 end

SELECT COUNT(t.id)
FROM "Tickets" t
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
AND f.departure_date < '2025-10-01'
GROUP BY f.id;

SELECT f.id,
f.max_tickets_count - COUNT(t.id) AS available_seats
FROM "Flights" f
LEFT JOIN "Tickets" t
ON f.id = t.flight_id
WHERE f.departure_date < '2025-10-01'
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

-- 12 end

SELECT COUNT(t.id)
FROM "Tickets" t
WHERE t.flight_id = 365
AND t.status = 3;

SELECT COUNT(t.id)
FROM "Tickets" t
WHERE t.status_date < '2025-10-01'
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

-- 13 end
