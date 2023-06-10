package com.StudentsBase.StudentsBase;

import jakarta.persistence.*;

@Entity
@Table
public class Grade {
    @Id
    @SequenceGenerator(name = "grade_sequence",
            sequenceName = "grade_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "grade_sequence"
    )
    private Long id;
    private Integer mark;
    @ManyToOne
    private Student student;
    @ManyToOne
    private Subject subject;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getMark() { return mark; }
    public void setMark(Integer mark) { this.mark = mark; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }
}
