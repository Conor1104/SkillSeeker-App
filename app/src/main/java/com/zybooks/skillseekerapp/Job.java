package com.zybooks.skillseekerapp;

public class Job {
    private String user_id;
    private String name;
    private String email;
    private String phone;
    private String job_category;
    private String job_title;
    private String date;
    private String city;
    private String description;
    private String posterUserId;
    private int star_review;

    private String freelancer_email;
    private String freelancer_exp;
    private String freelancer_name;
    //private String freelancer_password;
    private String freelancer_phone;
    //private int star_review;

    // Default constructor required for Firestore
    public Job() {}

    public Job(String user_id, String name, String email, String phone, String job_category, String job_title, String date, String city, String description, String posterUserId, int star_review) {
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.job_category = job_category;
        this.job_title = job_title;
        this.date = date;
        this.city = city;
        this.description = description;
        this.posterUserId = posterUserId;
        this.star_review = star_review;
        this.freelancer_email = freelancer_email;
        this.freelancer_exp = freelancer_exp;
        this.freelancer_name = freelancer_name;
        //this.freelancer_password = freelancer_password;
        this.freelancer_phone = freelancer_phone;
    }

    // Getters and Setters
    public String getUser_Id() {
        return user_id;
    }

    public void setUser_Id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJob_category() {
        return job_category;
    }

    public void setJob_category(String job_category) {
        this.job_category = job_category;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterUserId() {
        return posterUserId;
    }

    public void setPosterUserId(String posterUserId) {
        this.posterUserId = posterUserId;
    }

    public int getStarReview() {
        return star_review;
    }

    public void setStarReview(int starReview) {
        this.star_review = star_review;
    }

    //public String getUser_Id(){return freelancer_email;}
    //public void setUser_Id(String freelancer_email){this.freelancer_email = freelancer_email;}

    public String getFreelancer_exp(){return freelancer_exp;}
    public void setFreelancer_exp(String freelancer_exp){this.freelancer_exp = freelancer_exp;}

    public String getFreelancer_name(){return freelancer_name;}
    public void setFreelancer_name(String freelancer_name){this.freelancer_name = freelancer_name;}

    public String getFreelancer_email(){return freelancer_email;}
    public void setFreelancer_email(String freelancer_email){this.freelancer_email = freelancer_email;}

    public String getFreelancer_phone(){return freelancer_phone;}
    public void setFreelancer_phone(String freelancer_phone){this.freelancer_phone = freelancer_phone;}

    public int get_Star_review(){return star_review;}
    public void setStar_review(int star_review){this.star_review = star_review;}

}