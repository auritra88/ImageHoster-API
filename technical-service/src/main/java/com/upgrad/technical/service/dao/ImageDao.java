package com.upgrad.technical.service.dao;

import com.upgrad.technical.service.entity.ImageEntity;
import com.upgrad.technical.service.entity.UserAuthTokenEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class ImageDao {



    @PersistenceContext
    private EntityManager entityManager;

    public ImageEntity createImage(ImageEntity imageEntity) {
        //In this method we need to attach imageEntity to persistence context
        //Call persist() method for entityManager and pass imageEntity as argument
        //Write code here//
        entityManager.persist(imageEntity);
        return imageEntity;
    }

    public UserAuthTokenEntity getUserAuthToken(final String accessToken) {
        //Complete this method to get the record from user_auth_tokens table with corresponding accesstoken received as an argument
        //This method returns UserAuthTokenEntity type object if the record is found and returns null if NoResultException is caught
        //Examine the query that has been written in UserAuthTokenEntity
        //Write code here//
        try {
            return entityManager.createNamedQuery("userAuthTokenByAccessToken" , UserAuthTokenEntity.class).setParameter("accessToken" , accessToken).getSingleResult();
        } catch(NoResultException e) {
            return null;
        }

    }

    public ImageEntity getImage(final String imageUuid) {
        try {
            return entityManager.createNamedQuery("ImageEntityByUuid", ImageEntity.class).setParameter("uuid", imageUuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
