create table coleta_qrcode
(
    id                      bigint auto_increment
        primary key,
    ativo                   bit          null,
    creation_date           datetime     null,
    update_date             datetime     null,
    uuid                    varchar(255) null,
    estacao_id              bigint       null,
    error                   varchar(255) null,
    integration_description varchar(255) null,
    integration_status      varchar(255) null
);

