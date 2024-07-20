package com.zybooks.skillseekerapp;

public class Freelancer {
    private String freelancer_email;
    private String freelancer_exp;
    private String freelancer_name;
    //private String freelancer_password;
    private String freelancer_phone;
    private int star_review;

    public Freelancer() {}
    public  Freelancer(String freelancer_email, String freelancer_exp, String freelancer_name, String freelancer_phone, int star_review) {
    this.freelancer_email = freelancer_email;
    this.freelancer_exp = freelancer_exp;
    this.freelancer_name = freelancer_name;
    this.freelancer_phone = freelancer_phone;
    this.star_review = star_review;
    }
    public String getUser_Id(){return freelancer_email;}
    public void setUser_Id(String freelancer_email){this.freelancer_email = freelancer_email;}

    public String getFreelancer_exp(){return freelancer_exp;}
    public void setFreelancer_exp(String freelancer_exp){this.freelancer_exp = freelancer_exp;}

    public String getFreelancer_name(){return freelancer_name;}
    public void setFreelancer_name(String freelancer_name){this.freelancer_name = freelancer_name;}

    public String getFreelancer_email(){return freelancer_email;}
    public void setFreelancer_email(String freelancer_email){this.freelancer_email = freelancer_email;}

    public String getFreelancer_phone(){return freelancer_phone;}
    public void setFreelancer_phone(String freelancer_phone){this.freelancer_phone = freelancer_phone;}

    public int getStar_review(){return star_review;}
    public void setStar_review(int star_review){this.star_review = star_review;}




}
