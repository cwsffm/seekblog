package com.seek.seekblog.service;

import com.github.pagehelper.PageInfo;
import com.seek.seekblog.entity.Type;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface TypeService {

    Type saveType(Type type, RedirectAttributes attributes);

    Type getType(Long id);

    Type updateType(Type type, RedirectAttributes attributes);

    PageInfo<Type> listTypes(Integer pageNum, Integer pageSize);

    List<Type> listTypes();

    void deleteType(Long id,RedirectAttributes attributes);

    Long countType();
}
