package com.happypets.app_veterinaria_backend.room.application.service;

import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Room normalizer service class
 *
 */
@Service
public class RoomNameNormalizerService {

    private static final Pattern WHITESPACE = Pattern.compile("\\s+", Pattern.UNICODE_CHARACTER_CLASS);
    private static final Pattern DIACRITICS = Pattern.compile("\\p{M}+");
    private static final Pattern NON_ALPHANUMERIC = Pattern.compile("[^\\p{L}\\p{N}]+");

    /**
     * Helper method to sanitize the name of the room
     */
    public String sanitize(String name) {
        if (name == null) {
            return null;
        }
        String composed = Normalizer.normalize(name, Normalizer.Form.NFC);
        return WHITESPACE.matcher(composed).replaceAll(" ").strip();
    }

    /**
     * Principal normalize key method
     */
    public String normalize(String name) {
        if (name == null) {
            return null;
        }
        String decomposed = Normalizer.normalize(sanitize(name), Normalizer.Form.NFD);
        String withoutAccents = DIACRITICS.matcher(decomposed).replaceAll("");
        return NON_ALPHANUMERIC.matcher(withoutAccents.toLowerCase(Locale.ROOT)).replaceAll("");
    }

    /**
     * Optional fields
     */
    public String cleanOptional(String text) {
        return (text == null || text.isBlank()) ? null : text.strip();
    }
}