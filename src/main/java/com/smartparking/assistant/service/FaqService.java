package com.smartparking.assistant.service;

import org.springframework.stereotype.Service;

@Service
public class FaqService {

    public String getAnswer(String message) {

        String question = message.toLowerCase().trim();

        if (question.contains("si funksionon parkimi") || question.contains("how does parking work")) {
            return """
                    Hap harten, zgjidh nje vend parkimi te lire
                    dhe perdor opsionin Navigate per te marre
                    rrugen me te shkurter drejt tij.
                    """;

        } else if (question.contains("si paguaj") || question.contains("payment") || question.contains("paguaj parkimin")) {
            return """
                    Per vendet e parkimit me pagese,
                    zgjidh vendin dhe perdor opsionin vazhdo me pagesen.
                    Pagesa perpunohet ne menyre te sigurt
                    permes Stripe.
                    """;

        } else if (question.contains("vend i lire") || question.contains("parking i lire") || question.contains("free parking")) {
            return """
                    Vendet e lira shfaqen ne harte sipas
                    gjendjes aktuale te marre nga sensorët
                    ose kamera.
                    """;

        } else if (question.contains("ngjyra e gjelber") || question.contains("jeshile")) {
            return """
                    Ngjyra e gjelber tregon qe vendi
                    i parkimit eshte i lire.
                    """;

        } else if (question.contains("ngjyra e kuqe") || question.contains("e kuqe")) {
            return """
                    Ngjyra e kuqe tregon qe vendi
                    i parkimit eshte aktualisht i zene.
                    """;

        } else if (question.contains("out of service") || question.contains("jashte sherbimit")) {
            return """
                    Out of Service do te thote qe vendi
                    i parkimit nuk eshte aktualisht i
                    disponueshem per perdorim.
                    """;

        } else if (question.contains("navigate") || question.contains("navigim") || question.contains("rruga me e shkurter")) {
            return """
                    Pasi zgjedh nje vend parkimi,
                    shtyp Navigate. Sistemi llogarit
                    rrugen me te shkurter drejt vendit.
                    """;
        }
        return null;
    }
}
