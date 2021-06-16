package com.seek.seekblog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BlogQuery {

    private String title;
    private Long typeId;
    private Long tagId;
    private boolean recommend;

}
