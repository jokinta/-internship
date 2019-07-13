CREATE OR REPLACE PACKAGE sypks_user_login
IS
PROCEDURE pr_login(user_login_nameIN IN VARCHAR2,passwordIN IN VARCHAR2);
PROCEDURE pr_logout(user_login_nameIN IN VARCHAR2);
PROCEDURE pr_get_user_info(user_login_nameIN IN VARCHAR2);
PROCEDURE pr_unblock_user(user_login_nameIN IN VARCHAR2);
PROCEDURE pr_registration(user_login_nameIN IN VARCHAR2, passwordIN IN VARCHAR2, emailIN IN VARCHAR2);
PROCEDURE pr_change_password(user_login_nameIN IN VARCHAR2,new_pass IN VARCHAR2);
FUNCTION pr_ip_address_register(user_name_IN IN VARCHAR2 ,ip_addressIN IN VARCHAR2) RETURN NUMBER;
FUNCTION hash_key (v_input IN VARCHAR2) RETURN dbms_obfuscation_toolkit.varchar2_checksum;
END sypks_user_login;
/
CREATE OR REPLACE PACKAGE BODY sypks_user_login
IS
-------------------------------------------------------------------------
PROCEDURE pr_logout(user_login_nameIN IN VARCHAR2)
IS
BEGIN
 BEGIN 
  UPDATE sytb_user
SET last_logout = sysdate
WHERE user_login_name = user_login_nameIN;
  END;
DBMS_OUTPUT.PUT_LINE('You are logout');


EXCEPTION 
WHEN NO_DATA_FOUND
THEN
DBMS_OUTPUT.PUT_LINE('There is no user with username: ' || user_login_nameIN);


END;
-----------------------------------------------------------------------
PROCEDURE pr_login (
    user_login_namein   IN   VARCHAR2,
    passwordin          IN   VARCHAR2
) IS
BEGIN
    DECLARE
        counter                  INTEGER;
        flag                     INTEGER;
        hash_pass                VARCHAR2(255);
        ip_address_restriction   VARCHAR2(255);
        change_pass_months       NUMBER;
        login_date               DATE;
        last_pass_change         DATE;
        status                   VARCHAR(2);
        v_ld                     login_data;
        wrong_pass_or_username EXCEPTION;
        wrong_pass EXCEPTION;
        block_user_exception EXCEPTION;
        ip_address_exception EXCEPTION;
    BEGIN
        hash_pass := hash_key(passwordin);
        SELECT
            COUNT(*)
        INTO flag
        FROM
            sytb_user
        WHERE
            user_login_name = user_login_namein
            AND password = hash_pass;

        SELECT
            login_counter,
            user_status,
          last_login,
            ip_address_restriction,
            last_password_change
        INTO
            counter,
            status,
            login_date,
            ip_address_restriction,
            last_pass_change
        FROM
            sytb_user
        WHERE
            user_login_name = user_login_namein;

        IF ( flag = 1) THEN
            IF(status='BP' OR status = 'B') THEN
                 RAISE block_user_exception;
                 END IF;
            IF ( ip_address_restriction = 'yes' AND pr_ip_address_register(user_login_namein, '192.0.2.1') = 0  ) THEN
                RAISE ip_address_exception;
            ELSE
                SELECT
                    months_between(TO_DATE(login_date), TO_DATE(last_pass_change))
                INTO change_pass_months
                FROM
                    dual;

                IF ( change_pass_months > 5 ) THEN
                    UPDATE sytb_user
                    SET
                        force_password_change = 'should change'
                    WHERE
                        user_login_name = user_login_namein;
                        v_ld:= login_data(1,'Dear ' || user_login_namein || ' you should change your password','/'); 
                        v_ld.print_values;

                ELSE
                
                    dbms_output.put_line('Congratulations '
                                         || user_login_namein
                                         || ' you are successfully login ');
                    
                    UPDATE sytb_user
                    SET
                        last_login = SYSDATE,
                        login_counter = 0
                    WHERE
                        user_login_name = user_login_namein;

                END IF;

            END IF;
        END IF;

        IF ( flag != 1 ) THEN



            IF ( counter = 5 ) THEN
                UPDATE sytb_user
                SET
                    user_status = 'BP'
                WHERE
                user_login_name = user_login_namein;

                RAISE block_user_exception;
            ELSE
                UPDATE sytb_user
                SET
                    login_counter = login_counter + 1,
                    last_login = SYSDATE
                WHERE
                    user_login_name = user_login_namein;
                RAISE wrong_pass;
            END IF;

            RAISE wrong_pass_or_username;
        END IF;

    EXCEPTION
        WHEN no_data_found THEN
                            v_ld:= login_data(0,'There is no user with username:'  || user_login_namein,'001');
                            v_ld.print_values;
        WHEN wrong_pass THEN
                            v_ld:= login_data(0,'Wrong password you have ' || (5 - counter) || ' tries','002');
                            v_ld.print_values;
        WHEN wrong_pass_or_username THEN
                            v_ld:= login_data(0,'Wrong password or username' ,'003');
                            v_ld.print_values;
        WHEN block_user_exception THEN
                            v_ld:= login_data(0,'Your status is blocked' ,'004');
                            v_ld.print_values;
        WHEN ip_address_exception THEN
                            v_ld:= login_data(0,'Your status is blocked' ,'005');
                            v_ld.print_values;

    END;
END;
---------------------------------------------------------------  
PROCEDURE pr_get_user_info(user_login_nameIN IN VARCHAR2)
IS 
BEGIN
DECLARE
user_id VARCHAR2(255);
username VARCHAR2(255);
email VARCHAR2(255);
userstatus VARCHAR2(2);
datecreated VARCHAR2(255);
last_modification DATE;
last_login DATE;
last_logout DATE;
force_password_change VARCHAR2(255);
ip_address_restriction  VARCHAR2(255);
last_password_change DATE;

BEGIN

SELECT user_id,user_login_name,email,user_status
INTO user_id,username,email,userstatus
FROM sytb_user
WHERE user_login_name = user_login_nameIN;
DBMS_OUTPUT.PUT_LINE('User-> ' || username || '| With Id:-> ' || user_id ||'| Email-> ' || email ||' | Has status -> '|| userstatus);
EXCEPTION 
WHEN NO_DATA_FOUND
THEN
DBMS_OUTPUT.PUT_LINE('There is no user with username: ' || user_login_nameIN);

END;
END;

--------------------------------------------------------
PROCEDURE pr_unblock_user(user_login_nameIN IN VARCHAR2)
IS
BEGIN
BEGIN
UPDATE sytb_user
SET user_status = 'E',login_counter=0
WHERE user_login_name = user_login_nameIN;
IF SQL%NOTFOUND THEN
            RAISE NO_DATA_FOUND;
  ELSE 
  DBMS_OUTPUT.PUT_LINE('User status enabled');
END IF;



EXCEPTION 
WHEN NO_DATA_FOUND
THEN
DBMS_OUTPUT.PUT_LINE('There is no user with username: ' || user_login_nameIN);
END; 
END;

----------------------------------------------------------------------------------------------------
PROCEDURE pr_registration(user_login_nameIN IN VARCHAR2, passwordIN IN VARCHAR2, emailIN IN VARCHAR2)
IS
BEGIN
DECLARE
hash_pass VARCHAR2(255);
userexist INTEGER;
BEGIN
 SELECT COUNT(*) INTO userexist 
  FROM sytb_user
  WHERE user_login_name=user_login_nameIN;
  IF (userexist = 0)
             THEN

             hash_pass:=hash_key(passwordIN);
              DBMS_OUTPUT.PUT_LINE('Congratulations ' || user_login_nameIN || ' you are successfully registered');

              INSERT INTO sytb_user(user_id,user_login_name,password,email,user_status,date_created,last_password_change,login_counter)
              VALUES (user_id_SEQ.nextval,user_login_nameIN,hash_pass,emailIN,'E',sysdate,sysdate,0);

ELSE 
DBMS_OUTPUT.PUT_LINE('The user: '|| user_login_nameIN || ' is already is already registered');
END IF;
 END;             
END;
-----------------------------------------------------------------------------------------------------------------------------
PROCEDURE pr_change_password(user_login_nameIN IN VARCHAR2,new_pass IN VARCHAR2)
IS
BEGIN
DECLARE hash_pass VARCHAR2(255);
BEGIN
hash_pass:=hash_key(new_pass);
UPDATE sytb_user
SET password = hash_pass,last_password_change = sysdate,force_password_change = 'ok'
WHERE user_login_name = user_login_nameIN;
IF SQL%NOTFOUND THEN
  DBMS_OUTPUT.PUT_LINE('There is no user with username: ' || user_login_nameIN);
  ELSE 
DBMS_OUTPUT.PUT_LINE('Password successfully changed');
END IF;


EXCEPTION 
WHEN NO_DATA_FOUND 
THEN
DBMS_OUTPUT.PUT_LINE('There is no user with username: ' || user_login_nameIN);

END;
END;
--------------------------------------------------------------------------------------------------------
FUNCTION pr_ip_address_register(user_name_IN IN VARCHAR2 ,ip_addressIN IN VARCHAR2)
RETURN number 
IS 
    flag number; 
BEGIN
SELECT COUNT(*) INTO flag
FROM sytb_user JOIN ip_address_restriction
ON sytb_user.user_id = ip_address_restriction.user_id
WHERE sytb_user.user_login_name = user_name_IN
AND ip_address_restriction.ip_address = ip_addressIN;
IF(flag>0)
THEN 
RETURN flag;
ELSE
RETURN flag;
END IF;
END;
-------------------------------------------------------------------------
FUNCTION hash_key (v_input IN VARCHAR2)
   RETURN dbms_obfuscation_toolkit.varchar2_checksum
AS
BEGIN
   RETURN dbms_obfuscation_toolkit.md5(input_string => v_input);
END hash_key;

END sypks_user_login;
