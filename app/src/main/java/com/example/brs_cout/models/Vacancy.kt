package com.example.brs_cout.models

data class Vacancy(
    val id: String, // ვაკანსიის უნიკალური ID
    val companyId: String, // კომპანიის ID, რომელმაც გამოაქვეყნა ვაკანსია (CompanyProfile-სთან დასაკავშირებლად)
    val jobTitle: String, // ვაკანსიის დასახელება (მაგ. "Android Developer", "Senior UX Designer")
    val jobDescription: String, // ვაკანსიის დეტალური აღწერა (პასუხისმგებლობები, მოთხოვნები)
    val location: String, // სამუშაო ადგილი (მაგ. "თბილისი", "რუსთავი", "Remote")
    val jobType: String, // სამუშაოს ტიპი (მაგ. "სრული განაკვეთი", "ნახევარი განაკვეთი", "ფრილანსერი", "სტაჟირება")
//    val employmentType: String?, // დასაქმების ტიპი (მაგ. "მუდმივი", "დროებითი", "კონტრაქტი")
//    val minSalary: Double?, // მინიმალური ხელფასი (არასავალდებულოა)
//    val maxSalary: Double?, // მაქსიმალური ხელფასი (არასავალდებულოა)
//    val salaryCurrency: String?, // ხელფასის ვალუტა (მაგ. "GEL", "USD", "EUR")
//    val experienceLevel: String?, // გამოცდილების დონე (მაგ. "Junior", "Mid", "Senior", "Lead")

    // ვაკანსიის მოთხოვნები (ტექნიკური უნარები)
    val requiredTechnicalSkills: List<ListItem.SkillItem>, // საჭირო ტექნიკური უნარები
    // ვაკანსიის მოთხოვნები (soft უნარები)
    val requiredSoftSkills: List<ListItem.SkillItem>,     // საჭირო soft უნარები
    // ენობრივი მოთხოვნები
    val requiredLanguages: List<ListItem.SkillItem>,      // საჭირო ენები (მაგ. ინგლისური, ქართული)

    val benefits: List<String>?, // კომპანიის მიერ შემოთავაზებული ბენეფიტები (მაგ. "ჯანმრთელობის დაზღვევა", "ფიტნესის აბონემენტი")
    val applicationDeadline: Long?, // განაცხადების მიღების ბოლო ვადა (Unix timestamp-ში, არასავალდებულოა)
//    val contactEmail: String?, // ელ. ფოსტა ვაკანსიასთან დაკავშირებით შეკითხვებისთვის
//    val contactPhone: String?, // ტელეფონის ნომერი ვაკანსიასთან დაკავშირებით შეკითხვებისთვის (არასავალდებულოა)
    val remoteOption: Boolean, // შესაძლებელია თუ არა დისტანციურად მუშაობა

    val postedDate: Long, // ვაკანსიის გამოქვეყნების თარიღი
    val isActive: Boolean // არის თუ არა ვაკანსია აქტიური
)