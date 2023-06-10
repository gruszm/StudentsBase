package com.StudentsBase.StudentsBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
public class GradeController
{
    private final GradeService gradeService;

    @Autowired
    public GradeController(GradeService gradeService)
    {
        this.gradeService = gradeService;
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
}
