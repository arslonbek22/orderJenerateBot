package uz.pdp.order_generate_bot.security.dto;

public record  LoginResponse(String token, Long expiresInSeconds) {
}
