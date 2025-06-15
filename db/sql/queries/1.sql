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
