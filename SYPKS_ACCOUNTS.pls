CREATE OR REPLACE PACKAGE sypks_accounts
IS 
PROCEDURE create_account(user_idIN IN INTEGER,currency IN VARCHAR,deposit IN NUMBER);
PROCEDURE getTurnover(acc_idIN IN VARCHAR2, from_date IN DATE, end_date IN DATE);
PROCEDURE change_acc_status(user_idIN IN INTEGER,new_status IN VARCHAR);
FUNCTION generateIBAN(currency IN VARCHAR,check_number IN INTEGER, bank_identifier IN INTEGER, acc_number IN INTEGER) RETURN VARCHAR;
FUNCTION calculate_amount(from_ccy IN VARCHAR2, to_ccy VARCHAR2) RETURN NUMBER;
FUNCTION calculateCharge(amount IN NUMBER,from_ccy IN VARCHAR2, to_ccy IN VARCHAR2, description IN VARCHAR2) RETURN VARCHAR;

END sypks_accounts;
/
CREATE OR REPLACE PACKAGE BODY sypks_accounts
IS
PROCEDURE create_account(user_idIN IN INTEGER,currency IN VARCHAR,deposit IN NUMBER)
IS
BEGIN
DECLARE 
user_name VARCHAR2(255);
user_iban VARCHAR(255);
flag NUMBER;
amount_exception EXCEPTION;
same_acc_exception EXCEPTION;
BEGIN
IF(deposit=0 OR deposit<0)
THEN
RAISE amount_exception;
END IF;


SELECT user_login_name INTO user_name
FROM sytb_user 
WHERE sytb_user.user_id = user_idIN;


SELECT COUNT(*) INTO flag
FROM sytb_accounts
WHERE user_id = user_idIN
AND account_ccy=currency;
IF(flag>0) THEN
RAISE same_acc_exception;
END IF;                    


user_iban:= generateIBAN(currency,12,9321,acc_idSEQ.nextval);

INSERT INTO sytb_accounts(user_id, account_id, account_ccy,ac_open_date, account_status, date_created, date_last_modification, modification_no, lcy_curr_balance,
                          acy_month_turnover_cr, acy_month_turnover_dr, acy_year_turnover_cr,
                          acy_year_turnover_dr,iban_ac_no) 
                          VALUES (user_idIN,acc_idSEQ.nextval,currency,sysdate, 'E',sysdate,sysdate,0,deposit,0,0,0,0,user_iban);
DBMS_OUTPUT.PUT_LINE('Dear ' || user_name ||' your account is successfully created!');     

EXCEPTION 
WHEN same_acc_exception
THEN DBMS_OUTPUT.PUT_LINE('Account already exists ');
WHEN amount_exception
THEN DBMS_OUTPUT.PUT_LINE('Amount must be greater than zero');


END;
END;
----------------------------------------------------------------------------------------------------------------------------------------
PROCEDURE getTurnover(acc_idIN IN VARCHAR2, from_date IN DATE, end_date IN DATE)
IS
BEGIN
DECLARE 
credit_sum NUMBER :=0;
debit_sum NUMBER :=0;
CURSOR c_get_debit_turnover IS
SELECT user_turnover(account_id,to_account_id,trn_id,drcr_indicator,lcy_amount,trn_date,trn_description)
FROM sytb_transactions
WHERE  account_id =acc_idIN
AND sytb_transactions.trn_date >= from_date
AND sytb_transactions.trn_date <= end_date+1;
TYPE user_turnovers IS TABLE OF user_turnover;
credit_turnover user_turnovers;
BEGIN
OPEN c_get_debit_turnover;
LOOP
FETCH c_get_debit_turnover BULK COLLECT INTO credit_turnover;
EXIT WHEN credit_turnover.COUNT =0;
FOR I IN 1..credit_turnover.LAST
LOOP
credit_turnover(I).print_values;
credit_sum:=credit_sum+credit_turnover(I).get_credit_amount;
debit_sum:=debit_sum+credit_turnover(I).get_debit_amount;
END LOOP;
END LOOP;

CLOSE c_get_debit_turnover;
DBMS_OUTPUT.PUT_LINE('Credit:-> ' || credit_sum || '  Debit:-> ' || debit_sum);

EXCEPTION
WHEN NO_DATA_FOUND THEN
DBMS_OUTPUT.PUT_LINE('User does not exists');
END;
END;
----------------------------------------------------------------------------------------------------------------------------------------
PROCEDURE change_acc_status(user_idIN IN INTEGER,new_status IN VARCHAR)
IS 
BEGIN
UPDATE sytb_accounts
SET account_status =  new_status
WHERE user_id = user_idIN;
 IF SQL%NOTFOUND THEN
 RAISE NO_DATA_FOUND;
 END IF;
EXCEPTION
WHEN NO_DATA_FOUND THEN
DBMS_OUTPUT.PUT_LINE('User does not exists');
END;
----------------------------------------------------------------------------------------------------------------------------------------

FUNCTION generateIBAN(currency IN VARCHAR,check_number IN INTEGER, bank_identifier IN INTEGER, acc_number IN INTEGER) RETURN VARCHAR
IS
       IBAN VARCHAR2(255);
BEGIN
DECLARE 
   country_code VARCHAR2(255);
   BEGIN
   country_code:=SUBSTR(currency,0,2);
   IBAN:=(country_code || check_number|| bank_identifier || (acc_number+10000000000) );
   RETURN IBAN;
 END;   
END;
------------------------------------------------------------------------------------------------------------------------------------------
FUNCTION calculate_amount(from_ccy IN VARCHAR2, to_ccy VARCHAR2)
RETURN NUMBER
       IS rate NUMBER;
       BEGIN

       BEGIN
SELECT exchange_rate INTO rate
FROM daily_rates 
WHERE from_currency = from_ccy
AND to_currency = to_ccy;
RETURN rate;
DBMS_OUTPUT.PUT_LINE(rate);
END;
END;
-----------------------------------------------------------------------------------------------------------------------
 FUNCTION calculateCharge(amount IN NUMBER,from_ccy IN VARCHAR2, to_ccy IN VARCHAR2, description IN VARCHAR2) 
RETURN VARCHAR
IS
       charge_product VARCHAR2(255);
BEGIN

IF(amount>1000) THEN
charge_product:='1000+';
END IF;
 IF(amount >10000) THEN 
charge_product:='10000+';
END IF;
 IF(amount >1000000) THEN 
charge_product:='1M';
END IF;
IF(from_ccy!=to_ccy) THEN
charge_product:='df_ccy';
END IF;
IF(description = 'donation') THEN
charge_product:='donation';
END IF;
RETURN charge_product;
DBMS_OUTPUT.PUT_LINE(charge_product);
END;
----------------------------------------------------------------------------------------------------------------------------
END sypks_accounts;
