package Bank;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import Bank.BankApp.*;

public class User {

	String luname;
	int lin=0;
	
	protected int logIn() {
		
		
		boolean unr=false;
		boolean unl=false;
	  // String unn3;
		
	    
      	try {
			
			
		    do {
		    	
				System.out.println("Please Enter your Username");
				Scanner sc = new Scanner(System.in);
				String un=sc.nextLine();
				
				if(un.equals("quit")) {
					System.out.println("Exiting the Log In Section.........");
		      		lin=-2;
		      		return lin;
				}
				
				DBHandler db3=new DBHandler();
				
				//MySQL Query
				PreparedStatement pst5=db3.con.prepareStatement(" SELECT username FROM CustomerData WHERE username = ? ");
				
				
				//PostgreSQL Query
				//PreparedStatement pst5=db3.con.prepareStatement(" SELECT \"username\" FROM public.\"CustomerData\" WHERE \"username\"= ? ");
				pst5.setString(1, un);
				ResultSet myRs2= pst5.executeQuery();
				unr=myRs2.next();
				if(unr==true) unl=unr;
				if(!unr) {
					System.out.println("Entered User Name does not Exist "+"Please try again");
					System.out.println("Please Press \' quit \' if you want to exit");
				}
				this.luname=un;
	           
	            
	            //sc.close();
	           // db3.con.close();
	           
		    }while(!unl);	
		 
		}
		catch(Exception e) {
			
			System.out.println("Error Validating Username while Logging In "+e);
		}
      	
      	if(luname.equals("quit")) {
      		
      		System.out.println("Exiting the Log In Section.........");
      		lin=-2;
      		return lin;
      	}
			
	
			
			try {
				
					
                   // unn4=String.valueOf(unn3);		
				
					System.out.println("Please Enter your Password");
					Scanner sc19 = new Scanner(System.in);
					String pa=sc19.nextLine();
					
					
					
					
					DBHandler db3=new DBHandler();
					
					//MySQL Query
					PreparedStatement pst6=db3.con.prepareStatement(" SELECT password FROM CustomerData WHERE username = ? ");
					
					//PostgreSQL Query
					//PreparedStatement pst6=db3.con.prepareStatement(" SELECT \"password\" FROM \"CustomerData\" WHERE \"username\"= ? ");
					pst6.setString(1,luname);
					ResultSet myRs3= pst6.executeQuery();
					myRs3.next();
					String pass10=myRs3.getString("password");
				
					
					//System.out.println(pass10);
					
					while(!(pass10.equals(pa))) {
						
						if(!(pass10.equals(pa))) {
							System.out.println("Password Incorrect !!!");
							lin=-1;
							
								
						}
						
						else {
							System.out.println("Log In Successful !!!");
							lin=1;
							break;
							
						}
						
						System.out.println("Please Re-Enter your Password");
						Scanner sc17 = new Scanner(System.in);
						pa=sc17.nextLine();
						 if(!(pa.equals(pass10)))
						System.out.println("Press  'quit' for Exiting");
						if(pa.equals("quit")) {
							lin=-2;
							break;
						}
					       if(pa.equals(pass10)){
							System.out.println("Log In Successful !!!");
							lin=1;
							break;
							
						}
						
					}
					
					
					
					
				
			}
			catch(Exception e) {
				
				System.out.println("Error Validating Password during Log In   "+e);
			}
			
			
			lin=1;
			return lin;
		
		
	}
	int iiip;
	int iipI() {
		boolean g=true;
		
		while(g) {
			try {
				Scanner sc100=new Scanner(System.in);
				iiip= sc100.nextInt();
				g=false;
			}
			catch(Exception e) {
				System.out.println("Please Enter Numeric Value !!!"+e);
			}
		}
		return iiip;
		
		
	}
	protected int signUp(){
		int i=0;
		
		do {
			System.out.println("\n  \t \t \t \t*********** Welcome to SignUp Section ***********");
			System.out.println("Please Fill all the Required Details to Continue Signing Up");
			int j; 
			System.out.println("Please Press any  number except 11 to continue Signing Up");
			System.out.println("Please press 11 in order to Cancel SigningUp ");
			Scanner sc100=new Scanner(System.in);
			j=iipI();
			if(j==11) {
				System.out.println("Exiting the SignUp Section.......");
				return -456;
			};
			BankApp B1=new BankApp();
			Customer C1=B1.new Customer();
			C1.setCustDetails();
			
			System.out.println(" \n Please Check and Confirm all the  Filled Details");
			C1.getCusDetails();
		
			
			System.out.println("Please press 9 if all the details are correct or press 0 for again filling up the details");
			System.out.println("Please press 11 in order to Cancel SigningUp");
			Scanner sc1100=new Scanner(System.in);
			j=sc1100.nextInt();
			if(j==11) {
				System.out.println("Exiting the SignUp Section.......");
				return -456;
			};
		    if(j==9) {
		    	i=1;
		    	BankApp b100=new BankApp();
				Account a100=b100.new Account();
				a100.setAccid();
				a100.setAcctype();
				System.out.println(" Your New Account Number is: "+a100.getAccid());
				System.out.println("Your Customer ID is: "+ C1.getCustid());
				
				try {
					//Commiting Data into Database
					
					DBHandler db2=new DBHandler();
					db2.con.setAutoCommit(false);
					
					//MySQL Query
					PreparedStatement pst=db2.con.prepareStatement(" INSERT INTO CustomerData (CustID,fname,mname,lname,address,mobilenum,mailid,username,password,zipcode) VALUES (?,?,?,?,?,?,?,?,?,?) ");
					
					
					//PostgreSQL Query
					//PreparedStatement pst=db2.con.prepareStatement(" INSERT INTO \"CustomerData\" (\"CustID\",\"fname\",\"mname\",\"lname\",\"address\",\"mobilenum\",\"mailid\",\"username\",\"password\",\"zipcode\") VALUES (?,?,?,?,?,?,?,?,?,?) ");
					pst.setLong(1, C1.getCustid());
					pst.setString(2, C1.getFname());
					pst.setString(3, C1.getMname());
					pst.setString(4, C1.getLname());
					pst.setString(5, C1.getAddress());
					pst.setLong(6, C1.getMnum());
					pst.setString(7, C1.getMailid());
					pst.setString(8, C1.getUname());
					pst.setString(9, C1.getPassword());
					pst.setInt(10,C1.getZipcode());
					int v=pst.executeUpdate();
					db2.con.commit();
					
					//MySQL Query
					PreparedStatement pst1=db2.con.prepareStatement("INSERT INTO Accounts (AccountID,CustID,CurrBalance,AccountType) VALUES (?,?,?,?) ");
					
					//PostgreSQL Query
					//PreparedStatement pst1=db2.con.prepareStatement("INSERT INTO \"Accounts\" (\"AccountID\",\"CustID\",\"CurrBalance\",\"AccountType\") VALUES (?,?,?,?) ");
					pst1.setString(1, a100.getAccid());
					pst1.setLong(2, C1.getCustid());
					pst1.setDouble(3, 0.0);
					pst1.setString(4,a100.getAcctype().toString());
					int w=pst1.executeUpdate();
					db2.con.commit();
					db2.con.close();
					
				}
				catch(Exception e) {
					System.out.println("Error while commiting the SignUp Detail's Transaction in the Database" + e);
				}
		    }
		    
		}while(i==0);
		
		
		System.out.println(" You have successfully Signed Up for the Online Banking Application");
		System.out.println("\n \t \t \t \t \t   *********** Thank You for Choosing Bank of Airavat ***********");
		
		return i;
	
	}
	
	
	
	
	
	void BankServInfo() {
		System.out.println("Bank of Airavat Provides following Services");
	}
	
}
