CREATE TABLE public.users (
    createtime time without time zone,
    createuser character varying(255),
    deleted boolean NOT NULL,
    department character varying(255),
    enable boolean NOT NULL,
    password character varying(255),
    role character varying(255),
    uf character varying(255),
    updatetime time without time zone,
    username character varying(255),
    id bigint NOT NULL
);
ALTER TABLE public.users ADD CONSTRAINT users_pkey PRIMARY KEY (id);
CREATE UNIQUE INDEX users_pkey ON public.users USING btree (id);
