package br.com.insidesoftwares.commons.specification;

import org.springframework.boot.context.event.ApplicationReadyEvent;

public interface InsideSoftwaresStartupListener {
    void onStartupSystem(ApplicationReadyEvent event);
}
