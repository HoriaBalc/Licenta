package io.getarrays.userservice.service;

import io.getarrays.userservice.domain.AppUser;

public interface EmailService {
    public static void sendRegisterEmail(AppUser user) {}
    public static void sendPasswordResetEmail(AppUser user) {}
    private static void sendEmail(String email, String subject, String text) {}
}
