package com.increff.pos.dao;

import com.increff.pos.pojo.BrandPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BrandDao extends AbstractDao{
    private static String delete_id = "delete from BrandPojo b where id=:id";
    private static String select_id = "select b from BrandPojo b where id=:id";
    private static String select_all = "select b from BrandPojo b";
    private static String select_category = "select b from BrandPojo b where brand=:brand";
    private  static String get_category_id = "select b from BrandPojo b where brand=:brand and category=:category";



    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(BrandPojo b) {
        em.persist(b);
    }


    public BrandPojo select(int id) {
        try {
            TypedQuery<BrandPojo> query = getQuery(select_id, BrandPojo.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        }
        catch(NoResultException e){
            return null;
        }
    }

    public BrandPojo getCategoryId(String brand, String category) {

        TypedQuery<BrandPojo> query = getQuery(get_category_id, BrandPojo.class);
        query.setParameter("brand", brand);
        query.setParameter("category", category);
        return getSingle(query);
    }
    public List<BrandPojo> selectAll() {
        TypedQuery<BrandPojo> query = getQuery(select_all, BrandPojo.class);
        return query.getResultList();
    }

    public List<BrandPojo> getCategory(String brand){
        TypedQuery<BrandPojo> query = getQuery(select_category, BrandPojo.class);
        query.setParameter("brand", brand);
        return query.getResultList();
    }

    public void update(BrandPojo p) {
    }
}
