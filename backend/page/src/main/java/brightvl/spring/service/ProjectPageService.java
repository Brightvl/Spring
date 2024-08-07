package brightvl.spring.service;

import brightvl.spring.DTO.ProjectPageDto;
import brightvl.spring.client.ProjectResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class ProjectPageService {

    private final EurekaClientService eurekaClientService;

    public ProjectPageService(EurekaClientService eurekaClientService) {
        this.eurekaClientService = eurekaClientService;
    }

    private RestClient restClient() {
        return eurekaClientService.createRestClient("TIMESHEET-REST");
    }

    public List<ProjectPageDto> findAll() {
        List<ProjectResponse> projects = restClient().get()
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


    public Optional<ProjectPageDto> findById(Long id) {
        try {
            ProjectResponse project = restClient().get()
                    .uri("/projects/" + id)
                    .retrieve()
                    .body(ProjectResponse.class);
            return Optional.of(convert(project));
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }


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
        restClient().post()
                .uri("/projects")
                .body(project)
                .retrieve()
                .body(ProjectPageDto.class);
    }

    public void deleteProjectById(Long id) {
        restClient().delete()
                .uri("/projects/" + id)
                .retrieve()
                .toBodilessEntity();
    }

}
