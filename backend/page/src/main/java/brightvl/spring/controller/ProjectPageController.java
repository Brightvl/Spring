package brightvl.spring.controller;

import brightvl.spring.DTO.ProjectPageDto;
import brightvl.spring.service.ProjectPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Контроллер для отображения страниц проектов
 */
@Controller
@RequestMapping("/projects")
@RequiredArgsConstructor
//@Secured({"admin"})
public class ProjectPageController {

    private final ProjectPageService service;

    /**
     * Отображение страницы всех проектов.
     *
     * @param model модель
     * @return путь к HTML-странице всех таймшитов
     */
    @GetMapping
    public String getAllProjects(Model model) {
        List<ProjectPageDto> projects = service.findAll();
        model.addAttribute("projects", projects);
        return "projects-page";
    }

    /**
     * Отображение страницы конкретного проекта по идентификатору.
     *
     * @param id    идентификатор проекта
     * @param model модель
     * @return путь к HTML-странице проекта
     */
    // GET /home/projects/{id}
    @GetMapping("/{id}")
    public String getProjectPage(@PathVariable Long id, Model model) {
        Optional<ProjectPageDto> projectOpt = service.findById(id);
        if (projectOpt.isEmpty()) {
            throw new NoSuchElementException();
        }

        model.addAttribute("project", projectOpt.get());
        return "project-page";
    }


    @PostMapping
    public String createProject(@RequestParam String name) {
        ProjectPageDto project = new ProjectPageDto();
        project.setName(name);
        service.saveProject(project);
        return "redirect:/projects";
    }

    @DeleteMapping("/{id}")
    public String deleteProject(@PathVariable Long id) {
        service.deleteProjectById(id);
        return "redirect:/projects";
    }

}
