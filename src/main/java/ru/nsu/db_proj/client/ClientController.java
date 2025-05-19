package ru.nsu.db_proj.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.nsu.db_proj.Message;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.UnaryOperator;

public class ClientController {
    @FXML
    private ListView<String> departmentResponseArea;

    @FXML
    private ChoiceBox<String> departmentChoiceBox;

    @FXML
    private CheckBox departmentDirectorCheckBox;

    @FXML
    private TextField departmentAgeTextField;

    @FXML
    private TextField departmentChildTextField;

    @FXML
    private TextField departmentEmploymentTextField;

    @FXML
    private TextField departmentSalaryTextField;

    @FXML
    private ChoiceBox<String> departmentGenderChoiceBox;

    private final HashMap<String, Integer> departmentTypes = new HashMap<>();
    private final HashMap<String, Integer> flightStatus = new HashMap<>();
    private final HashMap<Integer, String> reverseFlightStatus = new HashMap<>();
    private final HashMap<String, Integer> pilotStatus = new HashMap<>();
    private final HashMap<String, Integer> routes = new HashMap<>();
    private final HashMap<String, Integer> types = new HashMap<>();
    private final Sender sender = new Sender();

    public void setStage(Stage stage) {
        stage.setOnCloseRequest(
                event -> {
                    sender.close();
                    System.exit(0);
                }
        );
    }

    public void updateDepartmentTypes() {
        departmentChoiceBox.getItems().clear();
        departmentChoiceBox.getItems().add("None");

        Message response = sender.sendRequest(new Message("GET_DEPARTMENT_TYPES"));

        if (response == null) {
            System.err.println("Error: No response from server");
            return;
        }

        try {
            ObjectNode payload = response.getContent();
            ArrayNode res = (ArrayNode) payload.get("results");

            if (res.isEmpty()) {
                System.err.println("No department types found");
                return;
            }

            for (int i = 0; i < res.size(); i++) {
                ObjectNode departmentType = (ObjectNode) res.get(i);
                String name = departmentType.get("name").asText();
                int id = departmentType.get("id").asInt();

                departmentTypes.put(name, id);
                departmentChoiceBox.getItems().add(name);
            }
        } catch (Exception e) {
            System.err.println("Error parsing response: " + e.getMessage());
        }
    }

    public void updateBrigadeTypes() {
        brigadeChoiceBox.getItems().clear();
        brigadeChoiceBox.getItems().add("None");

        Message response = sender.sendRequest(new Message("GET_BRIGADE_TYPES"));

        if (response == null) {
            System.err.println("Error: No response from server");
            return;
        }

        try {
            ObjectNode payload = response.getContent();
            ArrayNode res = (ArrayNode) payload.get("results");

            if (res.isEmpty()) {
                System.err.println("No brigade types found");
                return;
            }

            for (int i = 0; i < res.size(); i++) {
                ObjectNode brigadeType = (ObjectNode) res.get(i);
                int id = brigadeType.get("id").asInt();

                brigadeChoiceBox.getItems().add(String.valueOf(id));
            }
        } catch (Exception e) {
            System.err.println("Error parsing response: " + e.getMessage());
        }
    }

    public void updateFlightTypes() {
        List<ChoiceBox<String>> flightChoiceBoxes = List.of(
                brigadeFlightChoiceBox,
                passengerFlightChoiceBox,
                flightReturnChoiceBox,
                flightFreePlaceChoiceBox
        );

        for (ChoiceBox<String> flightChoiceBox : flightChoiceBoxes) {
            flightChoiceBox.getItems().clear();
            flightChoiceBox.getItems().add("None");
        }

        Message response = sender.sendRequest(new Message("GET_ALL_FLIGHTS"));

        if (response == null) {
            System.err.println("Error: No response from server");
            return;
        }

        try {
            ObjectNode payload = response.getContent();
            ArrayNode res = (ArrayNode) payload.get("results");

            if (res.isEmpty()) {
                System.err.println("No flight types found");
                return;
            }

            for (int i = 0; i < res.size(); i++) {
                ObjectNode flightType = (ObjectNode) res.get(i);
                int id = flightType.get("id").asInt();

                for (ChoiceBox<String> flightChoiceBox : flightChoiceBoxes) {
                    flightChoiceBox.getItems().add(String.valueOf(id));
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing response: " + e.getMessage());
        }
    }

    public void updateAirportTypes() {
        List<ChoiceBox<String>> airportChoiceBoxes = List.of(
                planePlaceChoiceBox
        );

        for (ChoiceBox<String> airportChoiceBox : airportChoiceBoxes) {
            airportChoiceBox.getItems().clear();
            airportChoiceBox.getItems().add("None");
        }

        Message response = sender.sendRequest(new Message("GET_AIRPORT_TYPES"));

        if (response == null) {
            System.err.println("Error: No response from server");
            return;
        }

        try {
            ObjectNode payload = response.getContent();
            ArrayNode res = (ArrayNode) payload.get("results");

            if (res.isEmpty()) {
                System.err.println("No airport types found");
                return;
            }

            for (int i = 0; i < res.size(); i++) {
                ObjectNode airportType = (ObjectNode) res.get(i);
                String name = airportType.get("place").asText();

                for (ChoiceBox<String> airportChoiceBox : airportChoiceBoxes) {
                    airportChoiceBox.getItems().add(name);
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing response: " + e.getMessage());
        }

        List<ChoiceBox<String>> airportChoiceBoxes2 = List.of(
                departureCityChoiceBox
        );

        for (ChoiceBox<String> airportChoiceBox : airportChoiceBoxes2) {
            airportChoiceBox.getItems().clear();
            airportChoiceBox.getItems().add("None");
        }

        Message response2 = sender.sendRequest(new Message("GET_ALL_ARRIVAL_AIRPORTS"));

        if (response2 == null) {
            System.err.println("Error: No response from server");
            return;
        }

        try {
            ObjectNode payload = response2.getContent();
            ArrayNode res = (ArrayNode) payload.get("results");

            if (res.isEmpty()) {
                System.err.println("No airport types found");
                return;
            }

            for (int i = 0; i < res.size(); i++) {
                ObjectNode airportType = (ObjectNode) res.get(i);
                String name = airportType.get("arrival_airport").asText();

                for (ChoiceBox<String> airportChoiceBox : airportChoiceBoxes2) {
                    airportChoiceBox.getItems().add(name);
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing response: " + e.getMessage());
        }
    }

    public void updateRoutes() {
        List<ChoiceBox<String>> routesChoiceBoxes = List.of(
                routeChoiceBox,
                flightRouteChoiceBox,
                routeDelayChoiceBox,
                routeReturnChoiceBox,
                routeFreePlaceChoiceBox
        );

        for (ChoiceBox<String> routesChoiceBox : routesChoiceBoxes) {
            routesChoiceBox.getItems().clear();
            routesChoiceBox.getItems().add("None");
        }

        Message response = sender.sendRequest(new Message("GET_ALL_ROUTES"));

        if (response == null) {
            System.err.println("Error: No response from server");
            return;
        }

        try {
            ObjectNode payload = response.getContent();
            ArrayNode res = (ArrayNode) payload.get("results");

            if (res.isEmpty()) {
                System.err.println("No flight types found");
                return;
            }

            for (int i = 0; i < res.size(); i++) {
                ObjectNode routeNode = (ObjectNode) res.get(i);
                String route = routeNode.get("departure_airport").asText() + " -> " + routeNode.get("arrival_airport").asText();
                routes.put(route, routeNode.get("id").asInt());

                for (ChoiceBox<String> routesChoiceBox : routesChoiceBoxes) {
                    routesChoiceBox.getItems().add(route);
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing response: " + e.getMessage());
        }
    }

    public void updateFlightStatuses() {
        List<ChoiceBox<String>> flightStatusChoiceBoxes = List.of(
                flightStatusChoiceBox
        );

        for (ChoiceBox<String> flightStatusChoiceBox : flightStatusChoiceBoxes) {
            flightStatusChoiceBox.getItems().clear();
            flightStatusChoiceBox.getItems().add("None");
        }

        Message response = sender.sendRequest(new Message("GET_ALL_FLIGHT_STATUSES"));

        if (response == null) {
            System.err.println("Error: No response from server");
            return;
        }

        try {
            ObjectNode payload = response.getContent();
            ArrayNode res = (ArrayNode) payload.get("results");

            if (res.isEmpty()) {
                System.err.println("No flight statuses found");
                return;
            }

            for (int i = 0; i < res.size(); i++) {
                ObjectNode flightStatusNode = (ObjectNode) res.get(i);
                String name = flightStatusNode.get("name").asText();
                int id = flightStatusNode.get("id").asInt();

                flightStatus.put(name, id);
                reverseFlightStatus.put(id, name);

                for (ChoiceBox<String> flightStatusChoiceBox : flightStatusChoiceBoxes) {
                    flightStatusChoiceBox.getItems().add(name);
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing response: " + e.getMessage());
        }
    }

    public void updateDelays() {
        List<ChoiceBox<String>> flightStatusChoiceBoxes = List.of(
                flightStatusDelayChoiceBox
        );

        for (ChoiceBox<String> flightStatusChoiceBox : flightStatusChoiceBoxes) {
            flightStatusChoiceBox.getItems().clear();
            flightStatusChoiceBox.getItems().add("None");
            flightStatusChoiceBox.getItems().add("Delayed");
        }

        Message response = sender.sendRequest(new Message("GET_ALL_DELAYS"));

        if (response == null) {
            System.err.println("Error: No response from server");
            return;
        }

        try {
            ObjectNode payload = response.getContent();
            ArrayNode res = (ArrayNode) payload.get("results");

            if (res.isEmpty()) {
                System.err.println("No flight statuses found");
                return;
            }

            for (int i = 0; i < res.size(); i++) {
                ObjectNode flightStatusNode = (ObjectNode) res.get(i);
                int status_id = flightStatusNode.get("status_id").asInt();

                for (ChoiceBox<String> flightStatusChoiceBox : flightStatusChoiceBoxes) {
                    flightStatusChoiceBox.getItems().add(reverseFlightStatus.get(status_id));
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing response: " + e.getMessage());
        }
    }

    public void updateModels() {
        List<ChoiceBox<String>> flightStatusChoiceBoxes = List.of(
                modelsChoiceBox,
                modelsChoice
        );

        for (ChoiceBox<String> flightStatusChoiceBox : flightStatusChoiceBoxes) {
            flightStatusChoiceBox.getItems().clear();
            flightStatusChoiceBox.getItems().add("None");
        }

        Message response = sender.sendRequest(new Message("GET_ALL_MODELS"));

        if (response == null) {
            System.err.println("Error: No response from server");
            return;
        }

        try {
            ObjectNode payload = response.getContent();
            ArrayNode res = (ArrayNode) payload.get("results");

            if (res.isEmpty()) {
                System.err.println("No models found");
                return;
            }

            for (int i = 0; i < res.size(); i++) {
                ObjectNode modelNode = (ObjectNode) res.get(i);
                String name = modelNode.get("model").asText();

                for (ChoiceBox<String> flightStatusChoiceBox : flightStatusChoiceBoxes) {
                    flightStatusChoiceBox.getItems().add(name);
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing response: " + e.getMessage());
        }
    }

    public void updateTypes() {
        List<ChoiceBox<String>> flightCategoryChoiceBoxes = List.of(
                categoryChoiceBox
        );

        for (ChoiceBox<String> flightCategoryChoiceBox : flightCategoryChoiceBoxes) {
            flightCategoryChoiceBox.getItems().clear();
            flightCategoryChoiceBox.getItems().add("None");
        }

        Message response = sender.sendRequest(new Message("GET_ALL_FLIGHT_TYPES"));

        if (response == null) {
            System.err.println("Error: No response from server");
            return;
        }

        try {
            ObjectNode payload = response.getContent();
            ArrayNode res = (ArrayNode) payload.get("results");

            if (res.isEmpty()) {
                System.err.println("No flight types found");
                return;
            }

            for (int i = 0; i < res.size(); i++) {
                ObjectNode flightTypeNode = (ObjectNode) res.get(i);
                String name = flightTypeNode.get("name").asText();
                int id = flightTypeNode.get("id").asInt();

                types.put(name, id);

                for (ChoiceBox<String> flightCategoryChoiceBox : flightCategoryChoiceBoxes) {
                    flightCategoryChoiceBox.getItems().add(name);
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing response: " + e.getMessage());
        }
    }

    @FXML
    private void initialize() {
        List<ChoiceBox<String>> genders = List.of(
                departmentGenderChoiceBox,
                pilotGenderChoiceBox,
                passengerGenderChoiceBox,
                genderReturnChoiceBox
        );

        for (ChoiceBox<String> genderChoiceBox : genders) {
            genderChoiceBox.getItems().add("None");
            genderChoiceBox.getItems().add("man");
            genderChoiceBox.getItems().add("woman");
        }

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            }
            return null;
        };

        List<TextField> textFields = List.of(
                departmentAgeTextField,
                departmentChildTextField,
                departmentEmploymentTextField,
                departmentSalaryTextField,
                pilotMedYearTextField,
                pilotSalaryTextField,
                pilotAgeTextField,
                flightsCountTextField,
                repairCountTextField,
                differenceTextField,
                agePlaneTextField,
                passengerAgeTextField,
                costFreePlaceTextField,
                returnCostTextField,
                ageReturnTextField,
                timeRouteTextField,
                costRouteTextField,
                flightPlacesTextField
        );

        for (TextField textField : textFields) {
            textField.setTextFormatter(new TextFormatter<>(filter));
        }

        pilotStatus.put("None", -1);
        pilotStatus.put("Не прошёл медосмотр", 0);
        pilotStatus.put("Прошёл медосмотр", 1);

        for (String status : pilotStatus.keySet()) {
            pilotStatusChoiceBox.getItems().add(status);
        }

        List<TextField> timeFields = List.of(
                excludedFlightTextField,
                arrivalTimeTextField,
                timeFreePlaceTextField,
                timeDepartTextField
        );

        for (TextField timeField : timeFields) {
            timeField.setTextFormatter(new TextFormatter<>(change -> {
                String text = change.getText();
                if (text.matches("[0-9:]*")) {
                    return change;
                }
                return null;
            }));
        }
    }

    @FXML
    private void onDepartmentGetButtonClick() {
        Message request = new Message("GET_DEPARTMENT");
        ObjectNode payload = request.getContent();

        String selectedDepartment = departmentChoiceBox.getValue();
        if (selectedDepartment == null || selectedDepartment.equals("None")) {
            payload.put("department_id", -1);
        } else {
            payload.put("department_id", departmentTypes.get(selectedDepartment));
        }

        String selectedGender = departmentGenderChoiceBox.getValue();
        if (selectedGender == null || selectedGender.equals("None")) {
            payload.put("gender", (JsonNode) null);
        } else {
            payload.put("gender", selectedGender);
        }

        payload.put("director", departmentDirectorCheckBox.isSelected() ? 1 : 0);
        payload.put("age", departmentAgeTextField.getText().isEmpty() ? -1 : Integer.parseInt(departmentAgeTextField.getText()));
        payload.put("child", departmentChildTextField.getText().isEmpty() ? -1 : Integer.parseInt(departmentChildTextField.getText()));
        payload.put("employment", departmentEmploymentTextField.getText().isEmpty() ? -1 : Integer.parseInt(departmentEmploymentTextField.getText()));
        payload.put("salary", departmentSalaryTextField.getText().isEmpty() ? -1 : Integer.parseInt(departmentSalaryTextField.getText()));

        Message response = sender.sendRequest(request);

        if (response == null) {
            System.err.println("Error: No response from server");
            return;
        }

        showResult(response, departmentResponseArea);
    }

    @FXML
    private ChoiceBox<String> brigadeChoiceBox;

    @FXML
    private CheckBox brigadeSalaryCheckBox;

    @FXML
    private ChoiceBox<String> brigadeFlightChoiceBox;

    @FXML
    private ListView<String> brigadeResponseArea;

    @FXML
    private void onBrigadeGetButtonClick() {
        Message request = new Message("GET_BRIGADE");
        ObjectNode payload = request.getContent();

        String selectedBrigade = brigadeChoiceBox.getValue();
        if (selectedBrigade == null || selectedBrigade.equals("None")) {
            payload.put("brigade_id", -1);
        } else {
            payload.put("brigade_id", Integer.parseInt(selectedBrigade));
        }

        String selectedFlight = brigadeFlightChoiceBox.getValue();
        if (selectedFlight == null || selectedFlight.equals("None")) {
            payload.put("flight_id", -1);
        } else {
            payload.put("flight_id", Integer.parseInt(selectedFlight));
        }

        payload.put("salary", brigadeSalaryCheckBox.isSelected() ? 1 : 0);

        Message response = sender.sendRequest(request);

        if (response == null) {
            System.err.println("Error: No response from server");
            return;
        }

        showResult(response, brigadeResponseArea);
    }

    private void showResult(Message response, ListView<String> responseArea) {
        try {
            ObjectNode resPayload = response.getContent();
            ArrayNode res = (ArrayNode) resPayload.get("results");

            if (res.isEmpty()) {
                responseArea.getItems().clear();
                responseArea.getItems().add("No results found");
                return;
            }

            responseArea.getItems().clear();
            
            for (int i = 0; i < res.size(); i++) {
                ObjectNode department = (ObjectNode) res.get(i);
                Iterator<String> fieldNames = department.fieldNames();
                
                StringBuilder result = new StringBuilder();

                while (fieldNames.hasNext()) {
                    String fieldName = fieldNames.next();
                    String fieldValue = department.get(fieldName).isNull() ? "null" : department.get(fieldName).asText();
                    result.append(fieldName).append(": ").append(fieldValue).append(", ");
                }

                responseArea.getItems().add(result.toString());
            }
        } catch (Exception e) {
            System.err.println("Error parsing response: " + e.getMessage());
        }
    }

    @FXML
    private TextField pilotMedYearTextField;

    @FXML
    private TextField pilotSalaryTextField;

    @FXML
    private TextField pilotAgeTextField;

    @FXML
    private ChoiceBox<String> pilotStatusChoiceBox;

    @FXML
    private ChoiceBox<String> pilotGenderChoiceBox;

    @FXML
    private ListView<String> pilotResponseArea;

    @FXML
    private void onPilotGetButtonClick() {
        Message request = new Message("GET_PILOT");
        ObjectNode payload = request.getContent();

        String selectedStatus = pilotStatusChoiceBox.getValue();
        if (selectedStatus == null || selectedStatus.equals("None")) {
            payload.put("status", -1);
        } else {
            payload.put("status", pilotStatus.get(selectedStatus));
        }

        String selectedGender = pilotGenderChoiceBox.getValue();
        if (selectedGender == null || selectedGender.equals("None")) {
            payload.put("gender", (JsonNode) null);
        } else {
            payload.put("gender", selectedGender);
        }

        payload.put("med_year", pilotMedYearTextField.getText().isEmpty() ? -1 : Integer.parseInt(pilotMedYearTextField.getText()));
        payload.put("salary", pilotSalaryTextField.getText().isEmpty() ? -1 : Integer.parseInt(pilotSalaryTextField.getText()));
        payload.put("age_years", pilotAgeTextField.getText().isEmpty() ? -1 : Integer.parseInt(pilotAgeTextField.getText()));

        Message response = sender.sendRequest(request);

        if (response == null) {
            System.err.println("Error: No response from server");
            return;
        }

        showResult(response, pilotResponseArea);
    }

    @FXML
    private ChoiceBox<String> planePlaceChoiceBox;

    @FXML
    private ChoiceBox<String> departureCityChoiceBox;

    @FXML
    private TextField excludedFlightTextField;

    @FXML
    private TextField arrivalTimeTextField;

    @FXML
    private TextField flightsCountTextField;

    @FXML
    private ListView<String> planeResponseArea;

    @FXML
    private void onPlaneGetButtonClick() {
        Message request = new Message("GET_PLANE");
        ObjectNode payload = request.getContent();

        String selectedPlaces = planePlaceChoiceBox.getValue();
        if (selectedPlaces == null || selectedPlaces.equals("None")) {
            payload.put("place", (JsonNode) null);
        } else {
            payload.put("place", selectedPlaces);
        }

        String selectedCity = departureCityChoiceBox.getValue();
        if (selectedCity == null || selectedCity.equals("None")) {
            payload.put("departure_airport", (JsonNode) null);
        } else {
            payload.put("departure_airport", selectedCity);
        }

        String excludedFlight = excludedFlightTextField.getText();
        if (excludedFlight.isEmpty()) {
            payload.put("exclude_time", (JsonNode) null);
        } else {
            payload.put("exclude_time", excludedFlight);
        }

        String arrivalTime = arrivalTimeTextField.getText();
        if (arrivalTime.isEmpty()) {
            payload.put("arrival_time", (JsonNode) null);
        } else {
            payload.put("arrival_time", arrivalTime);
        }

        String flightsCount = flightsCountTextField.getText();
        if (flightsCount.isEmpty()) {
            payload.put("flights_count", -1);
        } else {
            payload.put("flights_count", Integer.parseInt(flightsCount));
        }

        Message response = sender.sendRequest(request);

        if (response == null) {
            System.err.println("Error: No response from server");
            return;
        }

        showResult(response, planeResponseArea);
    }

    @FXML
    private DatePicker startRepairDatePicker;

    @FXML
    private DatePicker endRepairDatePicker;

    @FXML
    private CheckBox repairCheckBox;

    @FXML
    private TextField repairCountTextField;

    @FXML
    private TextField differenceTextField;

    @FXML
    private TextField agePlaneTextField;

    @FXML
    private ListView<String> repairResponseArea;

    @FXML
    private void onRepairGetButtonClick() {
        Message request = new Message("GET_REPAIR");
        ObjectNode payload = request.getContent();

        String startRepairDate = startRepairDatePicker.getValue() == null ? null : startRepairDatePicker.getValue().toString();
        String endRepairDate = endRepairDatePicker.getValue() == null ? null : endRepairDatePicker.getValue().toString();

        payload.put("start_date", startRepairDate);
        payload.put("end_date", endRepairDate);
        payload.put("status", repairCheckBox.isSelected() ? 1 : 0);
        payload.put("repair_count", repairCountTextField.getText().isEmpty() ? -1 : Integer.parseInt(repairCountTextField.getText()));
        payload.put("flights_different", differenceTextField.getText().isEmpty() ? -1 : Integer.parseInt(differenceTextField.getText()));
        payload.put("age", agePlaneTextField.getText().isEmpty() ? -1 : Integer.parseInt(agePlaneTextField.getText()));

        Message response = sender.sendRequest(request);

        if (response == null) {
            System.err.println("Error: No response from server");
            return;
        }

        showResult(response, repairResponseArea);
    }

    @FXML
    private CheckBox luggageCheckBox;

    @FXML
    private ChoiceBox<String> passengerGenderChoiceBox;

    @FXML
    private TextField passengerAgeTextField;

    @FXML
    private ChoiceBox<String> passengerFlightChoiceBox;

    @FXML
    private DatePicker passengerFlightDatePicker;

    @FXML
    private ListView<String> passengerResponseArea;

    @FXML
    private void onPassengerGetButtonClick() {
        Message request = new Message("GET_PASSENGER");
        ObjectNode payload = request.getContent();

        String selectedFlight = passengerFlightChoiceBox.getValue();
        if (selectedFlight == null || selectedFlight.equals("None")) {
            payload.put("route_id", -1);
        } else {
            payload.put("route_id", Integer.parseInt(selectedFlight));
        }

        String flightDate = passengerFlightDatePicker.getValue() == null ? null : passengerFlightDatePicker.getValue().toString();
        payload.put("status_date", flightDate);

        payload.put("luggage", luggageCheckBox.isSelected() ? 1 : 0);

        String selectedGender = passengerGenderChoiceBox.getValue();
        if (selectedGender == null || selectedGender.equals("None")) {
            payload.put("gender", (JsonNode) null);
        } else {
            payload.put("gender", selectedGender);
        }

        String passengerAge = passengerAgeTextField.getText();
        if (passengerAge.isEmpty()) {
            payload.put("age", -1);
        } else {
            payload.put("age", Integer.parseInt(passengerAge));
        }

        Message response = sender.sendRequest(request);

        if (response == null) {
            System.err.println("Error: No response from server");
            return;
        }

        showResult(response, passengerResponseArea);
    }

    @FXML
    private ChoiceBox<String> routeChoiceBox;

    @FXML
    private TextField timeRouteTextField;

    @FXML
    private TextField costRouteTextField;

    @FXML
    private ListView<String> routeResponseArea;

    @FXML
    private void onRouteGetButtonClick() {
        Message request = new Message("GET_FLIGHT_1");
        ObjectNode payload = request.getContent();

        String selectedRoute = routeChoiceBox.getValue();
        if (selectedRoute == null || selectedRoute.equals("None")) {
            payload.put("route_id", -1);
        } else {
            payload.put("route_id", routes.get(selectedRoute));
        }

        String timeRoute = timeRouteTextField.getText();
        if (timeRoute.isEmpty()) {
            payload.put("duration", -1);
        } else {
            payload.put("duration", Integer.parseInt(timeRoute));
        }

        String costRoute = costRouteTextField.getText();
        if (costRoute.isEmpty()) {
            payload.put("ticket_price", -1);
        } else {
            payload.put("ticket_price", Integer.parseInt(costRoute));
        }

        Message response = sender.sendRequest(request);

        if (response == null) {
            System.err.println("Error: No response from server");
            return;
        }

        showResult(response, routeResponseArea);
    }

    @FXML
    private ChoiceBox<String> flightStatusChoiceBox;

    @FXML
    private ChoiceBox<String> flightRouteChoiceBox;

    @FXML
    private TextField flightPlacesTextField;

    @FXML
    private CheckBox flightRatioCheckBox;

    @FXML
    private ListView<String> flightResponseArea;

    @FXML
    private void onFlightGetButtonClick() {
        Message request = new Message("GET_FLIGHT_2");
        ObjectNode payload = request.getContent();

        String selectedStatus = flightStatusChoiceBox.getValue();
        if (selectedStatus == null || selectedStatus.equals("None")) {
            payload.put("status", -1);
        } else {
            payload.put("status", flightStatus.get(selectedStatus));
        }

        String selectedRoute = flightRouteChoiceBox.getValue();
        if (selectedRoute == null || selectedRoute.equals("None")) {
            payload.put("route_id", -1);
        } else {
            payload.put("route_id", routes.get(selectedRoute));
        }

        if (flightRatioCheckBox.isSelected()) {
            String flightPlaces = flightPlacesTextField.getText();
            if (flightPlaces.isEmpty()) {
                payload.put("ticket_ratio", -1);
            } else {
                payload.put("ticket_ratio", Double.parseDouble(flightPlaces));
            }
            payload.put("max_tickets", -1);
        } else {
            String flightPlaces = flightPlacesTextField.getText();
            if (flightPlaces.isEmpty()) {
                payload.put("max_tickets", -1);
            } else {
                payload.put("max_tickets", Integer.parseInt(flightPlaces));
            }
            payload.put("ticket_ratio", -1);
        }

        Message response = sender.sendRequest(request);

        if (response == null) {
            System.err.println("Error: No response from server");
            return;
        }

        showResult(response, flightResponseArea);
    }

    @FXML
    private ChoiceBox<String> flightStatusDelayChoiceBox;

    @FXML
    private ChoiceBox<String> routeDelayChoiceBox;

    @FXML
    private CheckBox delayCheckBox;

    @FXML
    private ListView<String> delayResponseArea;

    @FXML
    private void onDelayGetButtonClick() {
        Message request = new Message("GET_FLIGHT_3");
        ObjectNode payload = request.getContent();

        String selectedStatus = flightStatusDelayChoiceBox.getValue();
        if (selectedStatus == null || selectedStatus.equals("None")) {
            payload.put("status", -1);
        } else if (selectedStatus.equals("Delayed")) {
            payload.put("status", -2);
        } else {
            payload.put("status", flightStatus.get(selectedStatus));
        }

        String selectedRoute = routeDelayChoiceBox.getValue();
        if (selectedRoute == null || selectedRoute.equals("None")) {
            payload.put("route_id", -1);
        } else {
            payload.put("route_id", routes.get(selectedRoute));
        }

        payload.put("return_ticket", delayCheckBox.isSelected() ? 1 : 0);

        Message response = sender.sendRequest(request);

        if (response == null) {
            System.err.println("Error: No response from server");
            return;
        }

        showResult(response, delayResponseArea);
    }

    @FXML
    private Label routeCalcLabel;

    @FXML
    private void onCalcButtonClick() {
        Message request = new Message("GET_CALC");

        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("Route Calculation");
        dialog.setHeaderText("Please enter the parameters for route calculation:");

        dialog.initModality(Modality.APPLICATION_MODAL);

        ListView<String> listView = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList(routes.keySet());
        listView.setItems(items);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType);

        VBox dialogPane = new VBox(10);
        dialogPane.getChildren().addAll(listView);
        dialog.getDialogPane().setContent(dialogPane);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return listView.getSelectionModel().getSelectedItems();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(selectedRoutes -> {
            if (selectedRoutes.isEmpty()) {
                return;
            }

            request.getContent().put("count", selectedRoutes.size());
            ArrayNode routesArray = request.getContent().putArray("routes");
            for (String route : selectedRoutes) {
                routesArray.add(routes.get(route));
            }

            Message response = sender.sendRequest(request);

            if (response == null) {
                System.err.println("Error: No response from server");
                return;
            }

            try {
                ObjectNode resPayload = response.getContent();
                ArrayNode res = (ArrayNode) resPayload.get("results");

                if (res.isEmpty()) {
                    routeCalcLabel.setText("No results found");
                    return;
                }

                for (int i = 0; i < res.size(); i++) {
                    ObjectNode department = (ObjectNode) res.get(i);
                    Iterator<String> fieldNames = department.fieldNames();

                    while (fieldNames.hasNext()) {
                        String fieldName = fieldNames.next();
                        String fieldValue = department.get(fieldName).isNull() ? "null" : department.get(fieldName).asText();

                        routeCalcLabel.setText(fieldValue);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error parsing response: " + e.getMessage());
            }
        });
    }

    @FXML
    private ChoiceBox<String> modelsChoiceBox;

    @FXML
    private TextField timeDepartTextField;

    @FXML
    private ListView<String> departResponseArea;

    @FXML
    private void onDepartGetButtonClick() {
        Message request = new Message("GET_FLIGHT_4");
        ObjectNode payload = request.getContent();

        String selectedModel = modelsChoiceBox.getValue();
        if (selectedModel == null || selectedModel.equals("None")) {
            payload.put("model", (JsonNode) null);
        } else {
            payload.put("model", selectedModel);
        }

        String timeDepart = timeDepartTextField.getText();
        if (timeDepart.isEmpty()) {
            payload.put("departure_time", (JsonNode) null);
        } else {
            payload.put("departure_time", timeDepart);
        }

        Message response = sender.sendRequest(request);

        if (response == null) {
            System.err.println("Error: No response from server");
            return;
        }

        showResult(response, departResponseArea);
    }

    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    @FXML
    private ChoiceBox<String> modelsChoice;

    @FXML
    private ListView<String> categoryResponseArea;

    @FXML
    private void onCategoryGetButtonClick() {
        Message request = new Message("GET_FLIGHT_5");
        ObjectNode payload = request.getContent();

        String selectedCategory = categoryChoiceBox.getValue();
        if (selectedCategory == null || selectedCategory.equals("None")) {
            payload.put("category", -1);
        } else {
            payload.put("category", types.get(selectedCategory));
        }

        String selectedModel = modelsChoice.getValue();
        if (selectedModel == null || selectedModel.equals("None")) {
            payload.put("model", (JsonNode) null);
        } else {
            payload.put("model", selectedModel);
        }

        Message response = sender.sendRequest(request);

        if (response == null) {
            System.err.println("Error: No response from server");
            return;
        }

        showResult(response, categoryResponseArea);
    }

    @FXML
    private DatePicker dateReturnDatePicker;

    @FXML
    private ChoiceBox<String> flightReturnChoiceBox;

    @FXML
    private TextField returnCostTextField;

    @FXML
    private ChoiceBox<String> routeReturnChoiceBox;

    @FXML
    private TextField ageReturnTextField;

    @FXML
    private ChoiceBox<String> genderReturnChoiceBox;

    @FXML
    private ListView<String> returnResponseArea;

    @FXML
    private void onReturnGetButtonClick() {
        Message request = new Message("GET_COUNT");
        ObjectNode payload = request.getContent();

        String selectedRoute = routeReturnChoiceBox.getValue();
        if (selectedRoute == null || selectedRoute.equals("None")) {
            payload.put("route_id", -1);
        } else {
            payload.put("route_id", routes.get(selectedRoute));
        }

        String selectedFlight = flightReturnChoiceBox.getValue();
        if (selectedFlight == null || selectedFlight.equals("None")) {
            payload.put("flight_id", -1);
        } else {
            payload.put("flight_id", Integer.parseInt(selectedFlight));
        }

        String returnDate = dateReturnDatePicker.getValue() == null ? null : dateReturnDatePicker.getValue().toString();
        payload.put("return_date", returnDate);

        String returnCost = returnCostTextField.getText();
        if (returnCost.isEmpty()) {
            payload.put("ticket_price", -1);
        } else {
            payload.put("ticket_price", Integer.parseInt(returnCost));
        }

        String ageReturn = ageReturnTextField.getText();
        if (ageReturn.isEmpty()) {
            payload.put("age", -1);
        } else {
            payload.put("age", Integer.parseInt(ageReturn));
        }

        String selectedGender = genderReturnChoiceBox.getValue();
        if (selectedGender == null || selectedGender.equals("None")) {
            payload.put("gender", (JsonNode) null);
        } else {
            payload.put("gender", selectedGender);
        }

        Message response = sender.sendRequest(request);

        if (response == null) {
            System.err.println("Error: No response from server");
            return;
        }

        showResult(response, returnResponseArea);
    }

    @FXML
    private CheckBox freePlaceCheckBox;

    @FXML
    private DatePicker dateOfPlaceDatePicker;

    @FXML
    private ChoiceBox<String> flightFreePlaceChoiceBox;

    @FXML
    private ChoiceBox<String> routeFreePlaceChoiceBox;

    @FXML
    private TextField costFreePlaceTextField;

    @FXML
    private TextField timeFreePlaceTextField;

    @FXML
    private ListView<String> freePlaceResponseArea;

    @FXML
    private void onFreePlaceGetButtonClick() {
        Message request = new Message("GET_FREE_PLACES");
        ObjectNode payload = request.getContent();

        payload.put("choice", freePlaceCheckBox.isSelected() ? 1 : 0);

        String selectedRoute = routeFreePlaceChoiceBox.getValue();
        if (selectedRoute == null || selectedRoute.equals("None")) {
            payload.put("route_id", -1);
        } else {
            payload.put("route_id", routes.get(selectedRoute));
        }

        String selectedFlight = flightFreePlaceChoiceBox.getValue();
        if (selectedFlight == null || selectedFlight.equals("None")) {
            payload.put("flight_id", -1);
        } else {
            payload.put("flight_id", Integer.parseInt(selectedFlight));
        }

        String freePlaceDate = dateOfPlaceDatePicker.getValue() == null ? null : dateOfPlaceDatePicker.getValue().toString();
        payload.put("date", freePlaceDate);

        String costFreePlace = costFreePlaceTextField.getText();
        if (costFreePlace.isEmpty()) {
            payload.put("ticket_price", -1);
        } else {
            payload.put("ticket_price", Integer.parseInt(costFreePlace));
        }

        String timeFreePlace = timeFreePlaceTextField.getText();
        if (timeFreePlace.isEmpty()) {
            payload.put("time", (JsonNode) null);
        } else {
            payload.put("time", timeFreePlace);
        }

        Message response = sender.sendRequest(request);

        if (response == null) {
            System.err.println("Error: No response from server");
            return;
        }

        showResult(response, freePlaceResponseArea);
    }
}