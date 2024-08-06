package brightvl.spring.service;

import brightvl.spring.DTO.ProjectPageDto;
import brightvl.spring.client.ProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectPageService {

    private final RestClient restClient;

    public ProjectPageService() {
        this.restClient = RestClient.create("http://localhost:8080");
    }

    public List<ProjectPageDto> findAll() {
        List<ProjectResponse> projects = restClient.get()
                .uri("/projects")
                .retrieve()
                .body(new ParameterizedTypeReference<List<ProjectResponse>>() {
                });

        List<ProjectPageDto> result = new ArrayList<>();

        for (ProjectResponse projectResponse : projects) {
            ProjectPageDto projectPageDto = new ProjectPageDto();
            projectPageDto.setId(projectResponse.getId());
            projectPageDto.setName(String.valueOf(projectResponse.getName()));

            result.add(projectPageDto);
        }

        return result;
    }
//    /**
//     * Получение всех проектов и преобразование в ДТО
//     *
//     * @return список всех проектов
//     */
//    public List<ProjectPageDto> findAll() {
//        return projectService.findAll().stream()
//                .map(this::convert).toList();
//
//    }


    public Optional<ProjectPageDto> findById(Long id) {
        try {
            ProjectResponse project = restClient.get()
                    .uri("/projects/" + id)
                    .retrieve()
                    .body(ProjectResponse.class);
            return Optional.of(convert(project));
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }
//    /**
//     * Поиск страницы проекта по идентификатору.
//     *
//     * @param id идентификатор проекта
//     * @return найденная страница проекта, если существует
//     */
//    public Optional<ProjectPageDto> findById(Long id) {
//        return projectService.findById(id)
//                .map(this::convert);
//    }
//
//

    /**
     * Преобразование проекта в DTO для отображения на странице.
     *
     * @param project проект
     * @return DTO страницы таймшита
     */
    private ProjectPageDto convert(ProjectResponse project) {
        ProjectPageDto projectPageDto = new ProjectPageDto();
        projectPageDto.setId(project.getId());
        projectPageDto.setName(project.getName());

        return projectPageDto;
    }


    public void saveProject(ProjectPageDto project) {
        restClient.post()
                .uri("/projects")
                .body(project)
                .retrieve()
                .body(ProjectPageDto.class);
    }

    public void deleteProjectById(Long id) {
        restClient.delete()
                .uri("/projects/" + id)
                .retrieve()
                .toBodilessEntity();
    }

}
