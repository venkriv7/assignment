DROP TABLE IF EXISTS TBL_CURRENCY_RATES;
DROP TABLE IF EXISTS TBL_CURRENCY;
  
CREATE TABLE TBL_CURRENCY (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  currency_code VARCHAR(50) NOT NULL
);

  
CREATE TABLE TBL_CURRENCY_RATES (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  currency_code_id INT,
  rate_date date NOT NULL,
  rate number(7,5)
);

ALTER TABLE TBL_CURRENCY_RATES
    ADD FOREIGN KEY (currency_code_id) 
    REFERENCES TBL_CURRENCY(id);
    
INSERT INTO TBL_CURRENCY (id, currency_code) VALUES
  (1, 'GBP'),
  (2, 'USD'),
  (3, 'HKD');