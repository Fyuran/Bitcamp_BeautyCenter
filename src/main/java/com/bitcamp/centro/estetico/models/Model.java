package com.bitcamp.centro.estetico.models;

public interface Model {
    Long getId();

    void setId(Long id);

    boolean isEnabled();

    void setEnabled(boolean isEnabled);

    public Object[] toTableRow();
}
