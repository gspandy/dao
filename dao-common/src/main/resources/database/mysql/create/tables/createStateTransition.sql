CREATE TABLE abp_STATE_TRANSITION (
      NAME varchar(200) ,
      FROM_STATE varchar(200) ,
      TO_STATE varchar(200) ,
     STATE_NODE_TRANSITIONS_ID BIGINT  ,
  ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'The identity for state transition'
)

 