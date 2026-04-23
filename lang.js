const translations = {
    "th": {
        // --- หน้า Login & Home ---
        "login_header": "เข้าสู่ระบบ",
        "user_label": "ชื่อผู้ใช้งาน",
        "pass_label": "รหัสผ่าน",
        "btn_submit": "ส่ง",
        "lang_title": "เลือกภาษา / Select Language",
        "btn_back": "กลับ",

        // --- หน้า Main Menu ---
        "title_main": "จองโต๊ะ & สั่งอาหาร",
        "main_desc": "SUT Smart Life",
        "main_subtitle": "เลือกรายการที่คุณต้องการ",
        "btn_book": "จองโต๊ะ",
        "btn_order": "สั่งอาหาร",
        "btn_logout": "ออกจากระบบ",

        // --- หน้า Booking ---
        "booking_title": "จองโต๊ะ",
        "book_time_label": "⏳ เลือกระยะเวลาการใช้งาน:",
        "time_5": "5 นาที (แวะรับอาหาร)",
        "time_10": "10 นาที (ทานด่วน)",
        "time_15": "15 นาที (ทานปกติ)",
        "time_30": "30 นาที (มาตรฐาน)",
        "booked_status": "จองแล้ว",
        "alert_booked": "ขออภัยค่ะ โต๊ะนี้ถูกจองไปแล้ว",
        "alert_booking_success": "ระบบได้ส่งคำขอจองโต๊ะที่",
        "table_1": "โต๊ะที่ 1", "table_2": "โต๊ะที่ 2", "table_3": "โต๊ะที่ 3", "table_4": "โต๊ะที่ 4", "table_5": "โต๊ะที่ 5",
        "table_6": "โต๊ะที่ 6", "table_7": "โต๊ะที่ 7", "table_8": "โต๊ะที่ 8", "table_9": "โต๊ะที่ 9", "table_10": "โต๊ะที่ 10",

        // --- หน้า Canteen & Store ---
        "title_canteen": "โรงอาหาร",
        "canteen1_name": "โรงอาหารกาสะลองคำ", "canteen1_time": "05:00 - 21:00 น.",
        "canteen2_name": "โรงอาหารเรียนรวม 2", "canteen2_time": "07:00 - 16:00 น.<br>เปิดในวันธรรมดา",
        "canteen3_name": "โรงอาหารพราวแสดทอง", "canteen3_time": "07:00 - 18:00 น.<br>เปิดในวันธรรมดา",
        "canteen4_name": "โรงอาหารกลาง", "canteen4_time": "07:00 - 18:00 น.<br>เปิดในวันธรรมดา",
        "canteen5_name": "โรงเตี๊ยม มทส.", "canteen5_time": "07:00 - 18:00 น.<br>เปิดในวันธรรมดา",
        "store_title": "ร้านอาหาร",
        "store_a": "ร้าน A", "store_b": "ร้าน B", "store_c": "ร้าน C", "store_d": "ร้าน D",
        "status_open": "เปิดให้บริการ",
        "status_closed": "ปิดให้บริการ",

        // --- หน้า Menu & Order ---
        "menu_recom": "เมนูแนะนำ<br>สำหรับคุณ", "menu_all": "รายการอาหาร",
        "menu_krapao_kung": "ข้าวผัดกระเพรากุ้ง", "menu_krapao_kai": "ข้าวผัดกระเพราไก่", "menu_krapao_moo": "ข้าวผัดกระเพราหมู",
        "menu_krapao_muek": "ข้าวผัดกระเพราปลาหมึก", "menu_krapao_nuea": "ข้าวผัดกระเพราเนื้อ", "menu_krapao_hotdog": "ข้าวผัดกระเพราฮอทดอก",
        "price_55": "ราคา 55 บาท", "price_45": "ราคา 45 บาท", "price_40": "ราคา 40 บาท", "price_35": "ราคา 35 บาท",
        "order_summary": "สรุปคำสั่งซื้อ", "order_add": "เพิ่มท็อปปิ้ง", "addon_egg": "ไข่ดาว", "addon_omelet": "ไข่เจียว", "addon_rice": "ข้าวเปล่า",
        "price_5": "ราคา 5 บาท", "note_title": "หมายเหตุ", "note_place": "เช่น ไม่เผ็ด, ไม่ใส่ผัก", "total_text": "ราคารวม", "currency": "บาท", "btn_buy": "สั่งซื้อ",

        // --- หน้า Payment ---
        "pay_title": "ชำระเงิน", "pay_store_name": "ชื่อร้านอาหาร", "pay_upload_btn": "อัปโหลดหลักฐานการโอน",
        "pay_upload_done": "อัปโหลดสลิปเรียบร้อยแล้ว", "btn_confirm": "ยืนยันการชำระเงิน",
        "alert_no_slip": "กรุณาอัปโหลดสลิปโอนเงินก่อนยืนยันค่ะ", "alert_pay_success": "ชำระเงินสำเร็จ! กำลังส่งออเดอร์ไปที่ร้านค้า",

        // --- หน้า Status ---
        "status_title": "สถานะออเดอร์", "status_cooking": "กำลังปรุงอาหาร", "status_done": "เสร็จสิ้น",
        "status_wait": "กรุณารออาหารสักครู่ค่ะ...", "status_ready": "อาหารของคุณเสร็จเรียบร้อยแล้ว! เชิญมารับได้ที่หน้าร้านค่ะ",
        "alert_order_ready": "ออเดอร์ของคุณเสร็จเรียบร้อยแล้ว!", "btn_home": "กลับหน้าหลัก",
    },
    "en": {
        // --- หน้า Login & Home ---
        "login_header": "Login",
        "user_label": "Username",
        "pass_label": "Password",
        "btn_submit": "SUBMIT",
        "lang_title": "Select Language",
        "btn_back": "Back",

        // --- หน้า Main Menu ---
        "title_main": "Booking & Ordering",
        "main_desc": "SUT Smart Life",
        "main_subtitle": "Please select an option",
        "btn_book": "Book Table",
        "btn_order": "Order Food",
        "btn_logout": "Logout",

        // --- หน้า Booking ---
        "booking_title": "Book a Table",
        "book_time_label": "⏳ Select Duration:",
        "time_5": "5 mins (Pick up)",
        "time_10": "10 mins (Quick meal)",
        "time_15": "15 mins (Normal)",
        "time_30": "30 mins (Standard)",
        "booked_status": "Booked",
        "alert_booked": "Sorry, this table is already booked.",
        "alert_booking_success": "Booking request sent for Table",
        "table_1": "Table 1", "table_2": "Table 2", "table_3": "Table 3", "table_4": "Table 4", "table_5": "Table 5",
        "table_6": "Table 6", "table_7": "Table 7", "table_8": "Table 8", "table_9": "Table 9", "table_10": "Table 10",

        // --- หน้า Canteen & Store ---
        "title_canteen": "Canteen",
        "canteen1_name": "Kasalongkham Canteen", "canteen1_time": "05:00 AM - 09:00 PM",
        "canteen2_name": "Complex Canteen", "canteen2_time": "07:00 AM - 04:00 PM<br>Open on Weekdays",
        "canteen3_name": "Proud Saed Thong Canteen", "canteen3_time": "07:00 AM - 06:00 PM<br>Open on Weekdays",
        "canteen4_name": "Central Canteen", "canteen4_time": "07:00 AM - 06:00 PM<br>Open on Weekdays",
        "canteen5_name": "SUT Rong Tiam", "canteen5_time": "07:00 AM - 06:00 PM<br>Open on Weekdays",
        "store_title": "Stores",
        "store_a": "Store A", "store_b": "Store B", "store_c": "Store C", "store_d": "Store D",
        "status_open": "Open Now",      // แก้ไขจากภาษาไทย
        "status_closed": "Closed",     // แก้ไขจากภาษาไทย

        // --- หน้า Menu & Order ---
        "menu_recom": "Recommended<br>for you", "menu_all": "All Menus",
        "menu_krapao_kung": "Basil Fried Rice (Shrimp)", "menu_krapao_kai": "Basil Fried Rice (Chicken)", "menu_krapao_moo": "Basil Fried Rice (Pork)",
        "menu_krapao_muek": "Basil Fried Rice (Squid)", "menu_krapao_nuea": "Basil Fried Rice (Beef)", "menu_krapao_hotdog": "Basil Fried Rice (Hotdog)",
        "price_55": "Price 55 THB", "price_45": "Price 45 THB", "price_40": "Price 40 THB", "price_35": "Price 35 THB",
        "order_summary": "Order Summary", "order_add": "Add-ons", "addon_egg": "Fried Egg", "addon_omelet": "Omelet", "addon_rice": "Plain Rice",
        "price_5": "Price 5 THB", "note_title": "Note", "note_place": "e.g., Not spicy, No veggies", "total_text": "Total Price", "currency": "THB", "btn_buy": "Checkout",

        // --- หน้า Payment ---
        "pay_title": "Payment", "pay_store_name": "Store Name", "pay_upload_btn": "Upload Transfer Slip",
        "pay_upload_done": "Slip Uploaded Successfully", "btn_confirm": "Confirm Payment",
        "alert_no_slip": "Please upload the transfer slip before confirming.", "alert_pay_success": "Payment successful! Sending order to the store.",

        // --- หน้า Status ---
        "status_title": "Order Status", "status_cooking": "Cooking", "status_done": "Done",
        "status_wait": "Please wait a moment for your food...", "status_ready": "Your food is ready! Please pick it up at the store.",
        "alert_order_ready": "Your order is ready!", "btn_home": "Back to Home",
    }
};

function applyLanguage() {
    let currentLang = localStorage.getItem('userLang') || 'th';
    let elements = document.querySelectorAll('[data-lang]');

    elements.forEach(function (el) {
        let key = el.getAttribute('data-lang');
        if (translations[currentLang] && translations[currentLang][key]) {
            if (el.tagName === 'INPUT') {
                el.placeholder = translations[currentLang][key];
            } else {
                el.innerHTML = translations[currentLang][key];
            }
        }
    });
}

// เรียกใช้งานทันทีเมื่อโหลดหน้า
window.addEventListener('DOMContentLoaded', applyLanguage);