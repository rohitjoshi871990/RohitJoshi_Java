# RohitJoshi_Java
To Solve position calculation problem

Problem Statement 
ABC Investment Bank is a prominent global bank, as part of the General Ledger System, you are required to develop a position calculation process.
Position Calculation process takes start of day positions and transaction files as input, apply transactions on positions based on transaction type (B/S) and account type (I/E), and computes end of day positions. Depending on the direction of the transaction (Buy/Sell) each transaction is recorded as debit and credit into external and internal accounts in the “Positions” file.

Input:
Positions File: contain start positions for instruments
Transactions Files: contains transactions for a day

Process:
Read Positions and Transactions files, compute new positions and write to new end of day positions file.For each transaction in Transaction file,Apply TransactionQuantity into matching instrument records in the position file

If Transaction Type =B ,
    For AccountType=E, Quantity=Quantity + TransactionQuantity
    For AccountType=I, Quantity=Quantity - TransactionQuantity
If Transaction Type =S ,
    For AccountType=E, Quantity=Quantity - TransactionQuantity
    For AccountType=I, Quantity=Quantity + TransactionQuantity
Query:
·         At the end of the process find instruments with largest and lowest net transaction volumes for the day. (net volume is change in positions from start of day positions to end of day positions) 

Solution : 

Please do find the project which includes the solution related to the problem statement mentioned above. 

Steps to execute as below - 
1. Download the project from git repository in Eclipse Editor and set JDK and JRE system to 1.8 version. 
2. check the Config file in resources folder and give file locations as per your system path you want to give. 
3. Select on Project right click -> Run As Maven Clean then Run As Maven Install which will create a JAR in Target Folder. 
4. Execute JAR or you can directly go to the PositionCalculator.java File and run it as java application as it has Main Method as well. 
5. Go to the location in which the final files generated as per your config values path. 

