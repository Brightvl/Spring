package brightvl.spring.controller;

import brightvl.spring.model.Timesheet;
import brightvl.spring.repository.TimesheetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TimesheetControllerTest {

    @Autowired
    TimesheetRepository timesheetRepository;

    @LocalServerPort
    private int port;
    private RestClient restClient;

    @BeforeEach
    void beforeEach() {
        restClient = RestClient.create("http://localhost:" + port);
    }

    @Test
    void getByIdNotFound() {
        assertThrows(HttpClientErrorException.NotFound.class, () -> {
            restClient.get()
                    .uri("/timesheets/-1")
                    .retrieve()
                    .toBodilessEntity();
        });
    }

    @Test
    void getByIdAllOk() {
        Timesheet timesheet = new Timesheet();
        timesheet.setProjectId(1L);
        timesheet.setMinutes(120);
        timesheet.setCreatedAt(LocalDate.now());
        Timesheet expected = timesheetRepository.save(timesheet);

        ResponseEntity<Timesheet> actual = restClient.get()
                .uri("/timesheets/" + expected.getId())
                .retrieve()
                .toEntity(Timesheet.class);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        Timesheet responseBody = actual.getBody();
        assertNotNull(responseBody);
        assertEquals(expected.getId(), responseBody.getId());
        assertEquals(expected.getProjectId(), responseBody.getProjectId());
    }

    @Test
    void getAllOk() {
        Timesheet timesheet1 = new Timesheet();
        timesheet1.setProjectId(1L);
        timesheet1.setMinutes(120);
        timesheet1.setCreatedAt(LocalDate.now());

        Timesheet timesheet2 = new Timesheet();
        timesheet2.setProjectId(2L);
        timesheet2.setMinutes(60);
        timesheet2.setCreatedAt(LocalDate.now());

        timesheetRepository.save(timesheet1);
        timesheetRepository.save(timesheet2);

        ResponseEntity<Timesheet[]> response = restClient.get()
                .uri("/timesheets")
                .retrieve()
                .toEntity(Timesheet[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Timesheet[] timesheets = response.getBody();
        assertNotNull(timesheets);
        assertTrue(timesheets.length >= 2);
    }

    @Test
    void testCreate() {
        Timesheet toCreate = new Timesheet();
        toCreate.setProjectId(1L);
        toCreate.setMinutes(100);
        toCreate.setCreatedAt(LocalDate.now());

        ResponseEntity<Timesheet> response = restClient.post()
                .uri("/timesheets")
                .body(toCreate)
                .retrieve()
                .toEntity(Timesheet.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Timesheet responseBody = response.getBody();
        assertNotNull(responseBody);
        assertNotNull(responseBody.getId());
        assertEquals(toCreate.getProjectId(), responseBody.getProjectId());

        assertTrue(timesheetRepository.existsById(responseBody.getId()));
    }

    @Test
    void testDeleteById() {
        Timesheet toDelete = new Timesheet();
        toDelete.setProjectId(1L);
        toDelete.setMinutes(100);
        toDelete.setCreatedAt(LocalDate.now());
        toDelete = timesheetRepository.save(toDelete);

        ResponseEntity<Void> response = restClient.delete()
                .uri("/timesheets/" + toDelete.getId())
                .retrieve()
                .toBodilessEntity();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        assertFalse(timesheetRepository.existsById(toDelete.getId()));
    }

    @Test
    void testUpdate() {
        Timesheet toUpdate = new Timesheet();
        toUpdate.setProjectId(1L);
        toUpdate.setMinutes(100);
        toUpdate.setCreatedAt(LocalDate.now());
        toUpdate = timesheetRepository.save(toUpdate);

        toUpdate.setMinutes(200);

        ResponseEntity<Timesheet> response = restClient.put()
                .uri("/timesheets/" + toUpdate.getId())
                .body(toUpdate)
                .retrieve()
                .toEntity(Timesheet.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Timesheet responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(toUpdate.getMinutes(), responseBody.getMinutes());
    }
}
