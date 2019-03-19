package com.meutley.studioadmintoolkit.core.model;

import javax.persistence.Column;

public abstract class SoftDeleteEntity extends BaseEntity {

    @Column(name = "is_active")
    private boolean isActive;

    public boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean value) {
        this.isActive = value;
    }
    
}