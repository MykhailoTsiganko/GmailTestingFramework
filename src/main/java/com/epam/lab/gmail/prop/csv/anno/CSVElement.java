package com.epam.lab.gmail.prop.csv.anno;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

@Retention(RUNTIME)
public @interface CSVElement {
    String name();


}
