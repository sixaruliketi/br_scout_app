package com.example.brs_cout.models

data class Vacancy(
    val id: String? = null, // ვაკანსიის უნიკალური ID
    val companyId: String? = null, // კომპანიის ID, რომელმაც გამოაქვეყნა ვაკანსია (CompanyProfile-სთან დასაკავშირებლად)
    val jobTitle: String? = null, // ვაკანსიის დასახელება (მაგ. "Android Developer", "Senior UX Designer")
    val jobDescription: String? = null, // ვაკანსიის დეტალური აღწერა (პასუხისმგებლობები, მოთხოვნები)
    val location: String? = null, // სამუშაო ადგილი (მაგ. "თბილისი", "რუსთავი", "Remote")
//    val jobType: String? = null, // სამუშაოს ტიპი (მაგ. "სრული განაკვეთი", "ნახევარი განაკვეთი", "ფრილანსერი", "სტაჟირება")

    // ვაკანსიის მოთხოვნები (ტექნიკური უნარები)
    val requiredTechnicalSkills: List<ListItem.SkillItem>? = null, // საჭირო ტექნიკური უნარები
    // ვაკანსიის მოთხოვნები (soft უნარები)
    val requiredSoftSkills: List<ListItem.SkillItem>? = null,     // საჭირო soft უნარები
    // ენობრივი მოთხოვნები
    val requiredLanguages: List<ListItem.SkillItem>? = null,      // საჭირო ენები (მაგ. ინგლისური, ქართული)

//    val benefits: List<String>?, // კომპანიის მიერ შემოთავაზებული ბენეფიტები (მაგ. "ჯანმრთელობის დაზღვევა", "ფიტნესის აბონემენტი")
    val applicationDeadline: String? = null, // განაცხადების მიღების ბოლო ვადა (Unix timestamp-ში, არასავალდებულოა)
//    val remoteOption: Boolean, // შესაძლებელია თუ არა დისტანციურად მუშაობა

    val isActive: Boolean? = true // არის თუ არა ვაკანსია აქტიური
)