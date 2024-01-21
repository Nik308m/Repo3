package Bank;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

import Bank.*;

public class BankService {

	private String accoid;
	private double transamount;
	private String transtype;
	private String Date;
	private String transid;
	
	public String getaccoid() {
		return accoid;
	}
		
	      void withdraw() {
				
				boolean cccc=false;
				
					
					while(!cccc) {
								
						try {
								System.out.println("Please Enter your Account ID from which you want to withdraw Money");
								 
								
								Scanner sc21=new Scanner(System.in);
								String gn=sc21.nextLine();
								if(gn.equals("quit")) {
									//sc16.close();
									return;
								}
								accoid = gn;

								
								DBHandler db6=new DBHandler();
								
								//MySQL Query
								PreparedStatement pst9=db6.con.prepareStatement(" SELECT AccountID FROM Accounts WHERE AccountID = ? ");
								
								//PostgreSQL Query
								//PreparedStatement pst9=db6.con.prepareStatement(" SELECT \"AccountID\" FROM public.\"Accounts\" WHERE \"AccountID\"= ? ");
								pst9.setString(1, accoid);
								ResultSet myRs5= pst9.executeQuery();
								cccc=myRs5.next();
								if(!cccc) {
								    System.out.println("Please Enter a valid Account ID !!!");
								    System.out.println("Press 'quit'  for Exiting");
								}
								else {
									 System.out.println("Validation of  Account ID is Successful");
								}
								//sc16.close();
							}
					     	catch(Exception e) {
							System.out.println("Error while Validating the  Account ID "+e);		
						}
						
					}
					
					setTranstype();
					setTransamount();
					setDate();
					setTransid();
					int qt=transac();
					if(qt==-1) {
						System.out.println(" Exiting Withdrawal Section.......");
						return;
					}
					updateTransac();			
			
		}
	      
	      
	  	public double getTransamount() {
			return transamount;
		}
		public void setTransamount() {
			boolean d=true;
			while(d) {
				
				try {
					
					System.out.println("Please Enter Transaction Amount");
					Scanner sc22=new Scanner(System.in);
					Double g=sc22.nextDouble();
					this.transamount = g;
					//sc12.close();

				}
				catch(Exception e) {
					
					System.out.println("Please Enter Valid Numeric Value while Entering the Transaction Amount "+e);
				}
				d=false;
			}
			
		}	
		public String getTranstype() {
			return transtype;
		}
		
	 void	setTranstype(){
		boolean r=true;
		while(r){
			
			 System.out.println("Please Enter how you want to withdraw Cash ?");
			 System.out.println("  1: Cheque   \t  2: Withdrawal Slip");
				Scanner sc23=new Scanner(System.in);
				String gg=sc23.nextLine();
				this.transtype = gg;
				//sc12.close();
			
				if((transtype.equals("Cheque")) | (transtype.equals("Withdrawal Slip"))){
					r=false;
				}
				else {
					System.out.println("Please Enter a Valid Choice !!!");
				}
		}
		}
	
	 
	 public String getDate() {
		 
		 return Date;
	 }
		
	 public void setDate() {
		 
				//System.out.println("Setting Transaction Date and Time");
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date date = new Date();
				//System.out.println(formatter.format(date));
				//DateTime = dateTime;
				this.Date=formatter.format(date);	
			
	 }
	  int transac() {
		  
		  try {
			  
				DBHandler db37=new DBHandler();
				
				//MySQL Query
				PreparedStatement pst68=db37.con.prepareStatement(" SELECT CurrBalance FROM Accounts WHERE AccountID = ? ");
				
				
				//PostgreSQL Query
				//PreparedStatement pst68=db37.con.prepareStatement(" SELECT \"CurrBalance\" FROM \"Accounts\" WHERE \"AccountID\"= ? ");
				pst68.setString(1,accoid);
				ResultSet myRs36= pst68.executeQuery();
				myRs36.next();
				double soaccbal=myRs36.getDouble("CurrBalance");
				
				if(soaccbal<getTransamount()) {
					System.out.println("Transaction Failed Due to Insufficient Balance !!!");
					return -1;
				}
				soaccbal=soaccbal-getTransamount();
				//Debitting Money and Updating the Balance from which money debitted
				
				db37.con.setAutoCommit(false);
				
				//MySQL Query
				PreparedStatement pst69=db37.con.prepareStatement(" UPDATE Accounts SET CurrBalance = ? WHERE AccountID = ? ");
				
				//PostgreSQL Query
				//PreparedStatement pst69=db37.con.prepareStatement(" UPDATE \"Accounts\" SET \"CurrBalance\"= ? WHERE \"AccountID\"= ? ");
				pst69.setDouble(1, soaccbal);
				pst69.setString(2, accoid);
				int v= pst69.executeUpdate();
				if(v==0) {
					System.out.println("Failed to update Current Balance of Source Account");
					return -2;
				}
			  db37.con.commit();
			  
			  
		  }
		  catch(Exception e) {
			  
			  System.out.println("Error while processing Transaction "+e);
			  
		  }
		  return 1;
	  }
	  
	  
	  int updateTransac() {
		  
		  try {
			  
			
						

						//Updating for Source Account
						DBHandler db38=new DBHandler();
						
						db38.con.setAutoCommit(false);
						
						
						//MySQL
						PreparedStatement pst73=db38.con.prepareStatement(" SELECT CurrBalance FROM Accounts WHERE AccountID= ? ");
						
						//PostgreSQL 
						//PreparedStatement pst73=db38.con.prepareStatement(" SELECT \"CurrBalance\" FROM \"Accounts\" WHERE \"AccountID\"= ? ");
						pst73.setString(1, accoid);
						ResultSet myRs37= pst73.executeQuery();
						myRs37.next();
						double sourcurrbal=myRs37.getDouble("CurrBalance");
						
						//MySQL
						PreparedStatement pst72=db38.con.prepareStatement(" INSERT INTO Transactions (TransID,TransAmount,TransType,MoneyInFrom,MoneyOutTo,TransDate,CurrBalance,AccountID) VALUES (  ? , ? , ? , NULL , ? , ? , ? , ? ) ");
						
						//Postgrsql  Query
						//PreparedStatement pst72=db38.con.prepareStatement(" INSERT INTO \"Transactions\" (\"TransID\",\"TransAmount\",\"TransType\",\"MoneyInFrom\",\"MoneyOutTo\",\"TransDate\",\"CurrBalance\",\"AccountID\") VALUES (  ? , ? , ? , NULL , ? , ? , ? , ? ) ");
						pst72.setString(1,getTransid());
						pst72.setDouble(2, transamount);
						pst72.setString(3,String.valueOf(getTranstype()));
						String destin;
						if(transtype.equals("Cheque")) {
							destin="Cash withdrawal using Cheque";
						}
						else {
							destin="Cash Withdrawal using Withdrawal Slip";
						}
						
						pst72.setString(4,destin);
						pst72.setString(5,getDate());
						pst72.setDouble(6, sourcurrbal);
						pst72.setString(7, accoid);
						int cv= pst72.executeUpdate();
						if(cv==0) {
							System.out.println(" Zero Rows Updated in Transactions Database");
						}
							  
			  
			 db38.con.commit();
			 System.out.println(" Cash Withdrawal of Amount: "+getTransamount() +" using "+getTranstype()+" is Successful !!!");
		  }
		  catch(Exception e) {
			  
			 System.out.println("Error While Updating the Transaction info into the TRansaction Database "+e);
		  }
		 
		  return 1;
	  }
	  
	  public String getTransid() {
		  return transid;
	  }
		public void setTransid() {
			boolean c=true;
			
		
			
	while(c) {
				
		try {
				//System.out.println("Setting Transaction ID");
				   UUID id1= UUID.randomUUID();
			       Long lo= Math.abs(id1.getMostSignificantBits());
			       String transid ="BR"+lo.toString();
				this.transid = transid.substring(0,11);
				
				
				DBHandler db9=new DBHandler();
				
				
				//MySQL Query
				PreparedStatement pst=db9.con.prepareStatement(" SELECT TransID FROM Transactions WHERE TransID = ? ");
				
				//PostgreSQL Query
				//PreparedStatement pst=db9.con.prepareStatement(" SELECT \"TransID\" FROM public.\"Transactions\" WHERE \"TransID\"= ? ");
				pst.setString(1, this.transid);
				ResultSet myRs9= pst.executeQuery();
				c=myRs9.next();
			}
			
		
		catch(Exception e) {
			System.out.println("Error while generating Unique Transaction ID "+e);
			
		}
		
	}	
		
			
			System.out.println("Your Transaction ID is: "+ getTransid());
		}
	  
	  
		void deposit() {
			
			
			boolean cccc=false;
			
				
				while(!cccc) {
							
					try {
							System.out.println("Please Enter the Account ID into which you want to Deposit Money");
							 
							
							Scanner sc21=new Scanner(System.in);
							String gn=sc21.nextLine();
							if(gn.equals("quit")) {
								//sc16.close();
								return;
							}
							accoid = gn;

							
							DBHandler db6=new DBHandler();
							
							//MySQL Query
							PreparedStatement pst9=db6.con.prepareStatement(" SELECT AccountID FROM Accounts WHERE AccountID = ? ");
							
							//PostgreSQL Query
							//PreparedStatement pst9=db6.con.prepareStatement(" SELECT \"AccountID\" FROM public.\"Accounts\" WHERE \"AccountID\"= ? ");
							pst9.setString(1, accoid);
							ResultSet myRs5= pst9.executeQuery();
							cccc=myRs5.next();
							if(!cccc) {
							    System.out.println("Please Enter a valid Account ID !!!");
							    System.out.println("Press 'quit'  for Exiting");
							}
							else {
								 System.out.println("Validation of  Account ID is Successful");
							}
							//sc16.close();
						}
				     	catch(Exception e) {
						System.out.println("Error while Validating the  Account ID "+e);		
					}
					
				}
				
				setDeTranstype();
				setTransamount();
				setDate();
				setTransid();
				Detransac();
				updateDeTransac();			
			
		}
		public String getDeTranstype() {
			return transtype;
		}
		
	 void	setDeTranstype(){
		boolean r=true;
		while(r){
			
			 System.out.println("Please Enter how you want to Deposit Money ?");
			 System.out.println("  1: Cheque   \t  2: Deposit Slip");
				Scanner sc23=new Scanner(System.in);
				String gg=sc23.nextLine();
				this.transtype = gg;
				//sc12.close();
			
				if((transtype.equals("Cheque")) | (transtype.equals("Deposit Slip"))){
					r=false;
				}
				else {
					System.out.println("Please Enter a Valid Choice !!!");
				}
		}
		}
	 
	 
	 int Detransac() {
		  
		  try {
			  
				DBHandler db37=new DBHandler();
				
				//MySQL Query
				PreparedStatement pst68=db37.con.prepareStatement(" SELECT CurrBalance FROM Accounts WHERE AccountID = ? ");
				
				//PostgreSQL Query
				//PreparedStatement pst68=db37.con.prepareStatement(" SELECT \"CurrBalance\" FROM \"Accounts\" WHERE \"AccountID\"= ? ");
				pst68.setString(1,accoid);
				ResultSet myRs36= pst68.executeQuery();
				myRs36.next();
				double soaccbal=myRs36.getDouble("CurrBalance");
				
				/*if(soaccbal<getTransamount()) {
					System.out.println("Transaction Failed Due to Insufficient Balance !!!");
					return -1;
				}*/
				soaccbal=soaccbal+getTransamount();
				//Creditting Money and Updating the Balance into which money is Depositted
				
				db37.con.setAutoCommit(false);
				
				//MySQL Query
				PreparedStatement pst69=db37.con.prepareStatement(" UPDATE Accounts SET CurrBalance = ? WHERE AccountID = ? ");
				
				//PostgreSQL Query
				//PreparedStatement pst69=db37.con.prepareStatement(" UPDATE \"Accounts\" SET \"CurrBalance\"= ? WHERE \"AccountID\"= ? ");
				pst69.setDouble(1, soaccbal);
				pst69.setString(2, accoid);
				int v= pst69.executeUpdate();
				if(v==0) {
					System.out.println("Failed to update Current Balance of Source Account");
					return -2;
				}
			  db37.con.commit();
			  db37.con.close();
			  
			  
		  }
		  catch(Exception e) {
			  
			  System.out.println("Error while processing Transaction "+e);
			  
		  }
		  return 1;
	  }
	  
	  
	  int updateDeTransac() {
		  
		  try {
			  
			
						

						//Updating for Source Account
						DBHandler db38=new DBHandler();
						
						db38.con.setAutoCommit(false);
						
						//MySQL Query
						PreparedStatement pst73=db38.con.prepareStatement(" SELECT CurrBalance FROM Accounts WHERE AccountID = ? ");
						
						
						//PostgreSQL Query
						//PreparedStatement pst73=db38.con.prepareStatement(" SELECT \"CurrBalance\" FROM \"Accounts\" WHERE \"AccountID\"= ? ");
						pst73.setString(1, accoid);
						ResultSet myRs37= pst73.executeQuery();
						myRs37.next();
						double sourcurrbal=myRs37.getDouble("CurrBalance");
						
						//MySQL Query
						PreparedStatement pst72=db38.con.prepareStatement(" INSERT INTO Transactions (TransID,TransAmount,TransType,MoneyInFrom,MoneyOutTo,TransDate,CurrBalance,AccountID) VALUES (  ? , ? , ? , ? , NULL , ? , ? , ? ) ");
						
						
						//PostgrSQL Query
						//PreparedStatement pst72=db38.con.prepareStatement(" INSERT INTO \"Transactions\" (\"TransID\",\"TransAmount\",\"TransType\",\"MoneyInFrom\",\"MoneyOutTo\",\"TransDate\",\"CurrBalance\",\"AccountID\") VALUES (  ? , ? , ? , ? , NULL , ? , ? , ? ) ");
						pst72.setString(1,getTransid());
						pst72.setDouble(2, transamount);
						pst72.setString(3,String.valueOf(getTranstype()));
						String destin;
						if(transtype.equals("Cheque")) {
							destin="Money Deposit using Cheque";
						}
						else {
							destin="Cash Deposit using Withdrawal Slip";
						}
						
						pst72.setString(4,destin);
						pst72.setString(5,getDate());
						pst72.setDouble(6, sourcurrbal);
						pst72.setString(7, accoid);
						int cv= pst72.executeUpdate();
						if(cv==0) {
							System.out.println(" Zero Rows Updated in Transactions Database");
						}
							  
			  
			 db38.con.commit();
			 db38.con.close();
			 System.out.println(" Money Deposit of Amount: "+getTransamount() +" using "+getTranstype()+" is Successful !!!");
		  }
		  catch(Exception e) {
			  
			 System.out.println("Error While Updating the Deposit Transaction info into the TRansaction Database "+e);
		  }
		 
		  return 1;
	  }
	  
	  void bankserv() {
		  
		  System.out.println("\t \t \t \t ******************* Welcome to the Bank of Airavat *******************");
		  System.out.println(" Currently Bank of Airavat offers following Services ");
		  System.out.println(" 1: Money Deposit \n 2: Money Withdrawal ");
		  System.out.println(" 3: Money Transfer via NEFT or RTGS \n 4: Banking Application ");
		  System.out.println(" Many more services like UPI, Debit/Credit Cards,etc. to be starting soon........");
	  } 
	  
	 
	
	 
	/*
		void transfer(double d);
		void balInquiry();
		*/
	

}
