package wrappersForDisplayMember;
import com.centro.estetico.bitcamp.Treatment;

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