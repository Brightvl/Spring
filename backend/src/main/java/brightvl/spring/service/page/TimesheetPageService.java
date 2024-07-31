package brightvl.spring.service.page;

import brightvl.spring.controller.page.DTO.TimesheetPageDto;
import brightvl.spring.model.Project;
import brightvl.spring.model.Timesheet;
import brightvl.spring.service.ProjectService;
import brightvl.spring.service.TimesheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления страницами таймшитов.
 */
@Service
@RequiredArgsConstructor
public class TimesheetPageService {

    private final TimesheetService timesheetService;
    private final ProjectService projectService;

    /**
     * Получение всех страниц таймшитов и преобразование в ДТО
     *
     * @return список всех страниц таймшитов
     */
    public List<TimesheetPageDto> findAll() {
        return timesheetService.findAll().stream()
                .map(this::convert)
                .toList();
    }

    /**
     * Поиск страницы таймшита по идентификатору.
     *
     * @param id идентификатор таймшита
     * @return найденная страница таймшита, если существует
     */
    public Optional<TimesheetPageDto> findById(Long id) {
        return timesheetService.findById(id) // Optional<Timesheet>
                .map(this::convert);
    }

    /**
     * Возвращает заметку по id проекта
     * @param projectId
     * @return
     */
    public List<TimesheetPageDto> getByProjectId(Long projectId) {
        return timesheetService.findById(projectId)
                .stream()
                .map(this::convert)
                .toList();
    }

    /**
     * Преобразование таймшита в DTO для отображения на странице.
     *
     * @param timesheet таймшит
     * @return DTO страницы таймшита
     */
    private TimesheetPageDto convert(Timesheet timesheet) {
        Project project = projectService.findById(timesheet.getProjectId())
                .orElseThrow();

        TimesheetPageDto timesheetPageParameters = new TimesheetPageDto();
        timesheetPageParameters.setProjectName(project.getName());
        timesheetPageParameters.setId(String.valueOf(timesheet.getId()));
        timesheetPageParameters.setMinutes(String.valueOf(timesheet.getMinutes()));
        timesheetPageParameters.setCreatedAt(timesheet.getCreatedAt().format(DateTimeFormatter.ISO_DATE));
        timesheetPageParameters.setProjectId(project.getId());  // Установка идентификатора проекта

        return timesheetPageParameters;
    }

}
