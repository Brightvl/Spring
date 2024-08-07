package brightvl.timesheet.service;

import brightvl.spring.model.Project;
import brightvl.spring.repository.ProjectRepository;
import brightvl.spring.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class ProjectServiceTest {

  @Autowired
  ProjectRepository projectRepository;

  @Autowired
  ProjectService projectService;

  @Test
  void findByIdEmpty() {
    // given
    assertFalse(projectRepository.existsById(2L));

    assertTrue(projectService.findById(2L).isEmpty());
  }

  @Test
  void findByIdPresent() {
    // given
    Project project = new Project();
    project.setName("projectName");
    project = projectRepository.save(project);

    // when
    Optional<Project> actual = projectService.findById(project.getId());

    // then
    assertTrue(actual.isPresent());
    assertEquals(actual.get().getId(), project.getId());
    assertEquals(actual.get().getName(), project.getName());
  }

}