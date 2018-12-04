package com.animal.scale.hodoo.custom.view.input;

public interface CustomCommonEditTextIn {

    void setTitle(String titleMessage);

    void setErrorMessage(String message);

    void setErrorMessageViewisExposed(Boolean exposed);

    void setStatus(boolean status);

    boolean getStatus();

    String getText();
}
