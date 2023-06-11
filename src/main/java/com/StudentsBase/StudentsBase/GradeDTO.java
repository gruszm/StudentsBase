package com.StudentsBase.StudentsBase;

public class GradeDTO
{
    private Long gradeId;
    private String firstName;
    private String lastName;
    private String subject;
    private Integer mark;

    public GradeDTO(Grade grade)
    {
        this.gradeId = grade.getId();
        this.firstName = grade.getStudent().getFirstName();
        this.lastName = grade.getStudent().getLastName();
        this.subject = grade.getSubject().getName();
        this.mark = grade.getMark();
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getSubject()
    {
        return subject;
    }

    public Integer getMark()
    {
        return mark;
    }
}
