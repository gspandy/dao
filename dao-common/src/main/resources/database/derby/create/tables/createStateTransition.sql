-- http://db.apache.org/derby/docs/10.4/ref/
CREATE TABLE abp_STATE_TRANSITION(
      NAME varchar(200),
      FROM_STATE varchar(200),
      TO_STATE varchar(200),
     STATE_NODE_TRANSITIONS_ID BIGINT,
  ID BIGINT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1))


 