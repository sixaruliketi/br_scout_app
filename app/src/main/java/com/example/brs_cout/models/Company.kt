package com.example.brs_cout.models

data class Company(
    val id: String? = null, // კომპანიის უნიკალური ID
    val companyName: String? = null, // კომპანიის სრული სახელი
    val registrationEmail: String? = null, // რეგისტრაციისთვის გამოყენებული ელ. ფოსტა (უნიკალური)
//    val websiteUrl: String?, // კომპანიის ვებსაიტის URL (არასავალდებულოა)
    val logoUrl: String? = null, // კომპანიის ლოგოს URL (არასავალდებულოა)
    val description: String? = null, // კომპანიის მოკლე აღწერა (რაზე მუშაობს, მისი ღირებულებები)
    val industry: String? = null, // ინდუსტრია (მაგ. IT, ფინანსები, ჯანდაცვა)
    val companySize: String? = null, // კომპანიის ზომა (მაგ. "1-10 თანამშრომელი", "100-500 თანამშრომელი")
    val address: String? = null, // სრული მისამართი (არასავალდებულოა)
//    val contactPersonName: String?, // საკონტაქტო პირის სახელი (HR/რეკრუტერი)
//    val contactPersonEmail: String?, // საკონტაქტო პირის ელ. ფოსტა
//    val contactPersonPhone: String?, // საკონტაქტო პირის ტელეფონის ნომერი (არასავალდებულოა)
    val foundedYear: Int? = null, // დაარსების წელი (არასავალდებულოა)
//    val socialMediaLinks: List<String>?, // სოციალური მედიის ლინკები (LinkedIn, Facebook, Twitter)

    // ვაკანსიების მართვისთვის (შესაძლოა ცალკე კოლექცია იყოს Firebase-ში)
     val postedJobIds: List<String>? = null, // კომპანიის მიერ გამოქვეყნებული ვაკანსიების ID-ები

//    val registrationDate: Long, // რეგისტრაციის თარიღი Unix timestamp-ში
//    val lastUpdated: Long // ბოლოს განახლების თარიღი
)