package com.StudentsBase.StudentsBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository, StudentRepository studentRepository) {
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
    }

    public List<Subject> getSubjects() {
        return subjectRepository.findAll();
    }

    public Subject getSubject(Long id) {
        return subjectRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Subject with id %d not found", id)));
    }

    public Subject addSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public void deleteSubject(Long id) {
        Subject thisSubject = subjectRepository.findById(id).orElseThrow(() -> new RuntimeException(("Subject with id " + id + " not found")));
        List<Student> allStudents = studentRepository.findAll();

        for (Student student : allStudents)
        {
            List<String> listOfSubjectsNames = new ArrayList<>();

            for (Subject studentsSubject : student.getSubjects())
            {
                listOfSubjectsNames.add(studentsSubject.getName());
            }

            if (listOfSubjectsNames.contains(thisSubject.getName()))
            {
                student.getSubjects().remove(thisSubject);
            }
        }

        subjectRepository.deleteById(id);
    }

    public Subject updateSubject(Long id, Subject newSubject) {
        return subjectRepository.findById(id)
                .map(subject -> {
                    subject.setName(newSubject.getName());
                    return subjectRepository.save(subject);
                })
                .orElseGet(() -> {
                    newSubject.setId(id);
                    return subjectRepository.save(newSubject);
                });
    }
}
