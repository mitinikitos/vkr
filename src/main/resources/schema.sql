CREATE TABLE IF NOT EXISTS own_operator (
    name text PRIMARY KEY,
    address text,
    phones text[],
    email text,
    fax text
);

CREATE TABLE IF NOT EXISTS engine (
    id uuid PRIMARY KEY,
    eng_count integer,
    eng_pwr integer,
    dvig text
);

CREATE TABLE IF NOT EXISTS ship (
    reg_num integer PRIMARY KEY,
    name text,
    type text,
    sub_type text,
    imo integer,
    call_sign text,
    project text,
    god_p integer,
    own_name text REFERENCES own_operator(name),
    operator_name text REFERENCES own_operator(name)
);

CREATE TABLE IF NOT EXISTS ship_capacity (
    reg_num integer REFERENCES ship(reg_num),
    dedv integer,
    pass_k integer,
    pass_p integer,
    gt integer,
    nt integer
);

CREATE TABLE IF NOT EXISTS ship_dimensions (
    reg_num integer REFERENCES ship(reg_num),
    disp integer,
    length double,
    breadth double,
    draught double,
    class text
);

CREATE TABLE IF NOT EXISTS ship_engine (
    reg_num integer REFERENCES ship(reg_num),
    eng1 uuid REFERENCES engine(id),
    eng2 uuid REFERENCES engine(id),
    eng3 uuid REFERENCES engine(id),
    sum_pwr integer
);

CREATE TABLE IF NOT EXISTS role (
    id bigint not null,
    description text,
    name text,
    primary key (id)
);

CREATE TABLE IF NOT EXISTS users (
    id uuid primary key,
    user_name text,
    password text,
    phone text,
    email text
);

CREATE TABLE IF NOT EXISTS user_role (
    user_id uuid REFERENCES users(id),
    role_id bigint REFERENCES role(id)
);