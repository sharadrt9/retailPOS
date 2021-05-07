import java.util.*;
import java.io.*;
class Data implements Serializable{
	
	public HashMap<String,String> admins=new HashMap<>();
	public HashMap<String,User> customers=new HashMap<>();
	public TreeMap<String, HashMap<Integer,Products>> categories=new TreeMap<>();
	public HashMap<String,HashMap<Integer,Products>> cart=new HashMap<>();
	public HashMap<String,HashMap<Integer,Products>> mail=new HashMap<>();
	public HashMap<String,String> rootAdmin=new HashMap<>();
}

class User implements Serializable{
	String password;
	String phnnum;
	String email;
	
	User(String password,String phnnum,String email){
		this.password=password;
		this.phnnum=phnnum;
		this.email=email;
	}
	public void setPassword(String password)
	{
		this.password=password;
	}
	
}

class Products implements Serializable{
	String name;
	private int quantity;
	private int price;
	private int discount;
	Products(Products pro, int quan)
	{
		this.name=pro.name;
		this.quantity=quan;
		this.discount=pro.discount;
		this.price=pro.price;
	}
	Products(String name, int price,int quantity,int discount){
		this.name=name;
		this.quantity=quantity;
		this.price=price;
		this.discount=discount;
	}
	public  String getName(){
		return name;
	}
	public  int getPrice(){
		return price;
	}
	public  int getQuan(){
		return quantity;
	}
	public int getDiscount(){
		return discount;
	}
	public String toString()
	{
		return getName()+" "+getPrice()+" "+getQuan();
	}
	public void setName(String name){
		this.name=name;
	}
	public void setPrice(int price){
		this.price=price;
	}
	public void setQuan(int quantity){
		this.quantity=quantity;
	}

}



class OnlineShopping{
	static Scanner sc;
    Data d = new Data();
	public String uname;
	public String catName;
	public String password;
	
	FileInputStream fis;
	ObjectInputStream ois;
	FileOutputStream fos;
	ObjectOutputStream oos;

	public static void main(String args[])throws Exception{
		
		sc=new Scanner(System.in);
		OnlineShopping t=new OnlineShopping();
		t.start();
		
		// sop(customers);
	}
	@SuppressWarnings("unchecked")
	public void start(){
		try{
			File f = new File("admin.txt");
			if(!f.exists()){
				f.createNewFile();
				d.rootAdmin.put("admin","admin");
				write();
			}
		}
		catch(Exception e){
			sop(e+"\n");
		}
		// try{
			// read();
		// }
		// catch(Exception e){
			
		// }
		
		while(true){
		System.out.println("1.Login\n2.register\n3.Exit\n");
		sop("Enter choice : ");
		int choice= sc.nextInt();
		switch(choice){
			case 1: login();
					break;
			case 2: register();
					break;
			case 3: return;
			default: sop("invalid option\n\n");
			
		}
	}
		
	}
	@SuppressWarnings("unchecked")
	public void login(){
		sc=new Scanner(System.in);
		read();
		// sop(d.admins);
		Console c= System.console();
		sop("\nEnter login details\n");
		while(true){
			System.out.print("\nUsername or exit : ");
			uname=sc.nextLine();
			if(uname.equals("exit"))
				break;
			char SecretString[]=c.readPassword("\nPassword");
			password= new String(SecretString);
			if(d.rootAdmin.containsKey(uname))
			{
				if(d.rootAdmin.containsValue(password))
				{
					startRootAdmin();
					break;
				}
			}
			else if(d.admins.containsKey(uname))
			{
				if(d.admins.containsValue(password))
				{
					startAdmin();
					break;
				}
			}
			else if(d.customers.containsKey(uname))
			{
				if(d.customers.get(uname).password.equals(password)){
				startCustomer(uname);					
					break;
				}
			}
			else
				sop("invalid login or password\n\n");
		}
		
	}
	@SuppressWarnings("unchecked")
	public void register(){
			sc=new Scanner(System.in);
			
			// String uname="";
			// String password="";
			while(true){
				sop("\tusername? or exit : ");
				uname=sc.nextLine();
				if(uname.equals("exit"))
					return;
				Console c= System.console();
				char SecretString[]=c.readPassword("\n\tPassword");
				password= new String(SecretString);
				// sop(uname + "" + password);
				sop("\n\tPhone number : ");
				String phnnum=sc.nextLine();
				sop("\n\tEmail : ");
				String email=sc.nextLine();
				
				if(uname.length()<1||password.length()<1||phnnum.length()<1||email.length()<1){
					sop("please enter all values");
					continue;
				}
				if(d.admins.containsKey(uname)||d.customers.containsKey(uname))
				{
					sop("username exists\n\n");
					continue;
				}
						
				else{
					d.customers.put(uname,new User(password,phnnum,email));
					write();
					sop("\n\tAdd successful\n\n");
				break;
				}
			}
				
	}		
	@SuppressWarnings("unchecked")
	public void startAdmin(){
		while(true){
			sop("\n\t1.Add customer\n\t2.Remove Customer\n\t3.View Customer\n\t4.Catalouge Management\n\t5.Logout\n");
			sop("\nEnter choice : ");
			int choice = sc.nextInt();
			switch(choice){
				case 1 : addCustomer();
						 break;
				case 2 : removeCustomer();
						 break;
				case 3 : viewCustomer();
						 break;
				case 4 : catalouge();
						 break;
				case 5 : return;
			}
		}
	}
	@SuppressWarnings("unchecked")
	public void startRootAdmin(){
		while(true){
			sop("\n\t1.Add Admins\n\t2.view Admins\n\t3.remove Admins\n\t4.view customer\n\t5.add customer\n\t6.remove customer\n\t7.Catalouge Management\n\t8.Logout\n\n");
			sop("\tEnter choice : ");
			int choice = sc.nextInt();
			sop("\n");
			switch(choice){
				case 1: addAdmins();
						break;
				case 2 : viewAdmins();
						break;
				case 3 : removeAdmins();
						break;
				case 4: viewCustomer();
						break;
				case 5: addCustomer();
						break;
				case 6: removeCustomer();
						break;
				case 7: catalouge();		
						break;
				case 8: return;
			}
			
		}
		
	}
	@SuppressWarnings("unchecked")
	public  void addAdmins(){
		sc=new Scanner(System.in);
		String password;
		while(true){
			sop("\n");
			sop("\n\tusername? or exit : ");
			uname=sc.nextLine();
			if(uname.equals("exit"))
				break;
			Console c= System.console();
			char SecretString[]=c.readPassword("\n\tPassword : ");
			password= new String(SecretString);
					// sop(uname + "" + password);
			if(uname.length()<1||password.length()<1){
				sop("\nplease enter a username and password\n");
				continue;
			}
			if(d.admins.containsKey(uname))
			{
				sop("\tadmin exists\n");
				continue;
			}
			
			else{
				d.admins.put(uname,password);
				// f1.delete();
				// f1.createNewFile();
				write();
				sop("\n");
				sop("\n\tAdd Successful\n\n");
				break;
			}
		}
	
	}
	@SuppressWarnings("unchecked")
	public  void viewAdmins(){
		read();
		Set<String> s= d.admins.keySet();
		int i=1;
		for(String name:s)
			sop("\t\t"+i++ +"."+name+"\n");
		sop("\n");
	}
	@SuppressWarnings("unchecked")
	public  void removeAdmins(){
		sc=new Scanner(System.in);
		viewAdmins();
		sop("\tEnter the admin id you want to remove : ");
		uname=sc.nextLine();
		if(!d.admins.containsKey(uname))
		{
			sop("admin with given id doesn't exist\n");
			return;
		}
		d.admins.remove(uname);
		write();
		sop("\tremoval successfull\n");
	}
	@SuppressWarnings("unchecked")
	public  void viewCustomer(){
		read();
		int i=1;
		if(d.customers.size()<1)
		{
			sop("\n\tNo customers\n");
			return;
		}
		Set<String> s= d.customers.keySet();
		for(String name:s){
			
			sop("\n\t\t"+name+"\t\t"+d.customers.get(name).phnnum+"\t\t"+d.customers.get(name).email+"\n");
			
		}
		
		
	}
	@SuppressWarnings("unchecked")
	public  void addCustomer(){
		register();
	}
	@SuppressWarnings("unchecked")
	public  void removeCustomer(){
		sc=new Scanner(System.in);
		while(true){
			viewCustomer();
			sop("\tEnter the customer id you want to remove or exit : ");
			uname=sc.nextLine();
			if(uname.equals("exit"))
				return;
			if(!d.customers.containsKey(uname))
			{	
				sop("\tUser doesn't exist\n");
				continue;
			}
			d.customers.remove(uname);
			write();
			sop("\tremoval successful\n\n");
		}
	}
	@SuppressWarnings("unchecked")
	public  void catalouge(){
		sc=new Scanner(System.in);
		while(true){
			sop("\n\t\t1.Category Management\n\t\t2.Products Management\n\t\t3.back\n");
			sop("\n\tEnter choice : ");
			int choice = sc.nextInt();
			switch(choice){
				
			case 1: catManage();
					break;
			case 2: productManage();
					break;
			case 3:	return;
			}
		}
	}
	@SuppressWarnings("unchecked")
	public  void catManage(){
		sc=new Scanner(System.in);
		while(true){
			sop("\n\t\t\t1.Add Category\n\t\t\t2.Remove Category\n\t\t\t3.View Category\n\t\t\t4.Back\n");
			sop("\n\tEnter choice : ");
			int choice = sc.nextInt();
			switch(choice){
				
			case 1: addCat();
					break;
			case 2: removeCat();
					break;
			case 3: viewCategory();
					break;
			case 4: return;
					
			}
		}
	}
	@SuppressWarnings("unchecked")
	public  void addCat(){
		sc=new Scanner(System.in);
		viewCategory();
		
			sop("\n\t\tEnter Category name : ");
			String catName=sc.nextLine();
			if(d.categories.containsKey(catName)){
				sop("\n\tcategory Exists");
				return;
			}
			d.categories.put(catName,new HashMap<>());
			write();
			sop("\n\t\tAdded Successful\n");
		
	}
	@SuppressWarnings("unchecked")
	public  void removeCat(){
		sc=new Scanner(System.in);
		viewCategory();
		
		sop("\n\tEnter Catefory name : ");
		String catName=sc.nextLine();
		if(!d.categories.containsKey(catName))
		{
			sop("\n\tcategory doesn;t exist\n");
			return;
		}
		d.categories.remove(catName);
		write();
		sop("\n\t Removed Successfully\n");
	}
	@SuppressWarnings("unchecked")
	public void viewCategory(){
		sc = new Scanner(System.in);
		read();
		if(d.categories.isEmpty())
		{
			sop("\n\tNo categories\n");
			return;
		}
		sop("\n\t\t------------\n");
		sop("\t\tCategories\n");
		sop("\t\t------------\n");
		Set<String> s1=d.categories.keySet();
		int i=1;
		for(String s : s1){
			
			sop("\t\t"+i++ + "." + s+"\n");
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public  void productManage(){
		sc=new Scanner(System.in);
		while(true){
			sop("\n\t\t\t1.Add product\n\t\t\t2.Remove product\n\t\t\t3.View products\n\t\t\t4.Back\n");
			sop("\n\tEnter choice : ");
			int choice = sc.nextInt();
			switch(choice){
				
			case 1: addProducts();
					break;
			case 2: removeProducts();
					break;
			case 3: viewProducts();
					break;
			case 4:	return;
					
			}
		}
	}
	@SuppressWarnings("unchecked")
	public  void addProducts(){
		sc=new Scanner(System.in);
		viewCategory();
		String input;
		try{
				
			sop("\n\tEnter Category name or back : ");
			input=sc.nextLine();
			if(input.equals("back"))
				return;
			if(!d.categories.containsKey(input))
			{
				sop("category doesn't exist");
				return;
			}
			catName=input;
			sop("\n\tEnter product details?\n");
			sop("\n\tenter product id : ");
			Integer id=sc.nextInt();
			sc.nextLine();
			if(d.categories.get(catName).containsKey(id))
			{
				sop("\n\tProduct exists\n");
				return;
			}
			sop("\n\t\tEnter product name : ");
			String prodName=sc.nextLine();
			sop("\n\t\tenter product price : ");
			int proPrice=sc.nextInt();
			sop("\n\t\tenter product quantity : ");
			int proQuan=sc.nextInt();
			sop("\n\t\tEnter the product discount : ");
			int proDisc=sc.nextInt();
			d.categories.get(catName).put(id,new Products(prodName,proPrice,proQuan,proDisc));
			write();
			sc.nextLine();
		}
		catch(Exception e){
			 System.out.println("Try again.");
			sc.nextLine();
		}
			// sop("-"+prodName+""+proPrice+""+proQuan);
	
	}
	@SuppressWarnings("unchecked")
	public  void removeProducts(){
		sc=new Scanner(System.in);
		viewProducts();
	
		// sop("\n\tEnter Category name");
		// String catName=sc.nextLine();
		
		sop("\n\tEnter product id");
		Integer id=sc.nextInt();
		d.categories.get(catName).remove(id);
		write();
		System.out.println("\n\tRemoval successful");
	}
	@SuppressWarnings("unchecked")
	public  boolean viewProducts(){
		sc=new Scanner(System.in);
		read();
		viewCategory();
		sop("\n\tEnter Category name : ");
		catName=sc.nextLine();
		// sop("enter id");
		// int id=sc.nextInt();
		// Set s=categories.entrySet();
		// sop(s);
		// categories.get(catName).get(id));
		 // sop(categories.get(catName));
		  if(!d.categories.containsKey(catName))
		 {
			 sop("\n\t\t***********There is no such category**************\n");
			 return false;
		 }
		 sop("\t\t------------------------------------------------------------------------------------------------");
		 sop("\n\t\tProd id\t\tprod Name\t\tpro price\t\tproduct quantity\t\tProduct Discount\n");
		 sop("\t\t------------------------------------------------------------------------------------------------\n");
		
		for(Map.Entry<Integer,Products> s : d.categories.get(catName).entrySet())
		{
			int i=s.getKey();
			Products pro=d.categories.get(catName).get(i);
			sop("\t\t"+i+"\t\t"+pro.getName()+"\t\t\t"+pro.getPrice()+"\t\t\t"+pro.getQuan()+"\t\t\t\t"+pro.getDiscount()+"\n");
		}
		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public void startCustomer(String name){
		sop("\n\thello "+name+"\n");
		
		if(!d.cart.containsKey(uname))
		{
			d.cart.put(uname,new HashMap<>());
			write();
		}
			
		while(true){
		sop("\n\t1.View Categories & products\n\t2.View Cart\n\t3.Change password\n\t4.Logout\n");
		sop("\n\tEnter choice : ");
		int choice=sc.nextInt();
		
			switch(choice){
				case 1 : viewCat();
						 break;
				case 2 : viewCart(name);
						 break;
				case 3 : changePass();
						 break;
				case 4 : return;
			}
		}
		
	}
	@SuppressWarnings("unchecked")
	public void changePass(){
		String prevPass;
		String presentPass,confirmPass;
		char SecretString[]=new char[50];	
		Console c=System.console();
		while(true){
			// sop("\n\t Enter previous password : ");
			SecretString =c.readPassword("\n\tPrevious Password");
			prevPass= new String(SecretString);
			if(!prevPass.equals(password))
			{
				sop("\n\t\twrong password!!\n");
				continue;
			}
		
			SecretString=c.readPassword("\n\tNew Password");
			presentPass= new String(SecretString);
			SecretString=c.readPassword("\n\tRe-enter Password");
			// confirmPass= new String(SecretString);
			if(presentPass.equals(new String(SecretString)))
			{	
				d.customers.get(uname).setPassword(presentPass);
				sop("\n\tPassword Changed Successfully\n");
				write();
				return;
			}
		}
	}
	@SuppressWarnings("unchecked")	
	public  void viewCat(){
		while(true){
			// sop("\n\t\t------------");
			// sop("\n\t\tCategories");
			// sop("\n\t\t------------\n");
			viewCategory();
			sop("\n");
			sop("\t\t-----------------------\n");
			sop("\t\t1.View Products\n\t\t2.back\n");
			sop("\n\t\t Enter a choice : ");
			int choice = sc.nextInt();
			switch(choice){
				case 1 : viewProd();
						 break;
				case 2 : return;
			}
		}
	}
	
	
	// public void viewMail()throws Exception{
		// Set s=mail.get(uname).entrySet();
		// for(String s1 : s)
		// {
			// sop(s);
		// }
		
		// for(Map.Entry<Integer,Products> s :	mail.get(uname).entrySet())
			// {
				// int i=s.getKey();
				// sop("i = "+ i);
				// Products pro=mail.get(uname).get(i);
				// sop("\n\t\t"+i+"\t"+pro.getName()+"\t"+pro.getPrice()+"\t"+pro.getQuan()+"\t"+pro.getDiscount()+"\n");
				// total+= pro.getQuan() * (pro.getPrice() - ((float)pro.getPrice()/(float)pro.getDiscount()));
			// }
	// }
	@SuppressWarnings("unchecked")
	public void viewProd(){
		
			boolean flag = viewProducts();
			if(!flag){
				return;
			}
				sop("\n\t\t1. Add to cart\n\t\t2. View Cart\n\t\t3.Back\n");
				sop("\n\tEnter choice : ");
				int choice = sc.nextInt();
				switch(choice){
					case 1 : addCart(uname);
							 break;
					case 2 : viewCart(uname);
							 break;
					
					case 3 : return;
					
				}
		
	}
	@SuppressWarnings("unchecked")
	public void addCart(String uname){
		int temp;
		sc=new Scanner(System.in);
		read();
		sop("\n\tEnter ID of product to add : ");
		int id = sc.nextInt();
		if(!d.categories.get(catName).containsKey(id))
		{
			sop("\n\t Product doesn't exist\n");
			return;
		}
		sop("\n\tEnter quantity : ");
		int quan = sc.nextInt();
		if(quan>d.categories.get(catName).get(id).getQuan())
			quan=d.categories.get(catName).get(id).getQuan();
		// if(quan<categories.get(catName).get(id).quantity)
		// {
			// sop("")
		// }
//		temp=categories.get(catName).get(id).quantity;
//		sop(temp+"\n");
//		categories.get(catName).get(id).quantity=quan;
		// sop(categories.get(catName).get(id).quantity+"\n");
		// sop(categories.get(catName).get(id).discount);
		// sop(categories.get(catName));
		d.cart.get(uname).put(id,new Products(d.categories.get(catName).get(id), quan));
		
		write();
		
		// sop(cart.get(uname).get(id).getQuan());
//		categories.get(catName).get(id).quantity=temp;
		// sop(categories.get(catName).get(id).quantity+"\n");
	}
	@SuppressWarnings("unchecked")
	public void viewCart(String uname){
		float total=0;
		sc=new Scanner(System.in);
		read();
		if(d.cart.isEmpty())
		{
			sop("n\tCart is empty\n");
			return;
		}
			sop("\n\t\t------------------------------------------------------------------------------------");
			sop("\n\t\tID\t\tName\t\tPrice\t\tquantity\t\tdiscount");
			sop("\n\t\t------------------------------------------------------------------------------------");
			for(Map.Entry<Integer,Products> s :	d.cart.get(uname).entrySet())
			{
				int i=s.getKey();
				Products pro=d.cart.get(uname).get(i);
				sop("\n\t\t"+i+"\t\t"+pro.getName()+"\t\t"+pro.getPrice()+"\t\t"+pro.getQuan()+"\t\t\t"+pro.getDiscount()+"\n");
				total+= pro.getQuan() * (pro.getPrice() - ((float)pro.getPrice()/(float)pro.getDiscount()));
			}
			sop("\n\tTOTAL : "+total+"\n");
			while(true){
			sop("\n\t1.Remove Product\n\t2.Checkout\n\t3.back\n");
			sop("\n\tEnter choice : ");
			int choice=sc.nextInt();
			
			switch(choice){
				case 1 : removeProduct();
						 break;
				case 2 : checkOut();
						 break;
				case 3 : return;
				
			}
		}
	}
	@SuppressWarnings("unchecked")
	public void removeProduct(){
		sc=new Scanner(System.in);
		read();
		if(d.cart.isEmpty()){
			sop("\n\tCart is empty\n");
			return;
		}
		sop("\n\tEnter ID of product to remove : ");
		int id = sc.nextInt();
		d.cart.get(uname).remove(id);
		write();
		sop("\n\tProduct remove successful\n");
	}
	@SuppressWarnings("unchecked")
	public void checkOut(){
		sc=new Scanner(System.in);
		read();
		int temp;
		int quantity;
		int quan;
		if(d.cart.get(uname).isEmpty()){
			sop("\n\tCart is empty\n");
			return;
		}
		
		sop("\n\t Do you want to checkout?Y/N : ");
		String choice = sc.nextLine();
		if(choice.equalsIgnoreCase("n"))
		{
			return;
		}
		for(Map.Entry<Integer,Products> s :	d.cart.get(uname).entrySet()){
			int i=s.getKey();
			quantity = d.categories.get(catName).get(i).getQuan();
			quan=d.cart.get(uname).get(i).getQuan();
			if(quantity<quan)
			{
				sop("Error! The product is out of stock or the store is having less stock than you requeste Pls try again!\n");
				d.cart.get(uname).remove(i);
				return;
			}
			// mail.put(uname,new HashMap<>(cart.get(uname)));
			d.categories.get(catName).get(i).setQuan(quantity-quan);
			if(d.categories.get(catName).get(i).getQuan()==0)
				d.categories.get(catName).remove(i);
			d.cart.get(uname).remove(i);
			 // sop(mail);
			 
		}	
		sop("\n\tcheckout successful\n");
		d.cart.get(uname).clear();
		
		write();
	}
	
	//Code simplification methods
	public static void sop(Object o){
		System.out.print(o);
	}
	private void write(){
		try{
			fos = new FileOutputStream("admin.txt");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(d);
			fos.close();
		}
		catch(Exception e){
			sop(e);
		}
	}
	
	private void read(){
		try{
			fis = new FileInputStream("admin.txt");
			ois = new ObjectInputStream(fis);
			d= (Data)ois.readObject();
			fis.close();
		}
		catch(Exception e){
			sop(e);
		}
		}
}