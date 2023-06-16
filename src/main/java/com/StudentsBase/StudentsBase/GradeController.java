package com.StudentsBase.StudentsBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for handling requests related to grades in the StudentsBase system.
 *
 * @author Mariusz Gruszczynski, Mateusz Pysera
 * @version 1.0
 * @since JDK 17
 */
@Controller
@RequestMapping("/grades")
public class GradeController
{
    private final GradeService gradeService;
    private final SubjectRepository subjectRepository;

    /**
     * Creates a new GradeController with the given grade service and subject repository.
     *
     * @param gradeService       the grade service
     * @param subjectRepository  the subject repository
     */
    @Autowired
    public GradeController(GradeService gradeService, SubjectRepository subjectRepository)
    {
        this.gradeService = gradeService;
        this.subjectRepository = subjectRepository;
    }

    /**
     * Returns a list of all grades in the system.
     *
     * @return a list of all grades
     */
    @GetMapping
    public List<Grade> getGrades()
    {
        return gradeService.getGrades();
    }

    /**
     * Adds a new grade to the system.
     *
     * @param grade the grade to add
     * @return the added grade
     */
    @PostMapping
    public Grade addGrade(@RequestBody Grade grade)
    {
        return gradeService.addGrade(grade);
    }

    /**
     * Returns a view with the average grades for each subject.
     *
     * @param model the model to add attributes to
     * @return a string specifying the view name
     */
    @GetMapping("/statistics")
    public String getStatistics(Model model) {
        Map<String, Double> subjectMeans = new HashMap<>();
        List<Subject> allSubjects = subjectRepository.findAll();

        for (Subject subject : allSubjects)
        {
            if (subject.getGrades() == null || subject.getGrades().isEmpty())
            {
                continue;
            }

            List<Grade> gradesOfThisSubject = subject.getGrades();

            double average = 0.0;
            int size = 0;
            for (Grade grade : gradesOfThisSubject)
            {
                if (grade.getMark() != null) {
                    average += grade.getMark();
                    size++;
                }
            }

            average /= size;

            subjectMeans.put(subject.getName(), average);
        }

        model.addAttribute("subjectMeans", subjectMeans);
        return "subjects_stats";
    }
}
