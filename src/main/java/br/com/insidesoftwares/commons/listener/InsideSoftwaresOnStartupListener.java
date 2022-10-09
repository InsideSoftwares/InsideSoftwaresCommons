package br.com.insidesoftwares.commons.listener;

import br.com.insidesoftwares.commons.specification.InsideSoftwaresStartupListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Order(1)
@Log4j2
public class InsideSoftwaresOnStartupListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired(required = false)
    private InsideSoftwaresStartupListener insideSoftwaresStartupListener;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("InsideSoftwaresOnStartupListener#onApplicationEvent() - Init");
        if(Objects.nonNull(insideSoftwaresStartupListener)) insideSoftwaresStartupListener.onStartupSystem(event);
        log.info("InsideSoftwaresOnStartupListener#onApplicationEvent() - Final");
    }

}
