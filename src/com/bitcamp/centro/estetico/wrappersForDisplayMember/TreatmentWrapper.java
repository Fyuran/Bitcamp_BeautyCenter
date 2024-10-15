package com.bitcamp.centro.estetico.wrappersForDisplayMember;
import com.bitcamp.centro.estetico.models.Treatment;

public class TreatmentWrapper {
    private Treatment treatment;

    public TreatmentWrapper(Treatment treatment) {
        this.treatment = treatment;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    @Override
    public String toString() {        
        return treatment.getType();
    }
}