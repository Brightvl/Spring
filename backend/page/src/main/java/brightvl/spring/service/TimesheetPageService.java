package brightvl.spring.service;

import brightvl.spring.DTO.TimesheetPageDto;
import brightvl.spring.client.TimesheetResponse;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Сервис для управления страницами таймшитов.
 */
@Service
public class TimesheetPageService {

    private final EurekaClientService eurekaClientService;
    private final ProjectPageService projectPageService;

    public TimesheetPageService(EurekaClientService eurekaClientService, ProjectPageService projectPageService) {
        this.eurekaClientService = eurekaClientService;
        this.projectPageService = projectPageService;
    }

    private RestClient restClient() {
        return eurekaClientService.createRestClient("TIMESHEET-REST");
    }

    public List<TimesheetPageDto> findAll() {
        List<TimesheetResponse> timesheets = restClient().get()
                .uri("/timesheets")
                .retrieve()
                .body(new ParameterizedTypeReference<List<TimesheetResponse>>() {
                });

        List<TimesheetPageDto> result = new ArrayList<>();
        for (TimesheetResponse timesheet : timesheets) {
            result.add(convert(timesheet));
        }
        return result;
    }

    /**
     * Поиск страницы таймшита по идентификатору.
     *
     * @param id идентификатор таймшита
     * @return найденная страница таймшита, если существует
     */
    public Optional<TimesheetPageDto> findById(Long id) {
        try {
            TimesheetResponse timesheet = restClient().get()
                    .uri("/timesheets/" + id)
                    .retrieve()
                    .body(TimesheetResponse.class);

            return Optional.of(convert(timesheet));
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }

    public void create(Long projectId, int minutes) {
        TimesheetResponse timesheet = new TimesheetResponse();
        timesheet.setProjectId(projectId);
        timesheet.setMinutes(minutes);
        timesheet.setCreatedAt(LocalDate.now());

        restClient().post()
                .uri("/timesheets")
                .body(timesheet)
                .retrieve()
                .body(TimesheetResponse.class);
    }

    public void deleteTimesheetById(Long id) {
        restClient().delete()
                .uri("/timesheets/" + id)
                .retrieve()
                .toBodilessEntity();
    }

    public List<TimesheetPageDto> getByProjectId(Long projectId) {
        List<TimesheetResponse> timesheets = restClient().get()
                .uri("/timesheets/projects/" + projectId)
                .retrieve()
                .body(new ParameterizedTypeReference<List<TimesheetResponse>>() {});
        List<TimesheetPageDto> result = new ArrayList<>();
        for (TimesheetResponse timesheet : timesheets) {
            result.add(convert(timesheet));
        }
        return result;
    }

    /**
     * Преобразование таймшита в DTO для отображения на странице.
     *
     * @param timesheet таймшит
     * @return DTO страницы таймшита
     */
    private TimesheetPageDto convert(TimesheetResponse timesheet) {
        TimesheetPageDto timesheetDTO = new TimesheetPageDto();
        timesheetDTO.setId(String.valueOf(timesheet.getId()));
        timesheetDTO.setProjectId(String.valueOf(timesheet.getProjectId()));
        timesheetDTO.setMinutes(String.valueOf(timesheet.getMinutes()));
        timesheetDTO.setCreatedAt(timesheet.getCreatedAt().format(DateTimeFormatter.ISO_DATE));
        timesheetDTO.setProjectName(
                projectPageService.findById(timesheet.getProjectId()).get().getName());
        return timesheetDTO;
    }
}
