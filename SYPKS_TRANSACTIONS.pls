CREATE OR REPLACE PACKAGE sypks_transactions
IS
PROCEDURE create_transaction(my_acc IN INTEGER, IBAN IN VARCHAR2,amountIN IN NUMBER, descriptionIN IN VARCHAR2);
END;
/
CREATE OR REPLACE PACKAGE BODY sypks_transactions
IS
PROCEDURE create_transaction(my_acc IN INTEGER, IBAN IN VARCHAR2,
amountIN IN NUMBER, descriptionIN IN VARCHAR2)
IS
BEGIN
DECLARE 
acc_id INTEGER;
to_acc_id INTEGER;
trn_id INTEGER:=trn_idSEQ.nextval;
curr_amount NUMBER;
my_exc_rate NUMBER;
recipient_exc_rate NUMBER;
amount NUMBER := amountIN;
charge VARCHAR2(255);
charge_amount NUMBER;
charge_description VARCHAR2(255);
my_acc_status VARCHAR(2);
recipient_acc_status VARCHAR(2);
acc_ccy VARCHAR2(3);
TOcurrency VARCHAR(3);
fcy_de_amount NUMBER;
fcy_cr_amount NUMBER;
not_enough_money EXCEPTION;
my_status_exception EXCEPTION;
recipient_status_exception EXCEPTION;
same_acc_exception EXCEPTION;
BEGIN
SAVEPOINT sav1;
SELECT account_id,account_ccy,account_status, lcy_curr_balance INTO acc_id, acc_ccy, my_acc_status, curr_amount
FROM sytb_accounts
WHERE account_id = my_acc;   


   

SELECT account_id,account_ccy ,account_status INTO to_acc_id, TOcurrency,recipient_acc_status
FROM sytb_accounts
WHERE iban_ac_no = IBAN;
IF(my_acc_status='C' OR my_acc_status = 'F' OR my_acc_status = 'BD' ) THEN
      RAISE my_status_exception;
   END IF;
IF (recipient_acc_status='C' OR recipient_acc_status = 'F' OR recipient_acc_status = 'BC') THEN 
      RAISE recipient_status_exception;
   END IF;
IF(to_acc_id = acc_id) THEN
RAISE same_acc_exception;
END IF;

IF(curr_amount<amount) THEN
RAISE not_enough_money;
END IF;

IF(acc_ccy!='BGN') THEN
my_exc_rate:=calculate_amount(acc_ccy,'BGN');
fcy_de_amount:=amountIN;
amount:=amountIN*my_exc_rate;
ELSE amount:= amountIN;
END IF;


IF(TOcurrency!='BGN') THEN
recipient_exc_rate:=calculate_amount('BGN',TOcurrency);
fcy_cr_amount:= amount *recipient_exc_rate;
ELSE
fcy_cr_amount:=null;
END IF;


charge:=calculateCharge(amount,acc_ccy,TOcurrency,descriptionin);
SELECT charge_amt_lcy ,charge_descr INTO charge_amount,charge_description
FROM sytb_charge
WHERE charge_product = charge;

charge_amount:=(charge_amount/100)*amount;
amount:=amount-charge_amount;
UPDATE sytb_accounts
SET lcy_curr_balance = lcy_curr_balance-amount
WHERE account_id = my_acc;
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
                                trn_description,charge_product)
                              VALUES(acc_id,to_acc_id,trn_id,'debit',fcy_de_amount,my_exc_rate,amount,sysdate,'T',descriptionIN,charge);
INSERT INTO sytb_transactions (account_id,to_account_id,trn_id,drcr_indicator,fcy_amount,exchange_rate, lcy_amount, trn_date, trn_type, 
                              trn_description,charge_product)
                              VALUES(to_acc_id,acc_id,trn_id,'credit',fcy_cr_amount,recipient_exc_rate,amount,sysdate,'T',descriptionIN,charge);
INSERT INTO sytb_transactions (account_id,to_account_id,trn_id,drcr_indicator,fcy_amount,exchange_rate, lcy_amount, trn_date, trn_type, 
                              trn_description,charge_product)
                              VALUES(acc_id,'BANK',trn_id,'debit',null,null,charge_amount,sysdate,'C',charge_description,null);
 INSERT INTO sytb_transactions (account_id,to_account_id,trn_id,drcr_indicator,fcy_amount,exchange_rate, lcy_amount, trn_date, trn_type, 
                              trn_description,charge_product)
                              VALUES('BANK',acc_id,trn_id,'credit',null,null,charge_amount,sysdate,'C',charge_description,null);
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
  THEN DBMS_OUTPUT.PUT_LINE('Transaction cannot be processed...Not enough money on account');
  ROLLBACK TO sav1;
  WHEN my_status_exception 
  THEN DBMS_OUTPUT.PUT_LINE('Transaction cannot be processed...Your status is: ' ||my_acc_status );
  WHEN recipient_status_exception 
  THEN DBMS_OUTPUT.PUT_LINE('Transaction cannot be processed...Recipient status is: ' ||recipient_acc_status );
  WHEN same_acc_exception 
  THEN DBMS_OUTPUT.PUT_LINE('Accounts are the same');

END;
END;
END sypks_transactions;
