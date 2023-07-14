# Sokoban
# Script Tablas Oracle

prompt PL/SQL Developer Export Tables for user JROJAS@XE
prompt Created by jitor on lunes, 26 de junio de 2023
set feedback off
set define off

prompt Dropping TBL_GAMES...
drop table TBL_GAMES cascade constraints;
prompt Creating TBL_GAMES...
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

prompt Disabling triggers for TBL_GAMES...
alter table TBL_GAMES disable all triggers;
prompt Loading TBL_GAMES...
insert into TBL_GAMES (gam_id, gam_playername, gam_game, gam_level, gam_steps)
values (1, 'Reds', '########################   ################  O################   ##############      ############# # ## ###########   # ## #####  +M## O            PMM###### ### # ##  +M######     ############################', 3, 106);
insert into TBL_GAMES (gam_id, gam_playername, gam_game, gam_level, gam_steps)
values (21, 'Jorge', '#######################+##################O    #M############P    # ########### # #  # M########## # # ##   ######### # #      #########       # ############    ## ############M##    ##########################', 6, 2);
commit;
prompt 2 records loaded
prompt Enabling triggers for TBL_GAMES...
alter table TBL_GAMES enable all triggers;

set feedback on
set define on
prompt Done
