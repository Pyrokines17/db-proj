SELECT p.id, p.model
FROM "Planes" p
WHERE p.last_checkup_date BETWEEN '2020-10-20' AND '2025-12-12';

SELECT COUNT(p.id)
FROM "Planes" p
WHERE p.last_checkup_date BETWEEN '2020-10-20' AND '2025-12-12';

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
