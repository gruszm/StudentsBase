package com.StudentsBase.StudentsBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller handling requests related to Subjects.
 *
 * @author Mariusz Gruszczynski, Mateusz Pysera
 * @version 1.0
 * @since JDK 17
 */
@Controller
@RequestMapping("/subjects")
public class SubjectController
{
    private final SubjectService subjectService;

    /**
     * Constructs a new SubjectController with the specified SubjectService.
     *
     * @param subjectService the service for managing subjects
     */
    @Autowired
    public SubjectController(SubjectService subjectService)
    {
        this.subjectService = subjectService;
    }

    /**
     * Returns a list of all subjects.
     *
     * @return a list of all subjects
     */
    public List<Subject> getSubjects() {
        return subjectService.getSubjects();
    }

    /**
     * Handles the request to get the subjects page.
     *
     * @param model the model to add attributes to for rendering in the view
     * @return a string indicating the view name
     */
    @GetMapping("/")
    public String subjects_page(Model model) {
        model.addAttribute("subjects", getSubjects());
        return "subjects_home";
    }

    /**
     * Handles the request to get the add subject page.
     *
     * @param model the model to add attributes to for rendering in the view
     * @return a string indicating the view name
     */
    @GetMapping("/add")
    public String addSubject(Model model) {
        return "add_subject";
    }

    /**
     * Handles the request to add a subject.
     *
     * @param subject the subject to add
     * @return a string indicating the view name
     */
    @PostMapping(value = "/addSubject", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addSubject(@ModelAttribute Subject subject) {
        subjectService.addSubject(subject);
        return "redirect:/subjects/";
    }

    /**
     * Handles the request to get a subject.
     *
     * @param id the id of the subject to get
     * @param model the model to add attributes to for rendering in the view
     * @return a string indicating the view name
     */
    @GetMapping("/edit/{id}")
    public String getSubject(@PathVariable Long id, Model model) {
        Subject subject = subjectService.getSubject(id);
        model.addAttribute("subject", subject);
        return "edit_subject";
    }

    /**
     * Handles the request to delete a subject.
     *
     * @param id the id of the subject to delete
     * @return a string indicating the view name
     */
    @DeleteMapping("/delete/{id}")
    public String deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return "redirect:/subjects/";
    }

    /**
     * Handles the request to update a subject.
     *
     * @param id the id of the subject to update
     * @param subject the subject with updated information
     * @param model the model to add attributes to for rendering in the view
     * @return a string indicating the view name
     */
    @PutMapping("/edit/{id}")
    public String updateSubject(@PathVariable Long id, @ModelAttribute Subject subject,
                                Model model) {
        Subject updated_subject = subjectService.updateSubject(id, subject);
        model.addAttribute("subject", updated_subject);
        return "edit_subject";
    }

}
