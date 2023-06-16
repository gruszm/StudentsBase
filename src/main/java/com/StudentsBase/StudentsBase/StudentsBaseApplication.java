package com.StudentsBase.StudentsBase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * Main application class for StudentsBaseApplication.
 *
 * @author Mariusz Gruszczynski, Mateusz Pysera
 * @version 1.0
 * @since JDK 17
 */
@SpringBootApplication
@RestController
public class StudentsBaseApplication
{

    /**
     * The main method that starts the application.
     *
     * @param args the command-line arguments (typically passed from the environment)
     */
    public static void main(String[] args)
    {
        SpringApplication.run(StudentsBaseApplication.class, args);
    }
}
