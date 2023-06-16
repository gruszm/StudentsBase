package com.StudentsBase.StudentsBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/students")
public class StudentController
{
    private final StudentService studentService;
    private final SubjectService subjectService;
    private final GradeService gradeService;

    @Autowired
    public StudentController(StudentService studentService, SubjectService subjectService, GradeService gradeService) {
        this.studentService = studentService;
        this.subjectService = subjectService;
        this.gradeService = gradeService;
    }

    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/")
    public String home_page(Model model) {
        model.addAttribute("students", getStudents());
        return "students_home";
    }

    @GetMapping("/add")
    public String add_student(Model model) {
        return "add_student";
    }

    @PostMapping(value = "/addStudent", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addStudent(@ModelAttribute Student student) {
        studentService.addStudent(student);
        return "redirect:/students/";
    }

    @GetMapping("/edit/{id}")
    public String getStudent(@PathVariable Long id, Model model) {
        Student student = studentService.getStudent(id);
        List<Subject> availableSubjects = subjectService.getSubjects().stream()
                .filter(subject -> !student.getSubjects().contains(subject))
                .collect(Collectors.toList());
        List<Grade> grades = studentService.getGrades(id);
        List<Double> possibleGrades = Arrays.asList(2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0);

        model.addAttribute("possibleGrades", possibleGrades);
        model.addAttribute("student", student);
        model.addAttribute("subjects", availableSubjects);
        model.addAttribute("grades", grades);
        return "edit_student";
    }

     @DeleteMapping("/delete/{id}")
     public String deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students/";
     }

     @PutMapping("/edit/{id}")
     public String updateStudent(@PathVariable Long id, @ModelAttribute Student
     student, Model model) {
        studentService.updateStudent(id, student);
        return getStudent(id, model);
     }

    @PutMapping("/editgrade/{id}")
    public String editGrade(@PathVariable("id") Long id,
                            @RequestParam(value = "mark", required = false) Double mark,
                            @RequestParam("studentId") Long studentId,
                            @RequestParam("subjectId") Long subjectId,
                            Model model) {
        Grade grade = studentService.getGrade(studentId, subjectId);
        if (mark == null) {
            grade.setMark(null);
        } else {
            grade.setMark(mark);
        }
        gradeService.addGrade(grade);

        return getStudent(studentId, model);
    }

    @GetMapping("/inspect/{id}")
    public String inspectStudent(@PathVariable Long id, Model model) {
        Student student = studentService.getStudent(id);
        List<Grade> grades = studentService.getGrades(id);

        model.addAttribute("student", student);
        model.addAttribute("grades", grades);
        return "inspect_student";
    }

    @PostMapping("/addSubject/{id}")
    public String addSubject(@PathVariable Long id, @RequestParam Long subject, Model model) {
        studentService.addSubjectToStudent(id, subject);
        return getStudent(id, model);
    }

    @DeleteMapping("/removeSubject/{id}/{subjectId}")
    public String removeSubject(@PathVariable Long id, @PathVariable Long subjectId, Model model) {
        studentService.removeSubjectFromStudent(id, subjectId);
        return getStudent(id, model);
    }
}
