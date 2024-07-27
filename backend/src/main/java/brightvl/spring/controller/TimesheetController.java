package brightvl.spring.controller;

import brightvl.spring.model.Timesheet;
import brightvl.spring.service.TimesheetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для управления таймшитами.
 */
@RestController
@RequestMapping("/timesheets")
public class TimesheetController {

    private final TimesheetService timesheetService;

    public TimesheetController(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }

    /**
     * Получить все табели учета рабочего времени.
     *
     * @return ResponseEntity со списком всех табелей и статусом OK.
     */
    @GetMapping
    public ResponseEntity<List<Timesheet>> getAll() {
        return ResponseEntity.ok(timesheetService.findAll());
    }

    /**
     * Получение таймшита по идентификатору.
     *
     * @param id идентификатор таймшита
     * @return найденный таймшит, если существует
     */
    @GetMapping("/{id}")
    public ResponseEntity<Timesheet> get(@PathVariable Long id) {
        return timesheetService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    /**
     * Создание нового таймшита.
     *
     * @param timesheet создаваемый таймшит
     * @return созданный таймшит
     */
    @PostMapping
    public ResponseEntity<Timesheet> create(@RequestBody Timesheet timesheet) {
        final Timesheet created = timesheetService.create(timesheet);

        // 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Удаление таймшита по идентификатору.
     *
     * @param id идентификатор таймшита
     * @return ответ без содержимого
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timesheetService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
