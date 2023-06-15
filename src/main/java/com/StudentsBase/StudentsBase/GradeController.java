package com.StudentsBase.StudentsBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/grades")
public class GradeController
{
    private final GradeService gradeService;
    private final SubjectRepository subjectRepository;

    @Autowired
    public GradeController(GradeService gradeService, SubjectRepository subjectRepository)
    {
        this.gradeService = gradeService;
        this.subjectRepository = subjectRepository;
    }

    @GetMapping
    public List<Grade> getGrades()
    {
        return gradeService.getGrades();
    }

    @PostMapping
    public Grade addGrade(@RequestBody Grade grade)
    {
        return gradeService.addGrade(grade);
    }

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
            for (Grade grade : gradesOfThisSubject)
            {
                if (grade.getMark() != null) {
                    average += grade.getMark();
                }
            }

            average /= gradesOfThisSubject.size();

            subjectMeans.put(subject.getName(), average);
        }

        model.addAttribute("subjectMeans", subjectMeans);
        return "subjects_stats";
    }
}
