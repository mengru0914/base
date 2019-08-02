package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;
    @Autowired
    private IdWorker idWorker;
    /**
     * 查询全部标签
     * @return
     */
    public List<Label> findAll(){
        return labelDao.findAll();
    }
    /**
     * 根据ID查询标签
     * @return
     */
    public Label findById(String id){
        return labelDao.findById(id).get();
    }
/**
 * 增加标签
 * @param label
 */
public void add(Label label){
    label.setId( idWorker.nextId()+"" );//设置ID
    labelDao.save(label);
}
    /**
     * 修改标签
     * @param label
     */
    public void update(Label label){
        labelDao.save(label);
    }
    /**
     * 删除标签
     * @param id
     */
    public void deleteById(String id){
        labelDao.deleteById(id);
    }

    public List<Label> findSearch(Label label) {
       return labelDao.findAll(new Specification<Label>() {
           /**
            * 根对象，也就是要把条件封装到那个对象中，where类名=label.getid
            * @param root
            * @param criteriaQuery 封装的都是查询关键字，比如group by order by 登
            * @param cb 用来封装条件对象的，如果直接返回null，标识不需要任何任何条件
            * @return
            */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (label.getLabelname()!=null&&!"".equals(label.getLabelname())) {

                    Predicate predicate=cb.like(root.get("labelname").as(String.class),"%"+label.getLabelname()+"%");//where labelname like '%%小明%'
                    return predicate;
                }
                if (label.getState()!=null&&!"".equals(label.getState())) {

                    Predicate predicate=cb.equal(root.get("state").as(String.class),label.getState());//where labelname like '%%小明%'
                    list.add(predicate);
                }

                Predicate[] parr =new Predicate[list.size()];
                parr = list.toArray(parr);
                return cb.and(parr);

            }
        });
    }

    public Page<Label> PageSearch(Label label, int page, int size) {

        Pageable pageble=PageRequest.of(page-1,size);
        return labelDao.findAll(new Specification<Label>() {
            /**
             * 根对象，也就是要把条件封装到那个对象中，where类名=label.getid
             * @param root
             * @param criteriaQuery 封装的都是查询关键字，比如group by order by 登
             * @param cb 用来封装条件对象的，如果直接返回null，标识不需要任何任何条件
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (label.getLabelname()!=null&&!"".equals(label.getLabelname())) {

                    Predicate predicate=cb.like(root.get("labelname").as(String.class),"%"+label.getLabelname()+"%");//where labelname like '%%小明%'
                    return predicate;
                }
                if (label.getState()!=null&&!"".equals(label.getState())) {

                    Predicate predicate=cb.equal(root.get("state").as(String.class),label.getState());//where labelname like '%%小明%'
                    list.add(predicate);
                }

                Predicate[] parr =new Predicate[list.size()];
                parr = list.toArray(parr);
                return cb.and(parr);

            }
        },pageble);
    }
}

