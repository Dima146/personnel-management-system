package com.company.managementsystem.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class MvcExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MvcExceptionHandler.class);

    @ExceptionHandler(DaoException.class)
    public ModelAndView handleError(HttpServletRequest request, DaoException exception) {

        LOGGER.error("Request " + request.getRequestURI() + " throws " + exception);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleError(HttpServletRequest request, EntityNotFoundException exception) {

        LOGGER.error("Request " + request.getRequestURI() + " throws " + exception);
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.setViewName("404");
        return mav;
    }
}
