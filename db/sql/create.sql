CREATE TYPE enum_gender AS ENUM ('man', 'woman');

CREATE TABLE "Employees" (
    "id" SERIAL PRIMARY KEY,
    "last_name" VARCHAR(127) NOT NULL,
    "first_name" VARCHAR(127) NOT NULL,
    "patronymic" VARCHAR(127) NULL,
    "gender" enum_gender NOT NULL,
    "child_count" INT NOT NULL,
    "position_id" INT NOT NULL,
    "brigade_id" INT NOT NULL,
    "medical_check_passed" BOOLEAN NULL,
    "medical_check_date" DATE NULL,
    "salary" REAL NOT NULL,
    "birthdate" DATE NOT NULL,
    "employment" DATE NOT NULL,
    "director" BOOLEAN NOT NULL,
);

CREATE TABLE "Departments" (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL UNIQUE,
    "department_type" INT NOT NULL
);

CREATE TABLE "Brigades" (
    "id" SERIAL PRIMARY KEY,
    "department_id" INT NOT NULL
);

CREATE TABLE "Plane_status" (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(127) NOT NULL UNIQUE
);

CREATE TABLE "Planes" (
    "id" SERIAL PRIMARY KEY,
    "model" VARCHAR(127) NOT NULL,
    "brigade_id" INT NOT NULL,
    "flights_count" INT NOT NULL,
    "repairs_count" INT NOT NULL,
    "last_checkup_date" DATE NOT NULL,
    "flights_count_after_last_repair" INT NOT NULL,
    "status" INT NOT NULL,
    "capacity" INT NOT NULL,
    "creation_date" DATE NOT NULL,
    "pilot_brigade_id" INT NOT NULL,
    "engineer_brigade_id" INT NOT NULL,
    FOREIGN KEY ("status") REFERENCES "Plane_status"("id")
);

CREATE TABLE "Positions" (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(127) NOT NULL UNIQUE
);

CREATE TABLE "Position_attributes" (
    "position_id" INT,  
    "attribute_name" VARCHAR(127),  
    "attribute_value" VARCHAR(255),  
    PRIMARY KEY ("position_id", "attribute_name"),  
    FOREIGN KEY ("position_id") REFERENCES "Positions"("id")
);

CREATE TABLE "Routes" (
    "id" SERIAL PRIMARY KEY,
    "departure_airport" VARCHAR(255) NOT NULL,
    "arrival_airport" VARCHAR(255) NOT NULL,
    "stopover_airport" VARCHAR(255) NULL
);

CREATE TABLE "Flight_types" (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(127) NOT NULL UNIQUE
);

CREATE TABLE "Flight_status" (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(127) NOT NULL UNIQUE
);

CREATE TABLE "Flights" (
    "id" SERIAL PRIMARY KEY,
    "plane_id" INT NOT NULL,
    "flight_type" INT NOT NULL,
    "departure_date" DATE NOT NULL,
    "departure_time" TIME NOT NULL,
    "arrival_time" TIME NOT NULL,
    "route_id" INT NOT NULL,
    "ticket_price" REAL NOT NULL,
    "max_tickets_count" INT NOT NULL,
    "min_tickets_count" INT NOT NULL,
    "status" INT NOT NULL,
    FOREIGN KEY ("flight_type") REFERENCES "Flight_types"("id"),
    FOREIGN KEY ("status") REFERENCES "Flight_status"("id")
);

CREATE TABLE "Passengers" (
    "id" SERIAL PRIMARY KEY,
    "last_name" VARCHAR(127) NOT NULL,
    "first_name" VARCHAR(127) NOT NULL,
    "patronymic" VARCHAR(127) NULL,
    "gender" enum_gender NOT NULL,
    "passport_number" VARCHAR(32) NOT NULL UNIQUE,
    "has_visa" BOOLEAN NOT NULL,
    "birthdate" DATE NOT NULL,
);

CREATE TABLE "Ticket_status" (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(127) NOT NULL UNIQUE
);

CREATE TABLE "Tickets" (
    "id" SERIAL PRIMARY KEY,
    "flight_id" INT NOT NULL,
    "passenger_id" INT NOT NULL,
    "status" INT NOT NULL,
    "status_date" DATE NOT NULL,
    "luggage" BOOLEAN NOT NULL,
    FOREIGN KEY ("status") REFERENCES "Ticket_status"("id")
);

CREATE TABLE "Department_types" (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(127) NOT NULL UNIQUE
);

CREATE TABLE "Department_positions" (
    "id" SERIAL PRIMARY KEY,
    "department_id" INT NOT NULL,
    "position_id" INT NOT NULL,
    FOREIGN KEY ("department_id") REFERENCES "Department_types"("id"),
    FOREIGN KEY ("position_id") REFERENCES "Positions"("id")
);

CREATE TABLE "Pilots_medical_check" (
    "id" SERIAL PRIMARY KEY,
    "employee_id" INT NOT NULL,
    "medical_check_date" DATE NOT NULL,
    FOREIGN KEY ("employee_id") REFERENCES "Employees"("id")
);

CREATE TABLE "Plane_places" (
    "id" SERIAL PRIMARY KEY,
    "plane_id" INT NOT NULL UNIQUE,
    "place" VARCHAR(127) NOT NULL
);

CREATE TABLE "Delays" (
    "id" SERIAL PRIMARY KEY,
    "status_id" INT NOT NULL,
    FOREIGN KEY ("status_id") REFERENCES "Flight_status"("id")
);

ALTER TABLE 
    "Employees" ADD CONSTRAINT "check_employment_date" CHECK ("employment" < CURRENT_DATE);
ALTER TABLE 
    "Employees" ADD CONSTRAINT "check_birthdate_age" CHECK (DATE_PART('year', AGE("birthdate")) >= 18);
ALTER TABLE 
    "Passengers" ADD CONSTRAINT "check_birthday_date" CHECK ("birthday" < CURRENT_DATE);
ALTER TABLE 
    "Planes" ADD CONSTRAINT "check_last_checkup_date" CHECK ("last_checkup_date" < CURRENT_DATE);
ALTER TABLE 
    "Planes" ADD CONSTRAINT "check_creation_date" CHECK ("creation_date" < CURRENT_DATE);

ALTER TABLE
    "Employees" ADD CONSTRAINT "employees_brigade_id_foreign" FOREIGN KEY("brigade_id") REFERENCES "Brigades"("id");
ALTER TABLE
    "Tickets" ADD CONSTRAINT "tickets_flight_id_foreign" FOREIGN KEY("flight_id") REFERENCES "Flights"("id");
ALTER TABLE
    "Tickets" ADD CONSTRAINT "tickets_passenger_id_foreign" FOREIGN KEY("passenger_id") REFERENCES "Passengers"("id");
ALTER TABLE
    "Planes" ADD CONSTRAINT "planes_brigade_id_foreign" FOREIGN KEY("brigade_id") REFERENCES "Brigades"("id");
ALTER TABLE
    "Employees" ADD CONSTRAINT "employees_position_id_foreign" FOREIGN KEY("position_id") REFERENCES "Positions"("id");
ALTER TABLE
    "Brigades" ADD CONSTRAINT "brigades_department_id_foreign" FOREIGN KEY("department_id") REFERENCES "Departments"("id");
ALTER TABLE
    "Flights" ADD CONSTRAINT "flights_route_id_foreign" FOREIGN KEY("route_id") REFERENCES "Routes"("id");
ALTER TABLE
    "Flights" ADD CONSTRAINT "flights_plane_id_foreign" FOREIGN KEY("plane_id") REFERENCES "Planes"("id");
ALTER TABLE
    "Departments" ADD CONSTRAINT "departments_department_type_foreign" FOREIGN KEY("department_type") REFERENCES "Department_types"("id");
ALTER TABLE
    "Planes" ADD CONSTRAINT "planes_pilot_brigade_id_foreign" FOREIGN KEY("pilot_brigade_id") REFERENCES "Brigades"("id");
ALTER TABLE
    "Planes" ADD CONSTRAINT "planes_engineer_brigade_id_foreign" FOREIGN KEY("engineer_brigade_id") REFERENCES "Brigades"("id");
ALTER TABLE
    "Plane_places" ADD CONSTRAINT "plane_places_plane_id_foreign" FOREIGN KEY("plane_id") REFERENCES "Planes"("id");
