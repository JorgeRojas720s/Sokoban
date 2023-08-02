
#Tables BD

-- Create table
create table TBL_GAMES
(
  gam_id         NUMBER(3) not null,
  gam_playername VARCHAR2(25),
  gam_game       VARCHAR2(250),
  gam_level      NUMBER(2),
  gam_steps      NUMBER(3)
)
tablespace UNA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints 
alter table TBL_GAMES
  add constraint PK_IDGAME primary key (GAM_ID)
  using index 
  tablespace UNA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

