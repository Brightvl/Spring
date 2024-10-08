package brightvl.spring.controller;

import brightvl.spring.model.Employee;
import brightvl.spring.model.Project;
import brightvl.spring.model.Timesheet;
import brightvl.spring.service.ProjectService;
import brightvl.spring.service.TimesheetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final TimesheetService timesheetService;

    /*
    ResponseEntity вместо простого List<Project> позволяет контролировать весь HTTP-ответ.
    Можно задать код состояния, заголовки и тело ответа.
    Это полезно, когда нужно больше контроля над тем, что именно отправляется клиенту.
    List<Project> вернет просто ответ 200
     */
    @Operation(
            summary = "Получить все проекты",
            description = "Возвращает список всех проектов",
            responses = {
                    @ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = Project.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<Project>> getAll() {
        return ResponseEntity.ok(projectService.findAll());
    }

    @Operation(
            summary = "Получить проект",
            description = "Возвращает проект по его идентификатору",
            responses = {
                    @ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = Project.class))),
                    @ApiResponse(description = "Проект не найден", responseCode = "404", content = @Content(schema = @Schema(implementation = Void.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Project> getById(@PathVariable Long id) {
        /*
        GET /projects/{id} @PathVariable будет передано в метод в качестве аргумента id
        Optional используется для обработки возможного отсутствия значения
        Если Optional содержит значение (проект найден), то метод map преобразует этот проект
        в объект ResponseEntity со статусом 200 OK.*/
        return projectService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @Operation(
            summary = "Создать проект",
            description = "Создает новый проект",
            responses = {
                    @ApiResponse(description = "Проект успешно создан", responseCode = "201", content = @Content(schema = @Schema(implementation = Project.class))),
                    @ApiResponse(description = "Ошибка запроса", responseCode = "400", content = @Content(schema = @Schema(implementation = Void.class)))
            }
    )
    @PostMapping
    public ResponseEntity<Project> create(@RequestBody @Parameter(description = "Данные нового проекта") Project project) {
        Project savedProject = projectService.create(project);
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Удалить проект",
            description = "Удаляет проект по его идентификатору",
            responses = {
                    @ApiResponse(description = "Проект успешно удален", responseCode = "204", content = @Content(schema = @Schema(implementation = Void.class))),
                    @ApiResponse(description = "Проект не найден", responseCode = "404", content = @Content(schema = @Schema(implementation = Void.class)))
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable @Parameter(description = "Идентификатор проекта") Long id) {
        projectService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(
            summary = "Получить сотрудников проекта",
            description = "Возвращает список всех сотрудников, работающих над проектом",
            responses = {
                    @ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = Employee.class))),
                    @ApiResponse(description = "Проект не найден", responseCode = "404", content = @Content(schema = @Schema(implementation = Void.class)))
            }
    )
    @GetMapping("/{id}/employees")
    public ResponseEntity<List<Employee>> getEmployeesByProjectId(@PathVariable Long id) {
        return projectService.findById(id)
                .map(project -> new ArrayList<>(project.getEmployees()))
                .map(employees -> ResponseEntity.ok((List<Employee>) employees))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @Operation(
            summary = "Получить заметки проекта",
            description = "Возвращает список всех заметок для проекта",
            responses = {
                    @ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = Timesheet.class))),
                    @ApiResponse(description = "Проект не найден", responseCode = "404", content = @Content(schema = @Schema(implementation = Void.class)))
            }
    )
    @GetMapping("/{id}/timesheets")
    public ResponseEntity<List<Timesheet>> getTimesheetsByProjectId(@PathVariable Long id) {
        List<Timesheet> timesheets = timesheetService.findByProjectId(id);
        if (timesheets != null && !timesheets.isEmpty()) {
            return ResponseEntity.ok(timesheets);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
