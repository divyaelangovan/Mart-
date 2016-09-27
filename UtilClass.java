package com.niit.martzone.model;

public class UtilClass {
public String replace(String st,String ch1,String ch2)
{
	return st.replace(ch1,ch2);
}
public static void main(String args[])
{
	UtilClass u=new UtilClass();
	String string=u.replace(",Prd",",","");
	System.out.println(string);
}
}
