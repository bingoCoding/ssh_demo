package com.bingo.ssh.shop.base;

import com.bingo.ssh.shop.bean.Page;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 26685 on 2017/7/4.
 */

/**
 * Transactional
 * spring中注解是可以继承的，但是不可以向上延伸。
 *
 * 既在本抽象类中不加入@Transactional注解，只在子类中加入注解，
 * 本类中的方法用到事物会报错
 * javax.persistence.TransactionRequiredException: No EntityManager with actual transaction available for current thread
 *
 * 若只在本类中加入事务控制，不在子类中加注解，
 * 子类也会有事务控制
 *
 */
@NoRepositoryBean
public  class DaoImpl<T,ID extends Serializable> extends SimpleJpaRepository<T, ID> implements Dao<T,ID> {
    @PersistenceContext
    protected EntityManager em;

    private final Class<T> entityClass;
    private final String entityName;


    /** 
    * 构造函数 
    * @param domainClass 
    * @param em 
    */
    public DaoImpl(final JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager){
        super(entityInformation,entityManager);
        this.em = entityManager;
        this.entityClass=entityInformation.getJavaType();
        this.entityName=entityInformation.getEntityName();
    }




    /*--------------------基本增删改查方法--------------------------------------------------------*/
    public void add(T entity) {
        em.persist(entity);
    }

    public void delete(Serializable id) {
        em.remove(em.getReference(entityClass,id));
    }

    public T update(T entity) {
        return em.merge(entity);
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public T findOne(Serializable id) {
        return em.find(entityClass,id);
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public long getCount() {
        return (Long)em.createQuery("select count(1) from "+ entityName+" o").getSingleResult();
    }

    /*---------------------带有分页的查询-----------------------------------------------------------*/
    /**
     *  查询所有数据
     * @return
     */
    public Page<T> find(){
        Page<T> page = new Page<T>();
        StringBuffer sb = new StringBuffer().append("select o from ").append(entityName).append(" o ");
        Query query = em.createQuery(sb.toString());
        page.setResult(query.getResultList());

        query = em.createQuery("select count(1) from "+ entityName +" o ");
        page.setAcount((Long) query.getSingleResult());
        return page;
    }
    /**
     *  带有条件，排序的查询,不带分页
     * @param whereSql 条件sql语句,占位符用？+数字形式，且数字从1开始累加 eg: username=?1 and password=?2
     * @param obj 条件参数
     * @param orderby 排序参数及排序规则 eg:oderby.put("username","desc");
     * @return
     */
    public Page<T> find(String whereSql, Object[] obj, LinkedHashMap<String,String> orderby){
        Page<T> page = new Page<T>();
        StringBuffer sb = new StringBuffer().append("select o from ").append(entityName).append(" o ").append(whereSql);
        if(orderby!=null&&orderby.size()>0){
            sb.append(tanslateOderBySql(orderby));
        }
        Query query = em.createQuery(sb.toString());
        if(!StringUtils.isEmpty(whereSql.trim())){
            for (int i=0;i<obj.length;i++){
                query.setParameter(i+1,obj[i]);
            }
        }
        page.setResult(query.getResultList());

        query = em.createQuery("select count(1) from "+entityName +" o " +whereSql);
        if(!StringUtils.isEmpty(whereSql.trim())){
            for (int i=0;i<obj.length;i++){
                query.setParameter(i+1,obj[i]);
            }
        }
        page.setAcount((Long) query.getSingleResult());
        return page;
    }
    /**
     *  带有查询条件，不排序的分页查询
     * @param firstResult 要查寻的记录数
     * @param maxResult 要查寻的记录数
     * @param whereSql 条件sql语句,占位符用？+数字形式，且数字从1开始累加 eg: username=?1 and password=?2
     * @param obj 条件参数
     * @return
     */
    public Page<T> find(int firstResult, int maxResult, String whereSql, Object[] obj){
        Page<T> page = new Page<T>();
        StringBuffer sb = new StringBuffer().append("select o from ").append(entityName).append(" o ").append(whereSql);
        Query query = em.createQuery(sb.toString());
        query.setFirstResult(firstResult).setMaxResults(maxResult);
        if(!StringUtils.isEmpty(whereSql.trim())){
            for (int i=0;i<obj.length;i++){
                query.setParameter(i+1,obj[i]);
            }
        }
        page.setResult(query.getResultList());

        query = em.createQuery("select count(1) from "+ entityName +" o " +whereSql);
        if(!StringUtils.isEmpty(whereSql.trim())){
            for (int i=0;i<obj.length;i++){
                query.setParameter(i+1,obj[i]);
            }
        }
        page.setAcount((Long) query.getSingleResult());
        return page;
    }
    /**
     *  不带查询条件，有排序的分页查询
     * @param firstResult 要查寻的记录数
     * @param maxResult 要查寻的记录数
     * @param orderby 排序参数及排序规则 eg:oderby.put("username","desc");
     * @return
     */
    public Page<T> find(int firstResult, int maxResult, LinkedHashMap<String,String> orderby){
        Page<T> page = new Page<T>();
        StringBuffer sb = new StringBuffer().append("select o from ").append(entityName).append(" o ");
        if(orderby!=null&&orderby.size()>0){
            sb.append(tanslateOderBySql(orderby));
        }
        Query query = em.createQuery(sb.toString());
        query.setFirstResult(firstResult).setMaxResults(maxResult);
        page.setResult(query.getResultList());

        query = em.createQuery("select count(1) from "+ entityName +" o ");
        page.setAcount((Long) query.getSingleResult());
        return page;
    }
    /**
     *  带有条件，排序的分页查询
     * @param firstResult 要查寻的记录数
     * @param maxResult 要查寻的记录数
     * @param whereSql 条件sql语句,占位符用？+数字形式，且数字从1开始累加 eg: username=?1 and password=?2
     * @param obj 条件参数
     * @param orderby 排序参数及排序规则 eg:oderby.put("username","desc");
     * @return
     */
    public Page<T> find(int firstResult, int maxResult, String whereSql, Object[] obj, LinkedHashMap<String,String> orderby){
        Page<T> page = new Page<T>();
        StringBuffer sb = new StringBuffer().append("select o from ").append(entityName).append(" o ").append(whereSql);
        if(orderby!=null&&orderby.size()>0){
            sb.append(tanslateOderBySql(orderby));
        }
        Query query = em.createQuery(sb.toString());
        query.setFirstResult(firstResult).setMaxResults(maxResult);
        if(!StringUtils.isEmpty(whereSql.trim())){
            for (int i=0;i<obj.length;i++){
                query.setParameter(i+1,obj[i]);
            }
        }
        page.setResult(query.getResultList());

        query = em.createQuery("select count(1) from "+ entityName +" o " +whereSql);
        if(!StringUtils.isEmpty(whereSql.trim())){
            for (int i=0;i<obj.length;i++){
                query.setParameter(i+1,obj[i]);
            }
        }
        page.setAcount((Long) query.getSingleResult());
        return page;
    }

    private String tanslateOderBySql(LinkedHashMap<String,String> orderby){
        StringBuffer sb=new StringBuffer();
        if (orderby!=null&&orderby.size()>0){
            for (Map.Entry<String,String> set : orderby.entrySet()){
                sb.append("o.").append(set.getKey()).append(" ").append(set.getValue()).append(",");
            }
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }
    /**
     * 获取泛型类型
     * 思路：
     *      BuyerServiceImpl extends DaoImpl<Buyer>
     *      通过子类获取泛型
     * 1. 获取子类Class，既BuyerServiceImpl
     *    getClass() 获取运行时类
     *
     * 2. 获取抽象类，抽象类包括泛型 ，既DaoImpl<Buyer>
     *    Type getGenericSuperclass() 获取带有泛型的父类
     *    Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
     *    ParameterizedType 是参数化类型，既泛型，继承Type类
     *
     * 3. 获取泛型类
     *    Type[] getActualTypeArguments() 获取真实参数类型
     */
   /* public Class<T> getEntityClass(){
        Type type = this.getClass().getGenericSuperclass();
        if(type instanceof ParameterizedType){
            ParameterizedType pt = (ParameterizedType)type;
            return (Class<T>) pt.getActualTypeArguments()[0];
        }
        return null;
    }

    *//**
     * 获取泛型数据库表名
     * 思路：
     *      获取泛型类
     *      @Entity
     *      public class Buyer {}
     *      通过泛型类获得注解Entity,获取Entity的属性name
     *
     * 1. 获取泛型类
     *    Class<T> getEntityClass()
     *
     * 2. 获得注解Entity
     *    <A extends Annotation> A getAnnotation(Class<A> annotationClass)
     *
     * 3. 判断Entity的name属性是否为空，非空的话，name值就是表名称
     *    Type[] getActualTypeArguments() 获取真实参数类型
     *
     * @return
     *//*
    public String getEntityName(){
        String entityName = getEntityClass().getSimpleName();
        Entity clazz = getEntityClass().getAnnotation(Entity.class);
        if(null!=clazz.name()&&clazz.name().trim().length()>0){
            entityName=clazz.name();
        }
        return entityName;
    }*/
}
