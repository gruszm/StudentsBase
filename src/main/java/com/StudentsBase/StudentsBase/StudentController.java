package com.StudentsBase.StudentsBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller handling requests related to Students.
 *
 * @author Mariusz Gruszczynski, Mateusz Pysera
 * @version 1.0
 * @since JDK 17
 */
@Controller
@RequestMapping("/students")
public class StudentController
{
    private final StudentService studentService;
    private final SubjectService subjectService;
    private final GradeService gradeService;

    /**
     * Constructs a new StudentController with the specified StudentService,
     * SubjectService and GradeService.
     *
     * @param studentService the service for managing students
     * @param subjectService the service for managing subjects
     * @param gradeService   the service for managing grades
     */
    @Autowired
    public StudentController(StudentService studentService, SubjectService subjectService, GradeService gradeService) {
        this.studentService = studentService;
        this.subjectService = subjectService;
        this.gradeService = gradeService;
    }

    /**
     * Returns a list of all students.
     *
     * @return a list of all students
     */
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    /**
     * Handles the request to get the home page.
     *
     * @param model the model to add attributes to for rendering in the view
     * @return a string indicating the view name
     */
    @GetMapping("/")
    public String home_page(Model model) {
        model.addAttribute("students", getStudents());
        return "students_home";
    }

    /**
     * Handles the request to get the add student page.
     *
     * @param model the model to add attributes to for rendering in the view
     * @return a string indicating the view name
     */
    @GetMapping("/add")
    public String add_student(Model model) {
        return "add_student";
    }

    /**
     * Handles the request to add a student.
     *
     * @param student the student to add
     * @return a string indicating the view name
     */
    @PostMapping(value = "/addStudent", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addStudent(@ModelAttribute Student student) {
        studentService.addStudent(student);
        return "redirect:/students/";
    }

    /**
     * Handles the request to get a student.
     *
     * @param id the id of the student to get
     * @param model the model to add attributes to for rendering in the view
     * @return a string indicating the view name
     */
    @GetMapping("/edit/{id}")
    public String getStudent(@PathVariable Long id, Model model) {
        // method implementation
        return "edit_student";
    }

    /**
     * Handles the request to delete a student.
     *
     * @param id the id of the student to delete
     * @return a string indicating the view name
     */
    @DeleteMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students/";
    }

    /**
     * Handles the request to update a student.
     *
     * @param id the id of the student to update
     * @param student the student with updated information
     * @param model the model to add attributes to for rendering in the view
     * @return a string indicating the view name
     */
    @PutMapping("/edit/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute Student student, Model model) {
        studentService.updateStudent(id, student);
        return getStudent(id, model);
    }

    /**
     * Handles the request to edit a grade.
     *
     * @param id the id of the grade to edit
     * @param mark the new mark
     * @param studentId the id of the student
     * @param subjectId the id of the subject
     * @param model the model to add attributes to for rendering in the view
     * @return a string indicating the view name
     */
    @PutMapping("/editgrade/{id}")
    public String editGrade(@PathVariable("id") Long id,
                            @RequestParam(value = "mark", required = false) Double mark,
                            @RequestParam("studentId") Long studentId,
                            @RequestParam("subjectId") Long subjectId,
                            Model model) {
        // method implementation
        return getStudent(studentId, model);
    }

    /**
     * Handles the request to inspect a student.
     *
     * @param id the id of the student to inspect
     * @param model the model to add attributes to for rendering in the view
     * @return a string indicating the view name
     */
    @GetMapping("/inspect/{id}")
    public String inspectStudent(@PathVariable Long id, Model model) {
        // method implementation
        return "inspect_student";
    }

    /**
     * Handles the request to add a subject to a student.
     *
     * @param id the id of the student
     * @param subject the id of the subject to add
     * @param model the model to add attributes to for rendering in the view
     * @return a string indicating the view name
     */
    @PostMapping("/addSubject/{id}")
    public String addSubject(@PathVariable Long id, @RequestParam Long subject, Model model) {
        studentService.addSubjectToStudent(id, subject);
        return getStudent(id, model);
    }

    /**
     * Handles the request to remove a subject from a student.
     *
     * @param id the id of the student
     * @param subjectId the id of the subject to remove
     * @param model the model to add attributes to for rendering in the view
     * @return a string indicating the view name
     */
    @DeleteMapping("/removeSubject/{id}/{subjectId}")
    public String removeSubject(@PathVariable Long id, @PathVariable Long subjectId, Model model) {
        studentService.removeSubjectFromStudent(id, subjectId);
        return getStudent(id, model);
    }
}
