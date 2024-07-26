package brightvl.spring.service;

import brightvl.spring.model.Project;
import brightvl.spring.repository.ProjectRepository;
import brightvl.spring.repository.TimesheetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    /**
     * Сервис для управления проектами.
     */
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;

    }

    /**
     * Поиск проекта по идентификатору.
     *
     * @param id идентификатор проекта
     * @return найденный проект, если существует
     */
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    /**
     * Поиск всех проектов.
     *
     * @return список всех проектов
     */
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    /**
     * Создание нового проекта.
     *
     * @param project создаваемый проект
     * @return созданный проект
     */
    public Project create(Project project) {
        return projectRepository.save(project);
    }

    /**
     * Удаление проекта по идентификатору.
     *
     * @param id идентификатор проекта
     */
    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }

}
