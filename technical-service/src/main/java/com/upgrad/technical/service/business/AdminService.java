package com.upgrad.technical.service.business;


import com.upgrad.technical.service.dao.ImageDao;
import com.upgrad.technical.service.dao.UserDao;
import com.upgrad.technical.service.entity.ImageEntity;
import com.upgrad.technical.service.entity.UserAuthTokenEntity;
import com.upgrad.technical.service.entity.UserEntity;
import com.upgrad.technical.service.exception.ImageNotFoundException;
import com.upgrad.technical.service.exception.UnauthorizedException;
import com.upgrad.technical.service.exception.UploadFailedException;
import com.upgrad.technical.service.exception.UserNotSignedInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private ImageDao imageDao;

    public ImageEntity getImage(final String imageUuid, final String authorization) throws ImageNotFoundException, UnauthorizedException, UserNotSignedInException {

        //Get the record from user_auth_tokens table in UserAuthTokenEntity type object with the received accesstoken
        //Call getUserAuthToken() method
        //Write code here//
        UserAuthTokenEntity userAuthTokenEntity=imageDao.getUserAuthToken(authorization);

        //If you get no record, throw user defined exception(UserNotSignedInException, the implementation of UserNotSignedInException has been done)
        //Pass two String type arguments
        //First argument is Exception code(e.g. USR-001)
        //Second argument is Exception message(e.g. You are not Signed in, sign in first to get the image details)
        //Write code here//
        if (userAuthTokenEntity == null) {
            //throw user defined exception(UploadFailedException has been implemented), so throw UploadFailedException
            //Exception constructor takes two arguments
            //Pass first argument as Exception code(String)(e.g. UP-001 )
            //Pass second argumment as Exception message(String) (e.g. User is not Signed in, sign in to upload an image)
            //Write code here//

            throw new UnauthorizedException("UP-001","User is not Signed in, sign in to upload an image");
        }


        //Now we need to check whether the user with corresponding access token is an admin or not
        //Get user from UserAuthTokenEntity type object and check

        UserEntity user=userAuthTokenEntity.getUser();

        //If he is an admin then get the image details using getImage() method in ImageDao class else throw UnauthorizedException(user-defined)(UnauthorizedException has been implemented and accepts two arguments, both are Strings, first is Exception code and second is Exception message)
        //Also throw an ImageNotFoundException if the image uuid is incorrect(ImageNotException has been implemented and accepts two arguments, both are Strings, first is Exception code and second is Exception message)
        //Write code here//

        ImageEntity image;

        if(user.getRole().contains("admin")){

            image=imageDao.getImage(imageUuid);
            if(image == null) {
                throw new ImageNotFoundException("IMG001","No such image by that uuid");
            }

        }else {
            throw new UnauthorizedException("U001","User is not authorized the get the image");
        }

        return image;
    }
}
