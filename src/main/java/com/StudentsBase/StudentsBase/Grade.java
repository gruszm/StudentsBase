package com.StudentsBase.StudentsBase;

import jakarta.persistence.*;

@Entity
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
