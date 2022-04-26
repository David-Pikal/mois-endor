package cz.uhk.mois.endor.planservice.planservice.controller;

import cz.uhk.mois.endor.planservice.planservice.model.Project;
import cz.uhk.mois.endor.planservice.planservice.repository.ProjectRepository;
import cz.uhk.mois.endor.planservice.planservice.util.DateParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Optional;

@RestController
@RequestMapping(path = "/plan/project")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @PostMapping(path = "/add")
    public String addNewProject(@RequestParam String title, @RequestParam String value, @RequestParam String userID, @RequestParam String startDate,
                                @RequestParam String endDate) {

        Date endD = DateParser.formatDate(endDate);

        Project project = new Project(title, userID, Date.valueOf(startDate), endD, new BigDecimal(value));
        projectRepository.save(project);

        return "Saved";
    }

    @PutMapping(path = "/update")
    public String updateProject(@RequestParam Integer id, @RequestParam String title, @RequestParam String value, @RequestParam String startDate,
                                @RequestParam String endDate) {

        Optional<Project> optional = projectRepository.findById(id);
        if (optional.isEmpty()) {
            return "Id does not exist";
        }

        Project project = optional.get();

        project.setTitle(title);
        project.setValue(new BigDecimal(value));
        project.setStartDate(Date.valueOf(startDate));
        project.setEndDate(DateParser.formatDate(endDate));

        projectRepository.save(project);

        return "Saved";
    }

    @GetMapping(path = "/all")
    public Iterable<Project> getAllUserProjects(@RequestParam String userID) {
        return projectRepository.findByUserID(userID);
    }

    @GetMapping(path = "/one")
    public Optional<Project> getOneUserProject(@RequestParam Integer id) {
        return projectRepository.findById(id);
    }

    @DeleteMapping(path = "/delete")
    public String deleteUserProject(@RequestParam Integer id) {
        try {
            projectRepository.deleteById(id);
            return "Deleted";
        } catch (EmptyResultDataAccessException e) {
            return "Id does not exist";
        }
    }
}
