package me.asite.common;

import lombok.Data;

@Data
public class IsSuccessReponse {

    private boolean success;

    public IsSuccessReponse(boolean success) {
        this.success = success;
    }
}
