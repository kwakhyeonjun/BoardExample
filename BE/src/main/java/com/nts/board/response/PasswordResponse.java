package com.nts.board.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResponse {
    String authorization;

    public static PasswordResponse from(String token) {
        PasswordResponse passwordResponse = new PasswordResponse();
        passwordResponse.setAuthorization(token);
        return passwordResponse;
    }
}
