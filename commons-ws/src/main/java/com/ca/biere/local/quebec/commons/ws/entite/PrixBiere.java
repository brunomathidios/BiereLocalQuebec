package com.ca.biere.local.quebec.commons.ws.entite;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "prix_biere")
@SequenceGenerator(name = "prix_biere_id_seq", sequenceName = "prix_biere_id_seq", allocationSize = 1, initialValue = 1)
public class PrixBiere extends BaseEntite {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prix_biere_id_seq")
    @Column(name = "id_prix_biere")
    private Long idPrix;

    @NotBlank(message = "Le champ 'prix' est obligatoire")
    @Size(max = 1000, message = "Le champ 'prix' doit avoir au maximum 1000 caractères")
    @Column(name = "prix")
    private String prix;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_biere", updatable = false, insertable = false)
    private Biere biere;

    @NotNull(message = "Le champ 'bière' est obligatoire")
    @Column(name = "id_biere")
    private Long idBiere;

    public Long getIdPrix() {
        return idPrix;
    }

    public void setIdPrix(Long idPrix) {
        this.idPrix = idPrix;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public Biere getBiere() {
        return biere;
    }

    public void setBiere(Biere biere) {
        this.biere = biere;
    }

    public Long getIdBiere() {
        return idBiere;
    }

    public void setIdBiere(Long idBiere) {
        this.idBiere = idBiere;
    }
}
