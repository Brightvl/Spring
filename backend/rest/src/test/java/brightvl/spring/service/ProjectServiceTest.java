package brightvl.spring.service;

import brightvl.spring.model.Project;
import brightvl.spring.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("test")
@SpringBootTest // чтобы не создавать объекты в ручную например с помощью autowired
class ProjectServiceTest {

  /*
  @MockBean это тоже что
  @Bean
  public ProjectRepository mockProjectRepository() {
    return Mockito.mock(ProjectRepository.class);
  }
  */

  @Autowired
  ProjectRepository projectRepository;

  @Autowired
  ProjectService projectService;

  @Test
  void findByIdEmpty() {
    // given
    assertFalse(projectRepository.existsById(2L)); // сначала нужно убедиться что тест готов к работе

    assertTrue(projectService.findById(2L).isEmpty());
  }

  @Test
  void findByIdPresent() {
    // given начальные данные
    Project project = new Project();
    project.setName("projectName");
    project = projectRepository.save(project);

    // when действие
    Optional<Project> actual = projectService.findById(project.getId());

    // then проверка
    assertTrue(actual.isPresent());
    assertEquals(actual.get().getId(), project.getId());
    assertEquals(actual.get().getName(), project.getName());
  }

}