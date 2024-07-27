package brightvl.spring.controller;

import brightvl.spring.model.Employee;
import brightvl.spring.model.Timesheet;
import brightvl.spring.service.EmployeeService;
import brightvl.spring.service.TimesheetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final TimesheetService timesheetService;

    @Operation(
            summary = "Получить сотрудника",
            description = "Возвращает данные сотрудника по его идентификатору",
            responses = {
                    @ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = Employee.class))),
                    @ApiResponse(description = "Сотрудник не найден", responseCode = "404", content = @Content(schema = @Schema(implementation = Void.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Employee> get(@PathVariable @Parameter(description = "Идентификатор сотрудника") Long id) {
        return employeeService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Получить всех сотрудников",
            description = "Возвращает список всех сотрудников",
            responses = {
                    @ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = Employee.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @Operation(
            summary = "Создать сотрудника",
            description = "Создает нового сотрудника",
            responses = {
                    @ApiResponse(description = "Сотрудник успешно создан", responseCode = "200", content = @Content(schema = @Schema(implementation = Employee.class))),
                    @ApiResponse(description = "Ошибка запроса", responseCode = "400", content = @Content(schema = @Schema(implementation = Void.class)))
            }
    )
    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody @Parameter(description = "Данные нового сотрудника") Employee employee) {
        return ResponseEntity.ok(employeeService.save(employee));
    }

    @Operation(
            summary = "Удалить сотрудника",
            description = "Удаляет сотрудника по его идентификатору",
            responses = {
                    @ApiResponse(description = "Сотрудник успешно удален", responseCode = "204", content = @Content(schema = @Schema(implementation = Void.class))),
                    @ApiResponse(description = "Сотрудник не найден", responseCode = "404", content = @Content(schema = @Schema(implementation = Void.class)))
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "Идентификатор сотрудника") Long id) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Получить все записи о рабочем времени сотрудника",
            description = "Возвращает список всех записей о рабочем времени для указанного сотрудника",
            responses = {
                    @ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = Timesheet.class))),
                    @ApiResponse(description = "Сотрудник не найден", responseCode = "404", content = @Content(schema = @Schema(implementation = Void.class)))
            }
    )
    @GetMapping("/{id}/timesheets")
    public ResponseEntity<List<Timesheet>> getTimesheetsByEmployee(@PathVariable @Parameter(description = "Идентификатор сотрудника") Long id) {
        return employeeService.findById(id)
                .map(employee -> ResponseEntity.ok(timesheetService.findByEmployeeId(employee.getId())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
