package com.StudentsBase.StudentsBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService
{
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository, StudentRepository studentRepository)
    {
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
    }

    public List<Subject> getSubjects()
    {
        return subjectRepository.findAll();
    }

    public Subject getSubject(Long id)
    {
        return subjectRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Subject with id %d not found", id)));
    }

    public Subject addSubject(Subject subject)
    {
        Subject existingSubject = subjectRepository.findByName(subject.getName());

        if (existingSubject != null)
        {
            throw new RuntimeException("Subject with name " + subject.getName() + " already exists");
        }
        return subjectRepository.save(subject);
    }


    public void deleteSubject(Long id)
    {
        Subject thisSubject = subjectRepository.findById(id).orElseThrow(() -> new RuntimeException(("Subject with id " + id + " not found")));

        List<Student> studentsWithSubject = studentRepository.findBySubjectsContains(thisSubject);

        for (Student student : studentsWithSubject)
        {
            student.getSubjects().remove(thisSubject);
            studentRepository.save(student);
        }

        subjectRepository.deleteById(id);
    }

    public Subject updateSubject(Long id, Subject newSubject)
    {
        return subjectRepository.findById(id)
                .map(subject ->
                {
                    subject.setName(newSubject.getName());
                    return subjectRepository.save(subject);
                })
                .orElseGet(() ->
                {
                    newSubject.setId(id);
                    return subjectRepository.save(newSubject);
                });
    }
}
