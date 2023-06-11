package com.StudentsBase.StudentsBase;

public class GradeDTO
{
    private final Long gradeId;
    private final String firstName;
    private final String lastName;
    private final String subject;
    private final Double mark;

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

    public Double getMark()
    {
        return mark;
    }
    public Long getGradeId()
    {
        return gradeId;
    }
}
