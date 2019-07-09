package com.upgrad.technical.service.business;


import com.upgrad.technical.service.dao.UserDao;
import com.upgrad.technical.service.entity.UserAuthTokenEntity;
import com.upgrad.technical.service.entity.UserEntity;
import com.upgrad.technical.service.exception.AuthenticationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class AuthenticationService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordCryptographyProvider CryptographyProvider;

    @Transactional(propagation = Propagation.REQUIRED)
    public UserAuthTokenEntity authenticate(final String username, final String password) throws AuthenticationFailedException {
        //Idea here is to fetch the user from users table with entered email
        //Call the getUserByEmail() method in UserDao class for userDao object and pass username(email) as argument
        //getUserByEmail() method returns UserEntity type object
        //Receive the value returned by getUserByEmail() method in UserEntity type object(name it as userEntity)
        //Here we are considering that the entered credentials are correct, so this will always return a valid user record from database
        //Write the code here//
        UserEntity userEntity = userDao.getUserByEmail(username);
        if(userEntity == null) {
            //userEntity = null means that getUserByEmail() method did not return any object, which means that user with given email is not present in "users" table
            //throw "AuthenticationFailedException", AuthenticationFailedException is user defined exception and has been implemented
            //Note that the AuthenticationFailedException constructor requires two strings as an argument
            //First string is an Exception code - You can create your own code desired for this exception(e.g. ATH-001)
            //Second string is an Exception message - You can create any user friendly message which indicates that user with given email is not found(User with email not found)
            //Write code here//
            throw new AuthenticationFailedException("ATH-001","Username and/password not matching");
        }
        //After that you have got userEntity from users table, we need to compare the password entered by the user with the password stored in the database
        //Since the password stored in the database is encrypted, so we also encrypt the password entered by the user using the Salt attribute in the database
        //Call the encrypt() method in PasswordCryptographyProvider class for CryptographyProvider object
        //Pass the password entered by the user as the first argument
        //Pass the Salt as the second argument.Get salt from userEntity which has been stored in a database
        //Receive the value returned by the encrypt() method in a String(name it as encryptedPassword)

        //Write the code here//
        final String encryptedPassword = CryptographyProvider.encrypt(password, userEntity.getSalt());

        //Now encryptedPassword contains the password entered by the user in encryppted form
        //And userEntity.getPassword() gives the password stored in the database in encrypted form
        //We compare both the passwords (Note that in this Assessment we are assuming that the credentials are correct)
        if (encryptedPassword.equals(userEntity.getPassword())) {
            //Implementation of JwtTokenProvider class need to be focused
            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);

            //Now UserAuthTokenEntity type object is to be persisted in a databse
            //Declaring an object userAuthTokenEntity of type UserAuthTokenEntity and setting its attributes
            UserAuthTokenEntity userAuthTokenEntity = new UserAuthTokenEntity();

            userAuthTokenEntity.setUser(userEntity);
            final ZonedDateTime now = ZonedDateTime.now();
            final ZonedDateTime expiresAt = now.plusHours(8);

            userAuthTokenEntity.setAccessToken(jwtTokenProvider.generateToken(userEntity.getUuid(), now, expiresAt));

            userAuthTokenEntity.setLoginAt(now);
            userAuthTokenEntity.setExpiresAt(expiresAt);

            //Call the createAuthToken() method in UserDao class for userDao
            //Pass userAuthTokenEntity as an argument
            //Write the code here//

            userDao.createAuthToken(userAuthTokenEntity);

            //To update the last login time of user
            //Carefully read how to update the existing record in a database(will be asked in later Assessments)
            //When the persistence context is closed the entity becomes detached and any further changes to the entity will not be saved
            //You need to associate the detached entity with a persistence context through merge() method to update the entity
            //updateUser() method in UserDao class calls the merge() method to update the userEntity
            userDao.updateUser(userEntity);
            userEntity.setLastLoginAt(now);
            return userAuthTokenEntity;
        } else{
            //Control will come here if user has entered a wrong password but correct email
            //Again throw "AuthenticationFailedException"
            //First string is an Exception code - You can create your own code desired for this exception(e.g. ATH-002)
            //Second string is an Exception message - You can create any user friendly message which indicates that password is wrong(e.g. Password Failed)
            //Write code here//
            throw new AuthenticationFailedException("ATH-002","Username and/password not matching");
        }
    }

}
