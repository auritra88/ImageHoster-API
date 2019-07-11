package com.upgrad.technical.api.controller;


import com.upgrad.technical.api.model.ImageDetailsResponse;
import com.upgrad.technical.service.business.AdminService;
import com.upgrad.technical.service.entity.ImageEntity;
import com.upgrad.technical.service.exception.ImageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AdminController {


    @Autowired
    private AdminService adminService;

    @RequestMapping(method = RequestMethod.GET, path = "/images/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ImageDetailsResponse> getImage(@PathVariable("id") final String imageUuid) throws ImageNotFoundException {


        ImageEntity image=adminService.getImage(imageUuid);

        ImageDetailsResponse imageDetailsResponse = new ImageDetailsResponse();

        Long l=new Long(image.getId());
        imageDetailsResponse.setId(l.intValue());
        imageDetailsResponse.setImage(image.getImage());
        imageDetailsResponse.setDescription(image.getDescription());
        imageDetailsResponse.setName(image.getName());
        imageDetailsResponse.setStatus(image.getStatus());


        //Complete this method
        //Call getImage() method for adminService and pass imageUuid as an argument(note that you get the details of the image after entering its uuid)
        //getImage() method returns ImageEntity type object
        //Declare an object of ImageDetailsResponse and set its attributes using ImageEntity type object returned by getImage() method

        //Return ResponseEntity<ImageDetailsResponse> type object with Http status OK

        return new ResponseEntity<ImageDetailsResponse>(imageDetailsResponse, HttpStatus.OK);
    }
}
