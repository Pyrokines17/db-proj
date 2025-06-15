CREATE OR REPLACE FUNCTION check_age()
RETURNS TRIGGER AS $$
BEGIN
    IF AGE(NEW.birthday) < INTERVAL '18 years' THEN
        RAISE EXCEPTION 'Человек должен быть старше 18 лет. Дата рождения: %', NEW.birthday;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_check_employee_age
BEFORE INSERT OR UPDATE ON "Employees"
FOR EACH ROW
EXECUTE FUNCTION check_age();

CREATE OR REPLACE FUNCTION check_medical_date()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.medical_check_date > CURRENT_DATE THEN
        RAISE EXCEPTION 'Дата медицинского осмотра не может быть в будущем: %', NEW.medical_check_date;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_check_medical_date
BEFORE INSERT OR UPDATE ON "Employees"
FOR EACH ROW
EXECUTE FUNCTION check_medical_date();

CREATE OR REPLACE FUNCTION check_employment_position()
RETURNS TRIGGER AS $$
DECLARE
    depart_type INT;
    position_exists BOOLEAN;
BEGIN
    SELECT d.department_type
    INTO depart_type
    FROM "Brigades" b
    JOIN "Departments" d ON b.department_id = d.id
    WHERE b.id = NEW.brigade_id;

    SELECT EXISTS (
        SELECT 1
        FROM "Department_positions" dp
        WHERE dp.department_id = depart_type 
        AND dp.position_id = NEW.position_id
    )
    INTO position_exists;

    IF NOT position_exists THEN
        RAISE EXCEPTION 'Должность % не существует в департаменте %', NEW.position_id, depart_type;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_check_employment_position
BEFORE INSERT OR UPDATE ON "Employees"
FOR EACH ROW
EXECUTE FUNCTION check_employment_position();

CREATE OR REPLACE FUNCTION check_medical_requirements()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.position_id = 1 THEN
        IF NEW.medical_check_passed IS NULL OR NEW.medical_check_passed = FALSE THEN
            RAISE EXCEPTION 'Для position_id = 1 поле medical_check_passed должно быть TRUE.';
        END IF;

        IF NEW.medical_check_date IS NULL OR NEW.medical_check_date <= CURRENT_DATE - INTERVAL '1 year' THEN
            RAISE EXCEPTION 'Для position_id = 1 поле medical_check_date должно быть не NULL и в пределах последнего года.';
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_check_medical_requirements
BEFORE INSERT OR UPDATE ON "Employees"
FOR EACH ROW
EXECUTE FUNCTION check_medical_requirements();

CREATE OR REPLACE FUNCTION check_unique_director_per_department()
RETURNS TRIGGER AS $$
DECLARE
    director_exists BOOLEAN;
    oth_department_id INT;
BEGIN
    SELECT b.department_id
    INTO oth_department_id
    FROM "Brigades" b
    WHERE b.id = NEW.brigade_id;

    SELECT EXISTS (
        SELECT 1
        FROM "Employees" e
        JOIN "Brigades" b ON e.brigade_id = b.id
        WHERE b.department_id = oth_department_id
          AND e.director = TRUE
          AND e.id != NEW.id 
    )
    INTO director_exists;

    IF director_exists AND NEW.director = TRUE THEN
        RAISE EXCEPTION 'В департаменте % уже есть директор.', oth_department_id;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_check_unique_director_per_department
BEFORE INSERT OR UPDATE ON "Employees"
FOR EACH ROW
EXECUTE FUNCTION check_unique_director_per_department();

CREATE OR REPLACE FUNCTION check_ticket_status_date()
RETURNS TRIGGER AS $$
DECLARE
    flight_departure_date DATE;
BEGIN
    SELECT f.departure_date
    INTO flight_departure_date
    FROM "Flights" f
    WHERE f.id = NEW.flight_id;

    IF NEW.status_date > flight_departure_date THEN
        RAISE EXCEPTION 'Дата статуса билета (status_date) не может быть позже даты вылета (departure_date): % > %', NEW.status_date, flight_departure_date;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_check_ticket_status_date
BEFORE INSERT OR UPDATE ON "Tickets"
FOR EACH ROW
EXECUTE FUNCTION check_ticket_status_date();

CREATE OR REPLACE FUNCTION check_flights_count()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.flights_count_after_last_repair > NEW.flights_count THEN
        RAISE EXCEPTION 'flights_count_after_last_repair (%), не может быть больше flights_count (%).',
            NEW.flights_count_after_last_repair, NEW.flights_count;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_check_flights_count
BEFORE INSERT OR UPDATE ON "Planes"
FOR EACH ROW
EXECUTE FUNCTION check_flights_count();

CREATE OR REPLACE FUNCTION check_planes_brigades()
RETURNS TRIGGER AS $$
DECLARE
    department_type INT;
    pilot_department_type INT;
    engineer_department_type INT;
BEGIN
    SELECT d.department_type
    INTO department_type
    FROM "Brigades" b
    JOIN "Departments" d ON b.department_id = d.id
    WHERE b.id = NEW.brigade_id;

    IF department_type != 7 THEN
        RAISE EXCEPTION 'brigade_id % должен ссылаться на department_type = 7.', NEW.brigade_id;
    END IF;

    SELECT d.department_type
    INTO pilot_department_type
    FROM "Brigades" b
    JOIN "Departments" d ON b.department_id = d.id
    WHERE b.id = NEW.pilot_brigade_id;

    IF pilot_department_type != 1 THEN
        RAISE EXCEPTION 'pilot_brigade_id % должен ссылаться на department_type = 1.', NEW.pilot_brigade_id;
    END IF;

    SELECT d.department_type
    INTO engineer_department_type
    FROM "Brigades" b
    JOIN "Departments" d ON b.department_id = d.id
    WHERE b.id = NEW.engineer_brigade_id;

    IF engineer_department_type != 3 THEN
        RAISE EXCEPTION 'engineer_brigade_id % должен ссылаться на department_type = 3.', NEW.engineer_brigade_id;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_check_planes_brigades
BEFORE INSERT OR UPDATE ON "Planes"
FOR EACH ROW
EXECUTE FUNCTION check_planes_brigades();

CREATE OR REPLACE FUNCTION check_pilot_position()
RETURNS TRIGGER AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM "Employees"
        WHERE "id" = NEW."employee_id" AND "position_id" = 1
    ) THEN
        RAISE EXCEPTION 'Employee ID % is not a pilot (position_id != 1)', NEW."employee_id";
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER validate_pilot_position
BEFORE INSERT OR UPDATE ON "Pilots_medical_check"
FOR EACH ROW
EXECUTE FUNCTION check_pilot_position();
