package com.seek.seekblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Type {

    private Long id;
    private String name;
    private Long number;

    private List<Blog> blogs = new ArrayList<>();
}
