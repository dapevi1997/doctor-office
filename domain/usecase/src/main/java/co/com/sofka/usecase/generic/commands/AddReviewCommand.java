package co.com.sofka.usecase.generic.commands;

import co.com.sofka.usecase.generic.Command;

public class AddReviewCommand extends Command {
    private String patientId;
    private  String reviewId;
    private String annotation;

    public AddReviewCommand() {
    }

    public AddReviewCommand(String patientId, String reviewId, String annotation) {
        this.patientId = patientId;
        this.reviewId = reviewId;
        this.annotation = annotation;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getReviewId() {
        return reviewId;
    }

    public String getAnnotation() {
        return annotation;
    }
}
