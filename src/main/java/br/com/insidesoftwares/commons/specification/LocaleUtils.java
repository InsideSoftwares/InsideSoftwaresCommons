package br.com.insidesoftwares.commons.specification;

import java.util.Locale;

public interface LocaleUtils {
    Locale getLocale();
    String getMessage(String code, String... args);
}
