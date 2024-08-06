package brightvl.spring.controller;

import brightvl.spring.DTO.TimesheetPageDto;
import brightvl.spring.service.TimesheetPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Контроллер для отображения страниц таймшитов.
 */
@Controller
@RequestMapping("/timesheets")
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
        return "timesheets-page";
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
        return "timesheet-page";
    }

    @PostMapping("/create")
    public String createTimesheet(@RequestParam Long projectId, @RequestParam int minutes) {
        service.create(projectId, minutes);
        return "redirect:/timesheets";
    }

    @DeleteMapping("/{id}")
    public String deleteTimesheet(@PathVariable Long id) {
        service.deleteTimesheetById(id);
        return "redirect:/timesheets";
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
        return "timesheets-page";
    }
//
//    /**
//     * Создание нового таймшита.
//     *
//     * @param projectId id проекта
//     * @param minutes количество минут
//     * @return путь к HTML-странице всех таймшитов
//     */
//    @PostMapping("/create")
//    public String createTimesheet(@RequestParam Long projectId, @RequestParam int minutes) {
//        service.create(projectId, minutes);
//        return "redirect:/home/timesheets";
//    }
}
