package ru.nsu.db_proj.server;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.nsu.db_proj.Message;
import ru.nsu.db_proj.queries.QExecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ClientThread implements Runnable {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String PASSWORD = "wasd1234";
    private static final String USER = "postgres";

    private final Socket clientSocket;
    private Connection connection;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            this.connection = connection;
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                Message request = Message.fromJson(inputLine);
                processRequest(request, out);
            }
        } catch (Exception e) {
            System.err.println("Error processing client request: " + e.getMessage());
        } finally {
            try {
                if (this.connection != null && !this.connection.isClosed()) {
                    this.connection.close();
                }
                if (clientSocket != null && !clientSocket.isClosed()) {
                    clientSocket.close();
                }
            } catch (IOException | SQLException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    private void processRequest(Message request, PrintWriter out) {
        switch (request.getType()) {
            case "GET_DEPARTMENT_TYPES": {
                processDepartmentRequest(out);
                break;
            }
            case "GET_BRIGADE_TYPES": {
                processBrigadeRequest(out);
                break;
            }
            case "GET_ALL_FLIGHTS": {
                processAllFlightsRequest(out);
                break;
            }
            case "GET_AIRPORT_TYPES": {
                processAllPlacesRequest(out);
                break;
            }
            case "GET_ALL_ARRIVAL_AIRPORTS": {
                processAllArrivalAirportsRequest(out);
                break;
            }
            case "GET_ALL_ROUTES": {
                processAllRoutesRequest(out);
                break;
            }
            case "GET_ALL_FLIGHT_STATUSES": {
                processAllFlightStatusesRequest(out);
                break;
            }
            case "GET_ALL_DELAYS": {
                processAllDelays(out);
                break;
            }
            case "GET_ALL_MODELS": {
                processAllModels(out);
                break;
            }
            case "GET_ALL_FLIGHT_TYPES": {
                processAllTypes(out);
                break;
            }
            case "GET_DEPARTMENT": {
                processGetDepartmentRequest(request, out);
                break;
            }
            case "GET_BRIGADE": {
                processGetBrigadeRequest(request, out);
                break;
            }
            case "GET_PILOT": {
                processGetPilot(request, out);
                break;
            }
            case "GET_PLANE": {
                processGetPlane(request, out);
                break;
            }
            case "GET_REPAIR": {
                processGetRepair(request, out);
                break;
            }
            case "GET_FLIGHT_1": {
                processGetFlight1(request, out);
                break;
            }
            case "GET_FLIGHT_2": {
                processGetFlight2(request, out);
                break;
            }
            case "GET_FLIGHT_3": {
                processGetFlight3(request, out);
                break;
            }
            case "GET_FLIGHT_4": {
                processGetFlight4(request, out);
                break;
            }
            case "GET_FLIGHT_5": {
                processGetFlight5(request, out);
                break;
            }
            case "GET_CALC": {
                processCalc(request, out);
                break;
            }
            case "GET_PASSENGER": {
                processGetPassenger(request, out);
                break;
            }
            case "GET_COUNT": {
                processCount(request, out);
                break;
            }
            case "GET_FREE_PLACES": {
                processFreePlaces(request, out);
                break;
            }
            case "ADD_EMPLOYEE": {
                processAddEmployee(request, out);
                break;
            }
            case "ADD_PLANE": {
                processAddPlane(request, out);
                break;
            }
            case "ADD_FLIGHT": {
                processAddFlight(request, out);
                break;
            }
            case "ADD_PASSENGER": {
                processAddPassenger(request, out);
                break;
            }
            case "ADD_TICKET": {
                processAddTicket(request, out);
                break;
            }
            default: {
                System.err.println("Unknown request type: " + request.getType());
                try {
                    out.println(new Message("ERROR").toJson());
                } catch (Exception e) {
                    System.err.println("Error sending error response: " + e.getMessage());
                }
            }
        }
    }

    private void processAddTicket(Message request, PrintWriter out) {
        ObjectNode payload = request.getContent();

        int flightId = payload.get("flight_id").asInt();
        int passengerId = payload.get("passenger_id").asInt();
        String statusDate = payload.get("status_date").asText();
        int status = payload.get("status").asInt();
        boolean luggage = payload.get("luggage").asBoolean();

        try {
            Message results = QExecutor.addTicket(
                    flightId,
                    passengerId,
                    statusDate,
                    status,
                    luggage,
                    connection
            );

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processAddPassenger(Message request, PrintWriter out) {
        ObjectNode payload = request.getContent();

        String firstName = payload.get("first_name").asText();
        String lastName = payload.get("last_name").asText();
        String patronymic = payload.get("patronymic").asText();
        String gender = payload.get("gender").asText();
        String birthDate = payload.get("birth_date").asText();
        String passport = payload.get("passport").asText();
        boolean hasVisa = payload.get("has_visa").asBoolean();

        try {
            Message results = QExecutor.addPassenger(
                    firstName,
                    lastName,
                    patronymic,
                    gender,
                    birthDate,
                    passport,
                    hasVisa,
                    connection
            );

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processAddFlight(Message request, PrintWriter out) {
        ObjectNode payload = request.getContent();

        int routeId = payload.get("route_id").asInt();
        int planeId = payload.get("plane_id").asInt();
        String departureTime = payload.get("departure_time").asText();
        String arrivalTime = payload.get("arrival_time").asText();
        int status = payload.get("status").asInt();
        int ticketPrice = payload.get("ticket_price").asInt();
        int maxTickets = payload.get("max_tickets").asInt();

        try {
            Message results = QExecutor.addFlight(
                    routeId,
                    planeId,
                    departureTime,
                    arrivalTime,
                    status,
                    ticketPrice,
                    maxTickets,
                    connection
            );

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processAddPlane(Message request, PrintWriter out) {
        ObjectNode payload = request.getContent();

        String model = payload.get("model").asText();
        int brigadeId = payload.get("brigade_id").asInt();
        int pilot_brigadeId = payload.get("pilot_brigade_id").asInt();
        int engineer_brigadeId = payload.get("engineer_brigade_id").asInt();
        int status = payload.get("status").asInt();
        String creationDate = payload.get("creation_date").asText();
        String lastCheckupDate = payload.get("last_checkup_date").asText();
        int flightCount = payload.get("flight_count").asInt();
        int repairCount = payload.get("repair_count").asInt();
        int flightCountAfterLRepair = payload.get("flight_count_after_last_repair").asInt();
        int capacity = payload.get("capacity").asInt();

        try {
            Message results = QExecutor.addPlane(
                    model,
                    brigadeId,
                    pilot_brigadeId,
                    engineer_brigadeId,
                    status,
                    creationDate,
                    lastCheckupDate,
                    flightCount,
                    repairCount,
                    flightCountAfterLRepair,
                    capacity,
                    connection
            );

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processAddEmployee(Message request, PrintWriter out) {
        ObjectNode payload = request.getContent();

        String lastName = payload.get("last_name").asText();
        String firstName = payload.get("first_name").asText();
        String patronymic = payload.get("patronymic").asText();
        String gender = payload.get("gender").asText();
        int childCount = payload.get("child_count").asInt();
        int positionId = payload.get("position_id").asInt();
        int brigadeId = payload.get("brigade_id").asInt();
        int salary = payload.get("salary").asInt();
        String birthDate = payload.get("birthday").asText();
        boolean director = payload.get("director").asBoolean();
        String employmentDate = payload.get("employment").asText();

        try {
            Message results = QExecutor.addEmployee(
                    lastName,
                    firstName,
                    patronymic,
                    gender,
                    childCount,
                    positionId,
                    brigadeId,
                    salary,
                    birthDate,
                    director,
                    employmentDate,
                    connection
            );

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processGetPassenger(Message request, PrintWriter out) {
        ObjectNode payload = request.getContent();

        int routeId = payload.get("route_id").asInt();
        JsonNode statusDate = payload.get("status_date");
        String statusDateStr = statusDate.isNull() ? null : statusDate.asText();
        int luggage = payload.get("luggage").asInt();
        JsonNode genderNode = payload.get("gender");
        String genderStr = genderNode.isNull() ? null : genderNode.asText();
        int age = payload.get("age").asInt();

        try {
            Message results = QExecutor.getPassenger(
                    routeId == -1 ? null : routeId,
                    statusDateStr,
                    new Integer[]{3,5},
                    luggage == 0 ? null : true,
                    genderStr,
                    age == -1 ? null : age,
                    connection
            );

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processCalc(Message request, PrintWriter out) {
        ObjectNode payload = request.getContent();

        int countOfArray = payload.get("count").asInt();
        Integer[] array = new Integer[countOfArray];

        for (int i = 0; i < countOfArray; i++) {
            array[i] = payload.get("routes").get(i).asInt();
        }

        try {
            Message results = QExecutor.getCalc(
                    countOfArray,
                    array,
                    connection
            );

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processGetFlight5(Message request, PrintWriter out) {
        ObjectNode payload = request.getContent();

        int flightType = payload.get("category").asInt();
        JsonNode planeIdNode = payload.get("model");
        String planeIdStr = planeIdNode.isNull() ? null : planeIdNode.asText();

        try {
            Message results = QExecutor.getFlight5(
                    flightType == -1 ? null : flightType,
                    planeIdStr,
                    connection
            );

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processGetFlight4(Message request, PrintWriter out) {
        ObjectNode payload = request.getContent();

        JsonNode planeIdNode = payload.get("model");
        String planeIdStr = planeIdNode.isNull() ? null : planeIdNode.asText();
        JsonNode departureTime = payload.get("departure_time");
        String departureTimeStr = departureTime.isNull() ? null : departureTime.asText();

        try {
            Message results = QExecutor.getFlight4(
                    planeIdStr,
                    departureTimeStr,
                    connection
            );

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processGetFlight3(Message request, PrintWriter out) {
        ObjectNode payload = request.getContent();

        int status = payload.get("status").asInt();
        int routeId = payload.get("route_id").asInt();
        int returnTicket = payload.get("return_ticket").asInt();

        Integer statusObj;
        Integer[] statusArray;

        if (status == -2) {
            statusObj = null;
            statusArray = new Integer[]{1, 2};
        } else if (status == -1) {
            statusObj = null;
            statusArray = null;
        } else {
            statusObj = status;
            statusArray = null;
        }

        try {
            Message results = QExecutor.getFlight3(
                    statusObj,
                    routeId == -1 ? null : routeId,
                    3,
                    statusArray,
                    connection,
                    returnTicket == 1
            );

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processGetFlight2(Message request, PrintWriter out) {
        ObjectNode payload = request.getContent();

        int status = payload.get("status").asInt();
        int routeId = payload.get("route_id").asInt();
        int maxTickets = payload.get("max_tickets").asInt();
        double ticketRatio = payload.get("ticket_ratio").asDouble();

        try {
            Message results = QExecutor.getFlight2(
                    status == -1 ? null : status,
                    routeId == -1 ? null : routeId,
                    maxTickets == -1 ? null : maxTickets,
                    ticketRatio == -1 ? null : ticketRatio,
                    connection
            );

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processGetFlight1(Message request, PrintWriter out) {
        ObjectNode payload = request.getContent();

        int routeId = payload.get("route_id").asInt();
        int duration = payload.get("duration").asInt();
        int ticketPrice = payload.get("ticket_price").asInt();

        try {
            Message results = QExecutor.getFlight1(
                    routeId == -1 ? null : routeId,
                    duration == -1 ? null : duration,
                    ticketPrice == -1 ? null : ticketPrice,
                    connection
            );

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processGetRepair(Message request, PrintWriter out) {
        ObjectNode payload = request.getContent();

        JsonNode repairNode = payload.get("start_date");
        String repairDate = repairNode.isNull() ? null : repairNode.asText();
        JsonNode repairEndNode = payload.get("end_date");
        String repairEndDate = repairEndNode.isNull() ? null : repairEndNode.asText();
        int repairStatus = payload.get("status").asInt();
        int repairCount = payload.get("repair_count").asInt();
        int flightsDifferent = payload.get("flights_different").asInt();
        int age = payload.get("age").asInt();

        try {
            Message results = QExecutor.getRepair(
                    repairDate,
                    repairEndDate,
                    repairStatus == 0 ? null : repairStatus,
                    repairCount == -1 ? null : repairCount,
                    flightsDifferent == -1 ? null : flightsDifferent,
                    age == -1 ? null : age,
                    connection
            );

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processGetPlane(Message request, PrintWriter out) {
        ObjectNode payload = request.getContent();

        JsonNode placeNode = payload.get("place");
        String place = placeNode.isNull() ? null : placeNode.asText();
        JsonNode departureAirportNode = payload.get("departure_airport");
        String departureAirport = departureAirportNode.isNull() ? null : departureAirportNode.asText();
        JsonNode excludeTimeNode = payload.get("exclude_time");
        String excludeTime = excludeTimeNode.isNull() ? null : excludeTimeNode.asText();
        JsonNode arrivalTimeNode = payload.get("arrival_time");
        String arrivalTime = arrivalTimeNode.isNull() ? null : arrivalTimeNode.asText();

        int flightCount = payload.get("flights_count").asInt();

        try {
            Message results = QExecutor.getPlane(
                    place,
                    departureAirport,
                    excludeTime,
                    arrivalTime,
                    flightCount == -1 ? null : flightCount,
                    connection
            );

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processGetPilot(Message request, PrintWriter out) {
        ObjectNode payload = request.getContent();

        int medicalCheckYear = payload.get("med_year").asInt();
        int hasMedicalCheck, noMedicalCheck;

        switch (payload.get("status").asInt()) {
            case 0 -> {
                hasMedicalCheck = -1;
                noMedicalCheck = 1;
            }
            case 1 -> {
                hasMedicalCheck = 1;
                noMedicalCheck = -1;
            }
            default -> {
                hasMedicalCheck = -1;
                noMedicalCheck = -1;
            }
        }

        JsonNode genderNode = payload.get("gender");
        String gender = genderNode.isNull() ? null : genderNode.asText();

        int ageYearsInt = payload.get("age_years").asInt();
        int salary = payload.get("salary").asInt();
        int positionId = 1;

        try {
            Message results = QExecutor.getPilot(
                    medicalCheckYear == -1 ? null : medicalCheckYear,
                    hasMedicalCheck == -1 ? null : hasMedicalCheck,
                    noMedicalCheck == -1 ? null : noMedicalCheck,
                    gender,
                    ageYearsInt == -1 ? null : ageYearsInt,
                    salary == -1 ? null : salary,
                    positionId,
                    connection
            );

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processGetBrigadeRequest(Message request, PrintWriter out) {
        ObjectNode payload = request.getContent();

        int brigadeId = payload.get("brigade_id").asInt();
        int flightId = payload.get("flight_id").asInt();
        int filter = payload.get("salary").asInt();

        try {
            Message results = QExecutor.getBrigade(
                    brigadeId == -1 ? null : brigadeId,
                    flightId == -1 ? null : flightId,
                    filter == 1 ? 1 : null,
                    connection
            );

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processGetDepartmentRequest(Message request, PrintWriter out) {
        ObjectNode payload = request.getContent();

        int departmentId = payload.get("department_id").asInt();
        int director = payload.get("director").asInt();
        int age = payload.get("age").asInt();
        int child = payload.get("child").asInt();
        int employment = payload.get("employment").asInt();
        int salary = payload.get("salary").asInt();

        JsonNode genderNode = payload.get("gender");
        String gender = genderNode.isNull() ? null : genderNode.asText();

        try {
            Message results = QExecutor.getEmployees(
                    director == -1 ? null : director == 1,
                    departmentId == -1 ? null : departmentId,
                    employment == -1 ? null : employment,
                    gender,
                    age == -1 ? null : age,
                    child == -1 ? null : child,
                    salary == -1 ? null : salary,
                    connection
            );

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processDepartmentRequest(PrintWriter out) {
        try {
            Message results = QExecutor.getAllDepartmentTypes(connection);

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processBrigadeRequest(PrintWriter out) {
        try {
            Message results = QExecutor.getAllBrigadeTypes(connection);

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processAllFlightsRequest(PrintWriter out) {
        try {
            Message results = QExecutor.getAllFlights(connection);

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processAllPlacesRequest(PrintWriter out) {
        try {
            Message results = QExecutor.getAllAirports(connection);

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processAllArrivalAirportsRequest(PrintWriter out) {
        try {
            Message results = QExecutor.getAllArrivalAirports(connection);

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processAllRoutesRequest(PrintWriter out) {
        try {
            Message results = QExecutor.getAllRoutes(connection);

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processAllFlightStatusesRequest(PrintWriter out) {
        try {
            Message results = QExecutor.getAllFlightStatuses(connection);

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processAllDelays(PrintWriter out) {
        try {
            Message results = QExecutor.getAllDelays(connection);

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processAllModels(PrintWriter out) {
        try {
            Message results = QExecutor.getAllModels(connection);

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processAllTypes(PrintWriter out) {
        try {
            Message results = QExecutor.getAllFlightTypes(connection);

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processCount(Message request, PrintWriter out) {
        ObjectNode payload = request.getContent();

        int flightId = payload.get("flight_id").asInt();
        JsonNode statusDate = payload.get("return_date");
        String statusDateStr = statusDate.isNull() ? null : statusDate.asText();
        int routeId = payload.get("route_id").asInt();
        int ticketPrice = payload.get("ticket_price").asInt();
        int age = payload.get("age").asInt();
        JsonNode genderNode = payload.get("gender");
        String genderStr = genderNode.isNull() ? null : genderNode.asText();

        try {
            Message results = QExecutor.getCount(
                    flightId == -1 ? null : flightId,
                    statusDateStr,
                    routeId == -1 ? null : routeId,
                    ticketPrice == -1 ? null : ticketPrice,
                    age == -1 ? null : age,
                    genderStr,
                    connection
            );

            try {
                out.println(results.toJson());
            } catch (Exception e) {
                System.err.println("Error sending response: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            try {
                out.println(new Message("ERROR").toJson());
            } catch (Exception ex) {
                System.err.println("Error sending error response: " + ex.getMessage());
            }
        }
    }

    private void processFreePlaces(Message request, PrintWriter out) {
        ObjectNode payload = request.getContent();

        int choice = payload.get("choice").asInt();
        int flightId = payload.get("flight_id").asInt();
        int routeId = payload.get("route_id").asInt();
        int ticketPrice = payload.get("ticket_price").asInt();
        JsonNode dateNode = payload.get("date");
        String dateStr = dateNode.isNull() ? null : dateNode.asText();
        JsonNode timeNode = payload.get("time");
        String timeStr = timeNode.isNull() ? null : timeNode.asText();

        if (choice == 1) {
            try {
                Message results = QExecutor.getFreePlaces(
                        flightId == -1 ? null : flightId,
                        dateStr,
                        routeId == -1 ? null : routeId,
                        ticketPrice == -1 ? null : ticketPrice,
                        timeStr,
                        connection
                );

                try {
                    out.println(results.toJson());
                } catch (Exception e) {
                    System.err.println("Error sending response: " + e.getMessage());
                }
            } catch (SQLException e) {
                System.err.println("Error executing query: " + e.getMessage());
                try {
                    out.println(new Message("ERROR").toJson());
                } catch (Exception ex) {
                    System.err.println("Error sending error response: " + ex.getMessage());
                }
            }
        } else {
            try {
                Message results = QExecutor.getNotFreePlaces(
                        flightId == -1 ? null : flightId,
                        dateStr,
                        routeId == -1 ? null : routeId,
                        ticketPrice == -1 ? null : ticketPrice,
                        timeStr,
                        connection
                );
                try {
                    out.println(results.toJson());
                } catch (Exception e) {
                    System.err.println("Error sending response: " + e.getMessage());
                }
            } catch (SQLException e) {
                System.err.println("Error executing query: " + e.getMessage());
                try {
                    out.println(new Message("ERROR").toJson());
                } catch (Exception ex) {
                    System.err.println("Error sending error response: " + ex.getMessage());
                }
            }
        }
    }
}
