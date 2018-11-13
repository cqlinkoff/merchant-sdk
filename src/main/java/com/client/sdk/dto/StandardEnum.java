package com.client.sdk.dto;

public enum StandardEnum {
    ETH("ETH"),
    ERC20("ERC20"),
    BTC("BTC");

    private String name;

    StandardEnum(String name) {
        this.name = name;
    }
}
