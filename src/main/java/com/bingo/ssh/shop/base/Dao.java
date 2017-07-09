package com.bingo.ssh.shop.base;

import com.bingo.ssh.shop.bean.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * Created by 26685 on 2017/7/4.
 */
@NoRepositoryBean
public interface Dao<T,ID extends Serializable> extends JpaRepository<T,ID>,JpaSpecificationExecutor<T> {
    public void add(T entity);
    //public void delete(Serializable ID);
    //public T update(T entity);
    //public T findOne(Serializable ID);
    //public Class<T> getEntityClass();
    public long getCount();
    /**
     *  查询所有数据
     * @return
     */
    public Page<T> find();
    /**
     *  带有条件，排序的查询,不带分页
     * @param whereSql 条件sql语句,占位符用？+数字形式，且数字从1开始累加 eg: username=?1 and password=?2
     * @param obj 条件参数
     * @param orderby 排序参数及排序规则 eg:oderby.put("username","desc");
     * @return
     */
    public Page<T> find(String whereSql, Object[] obj, LinkedHashMap<String,String> orderby);
    /**
     *  带有查询条件，不排序的分页查询
     * @param firstResult 要查寻的记录数
     * @param maxResult 要查寻的记录数
     * @param whereSql 条件sql语句,占位符用？+数字形式，且数字从1开始累加 eg: username=?1 and password=?2
     * @param obj 条件参数
     * @return
     */
    public Page<T> find(int firstResult, int maxResult, String whereSql, Object[] obj);
    /**
     *  不带查询条件，有排序的分页查询
     * @param firstResult 要查寻的记录数
     * @param maxResult 要查寻的记录数
     * @param orderby 排序参数及排序规则 eg:oderby.put("username","desc");
     * @return
     */
    public Page<T> find(int firstResult, int maxResult, LinkedHashMap<String,String> orderby);
    /**
     *  带有条件，排序的分页查询
     * @param firstResult 要查寻的记录数
     * @param maxResult 要查寻的记录数
     * @param whereSql 条件sql语句,占位符用？+数字形式，且数字从1开始累加 eg: username=?1 and password=?2
     * @param obj 条件参数
     * @param orderby 排序参数及排序规则 eg:oderby.put("username","desc");
     * @return
     */
    public Page<T> find(int firstResult, int maxResult, String whereSql, Object[] obj, LinkedHashMap<String,String> orderby);

}
