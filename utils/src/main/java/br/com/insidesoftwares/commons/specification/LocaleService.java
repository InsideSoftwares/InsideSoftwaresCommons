package br.com.insidesoftwares.commons.specification;

import java.util.Locale;

public interface LocaleService {
    Locale getLocale();
    String getMessage(String code, Object... args);
}
