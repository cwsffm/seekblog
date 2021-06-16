package com.seek.seekblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seek.seekblog.NotFoundException;
import com.seek.seekblog.entity.Tag;
import com.seek.seekblog.mapper.BlogTagMapper;
import com.seek.seekblog.mapper.TagMapper;
import com.seek.seekblog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.Transient;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private BlogTagMapper blogTagMapper;

    @Override
    @Transient
    public Tag saveTag(Tag tag, RedirectAttributes attributes) {
        Tag t = tagMapper.findByName(tag.getName());

        if (t == null) {
            tag.setId(tagMapper.save(tag));
            attributes.addFlashAttribute("message", "添加成功！");
            return tag;
        } else {

            attributes.addFlashAttribute("message", "已存在！");
            return t;
        }
    }

    @Override
    @Transient
    public Tag updateTag(Tag tag,RedirectAttributes attributes){
        Tag t = tagMapper.findById(tag.getId());
        if (t == null){
            throw new NotFoundException("不存在！");
        }
        BeanUtils.copyProperties(tag,t);
        attributes.addFlashAttribute("message","修改成功！");
        tagMapper.update(tag);
        return tag;
    }

    @Override
    @Transient
    public Tag getTag(Long id) {
        return tagMapper.findById(id);
    }

    @Override
    @Transient
    public PageInfo<Tag> listTags(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Tag> tags = tagMapper.listAllTags();
        PageInfo<Tag> tagPageInfo = new PageInfo<>(tags,pageSize);
        return tagPageInfo;
    }

    @Override
    @Transient
    public List<Tag> listTags(){
        return tagMapper.listAllTags();
    }


    @Override
    @Transient
    public void deleteTag(Long id,RedirectAttributes attributes) {
        attributes.addFlashAttribute("message","删除成功！");
        blogTagMapper.deleteByTagId(id);
        tagMapper.delete(id);
    }

    @Override
    public Long countTag(){
        return tagMapper.count();
    }
}
