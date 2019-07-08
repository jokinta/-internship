create or replace PACKAGE BODY sypks_accounts
IS
PROCEDURE create_account(user_name IN VARCHAR,currency IN VARCHAR,deposit IN NUMBER)
IS
BEGIN
DECLARE 
user_id INTEGER;
user_iban VARCHAR(255);
amount_exception EXCEPTION;

BEGIN
IF(deposit=0 OR deposit<0)
THEN
RAISE amount_exception;
END IF;
SELECT user_id INTO user_id
FROM sytb_user 
WHERE user_login_name = user_name;
user_iban:= generateIBAN(currency,12,9321,acc_idSEQ.nextval);

INSERT INTO sytb_accounts(user_id, account_id, account_ccy,ac_open_date, account_status, date_created, date_last_modification, modification_no, lcy_curr_balance,
                          acy_month_turnover_cr, acy_month_turnover_dr, acy_year_turnover_cr,
                          acy_year_turnover_dr,iban_ac_no) 
                          VALUES (user_id,acc_idSEQ.nextval,currency,sysdate, 'E',sysdate,sysdate,0,deposit,0,0,0,0,user_iban);
                
           DBMS_OUTPUT.PUT_LINE('Dear ' || user_name ||' your account is successfully created!');               

EXCEPTION 
WHEN DUP_VAL_ON_INDEX
THEN DBMS_OUTPUT.PUT_LINE('Account with username:'|| user_name || ' already exists ');
WHEN amount_exception
THEN DBMS_OUTPUT.PUT_LINE('Amount must be greater than zero');


END;
END;
-------------------------------------------------------------------------------------------------------
PROCEDURE create_transaction(myID IN INTEGER, IBAN IN VARCHAR2,
amountIN IN NUMBER,  dr_or_cr IN VARCHAR2, descriptionIN IN VARCHAR2)
IS
BEGIN
DECLARE 
acc_id INTEGER;
to_acc_id INTEGER;
curr_amount NUMBER;
exc_rate NUMBER;
amount NUMBER := amountIN;
charge VARCHAR2(255);
acc_status VARCHAR(2);
acc_ccy VARCHAR2(3);
TOcurrency VARCHAR(3);
fcy_amount NUMBER;
not_enough_money EXCEPTION;
status_exception EXCEPTION;
same_acc_exception EXCEPTION;
BEGIN
SAVEPOINT sav1;
SELECT account_id,account_ccy,account_status, lcy_curr_balance INTO acc_id, acc_ccy, acc_status, curr_amount
FROM sytb_accounts
WHERE user_id = myID;   

IF(acc_status='C' OR acc_status = 'F') THEN
      RAISE status_exception;
END IF;
  IF (acc_status = 'BD' AND dr_or_cr='debit') THEN
   RAISE status_exception;
   END IF;
   IF (acc_status = 'BC' AND dr_or_cr='credit') THEN 
      RAISE status_exception;
   END IF;

SELECT account_id,account_ccy INTO to_acc_id, TOcurrency
FROM sytb_accounts
WHERE iban_ac_no = IBAN;

--charge:=calculateCharge();

IF(to_acc_id = acc_id) THEN
RAISE same_acc_exception;
END IF;

IF(curr_amount<amount) THEN
RAISE not_enough_money;
END IF;
IF(acc_ccy!='BGN') THEN
exc_rate:=calculate_amount(acc_ccy,'BGN');
amount:=amountIN*exc_rate;
ELSE amount:= amountIN;
END IF;


IF(TOcurrency!='BGN') THEN
exc_rate:=calculate_amount('BGN',TOcurrency);
fcy_amount:= amount *exc_rate;
END IF;





UPDATE sytb_accounts
SET lcy_curr_balance = lcy_curr_balance-amount
WHERE myID = user_id;
IF SQL%NOTFOUND THEN
RAISE no_data_found;
END IF;

UPDATE sytb_accounts
SET lcy_curr_balance = lcy_curr_balance+amount
WHERE iban_ac_no = IBAN;
IF SQL%NOTFOUND THEN
RAISE no_data_found;
END IF;



INSERT INTO sytb_transactions (account_id,to_account_id,trn_id,drcr_indicator,fcy_amount,exchange_rate, lcy_amount, trn_date, trn_type, 
                              trn_description)
                              VALUES(acc_id,to_acc_id,trn_idSEQ.nextval,dr_or_cr,fcy_amount,exc_rate,amount,sysdate,'T',descriptionIN);
UPDATE sytb_accounts
SET date_last_transaction = sysdate
WHERE account_id = acc_id
OR account_id = to_acc_id;

COMMIT;

EXCEPTION
  WHEN no_data_found 
  THEN DBMS_OUTPUT.PUT_LINE('User not found');
    ROLLBACK TO sav1;
  WHEN not_enough_money
  THEN DBMS_OUTPUT.PUT_LINE('Not enough money on account');
  ROLLBACK TO sav1;
  WHEN status_exception 
  THEN DBMS_OUTPUT.PUT_LINE('User status: ' ||acc_status );
  WHEN same_acc_exception 
  THEN DBMS_OUTPUT.PUT_LINE('Accounts are the same');

END;
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
END sypks_accounts;