package com.seek.seekblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seek.seekblog.NotFoundException;
import com.seek.seekblog.entity.Type;
import com.seek.seekblog.mapper.BlogMapper;
import com.seek.seekblog.mapper.TypeMapper;
import com.seek.seekblog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.Transient;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private BlogMapper blogMapper;

    @Override
    @Transient
    public Type saveType(Type type, RedirectAttributes attributes) {
        Type t = typeMapper.findByName(type.getName());

        if (t == null) {
            type.setId(typeMapper.save(type));
            attributes.addFlashAttribute("message", "添加成功！");
            return type;
        } else {

            attributes.addFlashAttribute("message", "已存在！");
            return t;
        }
    }

    @Override
    @Transient
    public Type updateType(Type type,RedirectAttributes attributes){
        Type t = typeMapper.findById(type.getId());
        if (t == null){
            throw new NotFoundException("不存在！");
        }
        BeanUtils.copyProperties(type,t);
        attributes.addFlashAttribute("message","修改成功！");
        typeMapper.update(type);
        return type;
    }

    @Override
    @Transient
    public Type getType(Long id) {
        return typeMapper.findById(id);
    }

    @Override
    @Transient
    public PageInfo<Type> listTypes(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Type> types = typeMapper.listAllTypes();
        PageInfo<Type> typePageInfo = new PageInfo<>(types,pageSize);
        return typePageInfo;
    }

    @Override
    @Transient
    public List<Type> listTypes(){
        return typeMapper.listAllTypes();
    }




    @Override
    @Transient
    public void deleteType(Long id,RedirectAttributes attributes) {
        attributes.addFlashAttribute("message","删除成功！");
        blogMapper.updateByTypeId(id);
        typeMapper.delete(id);
    }

    @Override
    public Long countType() {
        return typeMapper.count();
    }
}
