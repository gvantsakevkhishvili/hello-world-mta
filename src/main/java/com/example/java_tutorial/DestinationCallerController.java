package com.example.java_tutorial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class DestinationCallerController {

    private static final Logger logger = LoggerFactory.getLogger(DestinationCallerController.class);

    private final DestinationCallerService destinationCallerService;

    @Autowired
    public DestinationCallerController(DestinationCallerService destinationCallerService) {
        this.destinationCallerService = destinationCallerService;
    }

    @GetMapping("/call-destination")
    public ResponseEntity<String> callDestination() {
        try {
            String response = destinationCallerService.callSecondApp();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            logger.error(e.getMessage());

            return new ResponseEntity<>("Error during destination call: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error(e.getMessage());

            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
