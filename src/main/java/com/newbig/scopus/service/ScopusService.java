package com.newbig.scopus.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newbig.scopus.mapper.ScopusMapper;
import com.newbig.scopus.model.Scopus;
import com.newbig.scopus.model.Scopus2016;
import com.newbig.scopus.utils.FileTxtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ScopusService {
    @Autowired
    private ScopusMapper scopusMapper;

    public void insert(Scopus scopus){
        scopusMapper.insert(scopus);
    }

    public List<Scopus> getScopusByStatus(Integer status){
        Example example = new Example(Scopus.class);
        example.createCriteria().andEqualTo("status",status)
        .andEqualTo("gateStatus",-1);
        return scopusMapper.selectByExample(example);
    }

    public PageInfo<Scopus> getScopusList(Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        Example example = new Example(Scopus.class);
        return new PageInfo<Scopus>(scopusMapper.selectByExample(example));
    }
    public void updateScores(){
        Example example = new Example(Scopus.class);
        example.createCriteria().andGreaterThan("status",2);
        List<Scopus> scopuses = scopusMapper.selectByExample(example);
        for(Scopus scopus : scopuses){
            if(scopus.getScopusScores()!=null) {
                List<String> scores = JSON.parseObject(scopus.getScopusScores(), List.class);
                if (scores.size() > 8) {
                    scopus.setLt2013(scores.get(0));
                    scopus.setS2013(scores.get(1));
                    scopus.setS2014(scores.get(2));
                    scopus.setS2015(scores.get(3));
                    scopus.setS2016(scores.get(4));
                    scopus.setS2017(scores.get(5));
                    scopus.setSubtotal(scores.get(6));
                    if(Integer.valueOf(scores.get(7))>Integer.valueOf(scores.get(8))){
                        scopus.setGt2017("0");
                        scopus.setTotal(Long.valueOf(scores.get(7)));
                    }else {
                        scopus.setGt2017(scores.get(7));
                        scopus.setTotal(Long.valueOf(scores.get(8)));
                    }
                    scopus.setScopusUrl("https://www.scopus.com/authid/detail.uri?authorId=" + scopus.getScopusAuthorId());
                    scopusMapper.updateByPrimaryKey(scopus);
                }
            }
        }
    }

    public void insertScopus() {
        List<Scopus> ss = FileTxtUtil.readtxt1617("d:\\jieqing.txt");
        for(Scopus s:ss) {
            scopusMapper.insert(s);
        }
    }
}
