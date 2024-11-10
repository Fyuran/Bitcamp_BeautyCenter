package com.bitcamp.centro.estetico.models;

import java.util.Map;

public interface Model {

    Long getId();

    void setId(Long id);

    boolean isEnabled();

    void setEnabled(boolean isEnabled);

    public Map<String, Object> toTableRow();
}
