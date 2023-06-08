package com.StudentsBase.StudentsBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    @PostMapping("/{studentId}/grades/{subjectId}")
    public Grade addGradeToStudent(@PathVariable Long studentId, @PathVariable Long subjectId, @RequestBody Integer mark) {
        return studentService.addGradeToStudent(studentId, subjectId, mark);
    }

    @PostMapping("/{studentId}/subjects/{subjectId}")
    public Subject addSubjectToStudent(@PathVariable Long studentId, @PathVariable Long subjectId) {
        return studentService.addSubjectToStudent(studentId, subjectId);
    }

    @GetMapping("/{id}/grades")
    public List<Grade> getGradesForStudent(@PathVariable Long id) {
        return studentService.getGradesForStudent(id);
    }
}
