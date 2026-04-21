# Project_WP2 : Foundite

Foundite is a community-driven web application designed to help people find lost items or report found belongings. 

The site allows users to post listings with photos and browse a gallery of items sorted by categories to quickly identify their property. The administrator oversees the platform to ensure safety and proper management of the listings.

## Team Members
* **Hugo CHI** (Team Leader)
* **Maxime AVOCAT**
* **Rayan ABOU EL NAY**
* **Soumeya HAMEUR**

## Documentation & Design
* **Wireframes (Figma):** [Link to Figma Design](https://www.figma.com/design/0XnkSfV7740reWY0Ylc8dh/Sans-titre?node-id=0-1&p=f&t=YPnSBWDgV5g3Plcn-0)

---

## User Stories

To define the core features of **Foundite**, we have established the following stories:

### 1. Reporting a Found Item (The "Finder" Story)
**As a** registered user,  
**I want to** upload a photo and a description of an item I found,  
**So that** the rightful owner can identify it and contact me.  
* *Key features:* Image upload, category selection, session management.

### 2. Searching for a Lost Item (The "Owner" Story)
**As a** visitor or registered user,  
**I want to** browse and filter the gallery of items by category or date,  
**So that** I can quickly find if someone has reported my lost belonging.  
* *Key features:* Search filters, responsive gallery (Bootstrap/Flexbox).

### 3. Moderation and Validation (The "Admin" Story)
**As an** administrator,  
**I want to** review, edit, or delete listings and manage user accounts,  
**So that** the platform remains clean, secure, and free of inappropriate content.  
* *Key features:* Admin dashboard, Spring Security (Role-based access), CRUD operations.
