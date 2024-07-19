
package com.zybooks.skillseekerapp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SSDataBaseHelper {

    private FirebaseFirestore db;

    public SSDataBaseHelper() {
        db = FirebaseFirestore.getInstance();
    }

    public FirebaseFirestore getDb() {
        return db;
    }

    //for adding Users to the User Database
    public void addUser(String name, String phone_num, String age, String user_password, String user_email, int star_review) {
        Map<String, Object> user = new HashMap<>();
        //Columns
        user.put("name", name);
        user.put("phone_num", phone_num);
        user.put("age", age);
        user.put("user_password", user_password);
        user.put("user_email", user_email);
        user.put("star_review", star_review);

        db.collection("users")
                .add(user)
                .addOnSuccessListener(documentReference -> {
                    // Successfully added user
                })
                .addOnFailureListener(e -> {
                    // Failed to add user
                });
    }

    //for adding Freelancers to the User Database
    public void addFreelancer(String freelancer_name, String freelancer_exp, String freelancer_email, String freelancer_password, String freelancer_phonenumber, int star_review) {
        Map<String, Object> freelancer = new HashMap<>();
        //Columns
        freelancer.put("freelancer_name", freelancer_name);
        freelancer.put("freelancer_exp", freelancer_exp);
        freelancer.put("freelancer_email", freelancer_email);
        freelancer.put("freelancer_password", freelancer_password);
        freelancer.put("freelancer_phonenumber", freelancer_phonenumber);
        freelancer.put("star_review", star_review);
        //freelancer.put("review_stars", review_stars);

        db.collection("freelancers")
                .add(freelancer)
                .addOnSuccessListener(documentReference -> {
                    //Successfully added freelancer
                })
                .addOnFailureListener(e -> {
                    //Failed to add freelancer
                });
    }

    //Fetch Users
    public void fetchUsers(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        db.collection("users")
                .get()
                .addOnCompleteListener(onCompleteListener);
    }

    //Fetch Freelancers
    public void fetchFreelancers(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        db.collection("freelancers")
                .get()
                .addOnCompleteListener(onCompleteListener);
    }

    //Update User
    public void updateUser(String userId, String name, String phone_num, String age, String user_password, String user_email, int star_review) {
        Map<String, Object> updates = new HashMap<>();
        if (name != null) updates.put("name", name);
        if (phone_num != null) updates.put("phone_num", phone_num);
        if (age != null) updates.put("age", age);
        if (user_password != null) updates.put("user_password", user_password);
        if (user_email != null) updates.put("user_email", user_email);
        if (star_review != 0) updates.put("star_review", star_review);

        db.collection("users").document(userId)
                .update(updates)
                .addOnSuccessListener(aVoid -> {
                    //Successfully updated user
                })
                .addOnFailureListener(e -> {
                    //Failed to update user
                });
    }

    //Update Freelancer
    public void updateFreelancer(String freelancerId, String freelancer_name, String freelancer_exp, String freelancer_email, String freelancer_password, String freelancer_phonenumber, int star_review) {
        Map<String, Object> updates = new HashMap<>();
        if (freelancer_name != null) updates.put("freelancer_name", freelancer_name);
        if (freelancer_exp != null) updates.put("freelancer_exp", freelancer_exp);
        if (freelancer_email != null) updates.put("freelancer_email", freelancer_email);
        if (freelancer_password != null) updates.put("freelancer_password", freelancer_password);
        if (freelancer_phonenumber != null) updates.put("freelancer_phonenumber", freelancer_phonenumber);
        if(star_review != 0) updates.put("star_review", star_review);

        db.collection("freelancers").document(freelancerId)
                .update(updates)
                .addOnSuccessListener(aVoid -> {
                    //Successfully updated freelancer
                })
                .addOnFailureListener(e -> {
                    //Failed to update freelancer
                });
    }

    public void fetchUserList() {
        fetchUsers(task -> {
            if (task.isSuccessful()) {
                ArrayList<ModalUser> userList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    ModalUser user = new ModalUser();
                    user.user_id = document.getId(); // Firebase uses document IDs
                    user.name = document.getString("name");
                    user.phone_num = document.getString("phone_num");
                    user.age = document.getString("age");
                    user.user_password = document.getString("user_password");
                    user.user_email = document.getString("user_email");
                    user.star_review = document.getLong("star_review").intValue();
                    userList.add(user);
                }
                //Use userList
            } else {
                //Fails
            }
        });
    }

    public void fetchFreelancerList() {
        fetchFreelancers(task -> {
            if (task.isSuccessful()) {
                ArrayList<ModalFreelancer> freelancerList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    ModalFreelancer freelancer = new ModalFreelancer();
                    freelancer.freelancer_id = document.getId(); // Firebase uses document IDs
                    freelancer.name = document.getString("freelancer_name");
                    freelancer.experience = document.getString("freelancer_exp");
                    freelancer.freelancer_email = document.getString("freelancer_email");
                    freelancer.freelancerPassword = document.getString("freelancer_password");
                    freelancer.freelancer_phone = document.getString("freelancer_phonenumber");
                    freelancer.star_review = document.getLong("star_review").intValue();
                    //freelancer.starReview = document.getString("review_stars");
                    freelancerList.add(freelancer);
                }
                //Use freelancerList
            } else {
                //Fails
            }
        });
    }

    public void addJob(String userId, String name, String email, String phone, String jobCategory,String jobTitle, String date, String city, String description) {
        Map<String, Object> job = new HashMap<>();
        //Columns
        job.put("user_id", userId);
        job.put("name", name);
        job.put("email", email);
        job.put("phone", phone);
        job.put("job_category", jobCategory);
        job.put("job_title", jobTitle);
        job.put("date", date);
        job.put("city", city);
        job.put("description", description);

        db.collection("jobs")
                .add(job)
                .addOnSuccessListener(documentReference -> {
                    //Successfully added job
                })
                .addOnFailureListener(e -> {
                    //Failed to add job
                });
    }


}

