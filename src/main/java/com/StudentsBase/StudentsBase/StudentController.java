package com.StudentsBase.StudentsBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

    @GetMapping("/{studentId}/subjects")
    public List<Subject> getSubjectsAssignedToStudent(@PathVariable Long studentId)
    {
        return studentService.getSubjectsAssignedToStudent(studentId);
    }

    @PostMapping("/{studentId}/subjects/{subjectId}")
    public Student addSubjectToStudent(@PathVariable Long studentId, @PathVariable Long subjectId) {
        return studentService.addSubjectToStudent(studentId, subjectId);
    }

    @GetMapping("{studentId}/grades")
    public List<Grade> getGrades(@PathVariable Long studentId)
    {
        return studentService.getGrades(studentId);
    }
}
