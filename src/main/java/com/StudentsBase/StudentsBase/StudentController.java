package com.StudentsBase.StudentsBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/")
    public String home_page(Model model) {
        model.addAttribute("students", getStudents());
        return "home";
    }

    @GetMapping("/add")
    public String add_student(Model model) {
        return "add_student";
    }

    @PostMapping(value = "/addStudent", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addStudent(@ModelAttribute Student student) {
        System.out.println(student);
        studentService.addStudent(student);
        return "redirect:/students/";
    }

     @GetMapping("/edit/{id}")
     public String getStudent(@PathVariable Long id, Model model) {
        Student student = studentService.getStudent(id); // TODO : add error handling
        model.addAttribute("student", student);
        return "edit_student";
    }

     @DeleteMapping("/delete/{id}")
     public String deleteStudent(@PathVariable("id") Long id) {
        System.out.println("Deleting student with id: " + id);
        studentService.deleteStudent(id);
        return "redirect:/students/";
     }

     @PutMapping("/edit/{id}")
     public String updateStudent(@PathVariable Long id, @ModelAttribute Student
     student, Model model) {
        System.out.println("Updating student with id: " + id);
        Student updated_student = studentService.updateStudent(id, student);
        model.addAttribute("student", updated_student);
        return "edit_student";
     }

    //
    // @GetMapping("/{studentId}/subjects")
    // public List<Subject> getSubjectsAssignedToStudent(@PathVariable Long
    // studentId)
    // {
    // return studentService.getSubjectsAssignedToStudent(studentId);
    // }
    //
    // @PostMapping("/{studentId}/subjects/{subjectId}")
    // public Student addSubjectToStudent(@PathVariable Long studentId,
    // @PathVariable Long subjectId) {
    // return studentService.addSubjectToStudent(studentId, subjectId);
    // }
    //
    // @GetMapping("{studentId}/grades")
    // public List<Grade> getGrades(@PathVariable Long studentId)
    // {
    // return studentService.getGrades(studentId);
    // }
}
