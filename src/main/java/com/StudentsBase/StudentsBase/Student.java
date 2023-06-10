package com.StudentsBase.StudentsBase;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Student {
    @Id
    @SequenceGenerator(name = "student_sequence",
                        sequenceName = "student_sequence",
                        allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;
    private String firstName;
    private String lastName;
    private String indexNumber;

    @ManyToMany
    @JoinTable(
            name = "student_subject",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> subjects;

    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
    private List<Grade> grades;

    public List<Subject> getSubjects()
    {
        return subjects;
    }
    public void setSubjects(List<Subject> subjects)
    {
        this.subjects = subjects;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getIndexNumber() { return indexNumber; }
    public void setIndexNumber(String indexNumber) { this.indexNumber = indexNumber; }
}
