package brightvl.spring.controller.page;

import brightvl.spring.controller.page.DTO.TimesheetPageDto;
import brightvl.spring.service.page.TimesheetPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Контроллер для отображения страниц таймшитов.
 */
@Controller
@RequestMapping("/home/timesheets")
@RequiredArgsConstructor
public class TimesheetPageController {

    private final TimesheetPageService service;

    /**
     * Отображение страницы всех таймшитов.
     *
     * @param model модель
     * @return путь к HTML-странице всех таймшитов
     */
    @GetMapping
    public String getAllTimesheets(Model model) {
        List<TimesheetPageDto> timesheets = service.findAll();
        model.addAttribute("timesheets", timesheets);
        return "timesheets-page.html";
    }

    /**
     * Отображение страницы конкретного таймшита по идентификатору.
     *
     * @param id    идентификатор таймшита
     * @param model модель
     * @return путь к HTML-странице таймшита
     */
    @GetMapping("/{id}")
    public String getTimesheetPage(@PathVariable Long id, Model model) {
        Optional<TimesheetPageDto> timesheetOpt = service.findById(id);
        if (timesheetOpt.isEmpty()) {
            throw new NoSuchElementException();
        }

        model.addAttribute("timesheet", timesheetOpt.get());
        return "timesheet-page.html";
    }

    /**
     * Выдает заметки по конкретному проекту.
     *
     * @param projectId id проекта
     * @param model     модель
     * @return путь к HTML-странице таймшитов проекта
     */
    @GetMapping("/projects/{projectId}")
    public String getTimesheetsByProjectId(@PathVariable Long projectId, Model model) {
        List<TimesheetPageDto> timesheets = service.getByProjectId(projectId);
        model.addAttribute("timesheets", timesheets);
        return "timesheets-page.html";
    }
}
