package Bank;
import Bank.*;

//This package is used to generat UUID (Universally Unique Identifiers)
import java.util.UUID; 
//Used for Regular Expressions
import java.util.regex.*;

//For Date and Time
/*import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;  
*/
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
//Taking Pasword input through Console readpassword method
import java.io.*;

import java.util.Arrays;
import java.util.Scanner;
import java.lang.Exception.*;
import java.sql.*;


public class BankApp {
	
	enum AccountType{
		SAVINGS,CURRENT,SALARY
	}
	enum TransactionType{
		UPI,NEFT,RTGS,CHEQUE,CASH
	}

	 protected class Customer {
		private long custid;
		private String fname;
		private String mname;
		private String lname;
		private String uname;
		private String password;
		private long mnum;
		private String mailid;
		private String Address;
		private int zipcode;
	
		
		public long getCustid() {
			return custid;
		}
		public void setCustid() {
			boolean c=true;
		try {
			  do {
				  
					//To convert the hex string into Long the getLeast or getMostSignificantBits method is used
					 UUID id= UUID.randomUUID();
				       Long lo= Math.abs(id.getLeastSignificantBits());
				       String s=lo.toString();
				       String ss=s.substring(0,11);
				       lo=Long.valueOf(ss);
					this.custid = (long)lo;
					DBHandler db1=new DBHandler();
					
					
					//MySQL Query
					PreparedStatement pst=db1.con.prepareStatement(" SELECT CustID FROM Accounts WHERE CustID =?");
					
					//PostgreSQL Query
					//PreparedStatement pst=db1.con.prepareStatement(" SELECT \"CustID\" FROM public.\"Accounts\" WHERE \"CustID\"=?;");
					pst.setLong(1, this.custid);
					ResultSet myRs= pst.executeQuery();
					c=myRs.next();
			  }while(c);
		}
		catch(Exception e) {
			System.out.println("Error while crosschecking uniqueness of Generated CustID in Database"+e);
		}
		
		}
		public String getFname() {
			return fname;
		}
		public void setFname() {
			System.out.println("Please Enter your First Name");
			Scanner sc=new Scanner(System.in);
			String fname=sc.nextLine();
			this.fname = fname;
			//sc.close();
		}
		public String getMname() {
			return mname;
		}
		public void setMname() {
			System.out.println("Please Enter your Middle Name");
			Scanner sc1=new Scanner(System.in);
			String mname=sc1.nextLine();
			this.mname = mname;
			//sc1.close();
		}
		public String getLname() {
			return lname;
		}
		public void setLname() {
			System.out.println("Please Enter your Last Name");
			Scanner sc3=new Scanner(System.in);
			String lname=sc3.nextLine();
			this.lname = lname;
			//sc3.close();
		}
		public String getUname() {
			return uname;
		}
		public void setUname() {
			boolean c=true;
			
			try {
				
				while(c) {
					
					System.out.println("Please Enter your Username");
					Scanner sc4=new Scanner(System.in);
					String uname=sc4.nextLine();
					   
					this.uname = uname;
					
					DBHandler db1=new DBHandler();
					//MySQL Query
					PreparedStatement pst=db1.con.prepareStatement(" SELECT username FROM CustomerData WHERE username= ? ");
					
					//PostgreSQL Query
					//PreparedStatement pst=db1.con.prepareStatement(" SELECT \"username\" FROM public.\"CustomerData\" WHERE \"username\"= ? ");
					pst.setString(1, this.uname);
					ResultSet myRs= pst.executeQuery();
					c=myRs.next();
					if(c) {
						System.out.println("Entered User Name Already Exists !!! "+"Please try again");
					}
					//sc4.close();
				}
					
			}
			catch(Exception e) {
				
				System.out.println("Error Occured while Setting the UserName   "+e);
			}
			
			
			
			
		}
		public String getPassword() {
			return password;
		}
		public void setPassword() {
			String ps1,ps2;
			do {
				System.out.println("Please Enter your Password");
				Scanner sc5=new Scanner(System.in);
				 ps1=sc5.nextLine();
				 System.out.println("Please ReEnter and Confirm your Password");
					Scanner sc6=new Scanner(System.in);
					 ps2=sc6.nextLine();
					 //System.out.println(ps1+"  "+ps2);
					 if(!(ps1.equals(ps2)))System.out.println("Password MissMatch!!! Please ReEnter and Confirm your Password");
			 //sc5.close();
			// sc6.close();
			}while(!(ps1.equals(ps2)));
              
				
				this.password = ps1;
			
		
		}
		
		public long getMnum() {
			return mnum;
		}
		public void setMnum() {
			boolean b0=true;
		  while(b0) {
				System.out.println("Please Enter your Mobile Number");
				Scanner sc7=new Scanner(System.in);
				String smnum=sc7.nextLine();
				//(0/91): number starts with (0/91)  
				//[7-9]: starting of the number may contain a digit between 0 to 9  
				//[0-9]: then contains digits 0 to 9  
				Pattern ptrn = Pattern.compile("(0/91)?[7-9][0-9]{9}");
				//the matcher() method creates a matcher that will match the given input against this pattern  
				Matcher match = ptrn.matcher(smnum);  
				//returns a boolean value
				if((match.find() && match.group().equals(smnum))) {
					long k=Long.parseLong(smnum);
					this.mnum = k;
					System.out.println("Mobile Number Successfully Updated");
					b0=false;
				}
				else {
					System.out.println("Please enter a valid Indian Mobile Number");
				}
				//sc7.close();
		  }
			
			
			/*System.out.println("Please Enter your Mobile Number");
			Scanner sc7=new Scanner(System.in);
			String mnum=sc7.nextLine();
			this.mnum=Long.parseLong(mnum);*/
			
		}
		public String getMailid() {
			return mailid;
		}
		public void setMailid() {
			boolean b1=true;
		
			while(b1) {
				
				
				System.out.println("Please Enter your Email ID");
				Scanner sc8=new Scanner(System.in);
				String mailid=sc8.nextLine();
				
				String regexPattern="^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
				        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
				if(Pattern.compile(regexPattern).matcher(mailid).matches()){
					this.mailid = mailid;
					System.out.println("Email ID Successfully Updated");
					b1=false;
				}
				else {
					System.out.println("Invalid Email ID!!! Please Enter a Valid Email Address");
				}
				//sc8.close();
			}
		}
		
		public String getAddress() {
			return Address;
		}
		public void setAddress() {
			System.out.println("Please Enter your Address");
			Scanner sc10=new Scanner(System.in);
			String address=sc10.nextLine();
			this.Address = address;
			//sc10.close();
		}
		public int getZipcode() {
			return zipcode;
		}
		public void setZipcode() {
			boolean cc=true;
			
			while(cc) {
				
				try {
					
					System.out.println("Please Enter your Postal Code/ ZIP Code");
					Scanner sc11=new Scanner(System.in);
					int zipcode=sc11.nextInt();
					this.zipcode = zipcode;
					cc=false;
					//sc11.close();
				}
				catch(Exception e) {
					System.out.println("Error while Setting ZIP CODE \n Please Enter Valid Postal/ZIP CODE"+e);		
					
					}
				
			}
			
		}
		
		public void getCusDetails() {
			/*System.out.println("Customer Details: "+"\n Customer ID: "+getCustid()+ "\n FirstName: "+getFname()+" \t Middle Name: "+getMname()+" \t Last Name: "+getLname());
			System.out.println("Mobile Number: "+getMnum()+"\n Email ID: "+getMailid()+"\n Address: "+getAddress());*/
			System.out.println(" Customer Details:");
			System.out.println(" Customer ID: "+getCustid());
			System.out.println(" First Name: "+getFname()+"\t Middle Name: "+getMname()+"\t Last Name: "+getLname());
			System.out.println(" Mobile Number: "+getMnum());
			System.out.println(" Email ID: "+getMailid());
			System.out.println(" Address: "+getAddress());
			System.out.println(" Username: "+getUname());
			System.out.println("Postal Code / ZIP Code: "+getZipcode());
		}
		public void setCustDetails () {
			
			setFname();
			setMname();
			setLname();
			setMnum();
			setMailid();
			setAddress();
			setZipcode();
			setUname();
			setPassword();
			setCustid();
		}

	}
	
	
	protected class Account {
  
	private String accid;
    private double cbalance;
    private AccountType acctype;
    
    public String getAccid() {
		return accid;
	}
	public void setAccid() {
		
		boolean c=true;
		try {
			
			while(c) {
						
						System.out.println("Setting Account ID");
						   UUID id1= UUID.randomUUID();
					       Long lo= Math.abs(id1.getMostSignificantBits());
					       String accccid ="AIR"+lo.toString();
						this.accid = accccid.substring(0,14);
						
						
						DBHandler db1=new DBHandler();
						//MySQL Query
						PreparedStatement pst=db1.con.prepareStatement(" SELECT AccountID FROM Accounts WHERE AccountID= ? ");
						
						//PostgreSQL Query
						//PreparedStatement pst=db1.con.prepareStatement(" SELECT \"AccountID\" FROM public.\"Accounts\" WHERE \"AccountID\"= ? ");
						pst.setString(1, this.accid);
						ResultSet myRs= pst.executeQuery();
						c=myRs.next();
					}
					
				}
				catch(Exception e) {
					System.out.println("Error while generating Unique Account ID "+e);
					
				}
		
		
		/* UUID id= UUID.randomUUID();
	       long lo= Math.abs(id.getLeastSignificantBits());
		Long q = lo;
		this.accid = "AIR".concat(Long.toString(lo));*/
		
		
	}
	public double getCbalance() {
		return cbalance;
	}
	public void setCbalance() {
		this.cbalance = cbalance;
	}
	public AccountType getAcctype() {
		return acctype;
	}
	public void setAcctype() {
		
		boolean bb=true;
		AccountType at=null;
		do {

			try {
				System.out.println("Please Enter the Valid Account Type from below options: ");
				System.out.println("1: SAVINGS \n 2: SALARY \n 3: CURRENT ");
				
				
				Scanner sc15=new Scanner(System.in);
				String k=sc15.nextLine();
				at=AccountType.valueOf(k);
				bb=false;
				//sc15.close();
			}
			catch(IllegalArgumentException f) {
				System.out.println("Wrong Account Type Entered !!! \n "+f);
		
			}
			
		}while(bb);
		
			
		switch(at) {
		
		case SAVINGS:{
			this.acctype=AccountType.valueOf("SAVINGS");
			break;
		}
		case CURRENT:{
			this.acctype=AccountType.valueOf("CURRENT");
			break;
		}
		case SALARY:{
			this.acctype=AccountType.valueOf("SALARY");
			break;
		}
		default:
			System.out.println("Please Enter a Valid Account Type");
		
		}
		
		
		this.acctype = acctype;
	}
	}
	
	
	protected class Transaction {

		private String transid;
		private double transamount;
		private TransactionType transtype;
		private String DateTime;
		private  String soaccid;
		private String deaccid;
	
		
		
		
		public void transact(int v,String un) {
			if(v!=1) {
				System.out.println("Exiting Transaction due to Failed Login......");
				return;
			}
			String uname=un;
			String Acccid;
			
			try {
				
				DBHandler db33=new DBHandler();
				//MySQL Query
				PreparedStatement pst66=db33.con.prepareStatement(" SELECT CustID FROM CustomerData WHERE username= ? ");
				
				//PostgreSQL Query
				//PreparedStatement pst66=db33.con.prepareStatement(" SELECT \"CustID\" FROM \"CustomerData\" WHERE \"username\"= ? ");
				pst66.setString(1,uname);
				ResultSet myRs3= pst66.executeQuery();
				
				myRs3.next();
				Long custID=myRs3.getLong("CustID");
			
				
				//MySQL Query
				PreparedStatement pst67=db33.con.prepareStatement(" SELECT AccountID FROM Accounts WHERE CustID= ? ");
				
				
				//PostgreSQL Query
				//PreparedStatement pst67=db33.con.prepareStatement(" SELECT \"AccountID\" FROM \"Accounts\" WHERE \"CustID\"= ? ");
				pst67.setLong(1, custID);
				ResultSet myRs4= pst67.executeQuery();
				myRs4.next();
			    Acccid=myRs4.getString("AccountID");
				
				
				this.soaccid=Acccid;
				
			}
			catch(Exception e){
				
				System.out.println("Error Occurred while Initiating the Transaction  "+e);
			}
			
			setTransamount();
			setTranstype();
			
			setdeaccid();
			
			
			
			
			
			setDateTime();
			setTransid();
			int cj=CalTransaction();
			if(cj==-1) {
			    return;
			}
			else if(cj==-2) {
				return;
			}
			updateTransaction();
			
			
			
		}
		
		protected int CalTransaction() {
			try {
				
				DBHandler db34=new DBHandler();
				//MySQL qUERY
				PreparedStatement pst68=db34.con.prepareStatement(" SELECT CurrBalance FROM Accounts WHERE AccountID= ? ");
				
				//PostgreSQL Query
				//PreparedStatement pst68=db34.con.prepareStatement(" SELECT \"CurrBalance\" FROM \"Accounts\" WHERE \"AccountID\"= ? ");
				pst68.setString(1,soaccid);
				ResultSet myRs36= pst68.executeQuery();
				myRs36.next();
				double soaccbal=myRs36.getDouble("CurrBalance");
				
				if(soaccbal<getTransamount()) {
					System.out.println("Transaction Failed Due to Insufficient Balance !!!");
					return -1;
				}
				soaccbal=soaccbal-getTransamount();
				
				//Debitting Money and Updating the Balance from which money debitted
				
				db34.con.setAutoCommit(false);
				//MySQL Query
				PreparedStatement pst69=db34.con.prepareStatement(" UPDATE Accounts SET CurrBalance = ? WHERE AccountID= ? ");
				
				//pOSTGREsql Query
				//PreparedStatement pst69=db34.con.prepareStatement(" UPDATE \"Accounts\" SET \"CurrBalance\"= ? WHERE \"AccountID\"= ? ");
				pst69.setDouble(1, soaccbal);
				pst69.setString(2, soaccid);
				int v= pst69.executeUpdate();
				if(v==0) {
					System.out.println("Failed to update Current Balance of Source Account");
					return -2;
				}
				
				//Creditting Money to the Destination Account
				
				//MySQL Query
				PreparedStatement pst70=db34.con.prepareStatement(" SELECT CurrBalance FROM Accounts WHERE AccountID = ? ");
				
				
				//PostgreSQL Query
				//PreparedStatement pst70=db34.con.prepareStatement(" SELECT \"CurrBalance\" FROM \"Accounts\" WHERE \"AccountID\"= ? ");
				pst70.setString(1,deaccid);
				ResultSet myRs39= pst70.executeQuery();
				myRs39.next();
				double deeaccbal=myRs39.getDouble("CurrBalance");
				
				deeaccbal=deeaccbal+getTransamount();
			
				
				//MySQL Query
				PreparedStatement pst71=db34.con.prepareStatement(" UPDATE Accounts SET CurrBalance = ? WHERE AccountID = ? ");
				
				
				//PostgreSQL Query
				//PreparedStatement pst71=db34.con.prepareStatement(" UPDATE \"Accounts\" SET \"CurrBalance\"= ? WHERE \"AccountID\"= ? ");
				pst71.setDouble(1, deeaccbal);
				pst71.setString(2, deaccid);
				int vv= pst71.executeUpdate();
				db34.con.commit();
				if(vv==0) {
					System.out.println("Failed to update Current Balance of Beneficiary Account");
					return -2;
				}
				
			}
			catch(Exception e) {
				
				
			}
			return 1;
		}
		
		protected int updateTransaction() {
			
			try {
				

				//Updating for Source Account
				DBHandler db35=new DBHandler();
				
				db35.con.setAutoCommit(false);
				
				
				//MySQL Query
				PreparedStatement pst73=db35.con.prepareStatement(" SELECT CurrBalance FROM Accounts WHERE AccountID = ? ");
				
				//PostgreSQL Query
				//PreparedStatement pst73=db35.con.prepareStatement(" SELECT \"CurrBalance\" FROM \"Accounts\" WHERE \"AccountID\"= ? ");
				pst73.setString(1, soaccid);
				ResultSet myRs37= pst73.executeQuery();
				myRs37.next();
				double sourcurrbal=myRs37.getDouble("CurrBalance");
				
				//Mysql query
				PreparedStatement pst72=db35.con.prepareStatement(" INSERT INTO Transactions (TransID,TransAmount,TransType,MoneyInFrom,MoneyOutTo,TransDate,CurrBalance,AccountID) VALUES (  ? , ? , ? , NULL , ? , ? , ? , ? ) ");
				
				//PostgreSQL Query
				//PreparedStatement pst72=db35.con.prepareStatement(" INSERT INTO \"Transactions\" (\"TransID\",\"TransAmount\",\"TransType\",\"MoneyInFrom\",\"MoneyOutTo\",\"TransDate\",\"CurrBalance\",\"AccountID\") VALUES (  ? , ? , ? , NULL , ? , ? , ? , ? ) ");
				pst72.setString(1,getTransid());
				pst72.setDouble(2, transamount);
				pst72.setString(3,String.valueOf(getTranstype()));
				
				pst72.setString(4,getdeaccid());
				pst72.setString(5,getDateTime());
				pst72.setDouble(6, sourcurrbal);
				pst72.setString(7, soaccid);
				int cv= pst72.executeUpdate();
				if(cv==0) {
					System.out.println(" Zero Rows Updated in Transactions Database");
				}
				
				
				
				//Updating For Destination Account
				
				//MySQL Query
				PreparedStatement pst74=db35.con.prepareStatement(" SELECT CurrBalance FROM Accounts WHERE AccountID = ? ");
				
				
				//PostgreSQL Query
				//PreparedStatement pst74=db35.con.prepareStatement(" SELECT \"CurrBalance\" FROM \"Accounts\" WHERE \"AccountID\"= ? ");
				pst74.setString(1, deaccid);
				ResultSet myRs38= pst74.executeQuery();
				myRs38.next();
				double destincurrbal=myRs38.getDouble("CurrBalance");
				
				
				//MySQL Query
				PreparedStatement pst75=db35.con.prepareStatement(" INSERT INTO Transactions (TransID,TransAmount,TransType,MoneyInFrom,MoneyOutTo,TransDate,CurrBalance,AccountID) VALUES (  ? , ? , ? , ? , NULL , ? , ? , ? ) ");
				
				//PostgreSQL Query
				//PreparedStatement pst75=db35.con.prepareStatement(" INSERT INTO \"Transactions\" (\"TransID\",\"TransAmount\",\"TransType\",\"MoneyInFrom\",\"MoneyOutTo\",\"TransDate\",\"CurrBalance\",\"AccountID\") VALUES (  ? , ? , ? , ? , NULL , ? , ? , ? ) ");
				String nTrns="R"+getTransid();
				pst75.setString(1,nTrns);
				pst75.setDouble(2, transamount);
				pst75.setString(3,String.valueOf(getTranstype()));
				pst75.setString(4,soaccid);
				
				pst75.setString(5,getDateTime());
				pst75.setDouble(6, destincurrbal);
				pst75.setString(7,getdeaccid() );
				int cvvv= pst75.executeUpdate();
				if(cvvv==0) {
					System.out.println(" Zero Rows Updated in Transactions Database for Beneficiary Account");
				}
				db35.con.commit();
				db35.con.close();
			}
			catch(Exception e) {
				System.out.println("Error Updating the Data into Transaction Database "+e);
			}
			System.out.println("Transaction Successfully Completed");
			return 1;
		}
/*		public String getSoaccid() {
			return soaccid;
		}

		public void setSoaccid(String soaccid) {
			this.soaccid = soaccid;
		}*/

	
		
		public String getdeaccid() {
			return this.deaccid;
		}
		public void setdeaccid() {
			
			
			boolean ccc=false;
			try {
				
				while(!ccc) {
							
							System.out.println("Please Enter Account ID in which you want to Transfer Money");
							 
							
							Scanner sc16=new Scanner(System.in);
							String gn=sc16.nextLine();
							if(gn.equals("quit")) {
								//sc16.close();
								return;
							}
							this.deaccid = gn;

							
							DBHandler db4=new DBHandler();
							
							//MySQL Query
							PreparedStatement pst9=db4.con.prepareStatement(" SELECT AccountID FROM Accounts WHERE AccountID = ? ");
							
							
							//PostgreSQL Query
							//PreparedStatement pst9=db4.con.prepareStatement(" SELECT \"AccountID\" FROM public.\"Accounts\" WHERE \"AccountID\"= ? ");
							pst9.setString(1, this.deaccid);
							ResultSet myRs5= pst9.executeQuery();
							ccc=myRs5.next();
							if(!ccc) {
							    System.out.println("Please Enter a valid Account ID !!!");
							    System.out.println("Press 'quit'  for Exiting");
							}
							else {
								 System.out.println("Validation of Beneficiary Account ID Successful");
							}
							//sc16.close();
						}
						
					}
					catch(Exception e) {
						System.out.println("Error while Validating the Beneficiary  Account ID "+e);
						
					}
			
			
			
			
			
			
			
			
			
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
			       String transid ="T"+lo.toString();
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
		public double getTransamount() {
			return transamount;
		}
		public void setTransamount() {
			System.out.println("Please Enter Transaction Amount");
			Scanner sc12=new Scanner(System.in);
			Double g=sc12.nextDouble();
			this.transamount = g;
			//sc12.close();

		}
		public TransactionType getTranstype() {
			return transtype;
		}
		public void setTranstype() {
			
		boolean b=true;
		TransactionType t=null;;
		
		do {

			try {
				System.out.println("Please Enter the Valid Transaction Type from below options: ");
				System.out.println("\n 1: NEFT \n 2: RTGS "); //1: UPI  \n 4: CHEQUE \n 5: CASH");
				
				
				System.out.println("Please Enter Transaction Type");
				Scanner sc14=new Scanner(System.in);
				String k=sc14.nextLine();
				 t=TransactionType.valueOf(k);
				b=false;
				//sc14.close();
			}
			catch(IllegalArgumentException e) {
				System.out.println("Wrong Transaction Type Entered !!!"+e);
		
			}
			
		}while(b);

			
		    switch(t) {
			
			case UPI:{
				this.transtype=TransactionType.valueOf("UPI");
				break;
			}
			case NEFT:{
				this.transtype=TransactionType.valueOf("NEFT");
				break;
			}
			case RTGS:{
				this.transtype=TransactionType.valueOf("RTGS");
				break;
			}
			case CHEQUE:{
				this.transtype=TransactionType.valueOf("CHEQUE");
				break;
			}
			case CASH:{
				this.transtype=TransactionType.valueOf("CASH");
				break;
			}
			default:{
				System.out.println("Please Enter a Valid Type for Transaction");
			}
			
			
			}
			this.transtype = t;
		}
		
		
		public String getDateTime() {
			return DateTime;
		}
		public void setDateTime() {
			//System.out.println("Setting Transaction Date and Time");
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			//System.out.println(formatter.format(date));
			//DateTime = dateTime;
			this.DateTime=formatter.format(date);
			
		}
		
	  void	getLast10Trans(){
			
		
		}
	  
	  String inpS(){
		  Scanner inpp=new Scanner (System.in);
		  String k=inpp.nextLine();
	      return k;
	  }
	  int inpI(){
		 boolean l=true;
		 int w=-456;
		 while(l) {
			
			 try {
				 Scanner inpp=new Scanner (System.in);
				   w=inpp.nextInt();
				  l=false;

			 }
			 catch(Exception e) {
				 System.out.println("Please Enter a proper Numeric Value "+e);
			 }
		 }
	      return w;
	  }
	  void menu() {
		  System.out.println("---------------------------------------------------------- Main Menu ----------------------------------------------------------  ");
		  System.out.println(" 1:   Deposit the amount \n 2:  Withdraw the amount");
		  System.out.println(" 3: Login if you already have an Account \n 4: SignUp for New Bank Account ");
		  System.out.println("Please press 11 to exit the application");
		  System.out.println("Please press the corresponding Number for selecting respective options");  
	  }
	  void Transapp() {
		 boolean we=true;
		  while(we) {
			  menu();
			  int q=inpI();
			  if(q==1) {
				  BankService k1=new BankService();
				  k1.deposit();
				 
			  }
			  else if(q==2) {
				  BankService k2=new BankService();
				  k2.withdraw();
			  }
			  else if(q==3) {
				  User u1=new User();
				 int s= u1.logIn();
				while(s==1) {
					 System.out.println("Please press 1 to Initiate a Transaction or 10 to Logout");
					 int p=inpI();  
					 if(p==1) {
						 Transaction t3=new Transaction();
						 t3.transact(p, u1.luname);
					 }
					 if(p==10) {
						 s=-10;
					 }
				}
			  }
			  else if(q==4) {
				  User u2=new User();
				  u2.signUp();
			  }
			  else if(q==11) {
				  System.out.println("Exiting the Application");
				  return;
			  }
			  else {
				  System.out.println("Please Enter a Valid Choice !!!");
				 menu();
			  }
			
		  }

	  }
	  
		
		
		
	}
	
	public static void main(String[] args)throws Exception {
		/*BankApp a1=new BankApp();
		//DBHandler d1=new DBHandler();
	//	User u1=new User();
	//	int v=u1.logIn();
		Transaction t=a1.new Transaction();
		t.transact(v,u1.luname);*/
		
		
		/*Transaction t=a1.new Transaction();
		t.setTransid();
		System.out.println(t.getTransid());
		*/
		
		
		
		/*Customer c1=a1.new Customer();
		c1.setCustDetails();
		c1.getCusDetails();*/
		
		
	/*	Transaction T1=a1.new Transaction();
		T1.setTransid();
		T1.setTransamount();
		T1.setTranstype();
		T1.setDateTime();
		
		System.out.println(T1.getTransid()+" "+
		T1.getTransamount()+"  "+
		T1.getTranstype()+"   "+
		T1.getDateTime() );

		/*Account A1=a1.new Account();
		
		A1.setAccid();
		A1.setAcctype();
		
		
         System.out.println( A1.getAccid()+"  "+
	    A1.getAcctype()+"  "+
	    A1.getCbalance());	*/
		
		/*BankService b=new BankService();
		b.withdraw();*/
		
		
	/*	BankService d=new BankService();
		d.deposit();
		*/
		

	    BankApp b1=new BankApp();
	    Transaction t1=b1.new Transaction();
	    Transaction t2=b1.new Transaction();
	    BankService B1=new BankService();
	    
	    
	  String Term="   ";
		while(! (Term.equals("Terminate"))) {
		  B1.bankserv();
		 t1.Transapp();
		 System.out.println(" please Type Terminate in order to exit the application or press any other key to continue using the Application");
		 Term= t1.inpS();		
		
		}
		
		System.out.println("Terminating the Bank Application ...........");
		
	}

}
