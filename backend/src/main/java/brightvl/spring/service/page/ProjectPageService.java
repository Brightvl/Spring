package brightvl.spring.service.page;

import brightvl.spring.controller.page.DTO.ProjectPageDto;
import brightvl.spring.model.Project;
import brightvl.spring.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectPageService {

    private final ProjectService projectService;

    /**
     * Получение всех проектов и преобразование в ДТО
     *
     * @return список всех проектов
     */
    public List<ProjectPageDto> findAll() {
        return projectService.findAll().stream()
                .map(this::convert).toList();

    }

    /**
     * Поиск страницы проекта по идентификатору.
     *
     * @param id идентификатор проекта
     * @return найденная страница проекта, если существует
     */
    public Optional<ProjectPageDto> findById(Long id) {
        return projectService.findById(id)
                .map(this::convert);
    }


    /**
     * Преобразование проекта в DTO для отображения на странице.
     *
     * @param project проект
     * @return DTO страницы таймшита
     */
    private ProjectPageDto convert(Project project) {

        ProjectPageDto projectPageDto = new ProjectPageDto();
        projectPageDto.setId(project.getId());
        projectPageDto.setName(project.getName());

        return projectPageDto;
    }

    public void saveProject(Project project) {
        projectService.create(project);
    }

    public void deleteProjectById(Long id) {
        projectService.deleteById(id);
    }
}
