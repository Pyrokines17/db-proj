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