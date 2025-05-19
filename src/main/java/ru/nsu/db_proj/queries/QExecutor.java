package ru.nsu.db_proj.queries;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.nsu.db_proj.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QExecutor {
    public static Message getAllDepartmentTypes(Connection connection)
            throws SQLException {
        String sql = """
            SELECT dt.id, dt.name
            FROM "Department_types" dt;
            """;

        Message response = new Message("RESPONSE");
        ArrayNode departmentTypes = response.getContent().putArray("results");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    int id = rs.getInt("id");

                    ObjectNode rowData = departmentTypes.addObject();
                    rowData.put("name", name);
                    rowData.put("id", id);
                }
            }
        }

        return response;
    }

    public static Message getAllBrigadeTypes(Connection connection)
            throws SQLException {
        String sql = """
            SELECT b.id
            FROM "Brigades" b;
            """;

        Message response = new Message("RESPONSE");
        ArrayNode brigadeTypes = response.getContent().putArray("results");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");

                    ObjectNode rowData = brigadeTypes.addObject();
                    rowData.put("id", id);
                }
            }
        }

        return response;
    }

    public static Message getAllFlights(Connection connection)
        throws SQLException {
        String sql = """
            SELECT f.id
            FROM "Flights" f;
            """;

        Message response = new Message("RESPONSE");
        ArrayNode flights = response.getContent().putArray("results");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");

                    ObjectNode rowData = flights.addObject();
                    rowData.put("id", id);
                }
            }
        }

        return response;
    }

    public static Message getAllAirports(Connection connection)
        throws SQLException {
        String sql = """
            SELECT a.id, a.place
            FROM "Plane_places" a;
            """;

        Message response = new Message("RESPONSE");
        ArrayNode airports = response.getContent().putArray("results");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("place");
                    int id = rs.getInt("id");

                    ObjectNode rowData = airports.addObject();
                    rowData.put("place", name);
                    rowData.put("id", id);
                }
            }
        }

        return response;
    }

    public static Message getAllArrivalAirports(Connection connection)
        throws SQLException {
        String sql = """
            SELECT a.id, a.arrival_airport
            FROM "Routes" a;
            """;

        Message response = new Message("RESPONSE");
        ArrayNode airports = response.getContent().putArray("results");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("arrival_airport");
                    int id = rs.getInt("id");

                    ObjectNode rowData = airports.addObject();
                    rowData.put("arrival_airport", name);
                    rowData.put("id", id);
                }
            }
        }

        return response;
    }

    public static Message getAllRoutes(Connection connection)
            throws SQLException {
        String sql = """
            SELECT r.id, r.departure_airport, r.arrival_airport
            FROM "Routes" r;
            """;

        Message response = new Message("RESPONSE");
        ArrayNode routes = response.getContent().putArray("results");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String departureAirport = rs.getString("departure_airport");
                    String arrivalAirport = rs.getString("arrival_airport");

                    ObjectNode rowData = routes.addObject();
                    rowData.put("id", id);
                    rowData.put("departure_airport", departureAirport);
                    rowData.put("arrival_airport", arrivalAirport);
                }
            }
        }

        return response;
    }

    public static Message getAllFlightStatuses(Connection connection)
            throws SQLException {
        String sql = """
            SELECT t.id, t.name
            FROM "Flight_status" t;
            """;

        Message response = new Message("RESPONSE");
        ArrayNode flightStatuses = response.getContent().putArray("results");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String status = rs.getString("name");

                    ObjectNode rowData = flightStatuses.addObject();
                    rowData.put("id", id);
                    rowData.put("name", status);
                }
            }
        }

        return response;
    }

    public static Message getAllDelays(Connection connection)
            throws SQLException {
        String sql = """
            SELECT d.id, d.status_id
            FROM "Delays" d;
            """;

        Message response = new Message("RESPONSE");
        ArrayNode delays = response.getContent().putArray("results");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("status_id");

                    ObjectNode rowData = delays.addObject();
                    rowData.put("id", id);
                    rowData.put("status_id", name);
                }
            }
        }

        return response;
    }

    public static Message getAllModels(
            Connection connection
    ) throws SQLException {
        String sql = """
                SELECT p.id, p.model
                FROM "Planes" p;
                """;

        Message response = new Message("RESPONSE");
        ArrayNode models = response.getContent().putArray("results");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String model = rs.getString("model");

                    ObjectNode rowData = models.addObject();
                    rowData.put("id", id);
                    rowData.put("model", model);
                }
            }
        }

        return response;
    }

    public static Message getAllFlightTypes(
            Connection connection
    ) throws SQLException {
        String sql = """
                SELECT f.id, f.name
                FROM "Flight_types" f;
                """;

        Message response = new Message("RESPONSE");
        ArrayNode flightTypes = response.getContent().putArray("results");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");

                    ObjectNode rowData = flightTypes.addObject();
                    rowData.put("id", id);
                    rowData.put("name", name);
                }
            }
        }

        return response;
    }

    public static Message getEmployees(
            Boolean director,
            Integer departmentId,
            Integer employmentYears,
            String gender,
            Integer ageYears,
            Integer childCount,
            Integer salary,
            Connection connection
    ) throws SQLException {
        String sql = """
                SELECT e.last_name, e.first_name, e.birthday, e.salary
                FROM "Employees" e
                JOIN "Brigades" b ON b.id = e.brigade_id
                WHERE (e.director = ? OR ? IS NULL)
                  AND (b.department_id = ?::integer OR ?::integer IS NULL)
                  AND (EXTRACT(YEAR FROM AGE(NOW(), e.employment)) > ?::integer OR ?::integer IS NULL)
                  AND (e.gender::text = ?::text OR ?::text IS NULL)
                  AND (EXTRACT(YEAR FROM AGE(NOW(), e.birthday)) > ?::integer OR ?::integer IS NULL)
                  AND (e.child_count = ?::integer OR ?::integer IS NULL)
                  AND (e.salary > ?::integer OR ?::integer IS NULL)
            """;

        Message response = new Message("RESPONSE");
        ArrayNode resultArray = response.getContent().putArray("results");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;

            stmt.setObject(index++, director);
            stmt.setObject(index++, director);
            stmt.setObject(index++, departmentId);
            stmt.setObject(index++, departmentId);
            stmt.setObject(index++, employmentYears);
            stmt.setObject(index++, employmentYears);
            stmt.setString(index++, gender);
            stmt.setString(index++, gender);
            stmt.setObject(index++, ageYears);
            stmt.setObject(index++, ageYears);
            stmt.setObject(index++, childCount);
            stmt.setObject(index++, childCount);
            stmt.setObject(index++, salary);
            stmt.setObject(index, salary);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String lastName = rs.getString("last_name");
                    String firstName = rs.getString("first_name");
                    String birthday = rs.getString("birthday");
                    int salaryValue = rs.getInt("salary");

                    ObjectNode rowData = resultArray.addObject();
                    rowData.put("last_name", lastName);
                    rowData.put("first_name", firstName);
                    rowData.put("birthday", birthday);
                    rowData.put("salary", salaryValue);
                }
            }
        }

        return response;
    }

    public static Message getBrigade(
            Integer brigadeId,
            Integer flightId,
            Integer applySalaryFilter,
            Connection connection
    ) throws SQLException {
        String sql = """
                SELECT DISTINCT e.last_name, e.first_name, e.brigade_id, e.salary
                FROM "Employees" e
                LEFT JOIN "Brigades" b ON e.brigade_id = b.id
                LEFT JOIN "Planes" p ON b.id IN (p.brigade_id, p.pilot_brigade_id, p.engineer_brigade_id)
                LEFT JOIN "Flights" f ON p.id = f.plane_id
                LEFT JOIN (
                    SELECT b.id AS brigade_id, AVG(e2.salary) AS average_salary
                    FROM "Employees" e2
                    JOIN "Brigades" b ON e2.brigade_id = b.id
                    GROUP BY b.id
                ) avg_salaries ON e.brigade_id = avg_salaries.brigade_id
                WHERE (e.brigade_id = ?::integer OR ?::integer IS NULL)
                  AND (f.id = ?::integer OR ?::integer IS NULL)
                  AND (e.salary > avg_salaries.average_salary OR ?::integer IS NULL)
                ORDER BY e.brigade_id, e.salary DESC
                """;

        Message response = new Message("RESPONSE");
        ArrayNode employees = response.getContent().putArray("results");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, brigadeId);
            stmt.setObject(2, brigadeId);
            stmt.setObject(3, flightId);
            stmt.setObject(4, flightId);
            stmt.setObject(5, applySalaryFilter);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String lastName = rs.getString("last_name");
                    String firstName = rs.getString("first_name");

                    ObjectNode rowData = employees.addObject();
                    rowData.put("last_name", lastName);
                    rowData.put("first_name", firstName);
                    rowData.put("brigade_id", rs.getInt("brigade_id"));
                    rowData.put("salary", rs.getInt("salary"));
                }
            }
        }

        return response;
    }

    public static Message getPilot(
        Integer medicalCheckYear,
        Integer hasMedicalCheck,
        Integer noMedicalCheck,
        String gender,
        Integer ageYears,
        Integer salary,
        Integer positionId,
        Connection connection
    ) throws SQLException {
        String sql = """
                SELECT DISTINCT e.last_name, e.first_name, e.birthday, e.salary
                FROM "Employees" e
                LEFT JOIN "Pilots_medical_check" p ON e.id = p.employee_id
                  AND EXTRACT(YEAR FROM p.medical_check_date) = ?::integer
                WHERE (p.employee_id IS NOT NULL OR ?::integer IS NULL)
                  AND (p.employee_id IS NULL OR ?::integer IS NULL)
                  AND (e.gender::text = ?::text OR ?::text IS NULL)
                  AND (AGE(NOW(), e.birthday) > INTERVAL '1 year' * ?::integer OR ?::integer IS NULL)
                  AND (e.salary > ?::integer OR ?::integer IS NULL)
                  AND (e.position_id = ?::integer OR ?::integer IS NULL)
                """;

        Message response = new Message("RESPONSE");
        ArrayNode employees = response.getContent().putArray("results");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;

            stmt.setObject(index++, medicalCheckYear);
            stmt.setObject(index++, hasMedicalCheck);
            stmt.setObject(index++, noMedicalCheck);
            stmt.setString(index++, gender);
            stmt.setString(index++, gender);
            stmt.setObject(index++, ageYears);
            stmt.setObject(index++, ageYears);
            stmt.setObject(index++, salary);
            stmt.setObject(index++, salary);
            stmt.setObject(index++, positionId);
            stmt.setObject(index, positionId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String lastName = rs.getString("last_name");
                    String firstName = rs.getString("first_name");
                    String birthday = rs.getString("birthday");
                    int salaryValue = rs.getInt("salary");

                    ObjectNode rowData = employees.addObject();
                    rowData.put("last_name", lastName);
                    rowData.put("first_name", firstName);
                    rowData.put("birthday", birthday);
                    rowData.put("salary", salaryValue);
                }
            }
        }

        return response;
    }

    public static Message getPlane(
        String place,
        String departureAirport,
        String excludeTime,
        String arrivalTime,
        Integer flightsCount,
        Connection connection
    ) throws SQLException {
        String sql = """
                SELECT DISTINCT p.id, p.model
                FROM "Planes" p
                LEFT JOIN "Plane_places" pp ON p.id = pp.plane_id AND pp.place = ?::text
                LEFT JOIN "Flights" f ON p.id = f.plane_id
                LEFT JOIN "Routes" r ON f.route_id = r.id AND r.arrival_airport = ?::text
                WHERE (pp.plane_id IS NOT NULL OR ?::text IS NULL)
                  AND (pp.plane_id IS NOT NULL AND ?::time NOT BETWEEN f.departure_time AND f.arrival_time OR ?::text IS NULL OR ?::time IS NULL)
                  AND (r.id IS NOT NULL AND f.arrival_time > ?::time OR ?::time IS NULL OR ?::text IS NULL)
                  AND (p.flights_count > ?::integer OR ?::integer IS NULL)
                """;

        Message response = new Message("RESPONSE");
        ArrayNode planes = response.getContent().putArray("results");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;

            stmt.setString(index++, place);
            stmt.setString(index++, departureAirport);
            stmt.setString(index++, place);
            stmt.setString(index++, excludeTime);
            stmt.setString(index++, place);
            stmt.setString(index++, excludeTime);
            stmt.setString(index++, arrivalTime);
            stmt.setString(index++, arrivalTime);
            stmt.setString(index++, departureAirport);
            stmt.setObject(index++, flightsCount);
            stmt.setObject(index, flightsCount);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String model = rs.getString("model");

                    ObjectNode rowData = planes.addObject();
                    rowData.put("id", id);
                    rowData.put("model", model);
                }
            }
        }

        return response;
    }

    public static Message getRepair(
        String startDate,
        String endDate,
        Integer status,
        Integer repairsCount,
        Integer flightsDifference,
        Integer ageYears,
        Connection connection
    ) throws SQLException {
        String sql = """
                SELECT DISTINCT p.id, p.model
                FROM "Planes" p
                WHERE (p.last_checkup_date BETWEEN ?::date AND ?::date OR ?::date IS NULL OR ?::date IS NULL)
                  AND (p.status = ?::integer OR ?::integer IS NULL)
                  AND (p.repairs_count > ?::integer OR ?::integer IS NULL)
                  AND ((p.flights_count - p.flights_count_after_last_repair) > ?::integer OR ?::integer IS NULL)
                  AND (AGE(NOW(), p.creation_date) > INTERVAL '1 year' * ?::integer OR ?::integer IS NULL)
                """;

        Message response = new Message("RESPONSE");
        ArrayNode planes = response.getContent().putArray("results");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;

            stmt.setString(index++, startDate);
            stmt.setString(index++, endDate);
            stmt.setString(index++, startDate);
            stmt.setString(index++, endDate);
            stmt.setObject(index++, status);
            stmt.setObject(index++, status);
            stmt.setObject(index++, repairsCount);
            stmt.setObject(index++, repairsCount);
            stmt.setObject(index++, flightsDifference);
            stmt.setObject(index++, flightsDifference);
            stmt.setObject(index++, ageYears);
            stmt.setObject(index, ageYears);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String model = rs.getString("model");

                    ObjectNode rowData = planes.addObject();
                    rowData.put("id", id);
                    rowData.put("model", model);
                }
            }
        }

        return response;
    }

    public static Message getFlight1(
        Integer routeId,
        Integer durationHours,
        Integer ticketPrice,
        Connection connection
    ) throws SQLException {
        String sql = """
                    SELECT DISTINCT f.id, f.plane_id, f.departure_time, f.arrival_time
                    FROM "Flights" f
                    WHERE (f.route_id = ?::integer OR ?::integer IS NULL)
                      AND (f.arrival_time - f.departure_time > INTERVAL '1 hour' * ?::integer OR ?::integer IS NULL)
                      AND (f.ticket_price > ?::integer OR ?::integer IS NULL)
                    """;

        Message response = new Message("RESPONSE");
        ArrayNode flights = response.getContent().putArray("results");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;

            stmt.setObject(index++, routeId);
            stmt.setObject(index++, routeId);
            stmt.setObject(index++, durationHours);
            stmt.setObject(index++, durationHours);
            stmt.setObject(index++, ticketPrice);
            stmt.setObject(index, ticketPrice);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int planeId = rs.getInt("plane_id");
                    String departureTime = rs.getString("departure_time");
                    String arrivalTime = rs.getString("arrival_time");

                    ObjectNode rowData = flights.addObject();
                    rowData.put("id", id);
                    rowData.put("plane_id", planeId);
                    rowData.put("departure_time", departureTime);
                    rowData.put("arrival_time", arrivalTime);
                }
            }
        }

        return response;
    }

    public static Message getFlight2(
            Integer status,
            Integer routeId,
            Integer maxTickets,
            Double ticketRatio,
            Connection connection
    ) throws SQLException {
        String sql = """
                SELECT DISTINCT f.id, f.plane_id, f.departure_time, f.arrival_time
                FROM "Flights" f
                LEFT JOIN (
                    SELECT t.flight_id, f.max_tickets_count - COUNT(t.id) AS ticket_count,
                           (f.max_tickets_count::FLOAT-COUNT(t.id)::FLOAT) / f.max_tickets_count * 100 AS ticket_ratio
                    FROM "Tickets" t
                    JOIN "Flights" f ON t.flight_id = f.id
                    GROUP BY t.flight_id, f.max_tickets_count
                ) ticket_stats ON f.id = ticket_stats.flight_id
                WHERE (f.status = ?::integer OR ?::integer IS NULL)
                  AND (f.route_id = ?::integer OR ?::integer IS NULL)
                  AND (ticket_stats.ticket_count > ?::integer OR ?::integer IS NULL)
                  AND (ticket_stats.ticket_ratio > ?::FLOAT OR ?::FLOAT IS NULL)
                """;

        Message response = new Message("RESPONSE");
        ArrayNode flights = response.getContent().putArray("results");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;
            
            stmt.setObject(index++, status);
            stmt.setObject(index++, status);
            stmt.setObject(index++, routeId);
            stmt.setObject(index++, routeId);
            stmt.setObject(index++, maxTickets);
            stmt.setObject(index++, maxTickets);
            stmt.setObject(index++, ticketRatio);
            stmt.setObject(index, ticketRatio);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int planeId = rs.getInt("plane_id");
                    String departureTime = rs.getString("departure_time");
                    String arrivalTime = rs.getString("arrival_time");
                    
                    ObjectNode rowData = flights.addObject();
                    rowData.put("id", id);
                    rowData.put("plane_id", planeId);
                    rowData.put("departure_time", departureTime);
                    rowData.put("arrival_time", arrivalTime);
                }
            }
        }

        return response;
    }

    public static Message getFlight3(
            Integer status,
            Integer routeId,
            Integer ticketStatus,
            Integer[] statusArray,
            Connection connection,
            Boolean returnTicket
    ) throws SQLException {
        String sql = """
                SELECT DISTINCT f.id, f.plane_id, f.departure_time, f.arrival_time, ticket_stats.ticket_count
                FROM "Flights" f
                LEFT JOIN (
                    SELECT t.flight_id, COUNT(t.id) AS ticket_count
                    FROM "Tickets" t
                    WHERE t.status = ?::integer OR ?::integer IS NULL
                    GROUP BY t.flight_id
                ) ticket_stats ON f.id = ticket_stats.flight_id
                WHERE ((?::integer IS NOT NULL AND f.status = ?::integer)
                       OR (?::integer[] IS NOT NULL AND f.status = ANY(?::integer[]))
                       OR (?::integer IS NULL AND ?::integer[] IS NULL))
                  AND (f.route_id = ?::integer OR ?::integer IS NULL)
                """;

        Message response = new Message("RESPONSE");
        ArrayNode flights = response.getContent().putArray("results");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;

            stmt.setObject(index++, ticketStatus);
            stmt.setObject(index++, ticketStatus);
            stmt.setObject(index++, status);
            stmt.setObject(index++, status);
            stmt.setObject(index++, statusArray);
            stmt.setObject(index++, statusArray != null ? connection.createArrayOf("INTEGER", statusArray) : null);
            stmt.setObject(index++, status);
            stmt.setObject(index++, statusArray);
            stmt.setObject(index++, routeId);
            stmt.setObject(index, routeId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int planeId = rs.getInt("plane_id");
                    String departureTime = rs.getString("departure_time");
                    String arrivalTime = rs.getString("arrival_time");
                    
                    ObjectNode rowData = flights.addObject();
                    rowData.put("id", id);
                    rowData.put("plane_id", planeId);
                    rowData.put("departure_time", departureTime);
                    rowData.put("arrival_time", arrivalTime);

                    if (returnTicket) {
                        int ticketCount = rs.getInt("ticket_count");
                        rowData.put("ticket_count", ticketCount);
                    }
                }
            }
        }

        return response;
    }

    public static Message getFlight4(
            String planeId,
            String departureTime,
            Connection connection
    ) throws SQLException {
        String sql = """
                SELECT DISTINCT f.id, f.plane_id, f.departure_time, f.arrival_time
                FROM "Flights" f
                LEFT JOIN "Planes" p ON f.plane_id = p.id
                WHERE (p.model = ?::text OR ?::text IS NULL)
                  AND (f.departure_time > ?::time OR ?::time IS NULL)
                """;

        Message response = new Message("RESPONSE");
        ArrayNode flights = response.getContent().putArray("results");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;

            stmt.setObject(index++, planeId);
            stmt.setObject(index++, planeId);
            stmt.setString(index++, departureTime);
            stmt.setString(index, departureTime);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int planeIdOth = rs.getInt("plane_id");
                    String departureTimeOth = rs.getString("departure_time");
                    String arrivalTime = rs.getString("arrival_time");

                    ObjectNode rowData = flights.addObject();
                    rowData.put("id", id);
                    rowData.put("plane_id", planeIdOth);
                    rowData.put("departure_time", departureTimeOth);
                    rowData.put("arrival_time", arrivalTime);
                }
            }
        }

        return response;
    }

    public static Message getFlight5(
            Integer flightType,
            String planeId,
            Connection connection
    ) throws SQLException {
        String sql = """
                SELECT DISTINCT f.id, f.plane_id, f.departure_time, f.arrival_time
                FROM "Flights" f
                LEFT JOIN "Planes" p ON f.plane_id = p.id
                WHERE (f.flight_type = ?::integer OR ?::integer IS NULL)
                  AND (p.model = ?::text OR ?::text IS NULL)
                """;

        Message response = new Message("RESPONSE");
        ArrayNode flights = response.getContent().putArray("results");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;
            
            stmt.setObject(index++, flightType);
            stmt.setObject(index++, flightType);
            stmt.setObject(index++, planeId);
            stmt.setObject(index, planeId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int planeIdOth = rs.getInt("plane_id");
                    String departureTime = rs.getString("departure_time");
                    String arrivalTime = rs.getString("arrival_time");
                    
                    ObjectNode rowData = flights.addObject();
                    rowData.put("id", id);
                    rowData.put("plane_id", planeIdOth);
                    rowData.put("departure_time", departureTime);
                    rowData.put("arrival_time", arrivalTime);
                }
            }
        }

        return response;
    }

    public static Message getCalc(
            Integer count,
            Integer[] array,
            Connection connection
    ) throws SQLException {
        String sql = """
                SELECT COUNT(t.id)::float / ?::integer
                FROM "Tickets" t
                JOIN "Flights" f ON t.flight_id = f.id
                WHERE f.route_id = ANY(?::integer[])
                """;

        Message response = new Message("RESPONSE");
        ArrayNode results = response.getContent().putArray("results");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;

            stmt.setObject(index++, count);
            stmt.setObject(index, array != null ? connection.createArrayOf("INTEGER", array) : null);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    double result = rs.getDouble(1);

                    ObjectNode rowData = results.addObject();
                    rowData.put("result", result);
                }
            }
        }

        return response;
    }

    public static Message getPassenger(
            Integer routeId,
            String statusDate,
            Integer[] excludedStatuses,
            Boolean luggage,
            String gender,
            Integer ageYears,
            Connection connection
    ) throws SQLException {
        String sql = """
                SELECT DISTINCT p.id, p.first_name, p.last_name, p.birthday
                FROM "Passengers" p
                LEFT JOIN "Tickets" t ON p.id = t.passenger_id
                LEFT JOIN "Flights" f ON t.flight_id = f.id AND f.id = ?::integer
                WHERE (f.id IS NOT NULL OR ?::integer IS NULL)
                  AND (t.status != ANY(?) AND t.status_date = ?::date OR ? IS NULL OR ?::date IS NULL)
                  AND (t.luggage = ?::bool OR ?::bool IS NULL)
                  AND (p.gender::text = ?::text OR ?::text IS NULL)
                  AND (AGE(NOW(), p.birthday) > INTERVAL '1 year' * ?::integer OR ?::integer IS NULL)
                """;

        Message response = new Message("RESPONSE");
        ArrayNode passengers = response.getContent().putArray("results");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;
            
            stmt.setObject(index++, routeId);
            stmt.setObject(index++, routeId);
            stmt.setObject(index++, excludedStatuses != null ? connection.createArrayOf("INTEGER", excludedStatuses) : null);
            stmt.setString(index++, statusDate);
            stmt.setObject(index++, excludedStatuses);
            stmt.setString(index++, statusDate);
            stmt.setObject(index++, luggage);
            stmt.setObject(index++, luggage);
            stmt.setString(index++, gender);
            stmt.setString(index++, gender);
            stmt.setObject(index++, ageYears);
            stmt.setObject(index, ageYears);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    //int id = rs.getInt("id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String birthday = rs.getString("birthday");
                    
                    ObjectNode rowData = passengers.addObject();

                    //rowData.put("id", id);
                    rowData.put("first_name", firstName);
                    rowData.put("last_name", lastName);
                    rowData.put("birthday", birthday);
                }
            }
        }

        return response;
    }

    public static Message getCount(
        Integer flightId,
        String statusDate,
        Integer routeId,
        Integer ticketPrice,
        Integer ageYears,
        String gender,
        Connection connection
    ) throws SQLException {
        String sql = """
                SELECT COALESCE(COUNT(t.id), 0) AS ticket_count
                FROM "Tickets" t
                LEFT JOIN "Flights" f ON t.flight_id = f.id
                LEFT JOIN "Passengers" p ON t.passenger_id = p.id
                WHERE t.status = ?::integer
                  AND (t.flight_id = ?::integer OR ?::integer IS NULL)
                  AND (t.status_date > ?::date OR ?::date IS NULL)
                  AND (f.route_id = ?::integer OR ?::integer IS NULL)
                  AND (f.ticket_price > ?::integer OR ?::integer IS NULL)
                  AND (AGE(NOW(), p.birthday) > INTERVAL '1 year' * ?::integer OR ?::integer IS NULL)
                  AND (p.gender::text = ?::text OR ?::text IS NULL)
                """;

        Message response = new Message("RESPONSE");
        ArrayNode results = response.getContent().putArray("results");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;
            
            stmt.setInt(index++, 3);
            stmt.setObject(index++, flightId);
            stmt.setObject(index++, flightId);
            stmt.setString(index++, statusDate);
            stmt.setString(index++, statusDate);
            stmt.setObject(index++, routeId);
            stmt.setObject(index++, routeId);
            stmt.setObject(index++, ticketPrice);
            stmt.setObject(index++, ticketPrice);
            stmt.setObject(index++, ageYears);
            stmt.setObject(index++, ageYears);
            stmt.setString(index++, gender);
            stmt.setString(index, gender);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    long count = rs.getLong("ticket_count");

                    ObjectNode rowData = results.addObject();
                    rowData.put("ticket_count", count);
                    return response;
                }

                ObjectNode rowData = results.addObject();
                rowData.put("ticket_count", 0);
                return response;
            }
        }
    }

    public static Message getFreePlaces(
        Integer flightId,
        String departureDate,
        Integer routeId,
        Integer ticketPrice,
        String departureTime,
        Connection connection
    ) throws SQLException {
        String sql = """
                SELECT f.id, f.max_tickets_count - COALESCE(COUNT(CASE WHEN t.status = ANY(?) THEN t.id END), 0) AS available_seats
                FROM "Flights" f
                LEFT JOIN "Tickets" t ON f.id = t.flight_id
                WHERE (f.id = ?::integer OR ?::integer IS NULL)
                  AND (f.departure_date > ?::date OR ?::date IS NULL)
                  AND (f.route_id = ?::integer OR ?::integer IS NULL)
                  AND (f.ticket_price > ?::integer OR ?::integer IS NULL)
                  AND (f.departure_time > ?::time OR ?::time IS NULL)
                GROUP BY f.id, f.max_tickets_count
                """;

        Message response = new Message("RESPONSE");
        ArrayNode availabilities = response.getContent().putArray("results");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;
            
            stmt.setArray(index++, connection.createArrayOf("INTEGER", new Integer[]{1,2,4}));
            stmt.setObject(index++, flightId);
            stmt.setObject(index++, flightId);
            stmt.setString(index++, departureDate);
            stmt.setString(index++, departureDate);
            stmt.setObject(index++, routeId);
            stmt.setObject(index++, routeId);
            stmt.setObject(index++, ticketPrice);
            stmt.setObject(index++, ticketPrice);
            stmt.setString(index++, departureTime);
            stmt.setString(index, departureTime);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    long availableSeats = rs.getLong("available_seats");

                    ObjectNode rowData = availabilities.addObject();
                    rowData.put("id", id);
                    rowData.put("available_seats", availableSeats);
                }
            }
        }

        return response;
    }

    public static Message getNotFreePlaces(
        Integer flightId,
        String departureDate,
        Integer routeId,
        Integer ticketPrice,
        String departureTime,
        Connection connection
    ) throws SQLException {
        String sql = """
                SELECT f.id, COALESCE(COUNT(t.id), 0) AS ticket_count
                FROM "Tickets" t
                JOIN "Flights" f ON t.flight_id = f.id
                WHERE t.status = ?::integer
                  AND (t.flight_id = ?::integer OR ?::integer IS NULL)
                  AND (f.departure_date > ?::date OR ?::date IS NULL)
                  AND (f.route_id = ?::integer OR ?::integer IS NULL)
                  AND (f.ticket_price > ?::integer OR ?::integer IS NULL)
                  AND (f.departure_time > ?::time OR ?::time IS NULL)
                GROUP BY f.id
                """;

        Message response = new Message("RESPONSE");
        ArrayNode availabilities = response.getContent().putArray("results");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;

            stmt.setObject(index++, 1);
            stmt.setObject(index++, flightId);
            stmt.setObject(index++, flightId);
            stmt.setString(index++, departureDate);
            stmt.setString(index++, departureDate);
            stmt.setObject(index++, routeId);
            stmt.setObject(index++, routeId);
            stmt.setObject(index++, ticketPrice);
            stmt.setObject(index++, ticketPrice);
            stmt.setString(index++, departureTime);
            stmt.setString(index, departureTime);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    long availableSeats = rs.getLong("ticket_count");

                    ObjectNode rowData = availabilities.addObject();
                    rowData.put("id", id);
                    rowData.put("ticket_count", availableSeats);
                }
            }
        }

        return response;
    }

    public static Message addEmployee(
        String lastName,
        String firstName,
        String patronymic,
        String gender,
        Integer childCount,
        Integer positionId,
        Integer brigadeId,
        Integer salary,
        String birthday,
        Boolean director,
        String employment,
        Connection connection
    ) throws SQLException {
        String sql = """
            INSERT INTO "Employees"(last_name, first_name, patronymic, gender, child_count, position_id, brigade_id, salary, birthday, director, employment)
            VALUES (?::text, ?::text, ?::text, ?::enum_gender, ?::integer, ?::integer, ?::integer, ?::integer, ?::integer, ?::boolean, ?::integer)
            """;

        Message response = new Message("RESPONSE");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;

            stmt.setString(index++, lastName);
            stmt.setString(index++, firstName);
            stmt.setString(index++, patronymic);
            stmt.setString(index++, gender);
            stmt.setObject(index++, childCount);
            stmt.setObject(index++, positionId);
            stmt.setObject(index++, brigadeId);
            stmt.setObject(index++, salary);
            stmt.setObject(index++, birthday);
            stmt.setObject(index++, director);
            stmt.setObject(index, employment);
            stmt.executeUpdate();

            ObjectNode rowData = response.getContent().putObject("employee");
            rowData.put("last_name", lastName);
            rowData.put("first_name", firstName);
            rowData.put("patronymic", patronymic);
        }

        return response;
    }

    public static Message deleteEmployee(
        Integer employeeId,
        Connection connection
    ) throws SQLException {
        String sql = """
            DELETE FROM "Employees" WHERE id = ?::integer
            """;

        Message response = new Message("RESPONSE");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, employeeId);
            stmt.executeUpdate();

            ObjectNode rowData = response.getContent().putObject("employee");
            rowData.put("id", employeeId);
        }

        return response;
    }

    public static Message updateEmployee(
            Integer employeeId,
            String lastName,
            String firstName,
            String patronymic,
            String gender,
            Integer childCount,
            Integer positionId,
            Integer brigadeId,
            Integer salary,
            String birthday,
            Boolean director,
            String employment,
            Connection connection
    ) throws SQLException {
        String sql = """
            UPDATE "Employees" SET last_name = ?::text,
                first_name = ?::text, patronymic = ?::text,
                gender = ?::enum_gender, child_count = ?::integer,
                position_id = ?::integer, brigade_id = ?::integer,
                salary = ?::integer, birthday = ?::date,
                director = ?::boolean, employment = ?::integer
            WHERE id = ?::integer
            """;

        Message response = new Message("RESPONSE");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;

            stmt.setString(index++, lastName);
            stmt.setString(index++, firstName);
            stmt.setString(index++, patronymic);
            stmt.setString(index++, gender);
            stmt.setObject(index++, childCount);
            stmt.setObject(index++, positionId);
            stmt.setObject(index++, brigadeId);
            stmt.setObject(index++, salary);
            stmt.setString(index++, birthday);
            stmt.setObject(index++, director);
            stmt.setObject(index++, employment);
            stmt.setObject(index, employeeId);
            stmt.executeUpdate();

            ObjectNode rowData = response.getContent().putObject("employee");

            rowData.put("last_name", lastName);
            rowData.put("first_name", firstName);
            rowData.put("patronymic", patronymic);
        }

        return response;
    }

    public static Message addPlane(
            String model,
            Integer brigadeId,
            Integer pilotBrigadeId,
            Integer engineerBrigadeId,
            Integer status,
            String creationDate,
            String lastCheckupDate,
            Integer flightsCount,
            Integer repairsCount,
            Integer flightsCountAfterLastRepair,
            Integer capacity,
            Connection connection
    ) throws SQLException {
        String sql = """
            INSERT INTO "Planes"(model, brigade_id, pilot_brigade_id, engineer_brigade_id, status, creation_date, last_checkup_date, flights_count, repairs_count, flights_count_after_last_repair, capacity)
            VALUES (?::text, ?::integer, ?::integer, ?::integer, ?::integer, ?::date, ?::date, ?::integer, ?::integer, ?::integer, ?::integer)
            """;

        Message response = new Message("RESPONSE");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;

            stmt.setString(index++, model);
            stmt.setObject(index++, brigadeId);
            stmt.setObject(index++, pilotBrigadeId);
            stmt.setObject(index++, engineerBrigadeId);
            stmt.setObject(index++, status);
            stmt.setString(index++, creationDate);
            stmt.setString(index++, lastCheckupDate);
            stmt.setObject(index++, flightsCount);
            stmt.setObject(index++, repairsCount);
            stmt.setObject(index++, flightsCountAfterLastRepair);
            stmt.setObject(index, capacity);
            stmt.executeUpdate();

            ObjectNode rowData = response.getContent().putObject("plane");
            rowData.put("model", model);
            rowData.put("brigade_id", brigadeId);
            rowData.put("pilot_brigade_id", pilotBrigadeId);
            rowData.put("engineer_brigade_id", engineerBrigadeId);
            rowData.put("status", status);
        }

        return response;
    }
    
    public static Message deletePlane(
            Integer planeId,
            Connection connection
    ) throws SQLException {
        String sql = """
            DELETE FROM "Planes" WHERE id = ?::integer
            """;

        Message response = new Message("RESPONSE");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, planeId);
            stmt.executeUpdate();

            ObjectNode rowData = response.getContent().putObject("plane");
            rowData.put("id", planeId);
        }

        return response;
    }

    public static Message updatePlane(
            Integer planeId,
            String model,
            Integer brigadeId,
            Integer pilotBrigadeId,
            Integer engineerBrigadeId,
            Integer status,
            String creationDate,
            String lastCheckupDate,
            Integer flightsCount,
            Integer repairsCount,
            Integer flightsCountAfterLastRepair,
            Integer capacity,
            Connection connection
    ) throws SQLException {
        String sql = """
            UPDATE "Planes" SET model = ?::text,
                brigade_id = ?::integer, pilot_brigade_id = ?::integer,
                engineer_brigade_id = ?::integer, status = ?::integer,
                creation_date = ?::date, last_checkup_date = ?::date,
                flights_count = ?::integer, repairs_count = ?::integer,
                flights_count_after_last_repair = ?::integer, capacity = ?::integer
            WHERE id = ?::integer
            """;

        Message response = new Message("RESPONSE");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;

            stmt.setString(index++, model);
            stmt.setObject(index++, brigadeId);
            stmt.setObject(index++, pilotBrigadeId);
            stmt.setObject(index++, engineerBrigadeId);
            stmt.setObject(index++, status);
            stmt.setString(index++, creationDate);
            stmt.setString(index++, lastCheckupDate);
            stmt.setObject(index++, flightsCount);
            stmt.setObject(index++, repairsCount);
            stmt.setObject(index++, flightsCountAfterLastRepair);
            stmt.setObject(index++, capacity);
            stmt.setObject(index, planeId);
            stmt.executeUpdate();

            ObjectNode rowData = response.getContent().putObject("plane");
            rowData.put("model", model);
        }

        return response;
    }

    public static Message addFlight(
            Integer planeId,
            Integer routeId,
            String departureTime,
            String arrivalTime,
            Integer ticketPrice,
            Integer status,
            Integer maxTicketsCount,
            Connection connection
    ) throws SQLException {
        String sql = """
                INSERT INTO "Flights"(plane_id, route_id, departure_time, arrival_time, ticket_price, status, max_tickets_count, flight_type, departure_date, min_ticket_count)
                VALUES (?::integer, ?::integer, ?::timestamp, ?::timestamp, ?::integer, ?::integer, ?::integer, ?::integer, ?::date, ?::integer)
                """;

        Message response = new Message("RESPONSE");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;

            stmt.setObject(index++, planeId);
            stmt.setObject(index++, routeId);
            stmt.setString(index++, departureTime);
            stmt.setString(index++, arrivalTime);
            stmt.setObject(index++, ticketPrice);
            stmt.setObject(index++, status);
            stmt.setObject(index++, maxTicketsCount);
            stmt.setObject(index++, 1);
            stmt.setString(index++, departureTime);
            stmt.setObject(index, 0);

            ObjectNode rowData = response.getContent().putObject("flight");
            rowData.put("plane_id", planeId);
            rowData.put("route_id", routeId);
        }

        return response;
    }
    
    public static Message deleteFlight(
            Integer flightId,
            Connection connection
    ) throws SQLException {
        String sql = """
                DELETE FROM "Flights" WHERE id = ?::integer
                """;

        Message response = new Message("RESPONSE");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, flightId);
            stmt.executeUpdate();

            ObjectNode rowData = response.getContent().putObject("flight");
            rowData.put("id", flightId);
        }

        return response;
    }

    public static Message updateFlight(
            Integer flightId,
            Integer planeId,
            Integer routeId,
            String departureTime,
            String arrivalTime,
            Integer ticketPrice,
            Integer status,
            Integer maxTicketsCount,
            Connection connection
    ) throws SQLException {
        String sql = """
                UPDATE "Flights" SET plane_id = ?::integer,
                    route_id = ?::integer, departure_time = ?::timestamp,
                    arrival_time = ?::timestamp, ticket_price = ?::integer,
                    status = ?::integer, max_tickets_count = ?::integer
                WHERE id = ?::integer
                """;

        Message response = new Message("RESPONSE");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;

            stmt.setObject(index++, planeId);
            stmt.setObject(index++, routeId);
            stmt.setString(index++, departureTime);
            stmt.setString(index++, arrivalTime);
            stmt.setObject(index++, ticketPrice);
            stmt.setObject(index++, status);
            stmt.setObject(index++, maxTicketsCount);
            stmt.setObject(index, flightId);
            stmt.executeUpdate();

            ObjectNode rowData = response.getContent().putObject("flight");
            rowData.put("plane_id", planeId);
        }

        return response;
    }

    public static Message addPassenger(
            String firstName,
            String lastName,
            String patronymic,
            String gender,
            String birthday,
            String passportNumber,
            Boolean hasVisa,
            Connection connection
    ) throws SQLException {
        String sql = """
                INSERT INTO "Passengers"(first_name, last_name, patronymic, gender, birthday, passport_number, has_visa)
                VALUES (?::text, ?::text, ?::text, ?::enum_gender, ?::date, ?::text, ?::boolean)
                """;
        
        Message response = new Message("RESPONSE");
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;

            stmt.setString(index++, firstName);
            stmt.setString(index++, lastName);
            stmt.setString(index++, patronymic);
            stmt.setString(index++, gender);
            stmt.setString(index++, birthday);
            stmt.setString(index++, passportNumber);
            stmt.setObject(index++, hasVisa);
            stmt.setObject(index, hasVisa);
            stmt.executeUpdate();

            ObjectNode rowData = response.getContent().putObject("passenger");

            rowData.put("first_name", firstName);
            rowData.put("last_name", lastName);
            rowData.put("patronymic", patronymic);
        }
        
        return response;
    }
    
    public static Message deletePassenger(
            Integer passengerId,
            Connection connection
    ) throws SQLException {
        String sql = """
                DELETE FROM "Passengers" WHERE id = ?::integer
                """;

        Message response = new Message("RESPONSE");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, passengerId);
            stmt.executeUpdate();

            ObjectNode rowData = response.getContent().putObject("passenger");
            rowData.put("id", passengerId);
        }

        return response;
    }

    public static Message updatePassenger(
            Integer passengerId,
            String firstName,
            String lastName,
            String patronymic,
            String gender,
            String birthday,
            String passportNumber,
            Boolean hasVisa,
            Connection connection
    ) throws SQLException {
        String sql = """
                UPDATE "Passengers" SET first_name = ?::text,
                    last_name = ?::text, patronymic = ?::text,
                    gender = ?::text, birthday = ?::date,
                    passport_number = ?::text, has_visa = ?::boolean
                WHERE id = ?::integer
                """;

        Message response = new Message("RESPONSE");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;

            stmt.setString(index++, firstName);
            stmt.setString(index++, lastName);
            stmt.setString(index++, patronymic);
            stmt.setString(index++, gender);
            stmt.setString(index++, birthday);
            stmt.setString(index++, passportNumber);
            stmt.setObject(index++, hasVisa);
            stmt.setObject(index, passengerId);
            stmt.executeUpdate();

            ObjectNode rowData = response.getContent().putObject("passenger");

            rowData.put("first_name", firstName);
            rowData.put("last_name", lastName);
            rowData.put("patronymic", patronymic);
        }

        return response;
    }

    public static Message addTicket(
            Integer flightId,
            Integer passengerId,
            String statusDate,
            Integer status,
            Boolean luggage,
            Connection connection
    ) throws SQLException {
        String sql = """
                INSERT INTO "Tickets"(flight_id, passenger_id, status_date, status, luggage)
                VALUES (?::integer, ?::integer, ?::date, ?::integer, ?::boolean)
                """;

        Message response = new Message("RESPONSE");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;

            stmt.setObject(index++, flightId);
            stmt.setObject(index++, passengerId);
            stmt.setString(index++, statusDate);
            stmt.setObject(index++, status);
            stmt.setObject(index, luggage);

            ObjectNode rowData = response.getContent().putObject("ticket");
            rowData.put("flight_id", flightId);
            rowData.put("passenger_id", passengerId);
        }

        return response;
    }
    
    public static Message deleteTicket(
            Integer ticketId,
            Connection connection
    ) throws SQLException {
        String sql = """
                DELETE FROM "Tickets" WHERE id = ?::integer
                """;

        Message response = new Message("RESPONSE");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, ticketId);
            stmt.executeUpdate();

            ObjectNode rowData = response.getContent().putObject("ticket");
            rowData.put("id", ticketId);
        }

        return response;
    }

    public static Message updateTicket(
            Integer ticketId,
            Integer flightId,
            Integer passengerId,
            String statusDate,
            Integer status,
            Boolean luggage,
            Connection connection
    ) throws SQLException {
        String sql = """
                UPDATE "Tickets" SET flight_id = ?::integer,
                    passenger_id = ?::integer, status_date = ?::date,
                    status = ?::integer, luggage = ?::boolean
                WHERE id = ?::integer
                """;

        Message response = new Message("RESPONSE");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;

            stmt.setObject(index++, flightId);
            stmt.setObject(index++, passengerId);
            stmt.setString(index++, statusDate);
            stmt.setObject(index++, status);
            stmt.setObject(index++, luggage);
            stmt.setObject(index, ticketId);
            stmt.executeUpdate();

            ObjectNode rowData = response.getContent().putObject("ticket");
            rowData.put("flight_id", flightId);
        }

        return response;
    }
}
