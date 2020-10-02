package es.pastorg.mpgstrava.repository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name ="client_comments")
public class ClientComments {

    @Id
    private UUID rowtableid;
    private String clientemail;
    private String clientcomment;
    private boolean answered;
    private String answertext;
    private String operatoranswered;

    public UUID getRowtableid() {
        return rowtableid;
    }

    public void setRowtableid(UUID rowtableid) {
        this.rowtableid = rowtableid;
    }

    public String getClientemail() {
        return clientemail;
    }

    public void setClientemail(String clientemail) {
        this.clientemail = clientemail;
    }

    public String getClientcomment() {
        return clientcomment;
    }

    public void setClientcomment(String clientcomment) {
        this.clientcomment = clientcomment;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public String getAnswertext() {
        return answertext;
    }

    public void setAnswertext(String answertext) {
        this.answertext = answertext;
    }

    public String getOperatoranswered() {
        return operatoranswered;
    }

    public void setOperatoranswered(String operatoranswered) {
        this.operatoranswered = operatoranswered;
    }

    @Override
    public String toString() {
        return "{" +
                "rowtableid=" + rowtableid +
                ", clientemail='" + clientemail + '\'' +
                ", clientcomment='" + clientcomment + '\'' +
                ", answered=" + answered +
                ", answertext='" + answertext + '\'' +
                ", operatoranswered='" + operatoranswered + '\'' +
                '}';
    }
}
