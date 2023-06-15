package com.StudentsBase.StudentsBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/students")
public class StudentController
{
    private final StudentService studentService;
    private final SubjectService subjectService;

    @Autowired
    public StudentController(StudentService studentService, SubjectService subjectService) {
        this.studentService = studentService;
        this.subjectService = subjectService;
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
        model.addAttribute("student", student);
        model.addAttribute("subjects", availableSubjects);
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

    @GetMapping("/inspect/{id}")
    public String inspectStudent(@PathVariable Long id, Model model) {
        Student student = studentService.getStudent(id);
        List<Grade> grades = studentService.getGrades(id);

        Map<Subject, List<Grade>> subjectGrades = new HashMap<>();
        for (Grade grade : grades) {
            Subject subject = grade.getSubject();
            if (subjectGrades.containsKey(subject)) {
                subjectGrades.get(subject).add(grade);
            } else {
                List<Grade> gradeList = new ArrayList<>();
                gradeList.add(grade);
                subjectGrades.put(subject, gradeList);
            }
        }

        model.addAttribute("student", student);
        model.addAttribute("subjectGrades", subjectGrades);
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
    //
    // @GetMapping("/{studentId}/subjects")
    // public List<Subject> getSubjectsAssignedToStudent(@PathVariable Long
    // studentId)
    // {
    // return studentService.getSubjectsAssignedToStudent(studentId);
    // }
    //
    //
    // @GetMapping("{studentId}/grades")
    // public List<Grade> getGrades(@PathVariable Long studentId)
    // {
    // return studentService.getGrades(studentId);
    // }
}
