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