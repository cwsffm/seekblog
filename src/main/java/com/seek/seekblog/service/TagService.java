package com.seek.seekblog.service;

import com.github.pagehelper.PageInfo;
import com.seek.seekblog.entity.Tag;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface TagService {

    Tag saveTag(Tag tag, RedirectAttributes attributes);

    Tag getTag(Long id);

    Tag updateTag(Tag Tag,  RedirectAttributes attributes);

    PageInfo<Tag> listTags(Integer pageNum, Integer pageSize);

    List<Tag> listTags();

    void deleteTag(Long id,RedirectAttributes attributes);

    Long countTag();
}
