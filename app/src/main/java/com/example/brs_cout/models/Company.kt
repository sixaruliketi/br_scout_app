package com.example.brs_cout.models

data class Company(
    val id: String, // კომპანიის უნიკალური ID
    val companyName: String, // კომპანიის სრული სახელი
    val registrationEmail: String, // რეგისტრაციისთვის გამოყენებული ელ. ფოსტა (უნიკალური)
//    val websiteUrl: String?, // კომპანიის ვებსაიტის URL (არასავალდებულოა)
    val logoUrl: String?, // კომპანიის ლოგოს URL (არასავალდებულოა)
    val description: String?, // კომპანიის მოკლე აღწერა (რაზე მუშაობს, მისი ღირებულებები)
//    val industry: String?, // ინდუსტრია (მაგ. IT, ფინანსები, ჯანდაცვა)
    val companySize: String?, // კომპანიის ზომა (მაგ. "1-10 თანამშრომელი", "100-500 თანამშრომელი")
    val address: String?, // სრული მისამართი (არასავალდებულოა)
//    val contactPersonName: String?, // საკონტაქტო პირის სახელი (HR/რეკრუტერი)
//    val contactPersonEmail: String?, // საკონტაქტო პირის ელ. ფოსტა
//    val contactPersonPhone: String?, // საკონტაქტო პირის ტელეფონის ნომერი (არასავალდებულოა)
    val foundedYear: Int?, // დაარსების წელი (არასავალდებულოა)
//    val socialMediaLinks: List<String>?, // სოციალური მედიის ლინკები (LinkedIn, Facebook, Twitter)

    // ვაკანსიების მართვისთვის (შესაძლოა ცალკე კოლექცია იყოს Firebase-ში)
     val postedJobIds: List<String>, // კომპანიის მიერ გამოქვეყნებული ვაკანსიების ID-ები

//    val registrationDate: Long, // რეგისტრაციის თარიღი Unix timestamp-ში
//    val lastUpdated: Long // ბოლოს განახლების თარიღი
)