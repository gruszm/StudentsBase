package com.StudentsBase.StudentsBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/subjects")
public class SubjectController
{
    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService)
    {
        this.subjectService = subjectService;
    }

    public List<Subject> getSubjects() {
        return subjectService.getSubjects();
    }

    @GetMapping("/")
    public String subjects_page(Model model) {
        model.addAttribute("subjects", getSubjects());
        return "subjects_home";
    }

    @GetMapping("/add")
    public String addSubject(Model model) {
        return "add_subject";
    }

    @PostMapping(value = "/addSubject", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addSubject(@ModelAttribute Subject subject) {
        subjectService.addSubject(subject);
        return "redirect:/subjects/";
    }

    @GetMapping("/edit/{id}")
    public String getSubject(@PathVariable Long id, Model model) {
        Subject subject = subjectService.getSubject(id);
        model.addAttribute("subject", subject);
        return "edit_subject";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return "redirect:/subjects/";
    }

    @PutMapping("/edit/{id}")
    public String updateSubject(@PathVariable Long id, @ModelAttribute Subject subject,
                                 Model model) {
        Subject updated_subject = subjectService.updateSubject(id, subject);
        model.addAttribute("subject", updated_subject);
        return "edit_subject";
    }

}
