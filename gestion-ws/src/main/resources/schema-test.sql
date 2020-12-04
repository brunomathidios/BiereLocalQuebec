CREATE SEQUENCE public.biere_id_seq;
CREATE SEQUENCE public.abonnement_id_seq;
CREATE SEQUENCE public.TYPE_BIERE_ID_SEQ;
CREATE SEQUENCE public.info_biere_id_seq;
CREATE SEQUENCE public.recompense_biere_id_seq;
CREATE SEQUENCE public.box_biere_id_seq;

CREATE TABLE public.type_biere
(
    id_type_biere integer NOT NULL,
    nom character varying(200) NOT NULL,
    description character varying(4000),
    date_creation timestamp with time zone NOT null DEFAULT current_timestamp,
    date_mis_a_jour timestamp with time zone,
    CONSTRAINT pk_type_biere PRIMARY KEY (id_type_biere),
    CONSTRAINT nom_type_biere_unique UNIQUE(nom)
);

CREATE table public.biere
(
    id_biere integer NOT NULL,
    nom character varying(150) NOT NULL,
    origine character varying(200) NOT NULL,
    taux_alcool decimal(5,2) NOT NULL,
    ibu smallint NOT NULL,
    amertume character varying(20) not null,
    description character varying(4000),
    photo BINARY(1000),
    id_type_biere integer NOT NULL,
    date_creation timestamp with time zone NOT null DEFAULT current_timestamp,
    date_mis_a_jour timestamp with time zone,
    CONSTRAINT pk_biere PRIMARY KEY (id_biere),
    CONSTRAINT fk_type_biere FOREIGN KEY (id_type_biere)
        REFERENCES public.type_biere (id_type_biere),
    CONSTRAINT nom_biere_unique UNIQUE(nom)
);

/**
--criar tabela premiacoes
CREATE table IF NOT EXISTS public.recompense_biere
(
    id_recompense_biere integer NOT NULL,
    recompense character varying(1000) NOT NULL,
    id_biere integer NOT NULL,
    date_creation timestamp with time zone NOT null DEFAULT current_timestamp,
    date_mis_a_jour timestamp with time zone,
    CONSTRAINT pk_recompense_biere PRIMARY KEY (id_recompense_biere),
    CONSTRAINT fk_biere FOREIGN KEY (id_biere)
        REFERENCES public.biere (id_biere) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

--criar tabela informacoes do produto
CREATE table IF NOT EXISTS public.info_biere
(
    id_info_biere integer NOT NULL,
    ingredient character varying(1000) NOT NULL,
    harmonisation character varying(1000),
    temperature_ideale character varying(20) NOT null,
    bouche character varying(200),
    nez character varying(200),
    yeux character varying(200),
    id_biere integer NOT NULL,
    date_creation timestamp with time zone NOT null DEFAULT current_timestamp,
    date_mis_a_jour timestamp with time zone,
    CONSTRAINT pk_info_biere PRIMARY KEY (id_info_biere),
    CONSTRAINT fk_biere FOREIGN KEY (id_biere)
        REFERENCES public.biere (id_biere) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

--criar tabela box biere (seleção do mês)
CREATE table IF NOT EXISTS public.box_biere
(
    id_box_biere integer NOT NULL,
    nom character varying(250) NOT NULL,
    description character varying(4000) NOT NULL,
    mois integer NOT NULL,
    annee integer NOT NULL,
    actuel boolean not null,
    photo bytea,
    date_creation timestamp with time zone NOT null DEFAULT current_timestamp,
    date_mis_a_jour timestamp with time zone,
    CONSTRAINT pk_box_biere PRIMARY KEY (id_box_biere)
);

-- tabela many to many box (seleção do mes) e suas cervejas
CREATE table IF NOT EXISTS public.biere_box_biere (
  id_box_biere integer NOT NULL,
  id_biere integer NOT NULL,
  date_creation timestamp with time zone NOT null DEFAULT current_timestamp,
  date_mis_a_jour timestamp with time zone,
  PRIMARY KEY (id_box_biere, id_biere),
  FOREIGN KEY (id_box_biere) REFERENCES box_biere(id_box_biere) ON UPDATE CASCADE,
  FOREIGN KEY (id_biere) REFERENCES biere(id_biere) ON UPDATE CASCADE
);
**/
