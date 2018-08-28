package com.animal.scale.hodoo.domain;

public enum ResultMessage
{
    SUCCESS("성공"),  FAILED("실패");

    private final String name;

    private ResultMessage(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }
}