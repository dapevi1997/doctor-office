package co.com.sofka.model.patient.values;


import co.com.sofka.model.patient.generic.Identity;

public class ReviewId extends Identity {
  /*  public ReviewId() {
    }*/

    private ReviewId(String uuid) {
        super(uuid);
    }

    public static ReviewId of(String uuid){
        return new ReviewId(uuid);
    }
}
