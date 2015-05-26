--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: address; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE address (
    id integer NOT NULL,
    inhabitant_name character varying(100),
    street_address character varying(100),
    zip_code integer,
    city character varying(50),
    country character varying(50)
);


ALTER TABLE address OWNER TO postgres;

--
-- Name: cart; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cart (
    id integer NOT NULL
);


ALTER TABLE cart OWNER TO postgres;

--
-- Name: customer; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE customer (
    id character varying(20) NOT NULL,
    first_name character varying(50),
    sur_name character varying(50),
    email character varying(100),
    phonenumber character varying(20),
    address integer,
    shopping_cart integer NOT NULL
);


ALTER TABLE customer OWNER TO postgres;

--
-- Name: order; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "order" (
    customer_id character varying(20),
    id integer NOT NULL,
    price numeric(19,4),
    status integer,
    payment_method character varying(50),
    purchase_date date,
    dispatched_date date,
    cart_id integer NOT NULL,
    delivery_address integer
);


ALTER TABLE "order" OWNER TO postgres;

--
-- Name: product; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE product (
    model character varying(25) NOT NULL,
    price numeric(19,4),
    type character varying(15),
    name character varying(50)
);


ALTER TABLE product OWNER TO postgres;

--
-- Name: productline; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE productline (
    cart_id integer NOT NULL,
    product_model character varying(25) NOT NULL,
    quantity integer
);


ALTER TABLE productline OWNER TO postgres;

--
-- Name: wishlist; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE wishlist (
    customer_id character varying(20),
    id integer,
    name character varying(30)
);


ALTER TABLE wishlist OWNER TO postgres;

--
-- Data for Name: address; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY address (id, inhabitant_name, street_address, zip_code, city, country) FROM stdin;
\.


--
-- Data for Name: cart; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cart (id) FROM stdin;
1
2
3
4
5
\.


--
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY customer (id, first_name, sur_name, email, phonenumber, address, shopping_cart) FROM stdin;
c123456	Hans	Hansen	hans.hansen@hotmail.com	+4512345678	\N	1
c223456	Peter	Petersen	peter.petersen@hotmail.com	+4522345678	\N	2
c323456	Gorm	Gormsen	gorm.gormsen@hotmail.com	+4532345678	\N	3
c423456	Lars	Larsen	lars.larsen@hotmail.com	+4542345678	\N	4
c523456	Hanne	Hansen	hanne.hansen@hotmail.com	+4552345678	\N	5
\.


--
-- Data for Name: order; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "order" (customer_id, id, price, status, payment_method, purchase_date, dispatched_date, cart_id, delivery_address) FROM stdin;
\.


--
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY product (model, price, type, name) FROM stdin;
FD8350FRW8KHK	179.9900	Processor	AMD FX-8350
FD9590FHW8KHK	239.9900	Processor	AMD FX-9590
FD6300WMW6KHK	109.9900	Processor	AMD FX-6300
FD4100WMW4KGU	79.9900	Processor	AMD FX-4100
FD8120FRW8KGU	100.0000	Processor	AMD FX-8120
AD740XOKA44HJ	72.8500	Processor	AMD 740
AD750KWOA44HJ	76.9900	Processor	AMD 750k
BX80646I74770K	335.9900	Processor	Intel i7-4770K
BX80646I74790K	339.9900	Processor	Intel i7-4790K
BX80646I54670K	234.9900	Processor	Intel i5-4670K
BX80646I54690K	239.9900	Processor	Intel i5-4690K
BX80637I53570K	239.9900	Processor	Intel i5-3570K
N82E16813131876	229.9900	Bundkort	Asus ROG Crosshair V Formula-Z
N82E16813131883	94.9900	Bundkort	Asus F2A85-M PRO
N82E16813131975	159.9900	Bundkort	Asus ROG Maximus VI Gene
N82E16813131854	299.9900	Bundkort	Asus ROG MAXIMUS V FORMULA
N82E16813132247	349.9900	Bundkort	ASUS MAXIMUS VII FORMULA
N82E16813132125	214.9900	Bundkort	Asus MAXIMUS VII HERO
KHX16C9T3K2/8X	112.8600	Hukommelse	Kingston DDR3 HyperX Beast 1600mhz 8GB
KHX16C9T3K2/16X	199.7900	Hukommelse	Kingston DDR3 HyperX Beast 1600mhz 16GB
KHX16C9T3K4/32X	346.2800	Hukommelse	Kingston DDR3 HyperX Beast 1600mhz 32GB
HX324C11T3K2/16	187.6200	Hukommelse	Kingston DDR3 HyperX Beast 2400mhz 16GB
CMD16GX3M4A1866C9	273.8400	Hukommelse	Corsair Dominator DDR3 1866mhz 16GB
CMD16GX4M4B3000C14	868.9000	Hukommelse	Corsair Dominator DDR4 3000mhz 16GB
STRIX-GTX970-DC2OC-4GD5	454.6500	Grafikkort	ASUS GeForce STRIX GTX 970 4GB
04G-P4-2974-KR	417.1200	Grafikkort	EVGA GeForce GTX 970 4GB
GTXTITANX-12GD5	1000.0000	Grafikkort	ASUS GeForce GTX TITAN X 12GB
ZT-90301-10M	260.6300	Grafikkort	ZOTAC GeForce GTX 960 2GB
GV-R928XOC-3GD	301.2000	Grafikkort	Gigabyte Radeon R9 280X 3GB
100361-2SR	339.9900	Grafikkort	SAPPHIRE TRI-X OC 290X 4GB
02G-P4-3753-KR	188.2500	Grafikkort	EVGA GeForce GTX 750Ti 2GB
FD-CA-DEF-R5-BK	121.5900	Kabinetter	Fractal Design Define R5 Black
CC-9011035-WW	192.5400	Kabinetter	Corsair Obsidian 750D Big Tower Black
ROC-11-80	94.4900	Mus & Keyboards	ROCCAT Kone XTD - Max Customization
RZ-01-01040100-R3G1	72.6200	Mus & Keyboards	Razer Naga
90LM00U0-B013L0	769.8100	Skærme	Asus 27\\" LED G-Sync ROG SWIFT PG278QE
WD1003FZEX	94.4400	Harddiske	WD Desktop Black 1TB
\.


--
-- Data for Name: productline; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY productline (cart_id, product_model, quantity) FROM stdin;
\.


--
-- Data for Name: wishlist; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY wishlist (customer_id, id, name) FROM stdin;
\.


--
-- Name: address_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY address
    ADD CONSTRAINT address_pkey PRIMARY KEY (id);


--
-- Name: cart_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cart
    ADD CONSTRAINT cart_pkey PRIMARY KEY (id);


--
-- Name: customer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);


--
-- Name: order_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "order"
    ADD CONSTRAINT order_pkey PRIMARY KEY (id);


--
-- Name: product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY product
    ADD CONSTRAINT product_pkey PRIMARY KEY (model);


--
-- Name: productline_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY productline
    ADD CONSTRAINT productline_pkey PRIMARY KEY (cart_id, product_model);


--
-- Name: customer_address_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT customer_address_fkey FOREIGN KEY (address) REFERENCES address(id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- Name: customer_shopping_cart_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT customer_shopping_cart_fkey FOREIGN KEY (shopping_cart) REFERENCES cart(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: order_cart_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "order"
    ADD CONSTRAINT order_cart_id_fkey FOREIGN KEY (cart_id) REFERENCES cart(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: order_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "order"
    ADD CONSTRAINT order_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES customer(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: order_delivery_address_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "order"
    ADD CONSTRAINT order_delivery_address_fkey FOREIGN KEY (delivery_address) REFERENCES address(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: productline_cart_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY productline
    ADD CONSTRAINT productline_cart_id_fkey FOREIGN KEY (cart_id) REFERENCES cart(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: productline_product_model_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY productline
    ADD CONSTRAINT productline_product_model_fkey FOREIGN KEY (product_model) REFERENCES product(model) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: wishlist_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY wishlist
    ADD CONSTRAINT wishlist_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES customer(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: wishlist_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY wishlist
    ADD CONSTRAINT wishlist_id_fkey FOREIGN KEY (id) REFERENCES cart(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

