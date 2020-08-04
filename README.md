
# Java Spring Boot Project #

此網頁專題為一寵物病歷與獸醫院掛號系統(飼主前台、醫院後台)  
***我於此專題負責後端部分，前台與後台的前端程式則由隊員負責***  
目的為滿足診所與飼主雙方

* 飼主:
  * 透明的線上病歷系統能查詢家中毛小孩上次就診紀錄
  * 能對支援此系統的獸醫診所進行掛號
* 院方:  
  * 管理院方人員帳號
  * 管理醫生班表讓飼主預約
  * 管理寵物病歷

---------------------------------------

# 技術介紹 #

## 後端使用到的技術 ##

* Java
* Spring Boot + JPA(Hibernate)
* GCP (Compute Engine)
* MySQL

## 後端程式設計 ##

功能或細節:  

1. 可註冊會員  
2. 可上傳圖片  
3. JWT token 機制(沒權限的 token 會被限制行為)

機制實作:

* 設計上採 MVC 分離，將存取 DB 相關的邏輯與商業邏輯隔離開
* 實作 service 層而使 controller 層邏輯更加單純且單一，分工上更明確、便於後續維護
* 實作[分頁機制(pagination)](https://github.com/wolke1007/cmoney_final_project/blob/master/src/main/java/com/cmoney_training_6th/final_project_intellij/controller/admin/AdminMedicalItemController.java)，請參考 getPageMedcalItems 的 API

---------------------------------------

***此專案中使用之源自網路所有圖片皆為該原創作者擁有  
本專案僅供教學、測試使用並無任何營利，仍如有侵權還請告知撤除***
