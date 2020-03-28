package bam.balaji.poc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvise {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAdvise.class);

    @ExceptionHandler(value = Exception.class)
    public void log(Exception e) {
        logger.error(e.getLocalizedMessage());
    }
}
